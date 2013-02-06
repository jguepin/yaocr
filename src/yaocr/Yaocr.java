package yaocr;

import yaocr.model.ImageManager;
import yaocr.view.MainFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Yaocr {

    public static String trainImagesFile = "data/train-images.gz";
    public static String trainLabelsFile = "data/train-labels.gz";
    public static String testImagesFile = "data/test-images.gz";
    public static String testLabelsFile = "data/test-labels.gz";

    public static void main(String[] args){
        ArrayList<Image> images = new ArrayList<>();
        ImageManager im = new ImageManager(images);

        getImages(images);

        int reducedSize = 10000;
        Learning learning = new Learning(im, reducedSize);
        Testing testing = new Testing(testImagesFile, testLabelsFile, learning);

        MainFrame mf = new MainFrame(im, learning, testing);
        mf.setVisible(true);
        mf.updateComboList();

    }

    /**
     * read images data from input files
      */
    public static void getImages(ArrayList<Image> images){
        System.out.print("Reading input files... ");

        try {
            BufferedReader trainImagesBr = new BufferedReader(new FileReader(trainImagesFile));
            BufferedReader trainLabelsBr = new BufferedReader(new FileReader(trainLabelsFile));
            String line;

            // read image pixels
            while((line = trainImagesBr.readLine()) != null){
                Image i = new Image(line.split(" "));
                images.add(i);
            }

            // read image labels
            int i = 0;
            while((line = trainLabelsBr.readLine()) != null){
               images.get(i++).setNumber(Integer.valueOf(line));
            }
            trainImagesBr.close();
            trainLabelsBr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("End reading!");

    }

    public static int[][] createSamples(){
    	int nb = 2;
    	int size = 2;
    	int[][] res = new int[nb][size];
    	for (int i = 0; i < nb; i++){
			if (Learning.modulo(i, 2) == 0)
				res[i] = new int[] {1,1,1,-1,-1,-1};
			else 
				res[i] = new int[] {-1,-1,-1,1,1,1};
    	}
    	return res;
    }
    
    public static int[] createGoals(){
    	return new int[] {1,0};
    }
}
