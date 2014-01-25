package edu.brown.laserapp;

import edu.brown.gamelogic.GameLogic;
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
    private boolean cameraReady;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("ELI", "onCreate fired");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		
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
	
	private TextView getTextView(int id) { return (TextView) findViewById(id); }
		

}
