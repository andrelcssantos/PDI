package Trabalho;

import pdi.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
public class EqualizacaoHistograma {

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

        int w = img.getWidth();
        int h = img.getHeight();
        int d = (w * h);
        int x = d / 256;
        int[] rgb = new int[d];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                rgb[i * j] = img.getRGB(i, j);
            }
        }

//        for (int i = 0; i < rgb.length; i++) {
//            Color c = new Color(rgb[i]);
//            System.out.println("Vermelho: " + c.getRed());
//        }
        int[] somaR = new int[rgb.length];
        int[] somaG = new int[rgb.length];
        int[] somaB = new int[rgb.length];

        int[] qR = new int[rgb.length];
        int[] qG = new int[rgb.length];
        int[] qB = new int[rgb.length];

        for (int i = 0; i < rgb.length; i++) {
            Color c = new Color(rgb[i]);
            if (i == 0) {
                somaR[i] = c.getRed();
                somaG[i] = c.getGreen();
                somaB[i] = c.getBlue();
            } else {
                somaR[i] = c.getRed() + somaR[i - 1];
                somaG[i] = c.getGreen() + somaG[i - 1];
                somaB[i] = c.getBlue() + somaB[i - 1];
            }
            qR[i] = (somaR[i] / x) - 1;
            if (qR[i] < 0) {
                qR[i] = 0;
            }
            qG[i] = (somaG[i] / x) - 1;
            if (qG[i] < 0) {
                qG[i] = 0;
            }
            qB[i] = (somaB[i] / x) - 1;
            if (qB[i] < 0) {
                qB[i] = 0;
            }
        }
        for (int i = 0; i < 256; i++) {
            System.out.println("SomaR: " + somaR[i] + " - qR: " + qR[i]);
        };
        return rgb;
    }

    public static void main(String[] args) {
        EqualizacaoHistograma m = new EqualizacaoHistograma();
        m.pegaPixels(arq);
    }
}
