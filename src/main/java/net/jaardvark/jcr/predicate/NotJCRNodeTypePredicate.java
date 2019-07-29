package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class NotJCRNodeTypePredicate extends NodePredicateBase {
    
    public final static String JCR_PREFIX = "jcr:";
    public final static String REP_PREFIX = "rep:";
    public final static String JCR_ROOT = "rep:root";
    
    protected boolean includeJCRRoot;

    public NotJCRNodeTypePredicate() {
        this(true);
    }
    
    public NotJCRNodeTypePredicate(boolean includeJCRRoot) {
        this.includeJCRRoot = includeJCRRoot;
    }

	@Override
	public boolean evaluate(Node node) {
		String name;
		try {
			name = node.getPrimaryNodeType().getName();
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
		if (name.equals(JCR_ROOT)) return includeJCRRoot;
		if (name.startsWith(JCR_PREFIX)) return false;
		if (name.startsWith(REP_PREFIX)) return false;
		return true;
	}
	
}
