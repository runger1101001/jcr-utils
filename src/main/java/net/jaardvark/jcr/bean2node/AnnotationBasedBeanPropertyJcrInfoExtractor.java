package net.jaardvark.jcr.bean2node;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jcr.PropertyType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationBasedBeanPropertyJcrInfoExtractor implements BeanPropertyJcrInfoExtractor {

	
	public static final Logger log = LoggerFactory.getLogger(AnnotationBasedBeanPropertyJcrInfoExtractor.class);
	

	public static final List<Class<?>> propertyTypes = Arrays.asList(
				CharSequence.class, Number.class, BigDecimal.class,
				Enum.class, Boolean.class,
				URL.class, URI.class,
				Date.class, Instant.class,
				boolean.class,
				byte.class, short.class, int.class, long.class, double.class, float.class,
				byte[].class, char[].class
			);
	
	public static final List<Class<? extends Annotation>> annotations = Arrays.asList(JcrType.class, JcrProperty.class, JcrNode.class);
	
	
	
	@Override
	public BeanPropertyJcrInfos extract(Object bean, PropertyDescriptor beanProperty) {
		BeanPropertyJcrInfos result = new BeanPropertyJcrInfos();
		log.trace("Extracting infos for bean property "+beanProperty.getName());
		
		Class<?> propType = beanProperty.getPropertyType();
		Method getter = beanProperty.getReadMethod();
		Field field = null;
		Class<?> currClass = bean.getClass();
		do {
			try {
				field = currClass.getDeclaredField(beanProperty.getName());
			} catch (NoSuchFieldException | SecurityException e) {
				// no field for property
			}
			currClass = currClass.getSuperclass();
		} while (field==null && currClass!=null && !currClass.equals(Object.class));
		
		// ignore properties of Object
		if (getter!=null && getter.getDeclaringClass().equals(Object.class)){
			result.includeAs = IncludeAs.EXCLUDE;
			return result;			
		}
		
		// ignore transient fields
		if (field !=null && Modifier.isTransient(field.getModifiers())){
			result.includeAs = IncludeAs.EXCLUDE;
			return result;
		}		
		// ignore transient getters
		if (getter!=null && Modifier.isTransient(getter.getModifiers())) {
			result.includeAs = IncludeAs.EXCLUDE;
			return result;
		}
		
		// get annotations for getter and field
		List<Annotation> propAnns = new ArrayList<>();
		if (getter!=null)
			propAnns.addAll(Arrays.asList(getter.getAnnotations()));
		if (field!=null)
			propAnns.addAll(Arrays.asList(field.getAnnotations()));
		List<Annotation> typeAnns = Arrays.asList(propType.getAnnotations());
		
		// check for nodes
		JcrNode nodeAnn = firstInstanceOf(propAnns,JcrNode.class);
		JcrType typeAnn = firstInstanceOf(propAnns,JcrType.class);
		if (typeAnn==null)
			typeAnn = firstInstanceOf(typeAnns,JcrType.class);
		if (nodeAnn!=null || typeAnn!=null) {
			getNodeInfos(bean, beanProperty, propType, getter, field, nodeAnn, typeAnn, result);
			return result;
		}
		
		// unwrap arrays
		Class<?> checkType = propType;
		if (propType.isArray())
			checkType = propType.getComponentType();
		else if (Collection.class.isAssignableFrom(propType)){
			if (getter!=null){
				Type type = ((ParameterizedType)getter.getGenericReturnType()).getActualTypeArguments()[0];
				if (type instanceof Class)
					checkType = (Class<?>)type;				
			}
			else if (field!=null){
				Type type = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
				if (type instanceof Class)
					checkType = (Class<?>)type;
			}
		}
		
		// check for JcrProperty annotations
		JcrProperty propAnn = firstInstanceOf(propAnns, JcrProperty.class);
		if (propAnn!=null){
			getPropertyInfos(bean, beanProperty, propType, checkType, getter, field, propAnn, result);
			return result;
		}
		
		
		// check for automatically mapped properties
		for (Class<?> clazz : propertyTypes){
			if (clazz.isAssignableFrom(checkType)){
				getPropertyInfos(bean, beanProperty, propType, checkType, getter, field, null, result);
				return result;
			}
		}
		
		// otherwise we don't know how to handle this type of object...
		log.warn("Don't know how to serialize objects of type: "+propType.getName()+" at "+bean.getClass().getName()+"."+beanProperty.getName());
		
		result.includeAs = IncludeAs.EXCLUDE;
		return result;
	}



	@SuppressWarnings("unchecked")
	protected <T> T firstInstanceOf(List<Annotation> list, Class<T> annotationType) {
		return (T) list.stream().filter(a->a.annotationType().equals(annotationType)).findFirst().orElse(null);
	}



	protected void getPropertyInfos(Object bean, PropertyDescriptor beanProperty, Class<?> propType, Class<?> checkType, Method getter, Field field, JcrProperty propAnn, BeanPropertyJcrInfos result) {
		result.includeAs = IncludeAs.PROPERTY;
		
		if (checkType!=propType){ // yes, != comparison is what we really want here
			result.multiple = true;
		}
		else
			result.multiple = false;
		if (propAnn!=null && StringUtils.isNotBlank(propAnn.name()))
			result.name = propAnn.name();
		if (result.name==null)
			result.name = beanProperty.getName();
		if (propAnn!=null)
			result.propertyType = propAnn.propertyType();
		if (result.propertyType<1)
			result.propertyType = determinePropertyType(checkType);
	}


	
	/**
	 *  java.util.Date, java.time.Instant --> DATE
	 *  String, char[] --> STRING
	 *  BigDecimal --> DECIMAL
	 *  Double, double, Float, float --> DOUBLE
	 *  Long, long, Integer, int, Short, short, byte --> LONG
	 *  byte[] --> BINARY
	 *  URI, URL --> URI
	 *  Boolean, boolean --> BOOLEAN
	 *  Enum<?> --> STRING (by name)
	 *  ? --> STRING (using toString)
	 * @param clazz input bean property type
	 * @return output jcr property type
	 */
	protected int determinePropertyType(Class<?> clazz) {
		if (CharSequence.class.isAssignableFrom(clazz) || char[].class.isAssignableFrom(clazz)
				|| Enum.class.isAssignableFrom(clazz))
			return PropertyType.STRING;
		if (Double.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz))
			return PropertyType.DOUBLE;
		if (Long.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)
				|| Short.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz))
			return PropertyType.LONG;
		if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz))
			return PropertyType.BOOLEAN;
		if (Date.class.isAssignableFrom(clazz) || Instant.class.isAssignableFrom(clazz))
			return PropertyType.DATE;
		if (byte[].class.isAssignableFrom(clazz))
			return PropertyType.BINARY;
		if (BigDecimal.class.isAssignableFrom(clazz))
			return PropertyType.DECIMAL;
		if (URI.class.isAssignableFrom(clazz) || URL.class.isAssignableFrom(clazz))
			return PropertyType.URI;
		return PropertyType.UNDEFINED;
	}

	


	protected void getNodeInfos(Object bean, PropertyDescriptor beanProperty, Class<?> propType, Method getter,
			Field field, JcrNode nodeAnn, JcrType typeAnn, BeanPropertyJcrInfos result) {
		
		JcrNode jcrNodeGetter = getter.getAnnotation(JcrNode.class);
		JcrNode jcrNodeField = field.getAnnotation(JcrNode.class);
		if (jcrNodeGetter!=null && jcrNodeField!=null)
			throw new IllegalStateException("Both getter and field contained JcrNode annotations for "+bean.getClass()+"."+beanProperty.getName());
		
		// set node name
		if (jcrNodeGetter!=null && StringUtils.isNotBlank(jcrNodeGetter.name()))
			result.name = jcrNodeGetter.name();
		else if (jcrNodeField!=null && StringUtils.isNotBlank(jcrNodeField.name()))
			result.name = jcrNodeField.name();
		else
			result.name = beanProperty.getName();
		
		// set node primary type
		JcrType jcrType = getter.getAnnotation(JcrType.class);
		if (jcrType!=null && field.getAnnotation(JcrType.class)!=null)
			throw new IllegalStateException("Both getter and field contained JcrType annotations for "+bean.getClass()+"."+beanProperty.getName());
		else if (jcrType==null)
			jcrType = field.getAnnotation(JcrType.class);
		if (jcrType==null)
			jcrType = propType.getAnnotation(JcrType.class);
		if (jcrType!=null && StringUtils.isNotBlank(jcrType.primaryType()))
			result.nodePrimaryType = jcrType.primaryType();
		else
			result.nodePrimaryType = "";
		result.includeAs = IncludeAs.NODE;
	}

}
