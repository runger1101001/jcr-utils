package net.jaardvark.jcr.stress;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Given a node, this class will write to N sub-nodes within that node, writing M properties P times.
 * 
 * Each time the node is written to, it is created if not existing, and then a property is written.
 * The nodes are named 1..N, the properties are named prefix_1..M, the property values written are equal to the 
 * iteration counter prefix2_1..P.
 * 
 * @author Richard Unger
 */

public class StressJCRParallel {
    
    Logger log = LoggerFactory.getLogger(StressJCRParallel.class);

    public void stressJCR(Node parent, int N, int M, int P, String propertyPrefix, String valuePrefix) throws RepositoryException {
        stressJCR(parent, N, M, P, propertyPrefix, valuePrefix, false, null);
    }
    
    public void stressJCR(Node parent, int N, int M, int P, String propertyPrefix, String valuePrefix, boolean seperateSessions, String password) throws RepositoryException {
        long start = System.currentTimeMillis();
        
        Session s = parent.getSession();
        Repository repo = s.getRepository();
        String workspaceName = s.getWorkspace().getName();
        String parentPath = parent.getPath();
        
        int allErrors = 0;
        for (int p = 0; p<P; p++) {
            log.debug("Beginning iteration for property value "+p);
            String propValue = valuePrefix + p;
            for (int m = 0; m<M ; m++) {
                log.debug("Beginning iteration for property name "+m);
                String propName = propertyPrefix + m;
                long itStart = System.currentTimeMillis();
                int errors = 0;
                for (int n : LCG.generateSequence(N)) {
                    if (seperateSessions)
                        s = repo.login(new SimpleCredentials("admin", password.toCharArray()), workspaceName);
                    String nodeName = ""+n;
                    try {
                        Node par = s.getNode(parentPath);
                        Node node = par.hasNode(nodeName)?par.getNode(nodeName):par.addNode(nodeName, "mgnl:content");
                        if (node!=null) {
                            node.setProperty(propName, propValue);
                            s.save();
                            log.trace("Saved "+propName+" value "+propValue+" for node "+n);
                        }
                    }
                    catch (RepositoryException ex) {
                        log.warn("Stress test had JCR error for node "+n+" property "+m+" value "+p+"! Error: "+ex);
                        errors += 1;
                        //parent.refresh(false);
                    }
                    finally {
                        if (seperateSessions)
                            s.logout();
                    }
                } // loop over nodes
                log.debug("Finished for "+N+" nodes with "+errors+" errors in "+(System.currentTimeMillis()-itStart)+"ms");
                allErrors += errors;
            } // loop over properties
        } // loop over values
        
        log.info("Finished stress test run with "+allErrors+" errors in "+(System.currentTimeMillis()-start)+"ms");
    }

}
