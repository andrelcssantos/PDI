package Trabalho;

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
public class RGBparaHSV {
    
    //abre o arquivo
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
        //pega a imagem
        BufferedImage img = pegaImagem(); 
        
        float hu = 0, sa = 0, br = 0;
        
        //w pega a largura da imagem - h pega a altura da imagem
        int w = img.getWidth();
        int h = img.getHeight();
        //d calcula o tamanho da imagem
        int d = (w * h);
        //x calcula o denominador da formula
        int x = d / 256;
        //red, green e blue irão receber os tons de cor antigo da imagem - u vai receber o RGB da cor 
        int red, green, blue, u;

        //retorna rgb no método
        int[] rgb = new int[d];
        //vr, vg e vb são vetores que irão receber o tons de cara cor a ser calculado
        int[] vr = new int[256];
        int[] vg = new int[256];
        int[] vb = new int[256];
        //somaR, somaG e somaB são vetores que irão receber os valores de vr, vg e vb 
        int[] somaR = new int[256];
        int[] somaG = new int[256];
        int[] somaB = new int[256];
        //qR, qG e qB são vetores que irão receber o cálculo do tom após aplicada a fórmuza da equalização
        int[] qR = new int[256];
        int[] qG = new int[256];
        int[] qB = new int[256];
        
        float[] hsbvals = new float[256];
        
        //cAux e oldColor pegam os tons originais da imagem - newColor pega os tons após o cálculo
        Color cAux;
        Color oldColor;
        Color newColor;

        //dois fors que percorrem a imagem por largura e altura
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                u = img.getRGB(i, j); //u vai receber o RGB da posição i, j
                cAux = new Color(u);  //cAux é instanciado e recebe o valor de u
                vr[cAux.getRed()]++;  //vr, vg, e vb irão receber os valores de cada tom da sua respectiva cor utilizando cAux como índice
                vg[cAux.getGreen()]++;
                vb[cAux.getBlue()]++;
                
            }
        }
        
        for (int i = 0; i < 256; i++) {
            if (i == 0) { //tratamento para não estourar o array
                somaR[i] = vr[i];
                somaG[i] = vg[i];
                somaB[i] = vb[i];
            } else { //como a posição do índice não é mais 0, somaX vai receber o valor de vX mais a somaX da posição i - 1
                somaR[i] = vr[i] + somaR[i - 1];
                somaG[i] = vg[i] + somaG[i - 1];
                somaB[i] = vb[i] + somaB[i - 1];
            }
            //qX recebe o valor de cada tom executando o cálculo da equalização
            qR[i] = (somaR[i] / x) - 1;
            if (qR[i] < 0) { //se o valor de qX for menor do que 0, o mesmo recebe 0 
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
        //for responsável por substituir os tons antigos pelos novos; percorrem a imagem por largura e altura
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                u = img.getRGB(i, j); //u vai receber o RGB da posição i, j
                oldColor = new Color(u); //oldColor é instanciado e recebe o valor de u
                //cada cor recebe o valor do tom original
                red = oldColor.getRed();
                green = oldColor.getGreen();
                blue = oldColor.getBlue();
                //newColor recebe os valores calculados com is índices de cada cor
                newColor = new Color(qR[red], qG[green], qB[blue]);
                Color.getHSBColor(hu, sa, br);
//                Color.RGBtoHSB(red, green, blue, hsbvals);
                //seta o RGB da imagem nas posições i, j pegando os valores da newColor
                img.setRGB(i, j, newColor.getRGB());
            }
        }
        //cria e escrever o novo arquivo
        File ouptut = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish5.png");
        ImageIO.write(img, "png", ouptut);
        return rgb;
    }

    public static void main(String[] args) throws IOException {
        RGBparaHSV m = new RGBparaHSV();
        m.pegaPixels(arq);
    }
}
