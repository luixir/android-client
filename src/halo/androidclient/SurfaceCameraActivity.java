package halo.androidclient;

import java.io.IOException;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class SurfaceCameraActivity extends Activity implements Runnable {
	private SurfaceView surfaceView;
	private Camera mCamera;
	
	SurfaceHolder.Callback sh_ob = null;
	SurfaceHolder surfaceHolder        = null;
	SurfaceHolder.Callback sh_callback  = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_surface_camera);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		surfaceView = new SurfaceView(getApplicationContext());
		addContentView(surfaceView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		if (surfaceHolder == null) {
            surfaceHolder = surfaceView.getHolder();
        }
		sh_callback = my_callback();
		surfaceHolder.addCallback(sh_callback);
	}
	
	SurfaceHolder.Callback my_callback(){
		SurfaceHolder.Callback ob1 = new SurfaceHolder.Callback() {
			

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                  mCamera.stopPreview();
                  mCamera.release();
                  mCamera = null;
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            	Toast.makeText(getApplicationContext(), 
            			"Starting Camera..", Toast.LENGTH_SHORT).show();
                mCamera = Camera.open();

                  try {
                       mCamera.setPreviewDisplay(holder);  
                  } catch (IOException exception) {  
                        mCamera.release();  
                        mCamera = null;  
                  }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                    int height) {
                mCamera.startPreview();
            }
        };
        return ob1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.surface_camera, menu);
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
