package net.jaardvark.jcr.predicate;

import org.apache.jackrabbit.commons.predicate.Predicate;

public class NotPredicate implements Predicate {

	protected Predicate p = null;

	public NotPredicate(Predicate p){
		this.p = p;
	}

	@Override
	public boolean evaluate(Object arg) {
		return !p.evaluate(arg);
	}

}
