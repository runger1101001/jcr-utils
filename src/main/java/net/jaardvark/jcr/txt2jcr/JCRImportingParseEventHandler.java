/**
 * 
 */
package net.jaardvark.jcr.txt2jcr;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.commons.lang.StringUtils;
import org.apache.jackrabbit.util.ISO8601;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Imports content into JCR.
 * 
 * This parser uses the import interface, and can therefore set all properties,
 * including jcr: properties like uuid and created-date.
 * 
 * Notes:
 *  - The import occurs within the JCR session, so make sure to call save() afterwards
 *    to persist your changes! 
 * 
 * TODO test correct import of multiple-properties that have only a single value
 *
 * @author Richard Unger
 */
public class JCRImportingParseEventHandler implements JCRParseEventHandler {

	public final static String JCR_SV = "http://www.jcp.org/jcr/sv/1.0";
	
	protected ContentHandler contentHandler = null;
	
	public JCRImportingParseEventHandler(Node rootNode, int importUUIDBehavior) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException, SAXException{
		contentHandler  = rootNode.getSession().getImportContentHandler(rootNode.getPath(), importUUIDBehavior);
		contentHandler.startDocument();
	}
	
	public JCRImportingParseEventHandler(Node rootNode) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException, SAXException{
		this(rootNode, ImportUUIDBehavior.IMPORT_UUID_COLLISION_THROW);
	}
	
	
	@Override
	public void finish() throws SAXException{
		contentHandler.endDocument();
	}
	
	
	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeBegin(java.lang.String, java.lang.String)
	 */
	@Override
	public void nodeBegin(String name, String primaryType, int nodeOrder) throws Exception {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute(JCR_SV, "name", null, "NMTOKEN", name);		
		contentHandler.startElement(JCR_SV, "node", null, atts);
		// add primary node type
		property("jcr:primaryType", "Name", primaryType);
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeEnd()
	 */
	@Override
	public void nodeEnd() throws Exception {
		contentHandler.endElement(JCR_SV, "node", null);
	}

	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void property(String name, String type, Object value) throws Exception {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute(JCR_SV, "name", null, "NMTOKEN", name);
		//type = StringUtils.capitalize(type.toLowerCase()); // normalize type-name
		int propType = PropertyType.valueFromName(type);
		atts.addAttribute(JCR_SV, "type", null, "NMTOKEN", type);	
		contentHandler.startElement(JCR_SV, "property", null, atts);
		value(propType, value);
		contentHandler.endElement(JCR_SV, "property", null);
	}

	
	
	protected void value(int propType, Object value) throws SAXException, ParseException {
		contentHandler.startElement(JCR_SV, "value", null, new AttributesImpl());
		String val = null;

		if (value instanceof Value){
			Value vv = (Value)value;
			try {
				val = vv.getString();
			} catch (RepositoryException e) {
				throw new IllegalStateException("Can't convert value to string",e);
			}
		}
		else
			switch (propType){
				case PropertyType.DATE:
					Calendar calVal = null;
					if (value instanceof Calendar)
						calVal = (Calendar) value;
					else if (value instanceof String){
						calVal = ISO8601.parse((String)value);
						if (calVal==null)
							throw new ParseException("Can't parse '"+value+"' as a Date.", 0);
					}
					else
						throw new IllegalStateException("Can't convert "+value.getClass().getName()+" to property of type Date");
					val = ISO8601.format(calVal);
					break;
				case PropertyType.DOUBLE:
				case PropertyType.LONG:
				case PropertyType.BOOLEAN:
					val = value.toString();
					break;
				case PropertyType.NAME:
				case PropertyType.STRING:
                case PropertyType.WEAKREFERENCE:
				case PropertyType.BINARY:
                case PropertyType.PATH:
                case PropertyType.REFERENCE:
					val = (String) value;
					break;
				case PropertyType.UNDEFINED:
				default:
					throw new IllegalStateException("Don't know how to handle properties of type "+propType);
			}
		
		contentHandler.characters(val.toCharArray(), 0, val.length());
		contentHandler.endElement(JCR_SV, "value", null);
	}


	/**
	 * @see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void multipleProperty(String name, String type, List<? extends Object> values) throws Exception {
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute(JCR_SV, "name", null, "NMTOKEN", name);
		//type = StringUtils.capitalize(type.toLowerCase()); // normalize type-name
		int propType = PropertyType.valueFromName(type);
		atts.addAttribute(JCR_SV, "type", null, "NMTOKEN", type);
		atts.addAttribute(JCR_SV, "multiple", null, "NMTOKEN", "true");
		contentHandler.startElement(JCR_SV, "property", null, atts);
		for (Object value : values)
			value(propType, value);
		contentHandler.endElement(JCR_SV, "property", null);
	}

}
