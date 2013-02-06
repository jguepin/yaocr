package yaocr.control;

import yaocr.Testing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalyzeTestControl implements ActionListener {

    private Testing testing;

    public AnalyzeTestControl(Testing testing){
        this.testing = testing;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Thread t = new Thread(testing);
        t.start();
    }
}
