package yaocr.model.neuralNetwork;

import yaocr.exceptions.LayerException;
import yaocr.exceptions.NetworkException;

import java.io.Serializable;

public class Network implements Serializable {
	
	public static final int IDENTITY = 1;
	public static final int SIGMOID = 2;
	public static final int TANH = 3;
	
	private Layer[] layers;
	private double[] lastFinalOutputs;
	private double learningCoef;
	private double momentumCoef;

	/**
	 * initialise le réseau
	 * @param layerSizes taille de chaque couche dans l'ordre
	 * @param inputSize taille du tableau d'entrée
	 * @param activationSort détermine quelle fonction d'activation utilise le réseau
	 * @param learningCoef coefficient d'apprentissage utilisé par le réseau
	 * @throws NetworkException
	 */
	public Network(int[] layerSizes, int inputSize, int activationSort, double learningCoef, double momentumCoef) throws NetworkException {
		this.learningCoef = learningCoef;
		this.momentumCoef = momentumCoef;
		
		layers = new Layer[layerSizes.length];
		
		try {
			if (layerSizes.length == 1){
				layers[0] = new LastLayer(layerSizes[0], inputSize, activationSort);
			}else{
				layers[0] = new MiddleLayer(layerSizes[0], inputSize, activationSort);
				layers[layerSizes.length - 1] = new LastLayer(layerSizes[layerSizes.length - 1], layerSizes[layerSizes.length - 2], activationSort);
			}
		} catch (LayerException e) {
			throw new NetworkException("Error in constructor for first middle layer or last layer ["+e.getMessage()+"]");
		}
		
		for (int i = 1; i < layerSizes.length - 1; i++){
			try {
				layers[i] = new MiddleLayer(layerSizes[i], layerSizes[i-1], activationSort);
			} catch (LayerException e) {
				throw new NetworkException("Error in constructor for middle layer #"+i+" ["+e.getMessage()+"]");
			}
		}
	}
	/**
	 * propage le calcul dans le réseau, garde le dernier output en mémoire
	 * @param inputs valeurs d'entrée
	 * @return the output tab
	 * @throws NetworkException
	 */
	public double[] process(double[] inputs) throws NetworkException{
		lastFinalOutputs = inputs;
		for (int i = 0; i < layers.length; i++){
			try {
				lastFinalOutputs = layers[i].process(lastFinalOutputs);
			} catch (LayerException e) {
				throw new NetworkException("Error in 'process()' for layer #"+i+" ["+e.getMessage()+"]");
			}
		}
		return lastFinalOutputs;
	}
	
	/**
	 * en partant du dernier output, et du résultat attendu, propage le calcul de l'erreur en sens inverse dans le réseau <br/>
	 * les neuronnes stockent l'erreur calculée
	 * @param goals résultat attendu
	 * @throws NetworkException
	 */
	public void errorPropagation(int[] goals) throws NetworkException{
		double[] followingErrors;
		try {
			followingErrors = ((LastLayer) layers[layers.length - 1]).errorPropagation(goals, lastFinalOutputs);
		} catch (LayerException e) {
			throw new NetworkException("Error in 'errorPropagation()' for the last layer ["+e.getMessage()+"]");
		}
		for (int i = layers.length -2; i >= 0; i--){
			try {
				double[][] neuronsWeights = new double[layers[i+1].getNeurons().length][layers[i+1].getNeuron(0).getWeights().length];
				for (int m = 0; m < layers[i+1].getNeurons().length; m++){
					if (layers[i+1].getNeuron(m).getWeights().length != layers[i+1].getNeuron(0).getWeights().length)
						throw new NetworkException("In layers["+(i+1)+"], neurons["+m+"].weights.length : "+layers[i+1].getNeuron(m).getWeights().length+",  neurons["+0+"].weights.length : "+layers[i+1].getNeuron(0).getWeights().length);
					neuronsWeights[m] = layers[i+1].getNeuron(m).getWeights();
				}
				followingErrors = ((MiddleLayer) layers[i]).errorPropagation(followingErrors, neuronsWeights);
			} catch (LayerException e) {
				throw new NetworkException("Error in 'errorPropagation()' for the layer #"+i+" ["+e.getMessage()+"]");
			}
		}
	}
	
	/**
	 * les neurones ajoutent la moyenne de toutes les erreurs calcuées depuis le dernier push à leurs poids
	 * @throws NetworkException
	 */
	public void pushErrors() throws NetworkException{
		for (int i = 0; i < layers.length; i++){
			try {
				layers[i].pushErrors(learningCoef, momentumCoef);
			} catch (LayerException e) {
				throw new NetworkException("Error in 'pushErrors()' in layer #"+i+"["+e.getMessage()+"]");
			}
		}
	}
	public double getLearningCoef() {
		return learningCoef;
	}
	public void setLearningCoef(double learningCoef) {
		this.learningCoef = learningCoef;
	}
	public Layer[] getLayers() {
		return layers;
	}

}
