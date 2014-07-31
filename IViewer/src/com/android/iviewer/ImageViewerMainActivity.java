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
import android.view.Surface;
import android.widget.Toast;

import com.android.iviewer.utils.Constants;
import com.android.iviewer.views.ViewerPage;

public class ImageViewerMainActivity extends Activity implements SensorEventListener {

	private static final int SENSOR_THRESHOLD_VALUE = 68;
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
	private float _X;
	private float _Y;
	private float _Z;
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
		if(mViewPager != null)
			mViewPager.changeItem(3,mPosition);
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
		int i = 0;
		int constant = 50;
		int rotation =  this.getWindowManager().getDefaultDisplay().getRotation();
		switch (rotation) {
		case Surface.ROTATION_0:
			Log.d("TAG","Surface.ROTATION_0");
			_X = event.values[0];
			_Y = event.values[1];
			_Z = event.values[2];
			i = 1;

			break;
		case Surface.ROTATION_90: 
			Log.d("TAG","Surface.ROTATION_90"); 
			_X = event.values[1];
			_Y = event.values[0];
			_Z = event.values[2];
			i = 2;
			break;
		case Surface.ROTATION_180: 
			Log.d("TAG","Surface.ROTATION_180");
			_X = event.values[0];
			_Y = event.values[1];
			_Z = event.values[2];
			i = 3;
			break;
		case Surface.ROTATION_270: 
			Log.d("TAG","Surface.ROTATION_270");
			_X = event.values[1];
			_Y = event.values[0];
			_Z = event.values[2];
			i = 4;
			break;
		}

		//		float x = event.values[0];
		//		float y = event.values[1];
		//		float z = event.values[2];

		_X *= constant;
		_Y *= constant;
		_Z *= constant;

		if (!mInitialized) {
			mLastX = _X;
			mLastY = _Y;
			mLastZ = _Z;
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - _X);
			float deltaY = Math.abs(mLastY - _Y);
			float deltaZ = Math.abs(mLastZ - _Z);
			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;
			mLastX = _X;
			mLastY = _Y;
			mLastZ = _Z;

			Log.d("TAG", "deltaX" + deltaX);
			switch (i) {
			case 1:
				if (deltaX > SENSOR_THRESHOLD_VALUE) {
					if(mLastX < 0){
						if(mViewPager != null)
							mViewPager.changeItem(1,0);
					} else {
						if(mViewPager != null)
							mViewPager.changeItem(2,0);
					}
				}
				break;
			case 2:
				if (deltaX > SENSOR_THRESHOLD_VALUE) {
					if(mLastX > 0){
						if(mViewPager != null)
							mViewPager.changeItem(1,0);
					} else {
						if(mViewPager != null)
							mViewPager.changeItem(2,0);
					}
				}
				break;
			case 3:
				if (deltaX > SENSOR_THRESHOLD_VALUE) {
					if(mLastX > 0){
						if(mViewPager != null)
							mViewPager.changeItem(1,0);
					} else {
						if(mViewPager != null)
							mViewPager.changeItem(2,0);
					}
				}
				break;
			case 4:
				if (deltaX > SENSOR_THRESHOLD_VALUE) {
					if(mLastX < 0){
						if(mViewPager != null)
							mViewPager.changeItem(1,0);
					} else {
						if(mViewPager != null)
							mViewPager.changeItem(2,0);
					}
				}
				break;
			}

		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
