package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class NodeHasPropertyPredicate extends NodePredicateBase {
	
	protected String name;
	protected CompareValuePredicate valueComparison;


	public NodeHasPropertyPredicate(String name){
		this.name = name;
		this.valueComparison = null;
	}
	public NodeHasPropertyPredicate(String name, ValueProvider value, Operator operator){
		this.name = name;
		this.valueComparison = new CompareValuePredicate(operator, value);
	}
	
	@Override
	public boolean evaluate(Node node) {
		try {
			if (node.hasProperty(name)){
				if (valueComparison==null)
					return true;
				return valueComparison.evaluate(node.getProperty(name));
			}
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	
	
	
}
