package halo.androidclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	String clientName = "Android1";
	public static final int SERVERPORT = 2525;
	// Computer local address
	public static final String SERVERIP = "192.168.2.8";
	Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startCamera(View view) { // start camera on click
		Intent intent = new Intent(this, CameraActivity.class);

		startActivity(intent);
	}
	
	public void startSurfaceCamera(View view){
		Intent intent = new Intent(this, SurfaceCameraActivity.class);
		startActivity(intent);
	}

	public static Camera getCameraInstances() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
			Log.e("CameraInstance", "Error", e);
		}
		return c; // returns null if camera is not available
	}

	public void Connect(View v) throws Exception {
		Thread cThread = new Thread(new ClientThread());
		Log.d("BANANA", "Clicked");
		cThread.start();
	}

	public class ClientThread implements Runnable {

		@Override
		public void run() {
			try {
				Log.d("ClientActivity", "Connecting...");
				Socket socket = new Socket(SERVERIP, SERVERPORT);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				PrintWriter printWriter = new PrintWriter(
						socket.getOutputStream(), true);
				printWriter.println("Hey Server!");

				System.out.println("Server said: " + br.readLine());

				socket.close();
				Log.d("ClientActivity", "Connection Closed");
			} catch (IOException e) {
				Log.e("ClientActivity", "Error", e);
				System.out.println(e);
			}
		}
	}
}
