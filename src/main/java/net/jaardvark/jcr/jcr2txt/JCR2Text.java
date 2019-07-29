package net.jaardvark.jcr.jcr2txt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.nodetype.PropertyDefinition;

import net.jaardvark.jcr.predicate.NamedPropertyPredicate;
import net.jaardvark.jcr.predicate.NodePredicate;
import net.jaardvark.jcr.predicate.PropertyPredicateBase;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


/*
 *  output format:
 *  
 *  [node-name] { 
 *  				[proptype] [propname] : [propvalue]
 *  				[proptype] [propname] : [propvalue]
 *  				[node-name] {
 * 					 				[proptype] [propname] : [propvalue]
 *  								[proptype] [propname] : [propvalue]
 *				  				}
 *  			}
 *  
 *  
 *  
 *  propvalue depends on property type:
 *  
 *    boolean, integer, double:  	string representation
 *    string:                   	quoted, escaped string
 *    date:                     	iso representation
 *    binary:						base64 encoded string representation
 *  
 *  proptype takes the following values:
 *    b: boolean
 *    s: string
 *    l: long
 *    d: double
 *    D: date
 *    B: binary
 *    *: multiple (can be combined with other types, like D* or s* )
 *  
 */
public class JCR2Text {

	
	PropertyPredicateBase skippedProperties = new NamedPropertyPredicate("jcr:primaryType", "jcr:created");
	
	/**
	 * Get a textual representation of a node
	 * @param node the node to convert to text
	 * @param nFilter subnodes included
	 * @param pFilter properties included
	 * @param nodeOrder order of node among siblings
	 * @return the node, as text
	 * @throws RepositoryException
	 * @throws IOException
	 */
	public String nodeAsText(Node node, NodePredicate nFilter, PropertyPredicateBase pFilter, int nodeOrder) throws RepositoryException, IOException {
		return nodeAsText(node, nFilter, pFilter, nodeOrder, 0);
	}
	
	/**
	 * Get a text representation for the given node.
	 * @param node the node
	 * @param filter the node filter which determines which nodes are included and which are omitted
	 * @return a text version thereof
	 * @throws RepositoryException 
	 * @throws IOException 
	 */
	public String nodeAsText(Node node, NodePredicate filter, PropertyPredicateBase pFilter, int nodeOrder, int indentLevel) throws RepositoryException, IOException {
		StringBuffer text = new StringBuffer();
		String indents = StringUtils.repeat("\t", indentLevel);
		// first the node name
		text.append(indents);
		text.append(node.getName());
		// primary type
		if (node.getPrimaryNodeType()!=null)
			text.append(" "+node.getPrimaryNodeType().getName());
		// node order
		text.append(" "+nodeOrder);
		text.append(" {\n");
		
		// then the properties - order is not stable, so collect them in a list first
		List<Property> propList = new ArrayList<Property>();
		PropertyIterator pi = node.getProperties();
		while (pi.hasNext()){
			Property p = pi.nextProperty();
			if (pFilter.evaluate(p) && !skippedProperties.evaluate(p))
				propList.add(p);
		}

		// sort the properties by name to obtain a stable order
		Collections.sort(propList, new Comparator<Property>(){
			@Override
			public int compare(Property p1, Property p2) {
				try {
					return p1.getName().compareTo(p2.getName());
				} catch (RepositoryException e) {
					throw new IllegalStateException("Could not obtain property name!");
				}
			}			
		});
		
		// and output the resulting sorted list
		for (Property p : propList){
			text.append(indents);
			text.append(propertyAsText(p));			
		}
		
		// and finally the child-nodes - node-order is stable
		NodeIterator ni = node.getNodes();
		int kidOrder = 0;
		while (ni.hasNext()){
			Node kidNode = ni.nextNode();
			if (filter.evaluate(kidNode))
				text.append(nodeAsText(kidNode, filter, pFilter, kidOrder, indentLevel+1));
			kidOrder++;
		}
		
		text.append(indents);
		text.append("}\n");
		
		return text.toString();
	}

	
	
	public String propertyAsText(Property p) throws RepositoryException, IOException {
		StringBuffer text = new StringBuffer();
		
		// get values, determine multiple status
		Value value = null;
		Value[] values = null;
		try {
			value = p.getValue();
		} catch (ValueFormatException e) {
			values = p.getValues();
		}
		
		boolean multiple = false;
		PropertyDefinition def = p.getDefinition();
		if (def!=null)
			multiple = def.isMultiple();
		else if (values!=null)
			multiple = true;

		// property type
		text.append("\t");
		text.append(PropertyType.nameFromValue(p.getType()));
		if (multiple) text.append(" *");
		text.append(" ");
		
		// property name
		text.append(p.getName());
		text.append(" : ");
		
		// property value(s)
		if (multiple)
			for (int i=0;i<values.length;i++){
				text.append(valueAsString(values[i]));
				if (i<values.length)
					text.append(", ");
			}
		else
			text.append(valueAsString(value));
		
		// newline after property ends
		text.append("\n");
		
		return text.toString();
	}



	public String valueAsString(Value value) throws ValueFormatException, RepositoryException, IOException {
		switch (value.getType()){
			case PropertyType.BINARY:
				String binVal = null;
				InputStream stream = null;
				Binary bin = value.getBinary();
				try{
					stream = bin.getStream();
					binVal = Base64.encodeBase64String(IOUtils.toByteArray(stream));
					binVal = "\"" + StringUtils.replaceChars(binVal, "\n\r", "") + "\"";					
				}
				finally {
					IOUtils.closeQuietly(stream);
					if (bin!=null)
						bin.dispose();
				}
				return binVal;
			case PropertyType.DATE:
				//return DateFormat.getDateTimeInstance().format(value.getDate().getTime());
			case PropertyType.LONG:
			case PropertyType.DOUBLE:
			case PropertyType.BOOLEAN:
			case PropertyType.NAME:
			case PropertyType.PATH:
				return value.getString();
			case PropertyType.STRING:
				StringBuffer text = new StringBuffer();
				text.append("\"");
				text.append(StringEscapeUtils.escapeJava(value.getString()));
				text.append("\"");
				return text.toString();
			case PropertyType.REFERENCE:
				throw new IllegalStateException("Don't know how to handle value of type REFERENCE");
			default:
				throw new IllegalStateException("Don't know how to handle value of type "+value.getType());
		}
	}

	
}
