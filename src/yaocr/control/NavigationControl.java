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
public class NavigationControl implements ActionListener{

    private boolean mustNext;
    private ImageManager modele;
    
    /**
     * Constructeur
     * @param next si le controleur doit aller de l'avant
     */
    public NavigationControl(ImageManager mod, boolean next){
        mustNext = next;
        modele = mod;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(mustNext){
            modele.nextImage();
        }else{
            modele.previousImage();
        }
    }
    
}
