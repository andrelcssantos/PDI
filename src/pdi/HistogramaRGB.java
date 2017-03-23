/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

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
public class HistogramaRGB {
    
    //instancia as variáveis
    private File arq = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\checkerboard.png");
    private BufferedImage imagem = pegaImagem();
    private Raster raster = imagem.getRaster();
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    private static final int BINS = 256;
    private final int w = imagem.getWidth();
    private final int h = imagem.getHeight();
    private double[] r = new double[w*h];
    
    //monta o histograma
    public ChartPanel criaHistograma(){
        dataset = new HistogramDataset();
        //pega o RGB
        r = raster.getSamples(0, 0, w, h, 0, r);
        dataset.addSeries("Red", r, BINS);
        r = raster.getSamples(0, 0, w, h, 1, r);
        dataset.addSeries("Green", r, BINS);
        r = raster.getSamples(0, 0, w, h, 2, r);
        dataset.addSeries("Blue", r, BINS);
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
    private JPanel criaPainel(){
        JPanel panel = new JPanel();
        JComboBox Jbox = new JComboBox();
        JButton histEqual = new JButton("Equalizar Histograma");
        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(0)));
        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(1)));
        panel.add(new JCheckBox(new HistogramaRGB.exibeAcao(2)));
        panel.add(histEqual);
        panel.add(Jbox);
        return panel;
    }
    
    //exibe a tela
    private void mostraTela(){
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
        try{
            return ImageIO.read(arq);
        }catch(IOException e){
            e.printStackTrace(System.err);
        }
        return null;
    }
    
    public int[] pegaPixels(File file){
        BufferedImage img = pegaImagem();
        int[] rgb = new int[w*h];
        
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++){
                rgb[i*j] = img.getRGB(i, j);
            }
        
         for(int i=0;i<rgb.length;i++){
            Color c = new Color(rgb[i]);
            System.out.println("Vermelho: "+c.getRed());
         }
         for(int i=0;i<rgb.length;i++){
            Color c = new Color(rgb[i]);
            System.out.println("Vermelho: "+c.getGreen());
         }
         for(int i=0;i<rgb.length;i++){
            Color c = new Color(rgb[i]);
            System.out.println("Vermelho: "+c.getBlue());
         }
        
        return rgb;
    }
    
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            new HistogramaRGB().mostraTela();
        });
    }
}
