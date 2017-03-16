/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exemplos;

import com.sun.media.jai.codecimpl.util.DataBufferFloat;
import java.awt.Point;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;

/**
 *
 * @author andre
 */
public class TiledImageUsu2 {
    
    public static void main(String[] args) throws IOException
{
        int width = 256;
        int height = 256;
        float[] imageData = new float[width * height];
        int count = 0;
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                imageData[count++] = 20f * w * h;
            }
        }
        DataBufferFloat dbuffer = new DataBufferFloat(imageData, width * height);
        SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_FLOAT, width, width, 1);
        ColorModel colorModel = PlanarImage.createColorModel(sampleModel);
        WritableRaster raster 
                = RasterFactory.createWritableRaster(sampleModel, dbuffer, new Point(0,0));
        TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, sampleModel, colorModel);
        tiledImage.setData(raster);
        JAI.create("filestore", tiledImage, "floatpattern.tif", "TIFF");
    }

    
}
