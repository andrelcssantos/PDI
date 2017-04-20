/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Trabalho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SELECTED_KEY;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

public class HistogramaHSB {

    //instancia as variáveis
    private File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\3.png");
    private BufferedImage imagem = pegaImagem();
    private Raster raster = imagem.getRaster();
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    private final int w = imagem.getWidth();
    private final int h = imagem.getHeight();
    private double[] r = new double[w * h];

    //monta o histograma
    public ChartPanel criaHistograma() throws IOException {

        //pega a imagem
        BufferedImage img = pegaImagem();
        //w pega a largura da imagem - h pega a altura da imagem
        int w = img.getWidth();
        int h = img.getHeight();
        //d calcula o tamanho da imagem
        int d = (w * h);
        //red, green e blue irão receber os tons de cor antigo da imagem - u vai receber o RGB da cor 
        int red, green, blue, u;
        //retorna rgb no método
        float[] hsb;
        int[] vetH = new int[256];
        int[] vetS = new int[256];
        int[] vetB = new int[256];
        float hue, sat, bri;
        //cAux e oldColor pegam os tons originais da imagem - newColor pega os tons após o cálculo
        Color oldColor;
        Color newColor;

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

//                hue = hue * 360;
//                int convH = Integer.valueOf(new Float(hue).intValue());
//                vetH[convH]++;
//
//                sat = sat * 100;
//                int convS = Integer.valueOf(new Float(sat).intValue());
//                vetS[convS]++;
//
//                bri = bri * 100;
//                int convB = Integer.valueOf(new Float(bri).intValue());
//                vetB[convB]++;

                newColor = new Color(hue, sat, bri);
                //seta o RGB da imagem nas posições i, j pegando os valores da newColor
                img.setRGB(i, j, newColor.getRGB());
            }
        }
        
        File ouptut = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\5.jpeg");
        ImageIO.write(img, "png", ouptut);

        dataset = new HistogramDataset();
        //pega o RGB

        r = raster.getSamples(0, 0, w, h, 0, r);
        dataset.addSeries("Red", r, 360);
        r = raster.getSamples(0, 0, w, h, 1, r);
        dataset.addSeries("Green", r, 101);
        r = raster.getSamples(0, 0, w, h, 2, r);
        dataset.addSeries("Blue", r, 101);

        JFreeChart chart = ChartFactory.createHistogram("Histograma", "Pixels", "Y",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        //Plota as cores
        XYPlot plot = (XYPlot) chart.getPlot();
        renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        //vermelho, verde, azul
        Paint[] paintArray = {
            new Color(0x80ff0000, true),
            new Color(0x8000ff00, true),
            new Color(0x800000ff, true)
        };
        //desenhando o gráfico
        plot.setDrawingSupplier(new DefaultDrawingSupplier(
                paintArray,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    //exibe ação do plot
    private class exibeAcao extends AbstractAction {

        private final int i;

        public exibeAcao(int i) {
            this.i = i;
            this.putValue(NAME, (String) dataset.getSeriesKey(i));
            this.putValue(SELECTED_KEY, true);
            renderer.setSeriesVisible(i, true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            renderer.setSeriesVisible(i, !renderer.getSeriesVisible(i));
        }
    }

    //cria o painel do bottom 
    private JPanel criaPainel() {
        JPanel panel = new JPanel();
        JComboBox Jbox = new JComboBox();
        JButton histEqual = new JButton("Equalizar Histograma");
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(0)));
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(1)));
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(2)));
        panel.add(histEqual);
        panel.add(Jbox);
        return panel;
    }

    //exibe a tela
    private void mostraTela() throws IOException {
        JFrame f = new JFrame("PDI - Histograma");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(criaHistograma(), BorderLayout.EAST);
        f.add(new JLabel(new ImageIcon(imagem)), BorderLayout.WEST);
        f.add(criaPainel(), BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(f);
        f.setVisible(true);
    }

    //método para pegar imagem
    private BufferedImage pegaImagem() {
        try {
            return ImageIO.read(arq);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        EventQueue.invokeLater(() -> {
            try {
                new HistogramaHSB().mostraTela();
            } catch (IOException ex) {
                Logger.getLogger(HistogramaHSB.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
}

/*
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 /*
package Trabalho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SELECTED_KEY;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;


public class HistogramaHSB {

    //instancia as variáveis
    private File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\3.png");
    private BufferedImage imagem = pegaImagem();
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    private final int w = imagem.getWidth();
    private final int h = imagem.getHeight();
    private double[] r = new double[w * h];
    
    //monta o histograma
    public ChartPanel criaHistograma() {
        
        //pega a imagem
        BufferedImage img = pegaImagem();
        //w pega a largura da imagem - h pega a altura da imagem
        int w = img.getWidth();
        int h = img.getHeight();
        //d calcula o tamanho da imagem
        int d = (w * h);
        //red, green e blue irão receber os tons de cor antigo da imagem - u vai receber o RGB da cor 
        int red, green, blue, u,cont=0;
        //retorna rgb no método
        float[] hsb;
        double[] vetH = new double[361];
        double[] vetS = new double[101];
        double[] vetB = new double[101];
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

                hue = hue * 360;
                int convH = Integer.valueOf(new Float(hue).intValue());
                vetH[convH]++;

                sat = sat * 100;
                int convS = Integer.valueOf(new Float(sat).intValue());
                vetS[convS]++;

                bri = bri * 100;
                int convB = Integer.valueOf(new Float(bri).intValue());
                vetB[convB]++;
                
            }
        }
        
        for(int i =0; i < vetH.length; i++)
        {
            cont = (int)vetH[i] + cont;
        }
        System.out.println(cont);
        
        dataset = new HistogramDataset();
        //pega o RGB
        
        dataset.addSeries("Red", vetH, 360, 0, 361);
        dataset.addSeries("Green", vetS, 101, 0, 101);
        dataset.addSeries("Blue", vetB, 101, 0, 101);
        
        JFreeChart chart = ChartFactory.createHistogram("Histograma", "Pixels", "Y",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        //Plota as cores
        XYPlot plot = (XYPlot) chart.getPlot();
        renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardXYBarPainter());
        //vermelho, verde, azul
        Paint[] paintArray = {
            new Color(0x80ff0000, true),
            new Color(0x8000ff00, true),
            new Color(0x800000ff, true)
        };
        //desenhando o gráfico
        plot.setDrawingSupplier(new DefaultDrawingSupplier(
                paintArray,
                DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        ChartPanel panel = new ChartPanel(chart);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    //exibe ação do plot
    private class exibeAcao extends AbstractAction {

        private final int i;

        public exibeAcao(int i) {
            this.i = i;
            this.putValue(NAME, (String) dataset.getSeriesKey(i));
            this.putValue(SELECTED_KEY, true);
            renderer.setSeriesVisible(i, true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            renderer.setSeriesVisible(i, !renderer.getSeriesVisible(i));
        }
    }

    //cria o painel do bottom 
    private JPanel criaPainel() {
        JPanel panel = new JPanel();
        JComboBox Jbox = new JComboBox();
        JButton histEqual = new JButton("Equalizar Histograma");
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(0)));
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(1)));
        panel.add(new JCheckBox(new HistogramaHSB.exibeAcao(2)));
        panel.add(histEqual);
        panel.add(Jbox);
        return panel;
    }

    //exibe a tela
    private void mostraTela() {
        JFrame f = new JFrame("PDI - Histograma");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(criaHistograma(), BorderLayout.EAST);
        f.add(new JLabel(new ImageIcon(imagem)), BorderLayout.WEST);
        f.add(criaPainel(), BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(f);
        f.setVisible(true);
    }

    //método para pegar imagem
    private BufferedImage pegaImagem() {
        try {
            return ImageIO.read(arq);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new HistogramaHSB().mostraTela();
        });
    }
}
 
*/