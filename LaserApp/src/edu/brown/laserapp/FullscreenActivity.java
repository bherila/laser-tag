package edu.brown.laserapp;

import java.io.File;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class FullscreenActivity extends Activity {

	private Camera mCamera;
    private CameraPreview mPreview;
    private int kills;

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
	}
	
	@Override
	protected void onPause(){
		if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
	}
	
	
	private static Camera getCameraInstance(){
	    try { 
	        return Camera.open();
	    }
	    catch (Exception e){
	    	return null;
	    }
	}
	
	private PictureCallback mPicture = new PictureCallback() {
	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {
	    	// 1. Crop data
	    	// 2. Send to vision algorithm
	    	// 3. Get result back
	    	// 4.
	    	// if (hit)
	    		// findViewById(R.id.kills).setText(""+(kills+1));
	    }
	};
	
	public void fire(){
		mCamera.takePicture(null, null, mPicture);
	}

}
