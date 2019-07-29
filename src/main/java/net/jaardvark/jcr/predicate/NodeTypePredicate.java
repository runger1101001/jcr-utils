package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class NodeTypePredicate extends NodePredicateBase {

	private final String[] nodeType;

	public NodeTypePredicate(String...nodeType){
		this.nodeType = nodeType;
	}
	
	@Override
	public boolean evaluate(Node node) {
		try {
			for (String nt : nodeType)
				if (node.isNodeType(nt))
					return true;
		} catch (RepositoryException e) {
			throw new RuntimeException("Problem reading node type.",e);
		}
		return false;
	}

}
