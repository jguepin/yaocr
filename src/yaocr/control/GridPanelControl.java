/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yaocr.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.PortableServer.POAManagerPackage.State;
import yaocr.model.ImageManager;

/**
 *
 * @author Nicolas DELSAUT
 */
public class GridPanelControl implements MouseListener,Runnable {

    private ImageManager modele;
    private int index;
    
    private boolean mouseHold= false;
    private boolean isIn = true;
    
    private boolean holding;
    private int seconds;
    private Thread thread;
    
    public GridPanelControl(int indexGrid, ImageManager mod) {
        modele = mod;
        index = indexGrid;
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
         
         
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseHold=true;
        thread = new Thread(this);
        thread.start();
            //thread = new Thread(this);
              //  thread.start();
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        mouseHold= false;
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isIn = true;
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            mouseHold=true;
            thread = new Thread(this);
            thread.start();
            
            
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
        isIn= false;
       //if(thread!=null){
         //   if(thread.isAlive())
//                thread.suspend();
        // }
    
    }
    
    
    public void run(){
         try
        {
            while(mouseHold &&isIn){
               modele.drawWithPen(index);
               
                Thread.sleep(500);
            }
        }
        catch (InterruptedException ex) {
            Logger.getLogger(GridPanelControl.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
}
