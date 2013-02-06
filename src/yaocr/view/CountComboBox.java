/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import yaocr.model.ImageManager;

/**
 *
 * @author Nicolas DELSAUT
 */
public class CountComboBox extends JComboBox<Integer> implements Observer {

    private ImageManager modele;
    private ComboBoxEditor cbe;
    private JTextField textField;

    public CountComboBox(ImageManager im) {
        modele = im;
        this.setEditable(true);
        this.setMaximumSize(new Dimension(20, 20));
        //Récupération de l'équivalent JTextField
        cbe = this.getEditor();
        textField = (JTextField) cbe.getEditorComponent();
        //On fixe la largeur
        textField.setColumns(3);

        im.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //this.setSelectedIndex(modele.getCurrentIndex()); OMG too slow
        textField.setText(""+modele.getCurrentIndex());
    }

    //Ajout de la liste des nombres
    public void updateComboList(){
        for (int i = 0; i < modele.getNumberImage(); ++i) {
            this.addItem(i);
        }
        repaint();
    }
}
