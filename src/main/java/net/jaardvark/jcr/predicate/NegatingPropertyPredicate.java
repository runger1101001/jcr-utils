/**
 * 
 */
package net.jaardvark.jcr.predicate;

import javax.jcr.Property;

/**
 * @author Richard Unger
 */
public class NegatingPropertyPredicate extends PropertyPredicateBase {

	private PropertyPredicateBase other;

	public NegatingPropertyPredicate(PropertyPredicateBase other){
		this.other = other;
	}

	@Override
	public boolean evaluate(Property p) {
		return !other.evaluate(p);
	}
	
}
