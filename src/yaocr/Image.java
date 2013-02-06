package yaocr;

import yaocr.exceptions.ImageException;

import java.io.Serializable;

public class Image implements Serializable {
    public static final int[] H_TAB ={-1,0,+1};
    public static final int[] V_TAB ={-28,0,+28};
	public static final int IMAGE_SIZE = 28*28;
    private int[] pixels;
    private int number;

    public Image(){
        resetImage();
    }

    public Image(String[] pixelsChars){
    	pixels = new int[IMAGE_SIZE];
        for(int i = 0; i < pixelsChars.length; i++)
            pixels[i] = Integer.valueOf(pixelsChars[i]);
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void drawWithPen(int index) throws ImageException {
    	if (index >= IMAGE_SIZE)
    		throw new ImageException("Invalid index in 'drawWithPen()' : "+index+" sould be < "+IMAGE_SIZE);
    	
        pixels[index] = 255;
    }

    public void drawWithBrush(int index) throws ImageException {
        if (index >= IMAGE_SIZE)
    		throw new ImageException("Invalid index in 'drawWithBrush()' : "+index+" sould be < "+IMAGE_SIZE);
        for(int i =0;i<H_TAB.length;i++){
            for (int j = 0;j < V_TAB.length;j++){
                int ind = index+H_TAB[i]+V_TAB[j];
                if(ind < IMAGE_SIZE && ind >=0 ){
                    if (i ==1 && j ==1){
                            pixels[ind] = Math.min(255, 100+pixels[ind]);
                        }
                        pixels[ind] = Math.min(255, 50+pixels[ind]);
                }
            }
        }
    }
    public void resetImage() {
    	pixels = new int[IMAGE_SIZE];
        for(int i = 0; i < IMAGE_SIZE; i++)
            pixels[i] = 0;
    }
}
