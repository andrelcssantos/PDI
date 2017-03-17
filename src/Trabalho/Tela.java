/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Andre
 */
public class Tela extends JFrame{
    
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    private JButton btnHist, btn2, btn3, btn4, btn5, btn6, btnAbrir, btnLimpar;
    public Tela(){
        super("Trabalho de PDI");
        setLayout(layout); //configura o layout frame
        constraints = new GridBagConstraints(); //instancia restrições
        
        //cria componentes da GUI
        btnHist = new JButton();
    }
    
    public void exibeTela(){
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
