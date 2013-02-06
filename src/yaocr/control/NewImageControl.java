/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yaocr.model.ImageManager;

/**
 *
 * @author Nicolas DELSAUT
 */
public class NewImageControl implements ActionListener {
    
    private ImageManager mod;
    
    public NewImageControl(ImageManager im){
        mod = im;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mod.addImage();
    }
    
}
