/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdi;

/**
 *
 * @author Andre
 */
import Trabalho.HistogramEqualization;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.awt.*;
import java.util.ArrayList;

public class HistTeste {

    public JFreeChart createHistogram(double[][] doubleMatrix){

        // Generate a one dimensional array of the size w*h of the double matrix
        ArrayList<Double> dataArrayList = new ArrayList<Double>();

        for (int i=0; i<doubleMatrix.length; i++) {
            for (int j = 0; j < doubleMatrix[i].length; j++) {
                double value =  doubleMatrix[i][j];
                if( Double.isNaN(value))
                    continue;
                else
                    dataArrayList.add(value);
                    System.out.println(value);
            }
        }

        double[] data = new double[dataArrayList.size()];

        for(int p = 0; p < dataArrayList.size();p++)
             data[p] = dataArrayList.get(p);


       // int number = data.length;
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Hist",data,200); // Number of bins is 50
        String plotTitle = "";
        String xAxis = "Frequency";
        String yAxis = "Mass Error (Da)";
        PlotOrientation orientation = PlotOrientation.VERTICAL;

        boolean show = false;
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xAxis, yAxis,
                dataset, orientation, show, toolTips, urls);

        chart.setBackgroundPaint(Color.white);

        return chart;
    }
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            new HistTeste();
        });
    }

    
}
