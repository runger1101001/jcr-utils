/**
 * 
 */
package net.jaardvark.jcr.predicate;

import javax.jcr.Node;

/**
 * @author Richard Unger
 */
public class NegatingNodePredicate extends NodePredicateBase {

	private NodePredicate other;

	public NegatingNodePredicate(NodePredicate other){
		this.other = other;
	}

	@Override
	public boolean evaluate(Node n) {
		return !other.evaluate(n);
	}
	
}
