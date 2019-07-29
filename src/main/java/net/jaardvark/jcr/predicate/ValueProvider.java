package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

public interface ValueProvider {
	Value getValue(Item evaluatedItem) throws RepositoryException;
	Value[] getValues(Item p) throws RepositoryException;
	boolean isMultiple(Item p) throws RepositoryException;
}
