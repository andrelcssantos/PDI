package exemplos;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author andre
 */
public class BufferedImageUsu {
    
    public static void main(String[] args) throws IOException
 {
        int width = 256;
        int height = 256;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        int[] cor1 = new int[]{100, 168, 244};
        int[] cor2 = new int[]{84, 200, 17};
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if ((((w / 32) + (h / 32)) % 2) == 0) {
                    raster.setPixel(w, h, cor1);
                } else {
                    raster.setPixel(w, h, cor2);
                }
            }
        }
        ImageIO.write(image, "PNG", new File("D:\\ProjetosNetBeans\\PDI\\src\\imagens\\checkerboard_color2.png"));
    }
    
}
