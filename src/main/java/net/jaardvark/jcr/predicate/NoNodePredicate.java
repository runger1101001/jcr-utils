package net.jaardvark.jcr.predicate;

import javax.jcr.Node;

public class NoNodePredicate extends NodePredicateBase {

	@Override
	public boolean evaluate(Node node) {
		return false;
	}

}
