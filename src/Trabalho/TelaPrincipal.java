/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andre
 */
public class TelaPrincipal {
    
    HistogramaRGB histRGB = new HistogramaRGB();

    //cria o painel do bottom 
    private JPanel criaPainel() {
        JPanel panel = new JPanel();
        JComboBox jBoxImagens = new JComboBox();
        JButton gerarHist = new JButton("Histograma");
        JButton equalHist = new JButton("Equalizar");
        JButton abrirImag = new JButton("Abrir");
//        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(0)));
//        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(1)));
//        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(2)));        
        panel.add(jBoxImagens);
        panel.add(abrirImag);
        panel.add(gerarHist);
        panel.add(equalHist);
        return panel;
    }

    //exibe a tela
    private void mostraPainel() {
        JFrame f = new JFrame("Trabalho de PDI");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(criaPainel(), BorderLayout.NORTH);
        f.pack();
        f.setLocationRelativeTo(f);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new TelaPrincipal().mostraPainel();
        });
    }

}
