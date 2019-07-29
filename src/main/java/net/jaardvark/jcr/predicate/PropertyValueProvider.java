package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

public class PropertyValueProvider implements ValueProvider {

	protected String propName;

	public PropertyValueProvider(String propName) {
		this.propName = propName;
	}
	
	
	protected Property getProperty(Item item) throws RepositoryException{
		Node nodeToSearch;
		if (item.isNode())
			nodeToSearch = (Node) item;
		else
			nodeToSearch = item.getParent();
		if (!nodeToSearch.hasProperty(propName))
			return null;
		return nodeToSearch.getProperty(propName);
	}
	

	@Override
	public Value getValue(Item item) throws RepositoryException {
		Property p = getProperty(item);
		if (p==null)
			return null;
		return p.getValue();
	}

	@Override
	public boolean isMultiple(Item item) throws RepositoryException {
		Property p = getProperty(item);
		if (p==null)
			return false;
		return p.isMultiple();
	}

	@Override
	public Value[] getValues(Item item) throws RepositoryException {
		Property p = getProperty(item);
		if (p==null)
			return null;
		return p.getValues();
	}

}
