package yaocr.exceptions;

public class ImageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5082797524916540795L;

	public ImageException(String message) {
		super("Image Error ["+message+"]");
	}

}
