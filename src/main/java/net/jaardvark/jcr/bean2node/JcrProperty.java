package net.jaardvark.jcr.bean2node;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jcr.PropertyType;

/**
 * JCRProperty can be applied to fields of beans to determine how the bean property
 * is serialized to JCR.
 * 
 * Normally, JCRProperty is applied to simple types, like int, Integer, String, etc...
 * 
 * JCRProperty can also be applied to collection and array types, which are serialized
 * to muliple-valued properties in JCR. There are two exceptions:
 * char[] is serialized to String by default, and byte[] is serialized to binary by default.
 * 
 * Finally, JCRProperty can also be applied to fields with object values, in which case the
 * toString() method is used to generate the serialized value.
 * 
 * The default type-mapping is as follows:
 * Automatic mapping:
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
 *  
 *  The default mapping can be overridden by using the propertyType field of this annotation.
 *  
 *  The jcr property name defaults to the bean property name. This can be overridden by using
 *  the name field of this annotation.
 * 
 * @author Richard Unger
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JcrProperty {

	/**
	 * JCR property name for the annotated bean property.
	 * Defaults to the bean property name.
	 */
	String name() default "";
	
	/**
	 * JCR property type for the annotated bean property.
	 * Defaults to UNDEFINED, meaning the property type will be auto-detected based on the
	 * bean property type.
	 */
	int propertyType() default PropertyType.UNDEFINED;
	
}
