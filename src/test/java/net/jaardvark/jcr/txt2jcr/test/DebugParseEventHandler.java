/**
 * 
 */
package net.jaardvark.jcr.txt2jcr.test;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.jaardvark.jcr.txt2jcr.JCRParseEventHandler;

/**
 * @author Richard Unger
 */
public class DebugParseEventHandler implements JCRParseEventHandler {

	protected int indentLevel = 0;
	
	/**
	 * @see jaardvark.jcr.txt2jcr.JCRParseEventHandler#node(java.lang.String)
	 */
	@Override
	public void nodeBegin(String name, String primaryType, int nodeOrder) {
		if (StringUtils.isEmpty(primaryType))
			primaryType = "UNKNOWN";
		System.out.println(StringUtils.repeat("\t", indentLevel)+"NODE: "+name+" (type="+primaryType+", order="+nodeOrder+")");
		indentLevel++;
	}

	@Override
	public void nodeEnd() {
		indentLevel--;
	}
	
	/**
	 * @see jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String)
	 */
	@Override
	public void property(String name, String type, Object value) {
		System.out.print(StringUtils.repeat("\t", indentLevel)+"PROP: "+name);
		System.out.print(" ("+type+") ");
		System.out.println(value);
	}

	/**
	 * @see jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void multipleProperty(String name, String type, List<? extends Object> values) {
		System.out.print(StringUtils.repeat("\t", indentLevel)+"PROP: "+name);
		System.out.print(" ("+type+"["+values.size()+"]) ");
		for (Object o : values)
			System.out.print(o + ",");
		System.out.println();
	}

	@Override
	public void finish() throws Exception {
		System.out.println("Parsing finished.");
	}


}
