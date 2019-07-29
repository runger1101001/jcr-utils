package net.jaardvark.jcr.predicate;

import org.apache.jackrabbit.commons.predicate.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrPredicate implements Predicate {

	protected List<Predicate> predicates = null;

	public OrPredicate(){ }
		
	public OrPredicate(Predicate...predicates){
		this.predicates = Arrays.asList(predicates);
	}
	
	
	public void addPredicate(Predicate p){
		if (predicates==null)
			predicates = new ArrayList<>();
		predicates.add(p);
	}
	
	
	@Override
	public boolean evaluate(Object o) {
		if (predicates==null||predicates.size()<1)
			return false;
		for (Predicate p : predicates)
			if (p.evaluate(o))
				return true;
		return false;
	}

}
