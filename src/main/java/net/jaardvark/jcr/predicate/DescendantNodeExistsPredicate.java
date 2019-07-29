package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class DescendantNodeExistsPredicate extends NodePredicateBase {

	protected String path;
	protected String primaryType;


	public DescendantNodeExistsPredicate(String path) {
		this.path = path;
		this.primaryType = null;
	}
	
	public DescendantNodeExistsPredicate(String path, String primaryType) {
		this.path = path;
		this.primaryType = primaryType;
	}
	
	
	@Override
	public boolean evaluate(Node node) {
		try {
			if (node.hasNode(path)){
				if (primaryType==null)
					return true;
				else
					return node.getNode(path).isNodeType(primaryType);
			}
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}			
		return false;
	}

}
