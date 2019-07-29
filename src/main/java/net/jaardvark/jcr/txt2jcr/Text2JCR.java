/**
 * 
 */
package net.jaardvark.jcr.txt2jcr;

import net.jaardvark.jcr.txt2jcr.parser.JCRParser;
import net.jaardvark.jcr.txt2jcr.parser.ParseException;
import net.jaardvark.jcr.txt2jcr.parser.TokenMgrError;

import java.io.Reader;
import java.io.StringReader;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author Richard Unger
 */
public class Text2JCR {
	
	protected final static Logger log = LoggerFactory.getLogger(Text2JCR.class);
	
	protected int importUUIDBehaviour = ImportUUIDBehavior.IMPORT_UUID_COLLISION_THROW;
	
	public void importText(Node rootNode, String text, boolean saveIt) throws ParseException, TokenMgrError, RepositoryException, SAXException{
		importText(rootNode, new StringReader(text), saveIt);		
	}
	
	public void importText(Node rootNode, Reader text, boolean saveIt) throws ParseException, TokenMgrError, RepositoryException, SAXException {		
		JCRParser.parse(text, new JCRImportingParseEventHandler(rootNode, importUUIDBehaviour));
		if (saveIt)
			rootNode.getSession().save();
	}
	
	public void updateText(Node rootNode, String text, boolean saveIt) throws ParseException, TokenMgrError, RepositoryException, SAXException{
		updateText(rootNode, new StringReader(text), saveIt);		
	}
	
	public void updateText(Node rootNode, Reader text, boolean saveIt) throws ParseException, TokenMgrError, RepositoryException, SAXException {		
		JCRParser.parse(text, new JCRWritingParseEventHandler(rootNode));
		if (saveIt)
			rootNode.getSession().save();
	}
	
	
	/**
	 * Extracts the infos of a single node (including node-name, UUID, primaryType and node-order)
	 * @param text the textual node
	 * @throws TokenMgrError 
	 * @throws ParseException 
	 */
	public NodeInformation getNodeInfos(Reader text) throws ParseException, TokenMgrError{
		JCRNodeInfoExtractor infos = new JCRNodeInfoExtractor();
		JCRParser.parse(text, infos);
		return infos;
	}
	

	public String getImportUUIDBehaviour() {
		return ""+importUUIDBehaviour;
	}

	public void setImportUUIDBehaviour(int importUUIDBehaviour) {
		this.importUUIDBehaviour = importUUIDBehaviour;
		log.debug("Setting importUUIDBehaviour to int value "+importUUIDBehaviour);
	}
	
	public void setImportUUIDBehaviour(String importUUIDBehaviour) {
		if ("IMPORT_UUID_CREATE_NEW".equals(importUUIDBehaviour)){
			this.importUUIDBehaviour = ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW;
			log.debug("Setting importUUIDBehaviour to string value IMPORT_UUID_CREATE_NEW ("+this.importUUIDBehaviour+")");
			return;
		}
		if ("IMPORT_UUID_COLLISION_REMOVE_EXISTING".equals(importUUIDBehaviour)){
			this.importUUIDBehaviour = ImportUUIDBehavior.IMPORT_UUID_COLLISION_REMOVE_EXISTING;
			log.debug("Setting importUUIDBehaviour to string value IMPORT_UUID_COLLISION_REMOVE_EXISTING ("+this.importUUIDBehaviour+")");
			return;
		}
		if ("IMPORT_UUID_COLLISION_REPLACE_EXISTING".equals(importUUIDBehaviour)){
			this.importUUIDBehaviour = ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING;
			log.debug("Setting importUUIDBehaviour to string value IMPORT_UUID_COLLISION_REPLACE_EXISTING ("+this.importUUIDBehaviour+")");
			return;
		}
		if ("IMPORT_UUID_COLLISION_THROW".equals(importUUIDBehaviour)){
			this.importUUIDBehaviour = ImportUUIDBehavior.IMPORT_UUID_COLLISION_THROW;
			log.debug("Setting importUUIDBehaviour to string value IMPORT_UUID_COLLISION_THROW ("+this.importUUIDBehaviour+")");
			return;
		}
		try{
			int temp = Integer.parseInt(importUUIDBehaviour);
			if (0<=temp && temp<=3){
				this.importUUIDBehaviour = temp;
				log.debug("Setting importUUIDBehaviour to string value "+importUUIDBehaviour+" ("+this.importUUIDBehaviour+")");
				return;
			}
		}
		catch (Exception ex){
			// nix
		}
		throw new IllegalArgumentException("Could not parse importUUIDBehaviour configuration.");
	}

}
