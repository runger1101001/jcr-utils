/**
 * 
 */
package net.jaardvark.jcr.txt2jcr;

import java.util.List;


/**
 * Extracts infos from a 
 * @author Richard Unger
 */
public class JCRNodeInfoExtractor implements JCRParseEventHandler, NodeInformation {

	boolean nodeDone = false;
	boolean uuidDone = false;
	boolean extDone = false;
	
	String nodeName = null;
	String primaryType = null;
	String UUID = null;
	int nodeOrder = -1;
	String extension = null;
	

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeBegin(java.lang.String, java.lang.String)
	 */
	@Override
	public void nodeBegin(String name, String primaryType, int nodeOrder) throws Exception {
		if (!nodeDone){
			this.nodeName = name;
			this.primaryType = primaryType;
			this.nodeOrder = nodeOrder;
			nodeDone = true;
		}
		else
			uuidDone = true;
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeEnd()
	 */
	@Override
	public void nodeEnd() throws Exception {
		// nix
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void property(String name, String type, Object value) throws Exception {
		if (!uuidDone && "jcr:uuid".equals(name)){
			UUID = (String) value;
			uuidDone = true;
		}
		if (!extDone && "extension".equals(name)){ // TODO this is stupid, keep all properties in a map instead
			extension = (String) value;
			extDone = true;
		}
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void multipleProperty(String name, String type, List<? extends Object> values) throws Exception {
		// nix
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#finish()
	 */
	@Override
	public void finish() throws Exception {
		// nix
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getPrimaryType() {
		return primaryType;
	}

	public String getIdentifier() {
		return UUID;
	}

	public int getNodeOrder() {
		return nodeOrder;
	}

	public String getExtension() {
		return extension;
	}

}
