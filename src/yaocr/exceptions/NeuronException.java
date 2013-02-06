package yaocr.exceptions;

public class NeuronException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8889426045230409024L;

	public NeuronException(String msg) {
		super("Neuron error ["+msg+"]");
	}
}
