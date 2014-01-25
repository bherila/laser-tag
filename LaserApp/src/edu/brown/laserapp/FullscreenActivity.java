package edu.brown.laserapp;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import edu.brown.gamelogic.GameLogic;

public class FullscreenActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;
    private int kills;
    private int deaths;
    
    private GameLogic engine;
    private boolean cameraReady;
	private String lastDeathString;
	private DeathChecker deathChecker;
	private int myId;
    
    private void hideUi(){
    	getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);	
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideUi();
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ELI", "onCreate fired");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
	    getActionBar().hide();                                 
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    					 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		hideUi();
		myId = 0;
		
		mCamera = getCameraInstance();
		
		if (mCamera == null) {
			Log.e("ERR", "camera instance not created!");
			cameraReady = false;
		} else {
			Log.d("ELI", "Got camera");
	        mPreview = new CameraPreview(this, mCamera);
	        cameraReady = true;
	        
	        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.addView(mPreview);
		}
        
        kills = 0;
        deaths = 0;
        
        engine = new GameLogic();
        deathChecker = new DeathChecker();
        
        TimerTask tt = new TimerTask(){
        	@Override
        	public void run() {
        		deathChecker.execute("http://192.168.1.1:3000/check/" + myId);
        	}
        };
        
        new Timer(true).scheduleAtFixedRate(tt, 0, 500);
	}
	
	@Override
	protected void onDestroy(){ if (mCamera != null) mCamera.release(); }
	
	
	private static Camera getCameraInstance(){ try { return Camera.open(); } catch (Exception e){ return null; } }
	
	private void vibrate(long millis) {
		Log.d("ELI", "trying to vibrate");
		Vibrator vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		if (vibe != null) vibe.vibrate(millis);
	}
	

	
	public void fire(){
		vibrate(250);
		
		final FullscreenActivity fa = this;
		Log.d("ELI", "FIRING!");
		
		if (cameraReady) {
			cameraReady = false;

			mCamera.takePicture(null, null, new PictureCallback() {
			    @Override
			    public void onPictureTaken(byte[] data, Camera camera) {
			    	camera.startPreview();
			    	cameraReady = true;
			    	Log.d("ELI", "picture taken");
			    	engine.convert(data, fa);
			    }
			});
		}
	}

	public void incrementKills(){
		vibrate(250);
		getTextView(R.id.kills).setText("" + (++kills) + " kills");
	}
	public void incrementDeaths(){
		vibrate(5000);
		getTextView(R.id.deaths).setText("" + (++deaths) + " deaths");
	}
	public void setMyId(int id) { myId = id; }
	public int getMyId(){ return myId; }
	
	private TextView getTextView(int id) { return (TextView) findViewById(id); }
	
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
        	if (!result.equals(lastDeathString)) {
    			incrementDeaths();
    			lastDeathString = result;
    		}
       }
    }
}
