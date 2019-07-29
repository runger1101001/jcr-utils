package net.jaardvark.jcr.predicate;

import java.util.Calendar;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class DateRangePredicate extends NodePredicateBase {

	protected Calendar from = null;
	protected Calendar to = null;
	protected String name;

	public DateRangePredicate(String name, Calendar from, Calendar to) {
		this.name = name;
		this.from = from;
		this.to = to;
	}

	@Override
	public boolean evaluate(Node node) {		
		try {
			if (!node.hasProperty(name))
				return false;			
			Calendar cal = node.getProperty(name).getDate();
			if (cal!=null){
				if (from!=null)
					if (cal.before(from))
						return false;
				if (to!=null)
					if (cal.after(to))
						return false;
				return true;
			}
		} catch (RepositoryException e) {
			return false;
		}
		return false;
	}

}
