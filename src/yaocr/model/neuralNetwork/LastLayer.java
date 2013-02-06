package yaocr.model.neuralNetwork;

import yaocr.exceptions.LayerException;
import yaocr.exceptions.NeuronException;

import java.io.Serializable;

public class LastLayer extends Layer implements Serializable {
	
	public LastLayer(int size, int inputSize, int activationSort) throws LayerException {
		super(size, inputSize, activationSort);
		//neurons[0].setDebug(true);
	}

	public double[] errorPropagation(int[] goals, double[] lastOutputs) throws LayerException {
		if (goals.length != neurons.length)
			throw new LayerException("invalid parameters in 'errorPropagation()', goals length : "+goals.length+", neurons length : "+neurons.length);
		
		double[] finalOutputErrors = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++){
			try {
				finalOutputErrors[i] = neurons[i].errorPropagationLastLayer(goals[i], lastInputs, lastOutputs[i]);
			} catch (NeuronException e) {
				throw new LayerException("error in 'errorPropagation()' for neuron #"+i+" ["+e.getMessage()+"]");
			}
		}
		return finalOutputErrors;
	}

}
