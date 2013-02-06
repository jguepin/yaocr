package yaocr.exceptions;

public class NetworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3384616922198793954L;

	public NetworkException(String msg) {
		super("Network error ["+msg+"]");
	}

}
