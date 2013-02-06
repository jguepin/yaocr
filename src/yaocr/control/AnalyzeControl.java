package yaocr.control;

import yaocr.Learning;
import yaocr.exceptions.NetworkManagerException;
import yaocr.model.ImageManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: julien
 * Date: 26/01/13
 * Time: 00:38
 */
public class AnalyzeControl implements ActionListener {

    private ImageManager im;
    private Learning learning;

    public AnalyzeControl(ImageManager im, Learning learning){
        this.im = im;
        this.learning = learning;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            learning.test(im.getCurrentImage().getPixels());
        } catch (NetworkManagerException e) {
            e.printStackTrace();
        }
    }
}
