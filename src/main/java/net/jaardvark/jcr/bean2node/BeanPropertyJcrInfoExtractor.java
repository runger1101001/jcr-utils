package net.jaardvark.jcr.bean2node;

import java.beans.PropertyDescriptor;

public interface BeanPropertyJcrInfoExtractor {

	BeanPropertyJcrInfos extract(Object bean, PropertyDescriptor beanProperty);

	
	public static enum IncludeAs {
		EXCLUDE, NODE, PROPERTY
	}
	
	
	public static class BeanPropertyJcrInfos {
		public IncludeAs includeAs;
		public int propertyType;
		public String nodePrimaryType;
		public String[] nodeMixinTypes;
		public String name;
		public boolean multiple;
	}
	
}
