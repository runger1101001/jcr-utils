/**
 * 
 */
package net.jaardvark.jcr.txt2jcr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JCRParseEventHandler which writes parsed content back into JCR.
 * 
 * This parser uses the standard node interfaces to create the content,
 * for this reason jcr-properties like jcr:uuid and jcr:created cannot 
 * be written.
 * 
 * Notes:
 *   Existing content is overwritten by this!
 *   After processing, you need to call save() to persist the changes!
 * 
 * So usage can be something like this:
 * 
 * InputStream inputStream = getInput(); 	// get the parseable input
 * Node root = getRootNode(); 				// get the root node
 * JCRParser.parse(inputStream, new JCRWritingParseEventHandler(root));
 * root.save();
 * 
 * 
 *  TODO implement possibility to overwrite nodes without removing them!
 * 
 *  
 * @author Richard Unger
 */
public class JCRWritingParseEventHandler implements JCRParseEventHandler {

	protected final static Logger log = LoggerFactory.getLogger(JCRWritingParseEventHandler.class);
	
	protected Node currentNode;

	protected ValueFactory valF;
	
	public JCRWritingParseEventHandler(Node rootNode) throws RepositoryException{
		this.currentNode = rootNode;
		valF = rootNode.getSession().getValueFactory();
	}
	
	
	/**
	 * @throws RepositoryException  on error
	 * @throws PathNotFoundException  on error
	 * @throws ConstraintViolationException  on error
	 * @throws LockException  on error
	 * @throws VersionException  on error
	 * see jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeBegin(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void nodeBegin(String name, String primaryType, int nodeOrder) throws VersionException, LockException, ConstraintViolationException, PathNotFoundException, RepositoryException {
		if (currentNode.hasNode(name))
			currentNode.getNode(name).remove();
		log.debug("Adding node "+name+" of type "+primaryType+" at path "+currentNode.getPath());
		if (StringUtils.isEmpty(primaryType))
			currentNode = currentNode.addNode(name);
		else
			currentNode = currentNode.addNode(name, primaryType);
	}

	/**
	 * @throws RepositoryException  on error
	 * @throws AccessDeniedException  on error
	 * @throws ItemNotFoundException  on error
	 * see jaardvark.jcr.txt2jcr.JCRParseEventHandler#nodeEnd()
	 */
	@Override
	public void nodeEnd() throws ItemNotFoundException, AccessDeniedException, RepositoryException {
		currentNode = currentNode.getParent();
	}

	/**
	 * @throws RepositoryException  on error
	 * @throws ConstraintViolationException  on error
	 * @throws LockException  on error
	 * @throws VersionException  on error
	 * @throws ValueFormatException  on error
	 * @throws IOException  on error
	 * see jaardvark.jcr.txt2jcr.JCRParseEventHandler#property(java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void property(String name, String type, Object value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException, IOException {
		log.debug("Adding prop "+name+" of type "+type+" at path "+currentNode.getPath());
		// handle the case that mixinTypes was specified as a non-multiple (this is actually an error)
		if ("jcr:mixinTypes".equals(name)){
			currentNode.addMixin((String)value);
			return;
		}
		if ("jcr:uuid".equals(name)){
			log.debug("Can't set property jcr:uuid at path "+currentNode.getPath());
			return;
		}
		if ("jcr:created".equals(name)){
			log.debug("Can't set property jcr:created at path "+currentNode.getPath());
			return;
		}
		// handle other properties
		type = StringUtils.capitalize(type.toLowerCase()); // normalize type-name
		int propType = PropertyType.valueFromName(type);
		currentNode.setProperty(name, getValue(propType, value), propType);
	}

	
	protected Value getValue(int propType, Object value) throws IOException, RepositoryException {
		switch (propType){
			case PropertyType.BINARY:
				Binary bin = valF.createBinary((InputStream)value);				
				return valF.createValue(bin);
			case PropertyType.BOOLEAN:
				return valF.createValue((Boolean)value);
			case PropertyType.DATE:
				return valF.createValue((Calendar)value);
			case PropertyType.DOUBLE:
				return valF.createValue((Double)value);
			case PropertyType.LONG:
				return valF.createValue((Long)value);
			case PropertyType.NAME:
				return valF.createValue((String)value);
			case PropertyType.STRING:
				return valF.createValue((String)value);
			case PropertyType.PATH:
			case PropertyType.REFERENCE:
			case PropertyType.UNDEFINED:
				break;
		}
		throw new IllegalStateException("Don't know how to handle properties of type "+propType);
	}


	/**
	 * @throws RepositoryException on error
	 * @throws LockException  on error
	 * @throws ConstraintViolationException  on error
	 * @throws VersionException  on error
	 * @throws NoSuchNodeTypeException  on error
	 * @throws IOException  on error
	 * see net.jaardvark.jcr.txt2jcr.JCRParseEventHandler#multipleProperty(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void multipleProperty(String name, String type, List<? extends Object> values) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException, IOException {
		// handle mixins
		if ("jcr:mixinTypes".equals(name)){
			for (Object val : values)
				currentNode.addMixin((String)val);
			return;
		}
		type = StringUtils.capitalize(type.toLowerCase()); // normalize type-name
		int propType = PropertyType.valueFromName(type);
		// handle other properties
		Value[] valuesArray = new Value[values.size()];
		int i = 0;
		for (Object val : values)
			valuesArray[i++] = getValue(propType, val);
		currentNode.setProperty(name, valuesArray, propType);
	}


	@Override
	public void finish() throws Exception {
		// nix to do
	}

}
