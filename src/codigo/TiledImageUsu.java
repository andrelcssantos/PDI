/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Arrays;
import javax.media.jai.JAI;
import javax.media.jai.RasterFactory;
import javax.media.jai.TiledImage;

/**
 *
 * @author andre
 */
public class TiledImageUsu {
    public static void main(String[] args) throws IOException{
      int width = 640;
      int height = 640;
      
      SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_BYTE, width, height, 1);
      TiledImage tiledImage = new TiledImage(0,0,width,height,0,0,sampleModel,null);
      WritableRaster wr = tiledImage.getWritableTile(0, 0);
      
      for(int h=0; h<height/32; h++)
          for(int w=0; w<width/32; w++){
              int[] fill = new int[32*32];
              Arrays.fill(fill, (int)(Math.random()*256));
              wr.setSamples(w*32, h*32, 32, 32, 0, fill);
          }
          JAI.create("filestore", tiledImage, "jaigl.png", "PNG");
    }
    
}
