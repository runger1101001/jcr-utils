package net.jaardvark.jcr.txt2jcr;

public interface NodeInformation {
	public String getNodeName();
	public String getPrimaryType();
	public String getIdentifier();
	public int getNodeOrder();
	public String getExtension();
}
