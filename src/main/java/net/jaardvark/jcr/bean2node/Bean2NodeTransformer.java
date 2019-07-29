package net.jaardvark.jcr.bean2node;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jcr.Binary;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jaardvark.jcr.bean2node.BeanPropertyJcrInfoExtractor.BeanPropertyJcrInfos;
import net.jaardvark.jcr.bean2node.BeanPropertyJcrInfoExtractor.IncludeAs;


public class Bean2NodeTransformer {
	
	public static final Logger log = LoggerFactory.getLogger(Bean2NodeTransformer.class);
	
	protected BeanPropertyJcrInfoExtractor extractor;

	protected String defaultType = "nt:unstructured";
	
	public Bean2NodeTransformer(){
		extractor = new AnnotationBasedBeanPropertyJcrInfoExtractor();
	}
	public Bean2NodeTransformer(BeanPropertyJcrInfoExtractor extractor){
		this.extractor = extractor;
	}	
    
	public <T> Node bean2Node(T bean, String nodeName, Node parent) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, RepositoryException, IOException {
	    long start = System.currentTimeMillis();
	    log.trace("Beginning bean2node for "+bean.getClass().getName()+" to "+parent.getPath()+"/"+nodeName);
		ValueFactory valF = parent.getSession().getValueFactory();
		Node r = bean2Node(bean, nodeName, parent, null, valF);
		log.trace("Beginning bean2node for "+bean.getClass().getName()+" to "+parent.getPath()+"/"+nodeName+" in "+(System.currentTimeMillis()-start)+"ms.");
		return r;
	}
    
    protected <T> Node bean2Node(T bean, String nodeName, Node parent, BeanPropertyJcrInfos jcrInfos, ValueFactory valF) throws IntrospectionException, ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
        if (bean==null){
        	if (parent.hasNode(nodeName))
        		parent.getNode(nodeName).remove();
        	return null;
        }
        Class<?> clazz = bean.getClass();
    	Node n = getOrCreateNodeFor(clazz, nodeName, parent, jcrInfos);
    	
