/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 *
 * @author andre
 */
public class PlanarImageComJAI {
    public static void main(String[] args) throws IOException{
        /*Colocando a imagem na memória*/
        PlanarImage image = JAI.create("fileload", "C:\\Users\\andre\\Documents\\NetBeansProjects\\PDI\\jaigl.png");
        System.out.println("Dimensões: "+image.getWidth()+"x"+image.getHeight()+"pixels");
    }
    
}
