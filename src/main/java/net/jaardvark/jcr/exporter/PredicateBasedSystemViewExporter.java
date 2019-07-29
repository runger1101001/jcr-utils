package net.jaardvark.jcr.exporter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.commons.predicate.Predicate;
import org.apache.jackrabbit.commons.xml.SystemViewExporter;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Exports only those nodes matching the given predicate
 * @author Richard Unger
 */
public class PredicateBasedSystemViewExporter extends SystemViewExporter {

    protected Predicate predicate;

    
    public PredicateBasedSystemViewExporter(Session session, ContentHandler handler, boolean recurse, boolean binary, Predicate predicate) {
        super(session, handler, recurse, binary);
        this.predicate = predicate;
    }

    
    @Override
    protected void exportNode(String uri, String local, Node node)
            throws RepositoryException, SAXException {
        if (predicate.evaluate(node))
            super.exportNode(uri, local, node);
    }
    
}