		// handle arrays and collections
    	if (clazz.isArray()){
    		long numInitial = countChildren(n);
    		int i=0;
    		for (Object o : (Object[])bean)
    			bean2Node(o, String.format("%03d", i++), n, null, valF);
    		while (i<numInitial){
    			String nn = String.format("%03d", i++);
    			if (n.hasNode(nn))
    				n.getNode(nn).remove();
    		}
    	}
    	else if (Collection.class.isAssignableFrom(clazz)){
    		long numInitial = countChildren(n);
    		int i=0;
    		for (Object o : (Collection<?>)bean)
    			bean2Node(o, String.format("%03d", i++), n, null, valF);
    		while (i<numInitial){
    			String nn = String.format("%03d", i++);
    			if (n.hasNode(nn))
    				n.getNode(nn).remove();
    		}
    	}
    	else{ // handle other objects
	        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
	        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
	        for (PropertyDescriptor p : properties){
	        	BeanPropertyJcrInfos infos = extractor.extract(bean, p);
	            if (infos.includeAs==IncludeAs.NODE){
	        		Object subBean = p.getReadMethod().invoke(bean);
	        		bean2Node(subBean, infos.name, n, null, valF); // recurse
	            }
	            else if (infos.includeAs==IncludeAs.PROPERTY){
	        		createPropertyFor(bean, p, n, infos, valF);            	
	            }
	        }
    	}
        return n;
    }

 


    protected long countChildren(Node n) throws RepositoryException {
    	int i = 0;
		if (n!=null){
			NodeIterator ni = n.getNodes();
			return ni.getSize();
		}
		return i;
	}
    
    
    
	protected <T> Property createPropertyFor(T bean, PropertyDescriptor p, Node parent, BeanPropertyJcrInfos infos, ValueFactory valF) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
    	Object value = p.getReadMethod().invoke(bean);
    	if (value==null) {
    		if (parent.hasProperty(infos.name))
    			parent.getProperty(infos.name).remove();
    		return null;
    	}
    	log.trace("Creating property "+infos.name+" in node "+parent.getPath()+" for "+bean.getClass().getName());
    	if (infos.multiple)
        	return parent.setProperty(infos.name, getValues(infos.propertyType, value, valF), infos.propertyType);    	
    	else
    		return parent.setProperty(infos.name, getValue(infos.propertyType, value, valF), infos.propertyType);    	
    }
    

	protected Value[] getValues(int propType, Object value, ValueFactory valF) throws IOException, RepositoryException {
		if (value.getClass().isArray()){
			return Arrays.stream((Object[])value)
						 .map(o->getValue(propType, o, valF))
						 .toArray(Value[]::new);
		}
		else if (Collection.class.isAssignableFrom(value.getClass())){
			return ((Collection<?>) value).stream()
			 .map(o->getValue(propType, o, valF))
			 .toArray(Value[]::new);
		}
		else
			throw new IllegalStateException("Don't know how to unwrap values of type "+value.getClass().getName());			
	}
    
	protected Value getValue(int propType, Object value, ValueFactory valF) {
		switch (propType){
			case PropertyType.BINARY:
				try {
					Binary bin = valF.createBinary(new ByteArrayInputStream((byte[])value));
					return valF.createValue(bin);
				} catch (RepositoryException e) {
					throw new RuntimeException(e);
				}
			case PropertyType.BOOLEAN:
				return valF.createValue((Boolean)value);
			case PropertyType.DATE:
				if (value instanceof Date){
					GregorianCalendar c = new GregorianCalendar();c.setTime((Date) value);
					return valF.createValue(c);
				}
                else if (value instanceof Calendar)
                    return valF.createValue((Calendar)value);
                else if (value instanceof Instant){
                    ZonedDateTime zdt = ZonedDateTime.ofInstant((Instant)value, ZoneId.systemDefault());
                    Calendar cal1 = GregorianCalendar.from(zdt);                    
                    return valF.createValue(cal1);
                }
			case PropertyType.DOUBLE:
				return valF.createValue((Double)value);// todo other types
			case PropertyType.LONG:
				return valF.createValue((Long)value); // todo other types
			case PropertyType.NAME:
				return valF.createValue((String)value);
			case PropertyType.STRING:
				if (value instanceof char[])
					return valF.createValue(new String((char[])value));
			case PropertyType.UNDEFINED:
				return valF.createValue(value.toString());
			case PropertyType.PATH:
			case PropertyType.REFERENCE:
				throw new IllegalStateException("Don't know how to produce properties of type "+propType);
		}
		throw new IllegalStateException("Don't know how to handle values of type "+value.getClass().getName());
	}
    
    
    
    
	protected <T> Node getOrCreateNodeFor(Class<T> beanClass, String nodeName, Node parent, BeanPropertyJcrInfos infos) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException {
        String primaryType = getDefaultType();
        JcrType ann = null;
		if (infos!=null && StringUtils.isNotBlank(infos.nodePrimaryType))
			primaryType = infos.nodePrimaryType;
		else {
	        ann = beanClass.getAnnotation(JcrType.class);
	        if (ann!=null && StringUtils.isNotBlank(ann.primaryType()))
	        	primaryType = ann.primaryType();
		}
		
		if (parent.hasNode(nodeName)){
			Node n = parent.getNode(nodeName);
			String existingType = n.getPrimaryNodeType().getName();
			// TODO also check mixins?
			if (!existingType.equals(primaryType)){
				log.trace("Existing node "+nodeName+" type "+primaryType+" is wrong type for "+beanClass.getName()+", which wants "+primaryType+". Removing.");
				n.remove();
			}
			else {
				log.trace("Returning existing node "+nodeName+" type "+primaryType+" for "+beanClass.getName());
				return n;
			}
		}
        
		log.trace("Creating node "+nodeName+" type "+primaryType+" for "+beanClass.getName());
        Node n = parent.addNode(nodeName, primaryType);
        if (infos!=null && infos.nodeMixinTypes!=null){
        	for (String mixin : infos.nodeMixinTypes)
        		n.addMixin(mixin);        	
        }
        else if (ann!=null){
        	for (String mixin : ann.mixinTypes())
        		n.addMixin(mixin);        	
        }
        return n;
    }
	
	
	
	private String getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
    
    
}
