/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.model;

import yaocr.Image;
import yaocr.exceptions.ImageException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class ImageManager extends Observable implements Serializable {

    private ArrayList<Image> listImage;
    private int currentImage = 0;
    private int currentNumber = -1;
    
    private boolean brushSelected = false;
    public ImageManager(ArrayList<Image> im) {
        listImage = im;
    }

    /**
     * Indique le nombre d'images qui ont été chargées
     * @return le nombre d'images
     */
    public int getNumberImage() {
        return listImage.size();
    }
    
    public int[] getGoals(){
    	int[] goals = new int[getNumberImage()];
    	for (int i = 0; i < getNumberImage(); i++)
    		goals[i] = listImage.get(i).getNumber();
    	
    	return goals;
    }
    
    public int[] getGoals(int reducedSize){
    	if (reducedSize < 1 || reducedSize > getNumberImage())
    		reducedSize = getNumberImage();
    	
    	int[] goals = new int[reducedSize];
    	for (int i = 0; i < reducedSize; i++)
    		goals[i] = listImage.get(i).getNumber();
    	
    	return goals;
    }
    
    public int[][] getSamples(){
    	int[][] samples = new int[getNumberImage()][listImage.get(0).getPixels().length];
    	
    	for (int i = 0; i < getNumberImage(); i++)
    		samples[i] = listImage.get(i).getPixels();
    	
    	return samples;
    }
    
    public int[][] getSamples(int reducedSize){
    	if (reducedSize < 1 || reducedSize > getNumberImage())
    		reducedSize = getNumberImage();
    	int[][] samples = new int[reducedSize][listImage.get(0).getPixels().length];
    	
    	for (int i = 0; i < reducedSize; i++)
    		samples[i] = listImage.get(i).getPixels();
    	
    	return samples;
    }

    /**
     * Retourne l'image courante
     * @return l'image courante
     */
    public Image getCurrentImage() {
        return listImage.get(currentImage);
    }
    
    /**
     * Retourne l'index de l'image courante
     * @return l'index courant
     */
    public int getCurrentIndex(){
        return currentImage;
    }

    /**
     * Indique l'index de l'image courante
     * @param index index de l'image courante
     */
    public void setCurrentImage(int index) {
        currentImage = index;
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode qui passe à l'image suivante
     * (cf setCurrentImage)
     */
    public void nextImage() {
        if (currentImage < listImage.size()-1) {
            setCurrentImage(currentImage + 1);
        }
    }

     /**
     * Méthode qui passe à l'image précédente
     * (cf setCurrentImage)
     */
    public void previousImage() {
        if (currentImage > 0) {
            setCurrentImage(currentImage - 1);
        }
    }
    
    /**
     * Ajout d'une nouvelle image vierge et place à l'index sur cette dernière
     */
    public void addImage(){
        listImage.add(new Image());
        setCurrentImage(listImage.size()-1);
    }
    
    
    public void drawWithPen(int index){
        try {   
                if(brushSelected){
                    listImage.get(currentImage).drawWithBrush(index);
			
                }else{
                    listImage.get(currentImage).drawWithPen(index);
                }
		} catch (ImageException e) {
			e.printStackTrace();
		}
        setChanged();
        notifyObservers();
    }

    public void clearImage() {
        listImage.get(currentImage).resetImage();
        setChanged();
        notifyObservers();
    }

    public void setCurrentNumber(int number){
        this.currentNumber = number;
        setChanged();
        notifyObservers();
    }

    public int getCurrentNumber(){
        return this.currentNumber;
    }

    public boolean isBrushSelected() {
        return brushSelected;
    }

    public void setBrushSelected(boolean brushSelected) {
        this.brushSelected = brushSelected;
    }

   
}
