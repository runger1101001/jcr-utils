package net.jaardvark.jcr.predicate;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;


public class NodePositionPredicate extends NodePredicateBase {

	protected long targetPosition;
	
	public NodePositionPredicate(long targetPosition) {
		this.targetPosition = targetPosition;
	}

	@Override
	public boolean evaluate(Node node) {
		try {
			Node parent = node.getParent();
			if (!parent.getPrimaryNodeType().hasOrderableChildNodes())
				return false;
			NodeIterator nodes = parent.getNodes();
			long pos = 0;
			while (nodes.hasNext()){
				Node n = nodes.nextNode();
				if (n.equals(node))
					return pos==targetPosition;
				pos = nodes.getPosition();
			}
			// should never reach this point
			return false;
		} catch (RepositoryException e) {
			throw new RuntimeException(e);
		}
	}

}
