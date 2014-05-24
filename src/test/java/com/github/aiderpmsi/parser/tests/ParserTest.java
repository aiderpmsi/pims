package com.github.aiderpmsi.parser.tests;

import java.io.StringReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.ext.DefaultHandler2;

import com.github.aiderpmsi.pims.parser.utils.Parser;

public class ParserTest {
 
	private Parser p;
	
    @Before
    public void setUp() {
    	p = new Parser();
   }
 
    @After
    public void tearDown() {
    	// DO NOTHING
    }
 
    
    
    @Test
    public void testrss() throws Exception {
    	String rss = 
    			"123456789001610101201331122013000477006346008902720988560\n"
    			+ "1328Z04Z 116000123456789016131113              302000195           142843    27071949188  0 010220138 280220138 30450000000        120000000Z491    N189    0000                                \n"
    			+ "1328Z04Z 116000123456789016131709              302000259           150903    150819521RES1  040320138 040320138 30110000000        010000001Z491            000                                 04032013JVJF00801       01\n";

    	p.setContentHandler(new DefaultHandler2());
    	p.setErrorHandler(new DefaultHandler2());
    	p.setStartState("rss116header");
    	p.parse(new InputSource(new StringReader(rss)));
    	
    	
    }
}