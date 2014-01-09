package com.github.aiderpmsi.jpmsi2stream.linestypes;

import java.io.IOException;

import org.xml.sax.ContentHandler;

import com.github.aiderpmsi.jpmi2stream.utils.MemoryBufferedReader;

/**
 * Défini l'architecture pour créer des patrons de lignes pmsi avec :
 * <ul>
 *  <li>Les noms des éléments</li>
 *  <li>Les regex des éléments</li>
 *  <li>Les contenus des éléments</li>
 * </ul>
 * @author delabre
 *
 */
public abstract class PmsiLineType {
	
	/**
	 * Checks if thie line is a valid line
	 * @return
	 */
	public abstract boolean isFound(MemoryBufferedReader br) throws IOException;

	/**
	 * Maps the line to content writen to the content handler
	 * @param contentHandler
	 * @throws IOException
	 */
	public abstract void writeResults(ContentHandler contentHandler) throws IOException;

	/**
	 * Consumes from the reader the number of characters matched
	 * @param br
	 * @throws IOException
	 */
	public abstract void consume(MemoryBufferedReader br) throws IOException;

}