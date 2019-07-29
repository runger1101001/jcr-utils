package net.jaardvark.jcr.predicate;

import javax.jcr.Node;

public abstract class NodePredicateBase implements NodePredicate {

	@Override
	public boolean evaluate(Object object) {
		if (object!=null && !(object instanceof Node))
			throw new IllegalArgumentException("NodePredicates can only evaluate jcr nodes. Supplied argument had type "+object.getClass().getName());
		return evaluate((Node)object);
	}

	@Override
	abstract public boolean evaluate(Node node);

}
