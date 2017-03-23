/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

/**
 *
 * @author andre
 */
public class PegandoPixelsSemJAI extends JFrame {
    
 
    
    public void pegaPixel() throws IOException{
        
        File f = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\ebola.png"); //seleciona o arquivo
        BufferedImage image = ImageIO.read(f); //le o arquivo
        System.out.println("Dimensões: "+image.getWidth()+"x"+image.getHeight()+"pixels"); //exibe suas dimensões
        
        Raster raster = image.getRaster(); //pega os valores dos pixels
        double pixel[] = new double[3]; //cria um array com 3 posições
        int brancos = 0; //cria uma variável para contar os pixels brancos
        for(int h = 0; h<image.getHeight(); h++)
            for(int w = 0; w<image.getWidth(); w++){
                raster.getPixel(w, h, pixel);
                if((pixel[0] == 255) && (pixel[1] == 255) && (pixel[2] == 255))//confirma se o RGB é igual a 255, ou seja, branco
                brancos++;
                
            }
        
       
    }
    
    
//    public ChartPanel criaHistograma(double[] histograma){
//        // int number = data.length;
//        dataset = new HistogramDataset();
//        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
//        dataset.addSeries("Hist", histograma, 256);
//        String plotTitle = "";
//        String xAxis = "Frequency";
//        String yAxis = "Mass Error (Da)";
//        PlotOrientation orientation = PlotOrientation.VERTICAL;
//
//        boolean show = false;
//        boolean toolTips = false;
//        boolean urls = false;
//        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xAxis, yAxis,
//                dataset, orientation, show, toolTips, urls);
//
//        chart.setBackgroundPaint(Color.white);
//        ChartPanel panel = new ChartPanel(chart);
//        panel.setMouseWheelEnabled(true);
//        return panel;
//    }
//    
//    public void montaTela() throws IOException{
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        add(pegaPixel(), BorderLayout.CENTER);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//    
//    public static void main(String args[]){
//        EventQueue.invokeLater(() -> {
//            try {
//                new PegandoPixelsSemJAI().montaTela();
//            } catch (IOException ex) {
//                Logger.getLogger(PegandoPixelsSemJAI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//    }
}
