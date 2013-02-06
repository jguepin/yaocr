/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.control;

import yaocr.model.ImageManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Nicolas DELSAUT
 */
public class GridKeyControl implements KeyListener{
    
    private ImageManager mod;
    
    public GridKeyControl(ImageManager im){
        mod = im;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'd' || e.getKeyChar() == 'j'){
            mod.nextImage();
        }else if(e.getKeyChar() == 'q' || e.getKeyChar() == 'k'){
            mod.previousImage();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
