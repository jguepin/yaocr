/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.view;

import yaocr.Learning;
import yaocr.Testing;
import yaocr.control.ComboControl;
import yaocr.control.GridKeyControl;
import yaocr.control.NavigationControl;
import yaocr.model.ImageManager;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Nicolas DELSAUT
 */
public class MainFrame extends JFrame {

    private CountComboBox jcb;

    public MainFrame(ImageManager im, Learning learning, Testing testing) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing
                    .UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        this.addKeyListener(new GridKeyControl(im));
        
        //Parametrage de la frame
        this.setTitle("Yaocr");
        this.setResizable(false);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(1, 2));
        
        /*//Menu
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("Learning");
        JMenuItem jmil = new JMenuItem("Load file");
        JMenuItem jmis = new JMenuItem("Start learning");
        jm.add(jmil);
        jm.add(jmis);
        jmb.add(jm);
        //this.setJMenuBar(jmb);*/

        //Partie gauche de l'IHM
        JPanel partieGauche = new JPanel();
        partieGauche.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Panel qui affiche les chiffres
        PanelGrid pg = new PanelGrid(im);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        partieGauche.add(pg, c);

        //Panel avec les boutons Précédent/Suivant
        JPanel buttonPN = new JPanel();
        JButton butPrev = new JButton("Previous (q)");
        butPrev.addActionListener(new NavigationControl(im, false));
        butPrev.setFocusable(false);
        buttonPN.add(butPrev);
        butPrev = new JButton("Next (d)");
        butPrev.addActionListener(new NavigationControl(im, true));
        butPrev.setFocusable(false);
        buttonPN.add(butPrev);
        //ComboBox
        jcb = new CountComboBox(im);
        jcb.addItemListener(new ComboControl(im));
        buttonPN.add(jcb);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        partieGauche.add(buttonPN, c);

        this.add(partieGauche);
        
        //Partie droite avec les onglets
        JTabbedPane jtp = new JTabbedPane();
        
        //Panel d'apprentissage
        JPanel learnPanel = new JPanel();
        learnPanel.add(new PanelLearning(learning));
        learnPanel.setSize(400,400);
        jtp.addTab("Learning", null, learnPanel, null);
        
        //Panel d'analyse
        JPanel recognitionPanel = new JPanel();
        recognitionPanel.add(new PanelAnalyze(im, learning, testing));
        jtp.addTab("Analyse", null, recognitionPanel, null);
        
        //Panel de dessin
        JPanel drawPanel = new JPanel();
        drawPanel.setSize(400, 400);
        drawPanel.add(new PanelDrawing(im));
        jtp.addTab("Drawing", null, drawPanel, null);
        
        this.add(jtp);

        //Calcul des dimensions
        pack();

    }

    public void updateComboList(){
        jcb.updateComboList();
    }
}
