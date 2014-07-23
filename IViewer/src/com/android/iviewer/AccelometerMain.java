package com.android.iviewer;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iviewer.utils.Constants;
import com.android.iviewer.utils.Utility;
import com.android.pirate.iviewer.R;
import com.squareup.picasso.Picasso;

public class AccelometerMain extends Activity implements SensorEventListener {
	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private final float NOISE = (float) 2.0;
    String[] projection = {MediaStore.Images.Thumbnails._ID};
	private Uri uri;
	private int mPosition=0;
	private Bitmap bitmap;
	private Bitmap newBitmap;
	private int mSize;



	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelometer_main);
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		init();
	}

	private void init() {
		Constants.IMAGE_PATH.clear();
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, // Which columns to return
                null,       // Return all rows
                null,       
                null); 
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
         mSize = cursor.getCount();
        // If size is 0, there are no images on the SD Card.
        if (mSize == 0) {
            //No Images available, post some message to the user
        }
        int imageID = 0;
        Log.d("SD","Size = "+ mSize);
        for (int i = 0; i < mSize; i++) {
            cursor.moveToPosition(i);
            imageID = cursor.getInt(columnIndex);
            uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imageID);
            Constants.IMAGE_PATH.add(uri);
           
            
        }
        cursor.close(); 
        Log.d("SD", "IMAGE_PATH Size " + Constants.IMAGE_PATH.size());
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		TextView tvX = (TextView) findViewById(R.id.x_axis);
		TextView tvY = (TextView) findViewById(R.id.y_axis);
		TextView tvZ = (TextView) findViewById(R.id.z_axis);
		ImageView iv = (ImageView) findViewById(R.id.image);
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			tvX.setText("0.0");
			tvY.setText("0.0");	
			tvZ.setText("0.0");
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);
			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			tvX.setText(Float.toString(deltaX));
			tvY.setText(Float.toString(deltaY));
			tvZ.setText(Float.toString(deltaZ));
			iv.setVisibility(View.VISIBLE);
			if (deltaX > deltaY) {
				Picasso.with(AccelometerMain.this).load(Constants.IMAGE_PATH.get(mPosition)).into(iv);
				if(mLastX > 0){
					 try {
						 Toast.makeText(AccelometerMain.this, " >>>>>>>>> ", Toast.LENGTH_SHORT).show();	
						//bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Constants.IMAGE_PATH.get(mPosition)));
						Picasso.with(AccelometerMain.this).load(Constants.IMAGE_PATH.get(mPosition)).into(iv);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                     
	                    mPosition++;
	                    if(mPosition >= mSize)
	                    	mPosition=0;
				}
				else {
					 try {
						 Toast.makeText(AccelometerMain.this, " <<<<<<<< ", Toast.LENGTH_SHORT).show();
						//bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Constants.IMAGE_PATH.get(mPosition)));
						Picasso.with(AccelometerMain.this).load(Constants.IMAGE_PATH.get(mPosition)).into(iv);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                     
	                    mPosition--;
	                    if(mPosition < 0)
	                    	mPosition=0;
					 
				}
					
				//iv.setImageResource(R.drawable.horizontal);
			} else if (deltaY > deltaX) {
				//iv.setImageResource(R.drawable.vertical);
			} else {
				//iv.setVisibility(View.INVISIBLE);
			}
		}
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

}