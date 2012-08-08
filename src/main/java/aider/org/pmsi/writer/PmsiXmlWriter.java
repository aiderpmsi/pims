package aider.org.pmsi.writer;

import java.io.OutputStream;
import java.io.PipedOutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import aider.org.pmsi.exceptions.PmsiWriterException;
import aider.org.pmsi.parser.linestypes.PmsiLineType;

/**
 * Implémente l'interface PmsiWriter
 * Crée un {@link PipedOutputStream} dans lequel sont écrites les données xml
 * pour transformer le flux pmsi en flux xml.
 * @author delabre
 *
 */
public class PmsiXmlWriter implements PmsiWriter {

	/**
	 * Flux xml dans lequel on écrit le fichier final (repose sur {@link PmsiXmlWriter#out}
	 * Il faut faire attention à l'ordre de création et de destruction
	 */
	private XMLStreamWriter xmlWriter = null;
	
	/**
	 * Construction, écrit sur le flux sortant fourni, avec l'encoding désiré
	 * @param outputStream
	 * @param encoding
	 * @throws PmsiWriterException
	 */
	public PmsiXmlWriter(OutputStream outputStream, String encoding) throws PmsiWriterException {
		open(outputStream, encoding);
	}

	/**
	 * Utilise un {@link OutputStream} pour écrire le flux xml
	 * @param outputStream
	 * @param encoding
	 * @throws PmsiWriterException
	 */
	public void open(OutputStream outputStream, String encoding) throws PmsiWriterException {
		// Si le flux xmlwriter est déjà ouvert, il faut le fermer
		this.close();
		
		try {
			// Création du writer de xml
			xmlWriter = new IndentingXMLStreamWriter(XMLOutputFactory.newInstance().
					createXMLStreamWriter(outputStream, encoding));
		} catch (XMLStreamException e) {
			throw new PmsiWriterException(e);
		}
		
	}
	
	/**
	 * Ouvre le document
	 * @param name Nom de la balise initiale
	 * @throws PmsiWriterException 
	 */
	public void writeStartDocument(String name, String[] attributes, String[] values) throws PmsiWriterException {
		try {
			xmlWriter.writeStartDocument();
			xmlWriter.writeStartElement(name);
			for (int i = 0 ; i < attributes.length ; i++) {
				xmlWriter.writeAttribute(attributes[i], values[i]);
			}
		} catch (Exception e) {
			throw new PmsiWriterException(e);
		}
	}
	
	/**
	 * Ecrit un nouvel élément
	 * @param name nom de l'élément
	 * @throws PmsiWriterException
	 */
	public void writeStartElement(String name) throws PmsiWriterException {
		try {
			xmlWriter.writeStartElement(name);
		} catch (Exception e) {
			throw new PmsiWriterException(e);
		}
	}

	/**
	 * Ferme l'élément en cours
	 * @throws PmsiWriterException
	 */
	public void writeEndElement() throws PmsiWriterException {
		try {
			xmlWriter.writeEndElement();
		} catch (Exception e) {
			throw new PmsiWriterException(e);
		}
	}

	/**
	 * Ouvre un élément en écrivant les attributs associés à la ligne pmsi dedans.
	 * Attention, il n'est pas fermé automatiquement
	 * @param lineType
	 * @throws PmsiWriterException
	 */
	public void writeLineElement(PmsiLineType lineType) throws PmsiWriterException {
		writeLineElement(lineType.getName(), lineType.getNames(), lineType.getContent());
	}
	
	/**
	 * Ouvre un élément en écrivant les attributs associés à la ligne pmsi dedans.
	 * Attention, il n'est pas fermé automatiquement, il faut le fermer dans un deuxième
	 * temps avec {@link PmsiWriter#writeEndElement()}
	 * @param name Nom de l'élément
	 * @param attNames Nom des attributs
	 * @param attContent Valeur des attributs
	 * @throws PmsiWriterException
	 */
	private void writeLineElement(String name, String[] attNames, String[] attContent) throws PmsiWriterException {
		try {
			xmlWriter.writeStartElement(name);
			for (int i = 0 ; i < attNames.length ; i++) {
				xmlWriter.writeAttribute(attNames[i], attContent[i]);
			}
		} catch (Exception e) {
			throw new PmsiWriterException(e);
		}
	}

	/**
	 * Ecrit la fin du document
	 * @throws PmsiWriterException
	 */
	public void writeEndDocument() throws PmsiWriterException {
		try {
	        // Ecriture de la fin du document xml
			xmlWriter.writeEndDocument();

		} catch (XMLStreamException e) {
			throw new PmsiWriterException(e);
		}
	}

	/**
	 * Libère toutes les ressources associées à ce writer
	 * @throws PmsiWriterException
	 */
	public void close() throws PmsiWriterException {
		// Fermeture des flux créés dans cette classe si besoin
		try {
			if (xmlWriter != null) {
				xmlWriter.flush();
				xmlWriter.close();
				xmlWriter = null;
			}

		} catch (XMLStreamException e) {
			throw new PmsiWriterException(e);
		}
	}
	
}
