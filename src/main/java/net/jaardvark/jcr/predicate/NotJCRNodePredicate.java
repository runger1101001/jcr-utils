package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class NotJCRNodePredicate extends NodePredicateBase {
    
    public final static String JCR_PREFIX = "jcr:";
    public final static String REP_PREFIX = "rep:";
    public final static String JCR_ROOT = "jcr:root";
    
    protected boolean includeJCRRoot;

    public NotJCRNodePredicate() {
        this(true);
    }
    
    public NotJCRNodePredicate(boolean includeJCRRoot) {
        this.includeJCRRoot = includeJCRRoot;
    }

	@Override
	public boolean evaluate(Node node) {
		String name;
		try {
			name = node.getName();
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		if (name.equals(JCR_ROOT)) return includeJCRRoot;
		if (name.startsWith(JCR_PREFIX)) return false;
		if (name.startsWith(REP_PREFIX)) return false;
		return true;
	}
	
}
