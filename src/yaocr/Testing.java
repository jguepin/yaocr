package yaocr;

import yaocr.exceptions.NetworkManagerException;

import java.io.*;
import java.util.ArrayList;

public class Testing implements Runnable {

    private String testImages;
    private String testLabels;
    private Learning learning;

    public Testing(String testImages, String testLabels, Learning learning){
        this.testImages = testImages;
        this.testLabels = testLabels;
        this.learning = learning;
    }

    @Override
    public void run() {
        try {
            ArrayList<Image> images = new ArrayList<>();
            this.getTests(images);
            for(Image i: images){
                i.setNumber(learning.test(i.getPixels()));
            }
            writeTests(images);
        } catch (NetworkManagerException e) {
            e.printStackTrace();
        }
    }

    public void getTests(ArrayList<Image> testsImages){
        System.out.print("Reading test file... ");

        try {
            BufferedReader testImagesBr = new BufferedReader(new FileReader(testImages));
            String line;

            // read test pixels
            while((line = testImagesBr.readLine()) != null){
                Image i = new Image(line.split(" "));
                testsImages.add(i);
            }

            testImagesBr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End reading!");
    }

    public void writeTests(ArrayList<Image> tests){
        System.out.print("Writing test result file... ");

        try {
            BufferedWriter testImagesBw = new BufferedWriter(new FileWriter(testLabels));
            for(Image i: tests){
                testImagesBw.write(i.getNumber() + "\n");
            }
            testImagesBw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("End writing!");
    }
}
