package trabalho;

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
 * @author andre
 */
public class Equalização {

    //instancia o arquivo
    File f = new File("C:\\Users\\andre\\Documents\\NetBeansProjects\\PDI\\src\\imagens\\ebola.png");
    //cria a variável que determina a quantidade de pixels
    private static final int BINS = 256;
    //classe para representar a imagem
    private final BufferedImage imagem = getImage();
    //classe que pega os dados da imagem para criar o histograma
    private HistogramDataset dataSet;
    //desenha as barras da imagem em um XYPlot
    private XYBarRenderer renderer;

    public ChartPanel criaChartPanel() {
        //instancia a classe HistogramDataset
        dataSet = new HistogramDataset();
        //armazenando os pixels da imagem
        Raster raster = imagem.getRaster();
        //pegando o valor de uma banda em determinado pixel
        //dimensões do arquivo e tamanho da imagem
        final long tamanho = f.length();
        final int largura  = imagem.getWidth();
        final int altura   = imagem.getHeight();
        //criando o vetor de doubles para armazxenar os pixels
        double[] valorPixel = new double[largura*altura];
        //pegando o valor de uma banda em um determinado pixel
        valorPixel = raster.getSamples(0, 0, largura, altura, 0, valorPixel);
        dataSet.addSeries("Red", valorPixel, BINS);
        valorPixel = raster.getSamples(0, 0, largura, altura, 1, valorPixel);
        dataSet.addSeries("Green", valorPixel, BINS);
        valorPixel = raster.getSamples(0, 0, largura, altura, 2, valorPixel);
        dataSet.addSeries("Blue", valorPixel, BINS);
        //criando o histograma
        JFreeChart chart = ChartFactory.createHistogram("Histograma RGB", "Pixels", "Quantidade", dataSet, 
                    PlotOrientation.VERTICAL, true, true, false);
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

    private BufferedImage getImage() {
        try{
            return ImageIO.read(f);
        } catch (IOException e){
            e.printStackTrace(System.err);
        }
        return null;
    }

    //cria o painel que fica no sul do borderlayout
    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JComboBox Jbox = new JComboBox();
        JButton histEqual = new JButton("Equalizar Histograma");
        panel.add(new JCheckBox(new VisibleAction(0)));
        panel.add(new JCheckBox(new VisibleAction(1)));
        panel.add(new JCheckBox(new VisibleAction(2)));
        panel.add(histEqual);
        panel.add(Jbox);
        return panel;
    }

    private class VisibleAction extends AbstractAction {

        private final int i;

        public VisibleAction(int i) {
            this.i = i;
            this.putValue(NAME, (String) dataSet.getSeriesKey(i));
            this.putValue(SELECTED_KEY, true);
            renderer.setSeriesVisible(i, true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            renderer.setSeriesVisible(i, !renderer.getSeriesVisible(i));
        }
    }

    private void criaTela() {
        JFrame f = new JFrame("Histograma");
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(criaChartPanel());
        f.add(createControlPanel(), BorderLayout.SOUTH);
        f.add(new JLabel(new ImageIcon(imagem)), BorderLayout.WEST);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Equalização().criaTela();
        });
    }
}

