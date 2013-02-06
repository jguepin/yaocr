package yaocr;

import yaocr.exceptions.NetworkManagerException;
import yaocr.model.ImageManager;
import yaocr.model.NetworkManager;
import yaocr.model.neuralNetwork.Network;

import java.io.*;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Learning extends Observable implements Runnable, Serializable {

    private double learningCoef;
    private double momentumCoef;
    private int[] layerSizes;
    private int activationSort;
    private int iterations;
    private int count;
    private boolean online;
    private double[][] samples;
    private double[][] testSamples;
    private int[] goals;
    private int[] testGoals;
    private NetworkManager netwManager;
    private ImageManager imageManager;
    private boolean running = true;
    private String lastInfo = "";

    public Learning(ImageManager imageManager, int reducedSize) {
        this(imageManager.getSamples(reducedSize), imageManager.getGoals(reducedSize));
        this.imageManager = imageManager;
    }

    public Learning(int[][] samples, int[] goals) {
        int sampleLength = 4 * samples.length / 5;
        int testLength = samples.length - sampleLength;
        
        double[][] tmpSamples = convertIntToDouble(samples);
        int[] tmpGoals = goals;
        
        this.testGoals = new int[testLength];
        this.testSamples = new double[testLength][samples[0].length];
        this.samples = new double[sampleLength][samples[0].length];
        this.goals = new int[sampleLength];
        for (int i = sampleLength; i < sampleLength + testLength; i++){
            this.testSamples[i - sampleLength] = tmpSamples[i];
            this.testGoals[i - sampleLength] = tmpGoals[i];
        }
        for (int i = 0; i < sampleLength; i++){
            this.samples[i] = tmpSamples[i];
            this.goals[i] = tmpGoals[i];
        }
        
        this.learningCoef = 0.3;
        this.momentumCoef = 0.5;
        this.iterations = 500;
        this.online = false;
        this.activationSort = Network.TANH;
        this.layerSizes = new int[2];
        layerSizes[0] = 60;
        layerSizes[1] = 10;
        //layerSizes[2] = 10;
        try {
            this.netwManager = new NetworkManager(layerSizes, samples[0].length, activationSort, learningCoef, momentumCoef);
        } catch (NetworkManagerException e) {
            e.printStackTrace();
        }
    }

    public Learning(int[][] samples, int[] goals, double learningCoef, int[] layerSizes, int iterations, boolean online, int activationSort) throws NetworkManagerException {
    	int sampleLength = 4 * samples.length / 5;
        int testLength = samples.length - sampleLength;
        
        double[][] tmpSamples = convertIntToDouble(samples);
        int[] tmpGoals = goals;
        
        this.testGoals = new int[testLength];
        this.testSamples = new double[testLength][samples[0].length];
        this.samples = new double[sampleLength][samples[0].length];
        this.goals = new int[sampleLength];
        for (int i = sampleLength; i < sampleLength + testLength; i++){
            this.testSamples[i - sampleLength] = tmpSamples[i];
            this.testGoals[i - sampleLength] = tmpGoals[i];
        }
        for (int i = 0; i < sampleLength; i++){
            this.samples[i] = tmpSamples[i];
            this.goals[i] = tmpGoals[i];
        }
        this.learningCoef = learningCoef;
        this.iterations = iterations;
        this.online = online;
        this.layerSizes = layerSizes;
        this.activationSort = activationSort;
        this.netwManager = new NetworkManager(layerSizes, samples[0].length, activationSort, learningCoef, momentumCoef);
    }

    /*public void learn() throws NetworkManagerException {
        System.out.print("start learning");
        System.out.println(", rate : " + test() + "%");
        //System.out.println(j+" goal : "+goals[j]+" -- > "+res);
        for (int i = 0; i < iterations; i++) {
            double tmp = netwManager.getLearningCoef();
            //netwManager.changeLearningCoef(learningCoef + 30*Math.exp(-i/8.0));
            netwManager.learnAll(samples, goals, online, !online);
            System.out.print("iteration : " + i + ", learning coef : " + tmp + " --> " + netwManager.getLearningCoef());
            System.out.println(", success rate : " + test() + "%");
            //learn(600);
            //System.out.println("iteration : "+i);
        }
    }*/

    private void learn(int rate) throws NetworkManagerException {
        for (int i = 0; i < samples.length; i++) {
            netwManager.learn(samples[i], goals[i], modulo(i, rate) == 0 && i != 0);
        }
    }

    private int testSample() throws NetworkManagerException {
        int count = 0;
        for (int j = 0; j < samples.length; j++) {
            int res = netwManager.test(samples[j]);
            if (goals[j] == res) {
                count++;
            }
        }
        return count * 100 / samples.length;
    }
    
    private int test() throws NetworkManagerException {
        int count = 0;
        for (int j = 0; j < testSamples.length; j++) {
            int res = netwManager.test(testSamples[j]);
            if (testGoals[j] == res) {
                count++;
            }
        }
        return count * 100 / testSamples.length;
    }

    public int test(int[] sample) throws NetworkManagerException {
        double[] input = convertIntToDouble(sample);
        int result = netwManager.test(input);
        imageManager.setCurrentNumber(result);
        return result;
    }

    public double[][] convertIntToDouble(int[][] input) {
        double[][] output = new double[input.length][input[0].length];
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                output[i][j] = input[i][j];
            }
        }
        return output;
    }

    public double[] convertIntToDouble(int[] input) {
        double[] output = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = input[i];
        }
        return output;
    }

    public static int modulo(int input, int modulo) {
        while (input < 0 || input >= modulo) {
            input += input < 0 ? modulo : -modulo;
        }
        return input;
    }
    
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            running = true;
            lastInfo = "start learning";
            lastInfo += ", success rate : " + testSample() + "% | " + test() + "%";
            setChanged();
            notifyObservers();
            //System.out.println(j+" goal : "+goals[j]+" -- > "+res);
            for (count = count; count < iterations &&  running; count++) {
        
                //netwManager.changeLearningCoef(learningCoef + 30*Math.exp(-i/8.0));
                netwManager.learnAll(samples, goals, online, !online);
                //System.out.print("iteration : " + count + ", learning coef : " + tmp + " --> " + netwManager.getLearningCoef());
                //System.out.println(", success rate : " + test() + "%");
                lastInfo = "iteration : " + count;
                lastInfo += ", success rate : " + testSample() + "% | " + test() + "%";
                //learn(600);
                //System.out.println("iteration : "+i);

                setChanged();
                notifyObservers();
            }
        } catch (NetworkManagerException ex) {
            Logger.getLogger(Learning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getCount() {
        return count;
    }
    
    public int getMax(){
        return iterations;
    }

    public String getLastInfo() {
        return lastInfo;
    }

    public void save(String filename) throws IOException {
        System.out.print("Begin of serialization...");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this);
        oos.close();
        System.out.println(" End of serialization!");
    }

    public void restore(String filename) throws IOException, ClassNotFoundException {
        System.out.print("Begin of deserialization...");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        Learning learning = ((Learning)ois.readObject());
        this.learningCoef = learning.getLearningCoef();
        this.momentumCoef = learning.getMomentumCoef();
        this.layerSizes = learning.getLayerSizes();
        this.activationSort = learning.getActivationSort();
        this.iterations = learning.getIterations();
        this.count = learning.getCount();
        this.online = learning.isOnline();
        this.netwManager = learning.getNetwManager();
        this.running = learning.isRunning();
        this.lastInfo = learning.getLastInfo();
        ois.close();
        setChanged();
        notifyObservers();
        System.out.println(" End of deserialization!");
    }

    public double getLearningCoef() {
        return learningCoef;
    }

    public double getMomentumCoef() {
        return momentumCoef;
    }

    public int[] getLayerSizes() {
        return layerSizes;
    }

    public int getActivationSort() {
        return activationSort;
    }

    public int getIterations() {
        return iterations;
    }

    public boolean isOnline() {
        return online;
    }

    public double[][] getSamples() {
        return samples;
    }

    public int[] getGoals() {
        return goals;
    }

    public NetworkManager getNetwManager() {
        return netwManager;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    public boolean isRunning() {
        return running;
    }

    public void setSamples(double[][] samples) {
        this.samples = samples;
    }

    public void setGoals(int[] goals) {
        this.goals = goals;
    }
    
    public void setParam(double learningCoef, int[] layerSizes, int iterations, int activationSort, double momentum, boolean online) throws NetworkManagerException{
    	this.online = online;
        this.learningCoef = learningCoef;
        this.iterations = iterations;
        this.layerSizes = layerSizes;
        this.activationSort = activationSort;
        this.momentumCoef = momentum;
        this.netwManager = new NetworkManager(layerSizes, samples[0].length, activationSort, learningCoef, momentumCoef);
    }
}
