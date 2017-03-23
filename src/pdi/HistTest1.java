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
import javax.swing.*;

import java.awt.*;
import java.util.Locale;

public class HistTest1 extends JPanel {

    // Count the occurrence of 26 letters
    private int[] count;

    /**
     * Set the count and display histogram
     */
    public void showHistogram(int[] count) {

        this.count = count;

        repaint();

    }

    /**
     * Paint the histogram
     */
    protected void paintComponent(Graphics g) {

        if (count == null) {
            return; // No display if count is null
        }

        super.paintComponent(g);

        // Find the panel size and bar width and interval dynamically
        int width = getWidth();

        int height = getHeight();

        int interval = (width - 40) / count.length;

        int individualWidth = (int) (((width - 40) / 24) * 0.60);

        // Find the maximum count. The maximum count has the highest bar
        int maxCount = 0;

        for (int i = 0; i < count.length; i++) {

            if (maxCount < count[i]) {
                maxCount = count[i];
            }

        }

        // x is the start position for the first bar in the histogram
        int x = 30;

        // Draw a horizontal base line
        g.drawLine(10, height - 45, width - 10, height - 45);

        for (int i = 0; i < count.length; i++) {

            // Find the bar height
            int barHeight
                    = (int) (((double) count[i] / (double) maxCount) * (height - 55));

            // Display a bar (i.e. rectangle)
            g.drawRect(x, height - 45 - barHeight, individualWidth,
                    barHeight);

            // Display a letter under the base line
            g.drawString((char) (65 + i) + "", x, height - 30);

            // Move x for displaying the next character
            x += interval;

        }

    }
    
//    public static void main(String[] args){
//        JFrame f = new JFrame();
//        f.add(new HistTest1());
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setSize(800, 600);
//        f.setVisible(true);
//    }

}
