package edu.brown.gamelogic;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import edu.brown.laserapp.MainActivity;

public class GameLogic {
	private final static String URL_BASE = "192.168.1.1:3000";
	
	/* Different guns!
	 */
	private final static String[]  GUN_NAMES   = new String[] {"Desert Eagle", "M4A1", "AK41", "AWP"};
	private final static Integer[] GUN_DAMAGES = new Integer[] {20, 40, 45, 90}; 
	
    private int kills;
    private int deaths;
    
	private int myId;
	private int gunId;
	
	private MainActivity ma;
	private String lastDeathString;

	public GameLogic(MainActivity ma){
		kills = 0;
		deaths = 0;
		myId = 0;
		gunId = 0;
        this.ma = ma;
        lastDeathString = null;
        
        TimerTask tt = new TimerTask(){
        	@Override
        	public void run() {
        		new DeathChecker().execute("http://" + URL_BASE + "/check/" + myId);
        	}
        };
        
        new Timer(true).scheduleAtFixedRate(tt, 0, 500);
	}

	
	public void convert(byte[] data, MainActivity act) {
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
			
			ma.vibrate(250);
			
			if (myId == 0)
				myId = result;
			else
				incrementKills(result);
		}
	}
	
	private void incrementKills(int id){
		ma.setKillsText("" + (++kills) + " kills");
		new KillReporter().execute("http://" + URL_BASE + "/hit/" + id);
	}
	private void incrementDeaths(){
		ma.vibrate(5000);
		ma.setDeathsText("" + (++deaths) + " deaths");
	}
	
	private class DeathChecker extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpGet httpGet = new HttpGet(urls[0]);
	
	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
        	if (result != null && !result.equals(lastDeathString)) {
    			incrementDeaths();
    			lastDeathString = result;
    		}
       }
    }
	
	private class KillReporter extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... urls) {
			try {
	            new DefaultHttpClient().execute(new HttpGet(urls[0]));
            } catch (IOException e) { }
			return null;
		}
	}
	
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
}
