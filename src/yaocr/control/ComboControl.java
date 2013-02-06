/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.control;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import yaocr.model.ImageManager;

/**
 *
 * @author Nicolas DELSAUT
 */
public class ComboControl implements ItemListener {

    private ImageManager modele;

    public ComboControl(ImageManager im) {
        modele = im;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            modele.setCurrentImage((Integer) (e.getItem()));
        } catch (IndexOutOfBoundsException | ClassCastException ex) {
            System.err.println("Saisie stupide de l'utilisateur");
        }
    }
}
