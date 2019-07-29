package net.jaardvark.jcr.predicate;

import org.apache.jackrabbit.commons.predicate.Predicate;


public class XorPredicate implements Predicate {

	private Predicate p1;
	private Predicate p2;


	public XorPredicate(Predicate p1, Predicate p2){
		this.p1 = p1;
		this.p2 = p2;		
	}
	
	
	@Override
	public boolean evaluate(Object o) {
		return p1.evaluate(o) ^ p2.evaluate(o); // XOR
	}

}
