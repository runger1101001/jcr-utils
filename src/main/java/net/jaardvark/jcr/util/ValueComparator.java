package net.jaardvark.jcr.util;

import java.util.Comparator;
import java.util.regex.Pattern;

import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

public class ValueComparator implements Comparator<Value> {

	@Override
	public int compare(Value o1, Value o2) {
		if (o2==null && o1==null)
			return 0;
		if (o2==null && o1!=null)
			return -1;
		if (o2!=null && o1==null)
			return 1;
		try {
			if (o1.getType()==o2.getType() && o1.getType()==PropertyType.DATE){
				// compare dates
				return o1.getDate().compareTo(o2.getDate());
			}
			if ( (o1.getType()==PropertyType.DOUBLE || o1.getType()==PropertyType.LONG)
				&& (o2.getType()==PropertyType.DOUBLE || o2.getType()==PropertyType.LONG)){
				// compare numbers
				if (o2.getDouble()==o1.getDouble()) return 0;
				if (o2.getDouble()>o1.getDouble()) return 1;
				return -1;
			}
			return o1.getString().compareTo(o2.getString());
		}
		catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	
	
	public static boolean equals(Value o1, Value o2) throws RepositoryException {
		if (o2==null)
			return false;
		if (o1.getType()==o2.getType())
			return o1.getString().equals(o2.getString());
		return false;
	}



	public static boolean gt(Value o1, Value o2) throws ValueFormatException, RepositoryException {
		if (o2==null)
			return true;
		if (o1.getType()==o2.getType() && o1.getType()==PropertyType.DATE){
			// compare dates
			return o1.getDate().compareTo(o2.getDate()) > 0;
		}
		if ( (o1.getType()==PropertyType.DOUBLE || o1.getType()==PropertyType.LONG)
			&& (o2.getType()==PropertyType.DOUBLE || o2.getType()==PropertyType.LONG)){
			// compare numbers
			return o1.getDouble() > o2.getDouble();
		}
		return false;
	}



	public static boolean gte(Value o1, Value o2) throws ValueFormatException, RepositoryException {
		if (o2==null)
			return true;
		if (o1.getType()==o2.getType() && o1.getType()==PropertyType.DATE){
			// compare dates
			return o1.getDate().compareTo(o2.getDate()) >= 0;
		}
		if ( (o1.getType()==PropertyType.DOUBLE || o1.getType()==PropertyType.LONG)
			&& (o2.getType()==PropertyType.DOUBLE || o2.getType()==PropertyType.LONG)){
			// compare numbers
			return o1.getDouble() >= o2.getDouble();
		}
		return false;
	}



	public static boolean lt(Value o1, Value o2) throws ValueFormatException, RepositoryException {
		if (o2==null)
			return false;
		if (o1.getType()==o2.getType() && o1.getType()==PropertyType.DATE){
			// compare dates
			return o1.getDate().compareTo(o2.getDate()) < 0;
		}
		if ( (o1.getType()==PropertyType.DOUBLE || o1.getType()==PropertyType.LONG)
			&& (o2.getType()==PropertyType.DOUBLE || o2.getType()==PropertyType.LONG)){
			// compare numbers
			return o1.getDouble() < o2.getDouble();
		}
		return false;
	}



	public static boolean lte(Value o1, Value o2) throws ValueFormatException, RepositoryException {
		if (o2==null)
			return false;
		if (o1.getType()==o2.getType() && o1.getType()==PropertyType.DATE){
			// compare dates
			return o1.getDate().compareTo(o2.getDate()) <= 0;
		}
		if ( (o1.getType()==PropertyType.DOUBLE || o1.getType()==PropertyType.LONG)
			&& (o2.getType()==PropertyType.DOUBLE || o2.getType()==PropertyType.LONG)){
			// compare numbers
			return o1.getDouble() <= o2.getDouble();
		}
		return false;
	}



	public static boolean regex(Value lhsV, Value rhsV) throws ValueFormatException, IllegalStateException, RepositoryException {
		if (lhsV==null || rhsV==null)
			return false; // can't match null by regex
		if (lhsV.getType()!=PropertyType.STRING || rhsV.getType()!=PropertyType.STRING)
			return false;
		return Pattern.matches(rhsV.getString(), lhsV.getString());
	}
	

}
