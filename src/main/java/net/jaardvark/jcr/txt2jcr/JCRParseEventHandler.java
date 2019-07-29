/**
 * 
 */
package net.jaardvark.jcr.txt2jcr;

import java.util.List;


/**
 * A JCRParseEventHandler works in conjunction with the JCRParser to
 * parse and process a text-based JCR representation.
 * 
 * The JCRParser is given an instance of a JCRParseEventHandler, and calls
 * the appropriate methods on the handler for each element parsed from the
 * textual representation.
 * 
 * The implementation of the JCRParseEventHandler, determines the result of
 * the parse operation.
 * For example, the JCRParseEventHandler might update/create nodes in JCR, 
 * or it might just extract and print the information, or to it might
 * produce an alternate representation (eg XML) as output.
 * 
 * @author Richard Unger
 */
public interface JCRParseEventHandler {

	public void nodeBegin(String name, String primaryType, int nodeOrder) throws Exception;
	
	public void nodeEnd() throws Exception;
	
	public void property(String name, String type, Object value) throws Exception;
	
	public void multipleProperty(String name, String type, List<? extends Object> values) throws Exception;

	public void finish() throws Exception;
	
}
