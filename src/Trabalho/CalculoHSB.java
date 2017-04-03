package Trabalho;

import pdi.CriaHistograma;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CalculoHSB {

    //abre o arquivo
    private static File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\3.png");

    //método para pegar imagem
    public BufferedImage pegaImagem() {
        try {
            return ImageIO.read(arq);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public void pegaPixels(File file) throws IOException {
        //pega a imagem
        BufferedImage img = pegaImagem();
        //w pega a largura da imagem - h pega a altura da imagem
        int w = img.getWidth();
        int h = img.getHeight();
        //d calcula o tamanho da imagem
        int d = (w * h);
        //red, green e blue irão receber os tons de cor antigo da imagem - u vai receber o RGB da cor 
        int red, green, blue, u, z = 0;
        //retorna rgb no método
        float[] hsb;
        double[] vetH = new double[d+d+d];
        double[] vetS = new double[d+d+d];
        double[] vetB = new double[d+d+d];
        double[] vetConv = new double[d+d+d];
        float hue, sat, bri;
        //cAux e oldColor pegam os tons originais da imagem - newColor pega os tons após o cálculo
        Color oldColor;

        //for responsável por substituir os tons antigos pelos novos; percorrem a imagem por largura e altura
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                u = img.getRGB(i, j); //u vai receber o RGB da posição i, j
                oldColor = new Color(u); //oldColor é instanciado e recebe o valor de u
                //cada cor recebe o valor do tom original
                red = oldColor.getRed();
                green = oldColor.getGreen();
                blue = oldColor.getBlue();
                hsb = Color.RGBtoHSB(red, green, blue, null);
                hue = hsb[0];
                sat = hsb[1];
                bri = hsb[2];
//                System.out.println("RGB [" + red + "," + green + "," + blue + "] converted to HSB [" + hue + "," + sat + "," + bri + "]");

                double convH = Double.parseDouble(new Float(hue).toString());
                vetH[z] = convH;
                z++;
                double convS = Double.parseDouble(new Float(sat).toString());
                vetS[z] = convS;
                z++;
                double convB = Double.parseDouble(new Float(bri).toString());
                vetB[z] = convB;
                z++;
               
//                rgb = Color.HSBtoRGB(hue, sat, bri);
//                red = (rgb >> 16) & 0xFF;
//                green = (rgb >> 8) & 0xFF;
//                blue = rgb & 0xFF;
//                System.out.println("HSB [" + hue + "," + sat + "," + bri + "] converted to RGB [" + red + "," + green + "," + blue + "]\n");

                
            }
        }

        CriaHistograma ch = new CriaHistograma();
        ch.criaHistograma (vetH, vetS, vetB);
//        return rgb;
    }

    public static void main(String[] args) throws IOException {
        CalculoHSB m = new CalculoHSB();
        m.pegaPixels(arq);
    }
}
