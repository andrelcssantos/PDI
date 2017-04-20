package Trabalho;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFrame;

public class AjustaImagem extends JFrame {

    float x1, x2, y1, y2, dx, dy, a, b;
    public JButton btnCalculo;
    public JButton btnAtualizaImg;
    public JLabel statusBar;
    public JPanel p;
    public File f = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish.png");
    public static BufferedImage img;
    public static BufferedImage img2;
    public ChartFrame frame1;
    private int[] inOut = new int[256];
    File output2 = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish2.png");
    int u = 0, red, green, blue;
    Color newColor;
    Color oldColor;

    public AjustaImagem() {
        super("PDI");
        img = pegaImagem();

        p = new JPanel() {
            Point pointStart = null;
            Point pointEnd = null;
            int w = img.getWidth();
            int h = img.getHeight();

            //eventos do mouse
            {
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        pointStart = e.getPoint();
                    }

                    public void mouseReleased(MouseEvent e) {
                        pointStart = null;
                    }
                });
                addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseMoved(MouseEvent e) {
                        pointEnd = e.getPoint();
                    }

                    public void mouseDragged(MouseEvent e) {
                        pointEnd = e.getPoint();
                        repaint();
                    }
                });
            }

            //monta grafico com coordenadas e pontos
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                if (pointStart != null) {
                    g.clipRect(0, 0, 255, 255);
                    g.fillRect(0, 0, 255, 255);
                    g.setColor(Color.red);
                    if (pointEnd.x > 255) {
                        pointEnd.x = 255;
                    }
                    if (pointEnd.y > 255) {
                        pointEnd.y = 255;
                    }
                    if (pointStart.y > 255) {
                        pointStart.y = 255;
                    }
                    if (pointEnd.x < 0 || pointEnd.y < 0) {
                        pointEnd.x = 0;
                        pointEnd.y = 0;
                    }
                    g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                    x1 = pointStart.x;
                    x2 = pointEnd.x;
                    y1 = pointStart.y;
                    y2 = pointEnd.y;
                }
                statusBar.setText(String.format("Pontos [x1 = %d, y1 = %d, x2 = %d, y2 = %d]", pointStart.x, pointStart.y, pointEnd.x, pointEnd.y));

            }
        };
        p.setPreferredSize(new Dimension(255, 255));;
        add(p, BorderLayout.CENTER);
        add(new JLabel(new ImageIcon(img)), BorderLayout.WEST);
        statusBar = new JLabel();
        add(getBtnAbrir(), BorderLayout.NORTH);
        add(statusBar, BorderLayout.SOUTH);
    }

    public JButton getBtnAbrir() {
        if (btnCalculo != null) {
            return btnCalculo;
        }
        btnCalculo = new JButton();
        btnCalculo.setText("Gerar Calculo");
        btnCalculo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                img = pegaImagem();
                int w = img.getWidth();
                int h = img.getHeight();
                float y = 0;
                for (int i = 0; i < 255; i++) {
                    y = calculoReta(x1, x2, y1, y2, i);
                    if (y > 255) {
                        y = 255;
                    }
                    if (y < 1) {
                        y = 0;
                    }
                    inOut[i] = Integer.valueOf(new Float(y).intValue());
                    System.out.println("Para x=" + i + " temos y=" + y);
                }
//                System.out.println("b = " + b + " m = " + m);
                System.out.println("x1 = " + x1 + " x2 = " + x2 + " y1 = " + y1 + " y2 = " + y2);

                for (int i = 0; i < w; i++) {
                    for (int j = 0; j < h; j++) {
                        u = img.getRGB(i, j); //u vai receber o RGB da posição i, j
                        oldColor = new Color(u); //oldColor é instanciado e recebe o valor de u
                        //cada cor recebe o valor do tom original
                        red = oldColor.getRed();
                        green = oldColor.getGreen();
                        blue = oldColor.getBlue();

                        red = inOut[red];
                        green = inOut[green];
                        blue = inOut[blue];
                        newColor = new Color(red, green, blue);
                        //seta o RGB da imagem nas posições i, j pegando os valores da newColor
                        img.setRGB(i, j, newColor.getRGB());
                    }
                }
                File output = new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\jellyfish2.png");
                try {
                    ImageIO.write(img, "png", output);
                } catch (IOException ex) {
                    Logger.getLogger(AjustaImagem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return btnCalculo;
    }

    private float calculoReta(float x1, float x2, float y1, float y2, float x) {
        //y = ax + b
        float m = 0.2f;
        float y = 0;
        m = (y2 - y1) / (x2 - x1);
        y = m * (x - x1) + y1;

        return y;
    }

    //método para pegar imagem
    public BufferedImage pegaImagem() {
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    //método para pegar imagem
    public BufferedImage pegaImagem2() {
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String args[]) throws Exception {
        AjustaImagem ai = new AjustaImagem();
        ai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ai.setSize(img.getWidth(), img.getHeight());
        ai.setLocationRelativeTo(ai);
        ai.setResizable(false);
        ai.pack();
        ai.setVisible(true);
    }
}
