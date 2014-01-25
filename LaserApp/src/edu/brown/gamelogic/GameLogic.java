package edu.brown.gamelogic;
import detector.*;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import edu.brown.laserapp.*;

public class GameLogic {
	public class RGBImage{
		public int[] red;
		public int[] green;
		public int[] blue;
		public RGBImage(int[] red, int[] green, int[] blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
	}
	
	public RGBImage convert(byte[] data, FullscreenActivity act) {
		/* Hard code the resolution of the display for now
		 * 768 * 1280
		 */
		int midX = 640;
		int midY = 384;
		
		BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(data, 0, data.length, false);
		Bitmap bitMap = decoder.decodeRegion(new Rect(midX-250, midY-250, midX+250, midY+250), null);
		int width = bitMap.getWidth();
		int height = bitMap.getHeight();
		int[] R = new int[width*height];
		int[] G = new int[width*height];
		int[] B = new int[width*height];
		int pixel;
		
		for(int x = 0; x < width; ++x) {
			for(int y = 0; y < height; ++y) {
				pixel = bitMap.getPixel(x, y);
				R[x*width+y] = Color.red(pixel);
				G[x*width+y] = Color.green(pixel);
				B[x*width+y] = Color.blue(pixel);
			}
		}
		
		DefaultDetector myDetector = new DefaultDetector();
		int result = myDetector.detect(R, G, B);
		
		if (result == 0) {
			//nothing for now
		} else {
			//only increment the score
			act.kills++;
		}

	}
	
}
