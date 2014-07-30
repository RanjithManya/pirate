package com.android.iviewer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.iviewer.utils.Constants;
import com.android.iviewer.views.ViewerPage;

public class ImageViewerMainActivity extends Activity implements SensorEventListener {

	private float mLastX, mLastY, mLastZ;
	private boolean mInitialized;
	private final float NOISE = (float) 2.0;
	String[] projection = {MediaStore.Images.Thumbnails._ID};
	private int mSize;
	private Uri mUri;
	String[] mProjection = {MediaStore.Images.Thumbnails._ID};
	private ViewerPage mViewPager;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private int mPosition;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  Bundle extras = getIntent().getExtras();
		  mPosition = extras.getInt("position");
		  Log.d("TAG", "position" + mPosition ); 
		  
	//	init();
		mViewPager = new ViewerPage();
		mInitialized = false;
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		setContentView(mViewPager.getViewPager(this));
	}

	private void init() {
		//Constants.IMAGE_PATH.clear();
		@SuppressWarnings("deprecation")
		Cursor cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				mProjection, // Which columns to return
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
			mUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + imageID);
			//Constants.IMAGE_PATH.add(mUri);
		}
		//Log.d("SD", "IMAGE_PATH Size " + Constants.IMAGE_PATH.size());
		cursor.close();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		int constant = 50;

		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		x *= constant;
		y *= constant;
		z *= constant;
		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
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
			if (deltaX > deltaY) {
				if(mLastX > 0){
					 try {
						 Toast.makeText(ImageViewerMainActivity.this, " >>>>>>>>> ", Toast.LENGTH_SHORT).show();	
						//bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Constants.IMAGE_PATH.get(mPosition)));
//						Picasso.with(ImageViewerMainActivity.this).load(Constants.IMAGE_PATH.get(mPosition)).into(iv);
						 if(mViewPager != null)
							 mViewPager.changeItem(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                     
				}
				else {
					 try {
						 Toast.makeText(ImageViewerMainActivity.this, " <<<<<<<< ", Toast.LENGTH_SHORT).show();
						//bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Constants.IMAGE_PATH.get(mPosition)));
						//Picasso.with(ImageViewerMainActivity.this).load(Constants.IMAGE_PATH.get(mPosition)).into(iv);
						 mViewPager.changeItem(2);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                     
					 


			}
			}
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
