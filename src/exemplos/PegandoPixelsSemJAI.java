/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplos;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author andre
 */
public class PegandoPixelsSemJAI {
    
    public static void main(String[] args) throws IOException{
        
        File f = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\checkerboard.png"); //seleciona o arquivo
        BufferedImage image = ImageIO.read(f); //le o arquivo
        System.out.println("Dimensões: "+image.getWidth()+"x"+image.getHeight()+"pixels"); //exibe suas dimensões
        
        Raster raster = image.getRaster(); //pega os valores dos pixels
        int[] pixel = new int[3]; //cria um array com 3 posições
        int brancos = 0; //cria uma variável para contar os pixels brancos
        for(int h = 0; h<image.getHeight(); h++)
            for(int w = 0; w<image.getWidth(); w++){
                raster.getPixel(w, h, pixel);
                if((pixel[0] == 255) && (pixel[1] == 255) && (pixel[2] == 255))//confirma se o RGB é igual a 255, ou seja, branco
                    brancos++;
            }
        
        System.out.println(brancos+" pixels brancos");
    }
    
}
