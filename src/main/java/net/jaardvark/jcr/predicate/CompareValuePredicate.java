package net.jaardvark.jcr.predicate;

import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import net.jaardvark.jcr.util.ValueComparator;

public class CompareValuePredicate extends PropertyPredicateBase {

	protected Operator operator;
	protected ValueProvider rhsValue;

	public CompareValuePredicate(Operator operator, ValueProvider value) {
		this.operator = operator;
		this.rhsValue = value;
	}

	@Override
	public boolean evaluate(Property lhs) {
		// TODO handle multiple value properties
		try{
			Value lhsV = lhs.getValue();
			Value rhsV = rhsValue.getValue(lhs);
			switch (operator){
			case EQUAL:
				return ValueComparator.equals(lhsV, rhsV);
			case GT:
				return ValueComparator.gt(lhsV, rhsV);
			case GT_EQ:
				return ValueComparator.gte(lhsV, rhsV);
			case LT:
				return ValueComparator.lt(lhsV, rhsV);
			case LT_EQ:
				return ValueComparator.lte(lhsV, rhsV);
			case NOTEQUAL:
				return !ValueComparator.equals(lhsV, rhsV);
			case REGEX:
				// TODO handle regex
				break;
			}
		}
		catch (RepositoryException e){
			throw new RuntimeException(e);
		}
		return false;
	}

}
