package yaocr.exceptions;

public class NetworkManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1149510297868049326L;

	public NetworkManagerException(String message) {
		super("NetworkManager error ["+message+"]");
	}

}
