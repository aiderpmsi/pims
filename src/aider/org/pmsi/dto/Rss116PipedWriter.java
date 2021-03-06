package aider.org.pmsi.dto;

import aider.org.pmsi.parser.PmsiRSS116Reader;
import aider.org.pmsi.parser.exceptions.PmsiPipedIOException;
import aider.org.pmsi.parser.linestypes.PmsiLineType;
import aider.org.pmsi.parser.linestypes.PmsiRss116Acte;
import aider.org.pmsi.parser.linestypes.PmsiRss116Da;
import aider.org.pmsi.parser.linestypes.PmsiRss116Dad;
import aider.org.pmsi.parser.linestypes.PmsiRss116Header;
import aider.org.pmsi.parser.linestypes.PmsiRss116Main;

/**
 * Writer {@link PmsiPipedWriterImpl} pour {@link PmsiRSS116Reader}
 * @author delabre
 *
 */
public class Rss116PipedWriter extends PmsiPipedWriterImpl {

	/**
	 * Construction du Writer avec son reader associé
	 * @throws PmsiPipedIOException 
	 */
	public Rss116PipedWriter(PmsiThreadedPipedReader pmsiPipedReader) throws PmsiPipedIOException {
		super(pmsiPipedReader);
	}
	
	/**
	 * Ajoute des données liées à une ligne pmsi
	 * @param lineType ligne avec les données à insérer
	 * @throws PmsiPipedIOException 
	 */
	public void writeLineElement(PmsiLineType lineType) throws PmsiPipedIOException  {
		// Lecture des données du header
		if (lineType instanceof PmsiRss116Header) {
			// Ecriture de la ligne header sans la fermer (va contenir les rss)
			super.writeLineElement(lineType);
		}
		
		// Lecture d'un RSS Main
		else if (lineType instanceof PmsiRss116Main) {
			// Si un rss main est ouvert, il faut le fermer
			if (getLastLine() instanceof PmsiRss116Main) {
				super.writeEndElement();
			}
			// ouverture du rss main
			super.writeLineElement(lineType);
		}
		
		// Lecture d'autres données
		else if (lineType instanceof PmsiRss116Acte || lineType instanceof PmsiRss116Da ||
				lineType instanceof PmsiRss116Dad) {
			// Ouverture de la ligne
			super.writeLineElement(lineType);
			// fermeture de la ligne
			super.writeEndElement();
		}
	}
}
