/**
 * 
 */
package net.jaardvark.jcr.predicate;

import java.util.HashSet;

import javax.jcr.Property;
import javax.jcr.RepositoryException;

/**
 * @author Richard Unger
 */
public class NamedPropertyPredicate extends PropertyPredicateBase {

	protected HashSet<String> names = new HashSet<String>();
	protected CompareValuePredicate valueComparison = null;
	
	
	public NamedPropertyPredicate(String... names){
		for (String name : names){
			this.names.add(name);
		}
	}
	
	public NamedPropertyPredicate(ValueProvider value, Operator operator,String...names){
		this(names);
		this.valueComparison = new CompareValuePredicate(operator, value);
	}	
	
	
	/**
	 * see PropertyPredicateBase.jcr.jcr2txt.PropertyFilter#evaluate(javax.jcr.Property)
	 */
	@Override
	public boolean evaluate(Property p) {
		try {
			if (!names.contains(p.getName()))
				return false;
			if (valueComparison==null)
				return true;
			// TODO handle multiple values
			return valueComparison.evaluate(p.getValue());
		} catch (RepositoryException e) {
			throw new IllegalStateException("Cannot read property name!");
		}
	}

	public boolean excludesName(String propertyName) {
		return names.contains(propertyName);
	}

}
