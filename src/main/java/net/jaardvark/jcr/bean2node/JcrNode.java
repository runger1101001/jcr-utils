package net.jaardvark.jcr.bean2node;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JcrNode {

	/**
	 * JCR node name for the annotated bean property.
	 * Defaults to the bean property name.
	 */
	String name() default "";
	
}
