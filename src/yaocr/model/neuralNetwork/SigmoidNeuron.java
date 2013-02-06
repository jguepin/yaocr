package yaocr.model.neuralNetwork;

import java.io.Serializable;

public class SigmoidNeuron extends Neuron implements Serializable {

	public SigmoidNeuron(int inputLength) {
		super(inputLength);
	}

	@Override
	public double activation(double input) {
		return 1/(1+Math.exp(-input));
	}

	@Override
	public double derivateActivation(double input) {
		if (false)
			System.out.println("derivate activation : "+input+" --> "+(activation(input)*(1 - activation(input))));
		return activation(input)*(1 - activation(input));
	}

}
