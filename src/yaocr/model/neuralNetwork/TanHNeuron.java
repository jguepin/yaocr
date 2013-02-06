package yaocr.model.neuralNetwork;

import java.io.Serializable;


public class TanHNeuron extends Neuron implements Serializable {

	public TanHNeuron(int inputLength) {
		super(inputLength);
	}

	@Override
	public double activation(double input) {
		if (false)
			System.out.println("activation : "+input+"-->"+Math.tanh(input));
		return Math.tanh(input);
	}

	@Override
	public double derivateActivation(double input) {
		if (false)
			System.out.println("derivate activation : "+input+" --> "+(1 - Math.pow(activation(input), 2)));
		return Math.pow(activation(input), 2);
	}

}
