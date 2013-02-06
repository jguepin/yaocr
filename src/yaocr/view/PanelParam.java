/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.view;

import java.util.logging.Level;
import java.util.logging.Logger;
import yaocr.Learning;
import yaocr.exceptions.NetworkManagerException;
import yaocr.model.neuralNetwork.Network;

/**
 *
 * @author Nicolas DELSAUT
 */
public class PanelParam extends javax.swing.JPanel {

    /**
     * Creates new form PanelParam
     */
    public PanelParam() {
        initComponents();
    }

    public void setParam(Learning l) {
        int tab[] = new int[4];
        try {
            tab[0] = Integer.parseInt(jtxtCouche1.getText());
        } catch (Exception e) {
            tab[0] = 0;
        }
        try {
            tab[1] = Integer.parseInt(jtxtCouche2.getText());
        } catch (Exception e) {
            tab[1] = 0;
        }
        try {
            tab[2] = Integer.parseInt(jtxtCouche3.getText());
        } catch (Exception e) {
            tab[2] = 0;
        }
        try {
            tab[3] = Integer.parseInt(jtxtCoucheF.getText());
        } catch (Exception e) {
            tab[3] = 0;
        }

        int count = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] > 0) {
                count++;
            }
        }
        int[] layersSize = new int[count];
        int count2 = 0;
        for (int i = 0; i < tab.length; i++){
            if (tab[i]!= 0){
                //count2++;
            //else{
                layersSize[count2] = tab[i];
                count2++;
            }
        }
        int activ ;
        switch(jComboBox1.getSelectedIndex()){
            case 0 : activ = Network.TANH; break;
            case 1 : activ = Network.SIGMOID; break;
            default: activ = Network.IDENTITY; break;
        }
        try {
            l.setParam(Double.parseDouble(jtxtLearning.getText()), layersSize, Integer.parseInt(jtxtIteration.getText()), activ, Double.parseDouble(jtxtMom.getText()), jCheckBox1.isSelected());
        } catch (NetworkManagerException ex) {
            Logger.getLogger(PanelParam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtLearning = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtMom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtxtCouche1 = new javax.swing.JTextField();
        jtxtCouche2 = new javax.swing.JTextField();
        jtxtCouche3 = new javax.swing.JTextField();
        jtxtCoucheF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtIteration = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();

        jLabel1.setText("Learning");

        jtxtLearning.setText("0.3");

        jLabel2.setText("Momentum");

        jtxtMom.setText("0.5");

        jLabel3.setText("Fct d'activation");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TanH", "Sigmoid", "Identity" }));

        jLabel4.setText("Couche 1");

        jLabel5.setText("Couche 2");

        jLabel6.setText("Couche 3");

        jLabel7.setText("Couche f.");

        jtxtCoucheF.setEditable(false);
        jtxtCoucheF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtCoucheF.setText("10");

        jLabel8.setText("Itérations");

        jtxtIteration.setText("500");

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Batch");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtCouche3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtCouche1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtCouche2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtCoucheF))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtxtLearning))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtIteration, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtMom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtLearning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtMom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtxtIteration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtCouche1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtCouche2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtCouche3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtCoucheF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jtxtCouche1;
    private javax.swing.JTextField jtxtCouche2;
    private javax.swing.JTextField jtxtCouche3;
    private javax.swing.JTextField jtxtCoucheF;
    private javax.swing.JTextField jtxtIteration;
    private javax.swing.JTextField jtxtLearning;
    private javax.swing.JTextField jtxtMom;
    // End of variables declaration//GEN-END:variables
}