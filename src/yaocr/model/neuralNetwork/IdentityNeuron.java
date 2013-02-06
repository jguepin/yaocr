package yaocr.model.neuralNetwork;

import java.io.Serializable;

public class IdentityNeuron extends Neuron implements Serializable {

	public IdentityNeuron(int inputLength) {
		super(inputLength);
	}

	@Override
	public double activation(double input) {
		return input;
	}

	@Override
	public double derivateActivation(double input) {
		return 1;
	}

}
