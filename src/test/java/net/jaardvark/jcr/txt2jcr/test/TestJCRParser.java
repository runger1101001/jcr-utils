/**
 * 
 */
package net.jaardvark.jcr.txt2jcr.test;

import net.jaardvark.jcr.txt2jcr.JCRParseEventHandler;
import net.jaardvark.jcr.txt2jcr.parser.JCRParser;
import net.jaardvark.jcr.txt2jcr.parser.ParseException;
import net.jaardvark.jcr.txt2jcr.parser.TokenMgrError;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * Basic tests of the parser, no actual repository involved.
 * @author Richard Unger
 */
public class TestJCRParser {

	@Test
	public void parseSimpleNode() throws ParseException, TokenMgrError, FileNotFoundException{
		FileReader fis = new FileReader("src/test/resources/simple1.txt");
		JCRParseEventHandler handler = new DebugParseEventHandler();
		JCRParser.parse(fis, handler);
		IOUtils.closeQuietly(fis);
	}
	
	@Test
	public void parseComplexNode() throws ParseException, TokenMgrError, FileNotFoundException{
		FileReader fis = new FileReader("src/test/resources/complex1.txt");
		JCRParseEventHandler handler = new DebugParseEventHandler();
		JCRParser.parse(fis, handler);
		IOUtils.closeQuietly(fis);
	}
	
//	@Test - requires order of tests, bad practice - TODO think about and change me
//	public void parseExportedNode() throws ParseException, TokenMgrError, FileNotFoundException{
//		FileReader fis = new FileReader("src/test/resources/net/jaardvark/jcr/txt2jcr/test/complex2.txt");
//		JCRParseEventHandler handler = new DebugParseEventHandler();
//		JCRParser.parse(fis, handler);
//		IOUtils.closeQuietly(fis);
//	}
}
