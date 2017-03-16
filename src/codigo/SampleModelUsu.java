/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import com.sun.media.jai.codec.TIFFEncodeParam;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import javax.media.jai.JAI;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;

/**
 *
 * @author andre
 */
public class SampleModelUsu {
    
    public static void main(String[] args) throws IOException{
        
        int width = 483; 
        int height = 483;
        int tWidth = 64; 
        int tHeight = 64;
        
        SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_BYTE, tWidth, tHeight, 3);
        ColorModel cm = TiledImage.createColorModel(sampleModel);
        TiledImage tileImage = new TiledImage(0,0,width,height,0,0,sampleModel,cm);
        
        //criando as cores
        int[] red    = new int[]{255,0,0};
        int[] green  = new int[]{0,255,0};
        int[] blue   = new int[]{0,0,255};
        int[] yellow = new int[]{255,255,0};
        int[] black  = new int[]{0,0,0};
        
        for(int th=tileImage.getMinTileY(); th<=tileImage.getMaxTileY(); th++)
            for(int tw=tileImage.getMinTileX(); tw<=tileImage.getMaxTileX(); tw++){
                WritableRaster wr = tileImage.getWritableTile(tw, th);
                for(int ih=0; ih<tHeight; ih++)
                    for(int iw=0; iw<tWidth; iw++){
                        int w = wr.getMinX()+iw;
                        int h = wr.getMinY()+ih;
                        if((w >= 17) && (w < 17+216) && (h >= 17) && (h < 17+216))
                            wr.setPixel(w, h, red);
                        else if ((w >= 250) && (w < 250+216) && (h >= 17) && (h < 17+216))
                            wr.setPixel(w, h, green);
                        else if ((w >= 17) && (w < 17+216) && (h >= 250) && (h < 250+216))
                            wr.setPixel(w, h, yellow);
                        else if ((w >= 250) && (w < 250+216) && (h >= 250) && (h < 250+216))
                            wr.setPixel(w, h, blue);
                        else
                            wr.setPixel(w, h, black);
                    }
            }
        TIFFEncodeParam tep = new TIFFEncodeParam();
        tep.setWriteTiled(true);
        tep.setTileSize(tWidth, tHeight);
        JAI.create("filestore", tileImage, "rgbtile", "TIFF", tep);
    }
    
}
