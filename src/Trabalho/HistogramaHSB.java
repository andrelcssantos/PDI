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

/**
 *
 * @author Andre
 */
public class HistogramaHSB {

    //instancia as variáveis
    private File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\ebola.png");
    private BufferedImage imagem = pegaImagem();
    private Raster raster = imagem.getRaster();
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    private static final int BINS = 256;
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
        int red, green, blue, u, z = 0;
        //retorna rgb no método
        float[] hsb;
        double[] vetH = new double[d+d+d];
        double[] vetS = new double[d+d+d];
        double[] vetB = new double[d+d+d];
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
            }
        }
        
        dataset = new HistogramDataset();
        //pega o RGB
//        r = raster.getSamples(0, 0, w, h, 0, r);
        dataset.addSeries("Red", vetH, BINS);
//        r = raster.getSamples(0, 0, w, h, 1, r);
        dataset.addSeries("Green", vetS, BINS);
//        r = raster.getSamples(0, 0, w, h, 2, r);
        dataset.addSeries("Blue", vetB, BINS);
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