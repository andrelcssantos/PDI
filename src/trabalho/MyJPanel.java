/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho;

import com.sun.prism.Graphics;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author andre
 */
class MyJPanel extends JPanel{
    private static Random generator = new Random();
    private ImageIcon picture; //imagem a ser exibida
    private final static String[] images = {
        "C:\\Users\\andre\\Documents\\NetBeansProjects\\PDI\\src\\imagens\\ebola.png"
    };
     //carrega a imagem
     public MyJPanel(){
         int randomNumber = generator.nextInt(images.length); 
         picture = new ImageIcon(images[randomNumber]); //configura o icone
     }
     
     //exibe imagemIcon no painel
     public void paintComponent(Graphics g){
         super.paintComponent((java.awt.Graphics) g);
         picture.paintIcon(this, (java.awt.Graphics) g, 0, 0); //exibe o frame
     }
     
     //retorna as dimensões da imagem
    @Override
     public Dimension getPreferredSize(){
         return new Dimension(picture.getIconWidth(), 
            picture.getIconHeight());
     }
}
