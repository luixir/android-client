package halo.androidclient;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class CameraActivity extends Activity {
	
	private static final int MEDIA_TYPE_IMAGE = 1;
	private static final int MEDIA_TYPE_VIDEO = 2;
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	
	private Uri fileUri;
	private ImageView imgPreview;
	private VideoView videoPreview;
	private Button btnCapturePicture;
	private Button btnRecordVideo;
	
	private Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		imgPreview = (ImageView) findViewById(R.id.imgPreview);
		videoPreview = (VideoView) findViewById(R.id.videoPreview);
		btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
		btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
		
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				captureImage();
			}
		});
		
		btnRecordVideo.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				recordVideo();	
			}
		});
		
		// Checking camera availability
		if(!checkCameraHardware()){
			Toast.makeText(getApplicationContext(), 
					"No camera available.", 
					Toast.LENGTH_LONG).show();
			// Then close the app
			finish();
		}
		
//		Thread cThread = new Thread(new SurfaceCameraActivity());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_pick, menu);
		return true;
	}
	
	public boolean checkCameraHardware(){
		if(getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)){
			// Device has camera
			return true;
		}else{
			// Doesn't has camera
			return false;
		}
	}
	
	/**
	 * Take pictures
	 */
	public void captureImage(){

		Toast.makeText(getApplicationContext(), 
				"Camera starting.",
				Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
		
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	/**
     * Recording video
     */
    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
 
        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                                                            // name
 
        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }
	
	/**
     * ------------ Helper Methods ---------------------- 
     */
 
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed to create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }
}
