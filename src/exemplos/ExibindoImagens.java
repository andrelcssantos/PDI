/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplos;

import com.sun.media.jai.widget.DisplayJAI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author andre
 */
public class ExibindoImagens {
    
    public static void main(String[] args) throws IOException
 {
        
        BufferedImage image = ImageIO.read(new File("checkerboard_color.png"));
        JFrame frame = new JFrame("Display Image: "+image);
        DisplayJAI display = new DisplayJAI(image);
        frame.getContentPane().add(new JScrollPane(display));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        frame.setVisible(true);
 }

    
}
