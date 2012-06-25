package aider.org.pmsi.dto;

import java.io.FileNotFoundException;
import java.util.Stack;

import aider.org.pmsi.parser.linestypes.PmsiLineType;
import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.Environment;
import com.sleepycat.dbxml.XmlContainer;
import com.sleepycat.dbxml.XmlContainerConfig;
import com.sleepycat.dbxml.XmlDocument;
import com.sleepycat.dbxml.XmlDocumentConfig;
import com.sleepycat.dbxml.XmlEventWriter;
import com.sleepycat.dbxml.XmlException;
import com.sleepycat.dbxml.XmlManager;
import com.sleepycat.dbxml.XmlManagerConfig;
import com.sleepycat.dbxml.XmlTransaction;

public abstract class DtoRsf implements DTOPmsiLineType {

	protected XmlManager xmlManager = null;
	
	protected XmlContainer xmlContainer = null;
	
	protected XmlTransaction txn = null;
	
	protected XmlDocument document = null;
	
	protected XmlEventWriter writer = null;
	
	protected Stack<PmsiLineType> lastLine = new Stack<PmsiLineType>();
	
	protected String name = null;
	
	/**
	 * Construction de la connexion à la base de données à partir des configurations
	 * données
	 * @param dbEnvironment
	 * @param xmlManagerConfig
	 * @param xmlContainerConfig
	 * @throws FileNotFoundException
	 * @throws DatabaseException
	 */
	public DtoRsf(Environment dbEnvironment,
			XmlManagerConfig xmlManagerConfig,
			XmlContainerConfig xmlContainerConfig) throws FileNotFoundException, DatabaseException {
		
		xmlManager = new XmlManager(dbEnvironment, xmlManagerConfig);
		xmlManager.setDefaultContainerType(XmlContainer.NodeContainer);
		
		XmlContainerConfig containerConf = new XmlContainerConfig();
	    containerConf.setTransactional(true);
	    containerConf.setAllowCreate(true);
		
		xmlContainer = xmlManager.openContainer("catalog.dbxml", containerConf);
	}
		
	/**
	 * Fermeture de la connexion à la base de données :
	 * Supprime toutes les données qui n'ont pas été validées
	 * et libère toutes les ressources associées à cette connexion
	 */
	public void close() throws XmlException {
		if (writer != null)
			writer.close();
		if (document != null)
			document = null;
		if (txn != null)
			txn.abort();
		if (xmlContainer != null)
			xmlContainer.close();
		if (xmlManager != null)
			xmlManager.close();
	}
	
	/**
	 * Ouvre une transaction pour enregistrer le xml et en remplit le début
	 * @param name Nom de la balise initiale
	 */
	public void start(String name) throws XmlException {
		this.name = name;
		if (document != null)
			throw new RuntimeException("Document already initialized");

		// Création de la transaction
		txn = xmlManager.createTransaction();
		
		// Création du document, avec autogénération du nom
		document = xmlManager.createDocument();
        
		XmlDocumentConfig config = new XmlDocumentConfig();
		config.setGenerateName(true);
		
		// Création du writer de document
        writer = xmlContainer.putDocumentAsEventWriter(txn, document, config);
            
        writer.writeStartDocument(null, null, null);
        
        // Ecriture dans le xml
        writer.writeStartElement(name, null, null, 0, false);
        writer.writeStartElement("content", null, null, 0, false);
	}

	/**
	 * Ajoute des données liées à une ligne pmsi
	 * @param lineType ligne avec les données à insérer
	 */
	public abstract void appendContent(PmsiLineType lineType) throws XmlException;
	
	/**
	 * Clôture de l'enregistrement du fichier dans la base de données
	 */
	public void end() throws XmlException {
		if (document == null)
			throw new RuntimeException("Document not initialized");

		// Fermeture de tous les tags non fermés
		while (!lastLine.empty()) {
			writer.writeEndElement(lastLine.pop().getName(), null, null);
		}
		writer.writeEndElement("content", null, null);
		writer.writeEndElement(name, null, null);
		
        // End the document
        writer.writeEndDocument();
        document = null;
        
        // Close the document
        writer.close();
        writer = null;
        
        // close the transaction
        txn.commit();
        txn = null;
	}

	
	protected void writeSimpleElement(PmsiLineType lineType) throws XmlException {
		writeSimpleElement(lineType.getName(), lineType.getNames(), lineType.getContent());
	}
	
	protected void writeSimpleElement(String name, String[] attNames, String[] attContent) throws XmlException {
		writer.writeStartElement(name, null, null, attNames.length, false);
		for (int i = 0 ; i < attNames.length ; i++) {
			writer.writeAttribute(attNames[i], null, null, attContent[i], true);
		}
	}

}
