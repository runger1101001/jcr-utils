package net.jaardvark.jcr.predicate;

import javax.jcr.Item;
import javax.jcr.RepositoryException;

public class WorkspaceNamePredicate extends ItemPredicateBase {

	public String workspaceName;
	
	public WorkspaceNamePredicate(String workspaceName){
		this.workspaceName = workspaceName;
	}
	
	@Override
	public boolean evaluate(Item p) {
		try {
			return (workspaceName.equals(p.getSession().getWorkspace().getName()));
		} catch (RepositoryException e) {
			throw new IllegalStateException("Problem reading workspace name.",e);
		}
	}


}
