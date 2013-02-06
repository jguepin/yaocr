package yaocr.model.neuralNetwork;

import yaocr.exceptions.LayerException;
import yaocr.exceptions.NeuronException;

import java.io.Serializable;

public abstract class Layer implements Serializable {
	
	protected double[] lastInputs;
	protected Neuron[] neurons;
	
	public Layer(int size, int inputSize, int activationSort) throws LayerException {
		neurons = new Neuron[size];
		switch (activationSort) {
		case Network.IDENTITY:
			for(int i = 0; i < size; i++)
				neurons[i] = new IdentityNeuron(inputSize);
			break;
		case Network.SIGMOID :
			for(int i = 0; i < size; i++)
				neurons[i] = new SigmoidNeuron(inputSize);
			break;
		case Network.TANH :
			for(int i = 0; i < size; i++)
				neurons[i] = new TanHNeuron(inputSize);
			break;
		default:
			throw new LayerException("invalid activation sort : "+activationSort);
		}
	}
	
	protected double[] process(double[] inputs) throws LayerException{
		double[] outputs = new double[neurons.length];
		lastInputs = inputs;
		
		for (int i = 0; i < outputs.length; i++){
			try {
				outputs[i] = neurons[i].process(inputs);
			} catch (NeuronException e) {
				throw new LayerException("Error in neuron #"+i+" ["+e.getMessage()+"]");
			}
		}
		return outputs;
	}
	
	public void pushErrors(double momentumCoef, double learningCoef) throws LayerException{
		for (int i = 0; i < neurons.length; i++){
			try {
				neurons[i].pushErrors(momentumCoef, learningCoef);
			} catch (NeuronException e) {
				throw new LayerException("Error in neuron #"+i+" ["+e.getMessage()+"]");
			}
		}
	}
	
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	public Neuron getNeuron(int index) {
		return neurons[index];
	}
	
}
