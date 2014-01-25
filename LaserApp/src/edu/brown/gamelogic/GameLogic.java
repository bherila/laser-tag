package edu.brown.gamelogic;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import detector.DefaultDetector;

import edu.brown.laserapp.FullscreenActivity;

public class GameLogic {
	/* Different guns!
	 */
	String[] gun_names = new String[] {"Desert Eagle", "M4A1", "AK41", "AWP"};
	Integer[] gun_damages = new Integer[] {20, 40, 45, 90}; 
	// Different guns!
	
	// Player class
	public class Players{
		private class Player{
			public int id;
			public int gun_id;
			public Integer HP;
			public Player(int id) {
				this.id = id;
				gun_id = 0;
				HP = 100;
			}
		}
		
		private Player[] players = new Player[4];
		
		public Players() {
			for (int i = 0 ; i < 4 ; i++) {
				players[i] = new Player(i);
			}
		}
		
		
	}
	// Player class
	
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
	
	public void convert(byte[] data, FullscreenActivity act) {
		int result = -1;
		
		try {
			BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(data, 0, data.length, false);
		
			int midX = decoder.getWidth()/2;
			int midY = decoder.getHeight()/2;
			
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
			
			//DefaultDetector myDetector = new DefaultDetector();
			//result = myDetector.detect(R, G, B);
			result = 3;
		} catch (IOException e) {
			Log.e("GL", "Failed to get a bitmapregiondecoder");
		}

		if (result > 0) {
			Log.d("ELI", "Got a hit!");
			act.incrementKills();
			
			if (act.getMyId() == 0)
				act.setMyId(result);
		}
	}
}
