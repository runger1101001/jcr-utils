/**
 * 
 */
package net.jaardvark.jcr.predicate;

import javax.jcr.Property;

/**
 * @author Richard Unger
 */
public class AnyPropertyPredicate extends PropertyPredicateBase {
	@Override
	public boolean evaluate(Property p) {
		return true;
	}
}
