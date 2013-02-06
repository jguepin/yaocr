package yaocr.model;

import yaocr.exceptions.NetworkException;
import yaocr.exceptions.NetworkManagerException;
import yaocr.model.neuralNetwork.Network;

import java.io.Serializable;

public class NetworkManager implements Serializable {

	private Network network;

	/**
	 * initialise un nouveau réseau de neurones
	 * @param layerSizes taille de chaque couche dans l'ordre
	 * @param inputSize taille du tableau d'entrée
	 * @param activationSort détermine quelle fonction d'activation utilise le réseau
	 * @param learningCoef coefficient d'apprentissage utilisé par le réseau
	 * @throws NetworkManagerException
	 */
	public NetworkManager(int[] layerSizes, int inputSize, int activationSort, double learningCoef, double momentumCoef) throws NetworkManagerException {
		initNetwork(layerSizes, inputSize, activationSort, learningCoef, momentumCoef);
	}
	/**
	 * initialise un nouveau réseau de neurones
	 * @param layerSizes taille de chaque couche dans l'ordre, la dernière taille correspond au nombre de sorties
	 * @param inputSize taille du tableau d'entrée
	 * @param activationSort détermine quelle fonction d'activation utilise le réseau
	 * @param learningCoef coefficient d'apprentissage utilisé par le réseau
	 * @throws NetworkManagerException
	 */
	public void initNetwork(int[] layerSizes, int inputSize, int activationSort, double learningCoef, double momentumCoef) throws NetworkManagerException{
		try {
			this.network = new Network(layerSizes, inputSize, activationSort, learningCoef, momentumCoef);
		} catch (NetworkException e) {
			throw new NetworkManagerException(e.getMessage());
		}
	}
	/**
	 * modifie le coefficient d'apprentissage
	 * @param learningCoef
	 */
	public void changeLearningCoef(double learningCoef){
		network.setLearningCoef(learningCoef);
	}
	
	public double getLearningCoef(){
		return network.getLearningCoef();
	}
	/**
	 * le réseau apprend sur un seul échantillon
	 * @param input l'échantillon d'apprentissage
	 * @param goal le numéro de la sortie attendue
	 * @param pushError détermine si le réseau applique les modification sur ses poids à la fin de cet apprentissage
	 * @throws NetworkManagerException
	 */
	public void learn(double[] input, int goal, boolean pushError) throws NetworkManagerException{
		learn(input, convert(goal), pushError);
	}

	/**
	 * le réseau apprend sur un seul échantillon
	 * @param input l'échantillon d'apprentissage
	 * @param goal le résultat attendu sous forme d'un tableau
	 * @param pushError détermine si le réseau applique les modification sur ses poids à la fin de cet apprentissage
	 * @throws NetworkManagerException
	 */
	public void learn(double[] input, int[] goal, boolean pushError) throws NetworkManagerException{
		try {
			network.process(input);
			network.errorPropagation(goal);
			if (pushError)
				pushErrors();
		} catch (NetworkException e) {
			throw new NetworkManagerException(e.getMessage());
		}
	}

	/**
	 * le réseau apprend sur l'ensemble des échantillons
	 * @param inputs les échantillons d'apprentissage
	 * @param goals les numéros de chaque sortie attendue
	 * @param pushErrorEachTime détermine si le réseau applique les modification sur ses poids à chaque apprentissage
	 * @param pushErrorAtTheEnd détermine si le réseau applique les modification sur ses poids à la fin de tous les apprentissages
	 * @throws NetworkManagerException
	 */
	public void learnAll(double[][] inputs, int[] goals, boolean pushErrorEachTime, boolean pushErrorAtTheEnd) throws NetworkManagerException{
		learnAll(inputs, convert(goals), pushErrorEachTime, pushErrorAtTheEnd);
	}

	/**
	 * le réseau apprend sur l'ensemble des échantillons
	 * @param inputs les échantillons d'apprentissage
	 * @param goals les objectifs sous forme de tableaux
	 * @param pushErrorEachTime détermine si le réseau applique les modification sur ses poids à chaque apprentissage
	 * @param pushErrorAtTheEnd détermine si le réseau applique les modification sur ses poids à la fin de tous les apprentissages
	 * @throws NetworkManagerException
	 */
	public void learnAll(double[][] inputs, int[][] goals, boolean pushErrorEachTime, boolean pushErrorAtTheEnd) throws NetworkManagerException{
		if (inputs.length != goals.length)
			throw new NetworkManagerException("Error in 'runAll()', inputs length : "+inputs.length+", goals length : "+goals.length);

		for (int i = 0; i < inputs.length; i++)
			learn(inputs[i], goals[i], pushErrorEachTime);

		if (pushErrorAtTheEnd)
			pushErrors();
	}

	/**
	 * renvoie la sortie du réseau sur un échantillon
	 * @param input l'échantillon à tester
	 * @return la sortie sous forme d'un tableau
	 * @throws NetworkManagerException
	 */
	public double[] testDetails(double[] input) throws NetworkManagerException{
		try {
			return network.process(input);
		} catch (NetworkException e) {
			throw new NetworkManagerException(e.getMessage());
		}
	}

	/**
	 * renvoie la sortie du réseau sur un échantillon
	 * @param input l'échantillon à tester
	 * @return le numéro de la sortie
	 * @throws NetworkManagerException
	 */
	public int test(double[] input) throws NetworkManagerException{
		return convert(testDetails(input));
	}

	/**
	 * le réseau applique les modifications sur ses poids
	 * @throws NetworkManagerException
	 */
	public void pushErrors() throws NetworkManagerException{
		try {
			network.pushErrors();
		} catch (NetworkException e) {
			throw new NetworkManagerException(e.getMessage());
		}
	}

	private int[] convert(int goal) throws NetworkManagerException{
		if (goal < 0 || goal >= network.getLayers()[network.getLayers().length-1].getNeurons().length)
			throw new NetworkManagerException("Invalid goal  in 'convert()' : "+goal+" ; "+network.getLayers()[network.getLayers().length-1].getNeurons().length);
		
		int[] goals = new int[network.getLayers()[network.getLayers().length-1].getNeurons().length];

		for (int i = 0; i < goals.length; i++)
			goals[i] = -1;

		goals[goal] = 1;
		
		return goals;
	}
	
	private int convert(double[] output){
		int max = 0;
		for (int i = 1; i < output.length; i++){
			if (output[i] > output[max])
				max = i;
		}
		return max;
	}
	
	private int[][] convert(int[] goals) throws NetworkManagerException{
		int[][] newGoals = new int[goals.length][network.getLayers()[network.getLayers().length-1].getNeurons().length];
		
		for (int i = 0; i < newGoals.length; i++)
			newGoals[i] = convert(goals[i]);
		
		return newGoals;
	}



}
