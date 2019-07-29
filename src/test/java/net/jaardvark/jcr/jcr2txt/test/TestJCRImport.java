package net.jaardvark.jcr.jcr2txt.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import net.jaardvark.jcr.txt2jcr.Text2JCR;
import net.jaardvark.jcr.txt2jcr.parser.ParseException;
import net.jaardvark.jcr.txt2jcr.parser.TokenMgrError;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.tika.io.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TestJCRImport {

	protected static Repository repository;
	protected static Session sess = null;
	
	@Test
	public void importSimpleNode() throws LoginException, NoSuchWorkspaceException, RepositoryException, FileNotFoundException, ParseException, TokenMgrError, SAXException{
		Text2JCR txt2jcr = new Text2JCR();
		txt2jcr.setImportUUIDBehaviour(ImportUUIDBehavior.IMPORT_UUID_COLLISION_THROW);
		Node rootNode = sess.getRootNode();
		Reader content = new FileReader("src/test/resources/simple1.txt");
		txt2jcr.importText(rootNode, content, true);
		IOUtils.closeQuietly(content);
	}
	
	@Test
	public void importComplexNode() throws RepositoryException, ParseException, TokenMgrError, SAXException, FileNotFoundException{
		Text2JCR txt2jcr = new Text2JCR();
		txt2jcr.setImportUUIDBehaviour(ImportUUIDBehavior.IMPORT_UUID_COLLISION_THROW);
		Node rootNode = sess.getRootNode();
		Reader content = new FileReader("src/test/resources/complex1.txt");
		txt2jcr.importText(rootNode, content, true);
		IOUtils.closeQuietly(content);
	}
	
	
	@BeforeClass
	public static void repoSetup() throws LoginException, NoSuchWorkspaceException, RepositoryException{
		File repoHome = new File("testrepository");
		if (!repoHome.canRead())
			throw new IllegalStateException("Repository home 'testrepository' was not found in the working directory. Please create it (and the repository config file) or set the working directory correctly.");

		// clean up files left over
		File tempFile1 = new File("testrepository","version");
		tempFile1.delete();
		File tempFile2 = new File("testrepository","workspaces");
		tempFile2.delete();
		
		// init repo and session
		repository = new TransientRepository(repoHome);
		sess = repository.login(new SimpleCredentials("admin",new char[0]));
	}
	
	
	@AfterClass
	public static void repoTeardown() throws PathNotFoundException, IOException, RepositoryException{
		// output repository contents to console
		System.out.println();
		sess.exportSystemView("/", System.out, true, false);
		System.out.println();
		// close session
		if (sess!=null)
			sess.logout();
	}
	
}
