package pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 *
 * @author andre
 */

/*
* equação:
* q = (En / x) -1, onde q são os novos niveis de tons da imagen,
* n é a quantidade de vezes que o tom aparece, E é a somatoria de tons da imagem original
* x = (h * w) / 256, onde h é a largura, w é a altura
 */

public class Main {

    private static File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\ebola.png");

    //método para pegar imagem
    public BufferedImage pegaImagem() {
        try {
            return ImageIO.read(arq);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public int[] pegaPixels(File file) {
        BufferedImage img = pegaImagem();
        int[] somaR = new int[256]; 
        int[] somaG = new int[256]; 
        int[] somaB = new int[256]; 
        
        int[] qR = new int[256]; 
        int[] qG = new int[256]; 
        int[] qB = new int[256]; 
        
        int w = img.getWidth();
        int h = img.getHeight();
        int x = (w*h) / 256;
        int[] rgb = new int[w * h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                rgb[i * j] = img.getRGB(i, j);
            }
        }

        for (int i = 0; i < rgb.length; i++) {
            Color c = new Color(rgb[i]);
            if(i == 0){
                somaR[i] = c.getRed();
                somaG[i] = c.getGreen();
                somaB[i] = c.getBlue();
            } else {
                somaR[i] = c.getRed()   + somaR[i - 1];
                somaG[i] = c.getGreen() + somaG[i - 1];;
                somaB[i] = c.getBlue()  + somaB[i - 1];;
            }
            qR[i] = (somaR[i] / x) - 1;
            if(qR[i] < 0)
                qR[i] = 0;
            qG[i] = (somaG[i] / x) - 1;
            if(qG[i] < 0)
                qG[i] = 0;
            qB[i] = (somaB[i] / x) - 1;
            if(qB[i] < 0)
                qB[i] = 0;
            
        }
        return rgb;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.pegaPixels(arq);
    }

}
