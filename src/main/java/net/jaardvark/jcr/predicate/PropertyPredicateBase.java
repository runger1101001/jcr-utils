/**
 * 
 */
package net.jaardvark.jcr.predicate;

import javax.jcr.Property;

import org.apache.jackrabbit.commons.predicate.Predicate;

/**
 * @author Richard Unger
 */
public abstract class PropertyPredicateBase implements Predicate {
	
	public abstract boolean evaluate(Property p);

	@Override
	public boolean evaluate(Object object) {
		if (object!=null && !(object instanceof Property))
			throw new IllegalArgumentException("PropertyPredicates can only evaluate jcr properties. Supplied argument had type "+object.getClass().getName());
		return evaluate((Property)object);
	}
	
}
