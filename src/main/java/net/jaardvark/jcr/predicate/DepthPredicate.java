package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.RepositoryException;

public class DepthPredicate extends ItemPredicateBase {

	protected int min;
	protected int max;

	public DepthPredicate(int min, int max){
		this.min = min;
		this.max = max;		
	}
	
	@Override
	public boolean evaluate(Item item) {
		int d;
		try {
			d = ((Item)item).getDepth();
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		return (min <= d && d <= max);
	}

}
