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

    private static File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish.png");

    //método para pegar imagem
    public BufferedImage pegaImagem() {
        try {
            return ImageIO.read(arq);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public int[] pegaPixels(File file) throws IOException {
        BufferedImage img = pegaImagem();

        int w = img.getWidth();
        int h = img.getHeight();
        int d = (w * h);
        int x = d / 256;
        int[] rgb = new int[d];
        int red, green, blue, u;
        int[] vr = new int[256];
        int[] vg = new int[256];
        int[] vb = new int[256];
        Color cAux;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                u = img.getRGB(i, j);
                cAux = new Color(u);
                vr[cAux.getRed()]++;
                vg[cAux.getGreen()]++;
                vb[cAux.getBlue()]++;
            }
        }
        int[] somaR = new int[256];
        int[] somaG = new int[256];
        int[] somaB = new int[256];
        int[] qR = new int[256];
        int[] qG = new int[256];
        int[] qB = new int[256];

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                somaR[i] = vr[i];
                somaG[i] = vg[i];
                somaB[i] = vb[i];
            } else {
                somaR[i] = vr[i] + somaR[i - 1];
                somaG[i] = vg[i] + somaG[i - 1];
                somaB[i] = vb[i] + somaB[i - 1];
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
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                u = img.getRGB(i, j);
                Color oldColor = new Color(u);
                red = oldColor.getRed();
                green = oldColor.getGreen();
                blue = oldColor.getBlue();
                Color newColor = new Color(qR[red], qG[green], qB[blue]);
                img.setRGB(i, j, newColor.getRGB());
            }
        }
        File ouptut = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish1.png");
        ImageIO.write(img, "png", ouptut);
        return rgb;
    }

    public static void main(String[] args) throws IOException {
        EqualizacaoHistograma m = new EqualizacaoHistograma();
        m.pegaPixels(arq);
    }
}
