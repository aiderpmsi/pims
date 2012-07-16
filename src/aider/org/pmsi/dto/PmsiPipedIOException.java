package aider.org.pmsi.dto;

/**
 * Exceptions levées lors de l'utilisation de DtoPmsi
 * A noter, est plus utilisée pour encapsuler tous les autres types
 * d'erreurs que pour donner un type d'erreur particulier
 * @author delabre
 *
 */
public class PmsiPipedIOException extends Exception {

	/**
	 * Numéro de série autogénéré
	 */
	private static final long serialVersionUID = -5876923561804105709L;

	public PmsiPipedIOException() {
	}

	public PmsiPipedIOException(String arg0) {
		super(arg0);
	}

	public PmsiPipedIOException(Throwable arg0) {
		super(arg0);
	}

	public PmsiPipedIOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}