package net.jaardvark.jcr.bean2node;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JcrType {

	/**
	 * JCR node type for the annotated class.
	 * Defaults to nt:unstructured
	 */
	String primaryType() default "nt:unstructured";

	/**
	 * Mixin types for the annotated class.
	 */
	String[] mixinTypes() default {};
	
}
