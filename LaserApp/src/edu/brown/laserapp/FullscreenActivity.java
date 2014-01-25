package edu.brown.laserapp;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

public class FullscreenActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;
    private int kills;
    private int deaths;
    
    private GameLogic engine;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
		mCamera = getCameraInstance();
		
		if (mCamera == null) {
			Log.e("ERR", "camera instance not created!");
		} else {
	        mPreview = new CameraPreview(this, mCamera);
	        
	        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.addView(mPreview);
		}
        
        kills = 0;
        deaths = 0;
        
        engine = new GameLogic();
	}
	
	@Override
	protected void onDestroy(){ if (mCamera != null) mCamera.release(); }
	
	
	private static Camera getCameraInstance(){ try { return Camera.open(); } catch (Exception e){ return null; } }
	
	private void vibrate(long millis) {
		Vibrator vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		if (vibe != null) vibe.vibrate(millis);
	}
	

	
	public void fire(){
		vibrate(250);
		
		mCamera.takePicture(null, null, new PictureCallback() {
		    @Override
		    public void onPictureTaken(byte[] data, Camera camera) {
		    	engine.convert(data, this);
		    }
		});
	}

	public void incrementKills(){
		vibrate(250);
		getTextView(R.id.kills).setText("" + (++kills) + " kills");
	}
	public void incrementDeaths(){
		vibrate(5000);
		getTextView(R.id.deaths).setText("" + (++deaths) + " deaths");
	}
	
	private TextView getTextView(int id) { return (TextView) findViewById(id); }
		

}
