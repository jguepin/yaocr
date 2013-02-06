package yaocr.exceptions;

public class LayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5235416393653376059L;

	public LayerException(String msg) {
		super("Layer error ["+msg+"]");
	}

}
