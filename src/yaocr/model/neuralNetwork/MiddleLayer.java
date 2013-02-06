package yaocr.model.neuralNetwork;

import yaocr.exceptions.LayerException;
import yaocr.exceptions.NeuronException;

import java.io.Serializable;

public class MiddleLayer extends Layer implements Serializable {
	

	public MiddleLayer(int size, int inputSize, int activationSort) throws LayerException {
		super(size, inputSize, activationSort);
	}

	public double[] errorPropagation(double[] followingErrors, double[][] neuronsWeights) throws LayerException {
		if (followingErrors.length != neuronsWeights.length)
			throw new LayerException("Invalid parameters in 'errorPropagation()', followingErrors length : "+followingErrors.length+", neuronsWeights length : "+neuronsWeights.length);
		for (int j = 0; j < neuronsWeights.length; j++){
			if (neuronsWeights[j].length != neurons.length) 
				throw new LayerException("Invalid size of neurons weights in 'errorPropagation()', neuronsWeights["+j+"] length : "+neuronsWeights[j].length+", neurons length : "+neurons.length);
		}
		
		double[] outputErrors = new double[neurons.length];
		for (int i = 0; i < neurons.length; i++){
			try {
				double error = 0;
				for (int j = 0; j < neuronsWeights.length; j++)
					error += neuronsWeights[j][i]*followingErrors[j];
				outputErrors[i] = neurons[i].errorPropagation(error, lastInputs);
			} catch (NeuronException e) {
				throw new LayerException("Error in 'errorPropagation()' for neuron #"+i+" ["+e.getMessage()+"]");
			}
		}
		return outputErrors;
	}


}
