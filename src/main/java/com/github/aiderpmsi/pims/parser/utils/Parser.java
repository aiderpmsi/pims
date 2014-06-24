package com.github.aiderpmsi.pims.parser.utils;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Attributes2Impl;
import org.xml.sax.helpers.XMLFilterImpl;

import com.github.aiderpmsi.pims.parser.linestypes.LineBuilder;
import com.github.aiderpmsi.pims.parser.linestypes.LineConfDictionary;
import com.github.aiderpmsi.pims.parser.linestypes.PmsiLineType.LineWriter;
import com.github.aiderpmsi.pims.treebrowser.TreeBrowser;
import com.github.aiderpmsi.pims.treebrowser.TreeBrowserException;
import com.github.aiderpmsi.pims.treebrowser.actions.IActionFactory;
import com.github.aiderpmsi.pims.treemodel.Node;

public class Parser extends XMLFilterImpl {

	private Node<IActionFactory.IAction> tree;

	private String type;
	
	private LineConfDictionary dico;
	
	private LineWriter lineWriter;
	
	public Parser(Node<IActionFactory.IAction> tree, LineConfDictionary dico, LineWriter lineWriter, String type) throws TreeBrowserException {
		this.tree = tree;
		this.type = type;
		this.dico = dico;
		this.lineWriter = lineWriter;
	}


	@Override
	public void parse(InputSource input) throws SAXException, IOException {

		// THIS WORKS ONLY ON CHARACTER STREAMS
		if (input.getCharacterStream() == null)
			throw new IOException("No CharacterStream on input");

		// CREATES THE VARS MAP
		HashMap<String, Object> context = new HashMap<>();
		context.put("lb", new LineBuilder(dico, lineWriter));
		context.put("br", new MemoryBufferedReader(input.getCharacterStream()));
		context.put("ch", getContentHandler());
		context.put("eh", getErrorHandler());
		context.put("utils", new Utils());
		context.put("start", type);
		JexlContext jc = new MapContext(context);

		boolean started = false;
		try {
			getContentHandler().startDocument();
			started = true;

			getContentHandler().startElement("", "root", "root", new Attributes2Impl());

			// CREATES AND EXECUTES THE TREE BROWSER
			TreeBrowser tb = new TreeBrowser(tree);
			tb.setJc(jc);

			try {
				tb.go();
			} catch (Exception e) {
				// BE SURE TO CATCH EVERY EXCEPTION FROM MACHINE
				throw new IOException(e);
			}
			
			getContentHandler().endElement("", "root", "root");
			
		} finally {
			// ALWAYS BE SURE TO SEND END DOCUMENT (CAN CLOSE STREAMS, ...)
			if (started)
				getContentHandler().endDocument();
		}
	}

	public String getType() {
		return type;
	}
		
}

