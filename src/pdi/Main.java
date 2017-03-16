package pdi;

import javax.swing.JFrame;

/**
 *
 * @author andre
 */
public class Main {

    public static void main(String[] args) {
        TelaPrincipal tp = new TelaPrincipal();
//        tp.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tp.setSize(800,600);
        tp.setVisible(true);
    }
    
}
