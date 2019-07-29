package net.jaardvark.jcr.predicate;

import javax.jcr.Item;

import org.apache.jackrabbit.commons.predicate.Predicate;

public abstract class ItemPredicateBase implements Predicate {
	
	public abstract boolean evaluate(Item p);

	@Override
	public boolean evaluate(Object object) {
		if (object!=null && !(object instanceof Item))
			throw new IllegalArgumentException("ItemPredicates can only evaluate jcr items. Supplied argument had type "+object.getClass().getName());
		return evaluate((Item)object);
	}

}
