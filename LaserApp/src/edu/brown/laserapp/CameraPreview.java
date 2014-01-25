package edu.brown.laserapp;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private MainActivity ma;
    private long lastPicture;

	public CameraPreview(MainActivity context, Camera camera) {
        super(context);
        mCamera = camera;
        ma = context;
        lastPicture = 0;

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

	@Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("CAM", "Error setting camera preview: " + e.getMessage());
        }
    }
    
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {}

	@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) return;

        try {
            mCamera.stopPreview();
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d("CAM", "Error starting camera preview: " + e.getMessage());
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {
    	Log.d("ELI", "Camera touch event received");
    	if (System.currentTimeMillis() > lastPicture + 250) {
        	Log.d("ELI", "Camera taking picture");
    		ma.takePicture();
    		lastPicture = System.currentTimeMillis();
    	}
    	return true;
    }


}