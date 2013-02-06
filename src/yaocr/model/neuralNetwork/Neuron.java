package yaocr.model.neuralNetwork;

import yaocr.exceptions.NeuronException;

import java.io.Serializable;

public abstract class Neuron implements Serializable {

	private double[] weights;
	private double[] deltaErrorsTotal;
	private double[] lastDeltaErrorsTotal;
	private int nbOfErrors;
	private double bias;
	protected boolean debug = false;
	String s = "";

	public Neuron(int inputLength) {
		this.weights = new double[inputLength];
		deltaErrorsTotal = new double[inputLength + 1];
		lastDeltaErrorsTotal = new double[inputLength + 1];
		nbOfErrors = 0;
		//init le biais aléatoirement
		this.bias = (2*Math.random() - 1);
		for (int i = 0; i < inputLength; i++){
			//init les poids aléatoirement
			weights[i] = (2*Math.random() - 1);
		}

	}

	public void setDebug(boolean debug){
		this.debug = debug;
	}

	public double process(double[] inputs) throws NeuronException{

		double input = calcInput(inputs);

		return activation(input);
	}

	public double errorPropagationLastLayer(int goal, double[] lastInputs, double lastOutput) throws NeuronException{

		double input = calcInput(lastInputs);

		double error = (goal - lastOutput)*derivateActivation(input);
		
		if (true)
			s = "neuron error propagation, lastoutput : "+lastOutput+", goal : "+goal+", error : "+error;

		calcErrors(error, lastInputs);
		
		return error;
	}

	public double errorPropagation(double followingOutputError, double[] lastInputs) throws NeuronException{
		
		double input = calcInput(lastInputs);
		
		double error = followingOutputError*derivateActivation(input);
		
		calcErrors(error, lastInputs);
		
		return error;
	}

	private double calcInput(double[] lastInputs) throws NeuronException{
		if (lastInputs.length != weights.length)
			throw new NeuronException("invalid parameters in 'calcInput()', lastInputs length : "+lastInputs.length+", weights length : "+weights.length);

		double input = bias;
		for (int i = 0; i < weights.length; i++)
			input += weights[i]*lastInputs[i];

		return input;
	}

	private void calcErrors(double error, double[] lastInputs){
		double[] deltaError = new double[deltaErrorsTotal.length];
		//erreur du biais
		deltaError[0] = error*1;
		addError(0, deltaError);
		//erreurs des poids
		for (int i = 1; i < deltaErrorsTotal.length; i++){
			deltaError[i] = error*lastInputs[i-1];
			addError(i, deltaError);
			if (false)// && i == 1)
				System.out.println(s+" neuron error calculation, delta error : "+deltaError[i]+", "+deltaErrorsTotal[i]+", total : "+deltaErrorsTotal[i]+", last input : "+lastInputs[i-1]);
		}
		nbOfErrors++;
	}
	
	private void addError(int i, double[] deltaError){
		deltaErrorsTotal[i] = (deltaErrorsTotal[i]*nbOfErrors + deltaError[i])/(nbOfErrors+1);
	}


	public double getBias() {
		return bias;
	}

	public double[] getWeights() {
		return weights;
	}

	public double getWeight(int index) {
		return weights[index];
	}

	public void pushErrors(double learningCoef, double momentumCoef) throws NeuronException{
		nbOfErrors = 0;
		double tmp = weights[0];
		//on applique les changements à chaque poids, y compris le biais (poid 0)
		deltaErrorsTotal[0] = (learningCoef*deltaErrorsTotal[0]) + (momentumCoef*lastDeltaErrorsTotal[0]);
		bias += deltaErrorsTotal[0];
		for (int i = 1; i < deltaErrorsTotal.length; i++){
			deltaErrorsTotal[i] = (learningCoef*deltaErrorsTotal[i]) + (momentumCoef*lastDeltaErrorsTotal[i]);
			weights[i-1] += deltaErrorsTotal[i];
		}
		lastDeltaErrorsTotal = deltaErrorsTotal;
		if (debug)
			System.out.println(" delta error : "+deltaErrorsTotal[1]+", weight : "+tmp+" --> "+weights[0]);
		deltaErrorsTotal = new double[deltaErrorsTotal.length];
	}

	public abstract double activation(double input);

	public abstract double derivateActivation(double input);
}