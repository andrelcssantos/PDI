package pdi;

import pdi.*;
import java.io.*;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

public class CriaHistograma {

    public void criaHistograma(double[] h, double[] s, double[] b) {
        
        for (int i = 1; i < 100; i++) {
            int number = 256;
            HistogramDataset dataset = new HistogramDataset();
            dataset.setType(HistogramType.RELATIVE_FREQUENCY);
            dataset.addSeries("Red", h, number);
            dataset.addSeries("Green", s, number);
            dataset.addSeries("Blue", b, number);
            String plotTitle = "Histogram";
            String xaxis = "number";
            String yaxis = "value";
            PlotOrientation orientation = PlotOrientation.VERTICAL;
            boolean show = false;
            boolean toolTips = false;
            boolean urls = false;
            JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis,
                    dataset, orientation, show, toolTips, urls);
            int width = 500;
            int height = 300;
            try {
                ChartUtilities.saveChartAsPNG(new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\histogram.PNG"), chart, width, height);
            } catch (IOException e) {
            }
        }
    }
}
