package net.jaardvark.jcr.predicate;

import javax.jcr.Node;

import org.apache.jackrabbit.commons.predicate.Predicate;

public interface NodePredicate extends Predicate {

	public boolean evaluate(Node node);
	
}
