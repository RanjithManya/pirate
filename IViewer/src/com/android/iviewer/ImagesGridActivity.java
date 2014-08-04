/**
 * 
 */
package com.android.iviewer;

import java.io.File;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.iviewer.utils.Constants;
import com.android.pirate.iviewer.R;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.squareup.picasso.Picasso;

/**
 * @author Kingsley Gomes
 *
 */
public class ImagesGridActivity  extends Activity {



	public static String mFileDirPath;
	public static ArrayList<String> test = new ArrayList<String>();

	private final BaseSpringSystem mSpringSystem = SpringSystem.create();
	private final ExampleSpringListener mSpringListener = new ExampleSpringListener();
	private Spring mScaleSpring;
	private View mImageView;
	private String mParentDirName;



	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid2);
		
		Bundle extras = getIntent().getExtras();
		mFileDirPath = extras.getString("path");
		mParentDirName = extras.getString("parentDirName");
		setActionBar();
		init();
	}

	@Override
	public void onResume() {
		super.onResume();
		//mScaleSpring.addListener(mSpringListener);
	}

	@Override
	public void onPause() {
		super.onPause();
		//mScaleSpring.removeListener(mSpringListener);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			closeActivity();
			finish();
			break;
		}
		return true;
	}

	/**
	 * This method is used to inflate the Action bar with custom views, set
	 * Action Bar icon, Action Bar text
	 */
	private void setActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		
		if(mParentDirName != null)
			actionBar.setTitle(mParentDirName);

	}
	private void init() {
		// TODO Auto-generated method stub
		Constants.IMAGE_PATH1.clear();
		displayDirectoryContents();
	}


	/**
	 * display the contents of "mdestination path"
	 * @param view 
	 */
	private void displayDirectoryContents() {
		// TODO advanced stuff
		//	Constants.ORIGINAL_CONTENTS.clear();
		test.clear();
		File f = new File(mFileDirPath);        
		File file[] = f.listFiles();
		if(file.length != 0){
			Log.d("TAG", "frag 1 Size: "+ file.length);

			for (int i=0; i < file.length; i++)
			{
				if(file[i].isDirectory() || !(file[i].getName().contains(".jpg") ||(file[i].getName().contains(".png"))))
					continue;
				Log.d("TAG", "FileName: " + file[i].getName());

				test.add(file[i].getName());
			}

		} 
		inflateGridview();
	}

	protected void startActivity(int inPosition) {
		// TODO Auto-generated method stub
		String str;
		Constants.IMAGE_PATH1.clear();
		for(int i=0; i<test.size(); i++){

			str =ImagesGridActivity.mFileDirPath +"/"+ ImagesGridActivity.test.get(i);
			Log.d("TAG", "testt testse  " + str);
			Uri uri = Uri.parse(str);
			Constants.IMAGE_PATH1.add(uri);
		}

		Intent intent =  new Intent(this,ImageViewerMainActivity.class);
		intent.putExtra("position", inPosition);
		startActivity(intent);
	}


	private void inflateGridview() {
		GridView gridview = (GridView) findViewById(R.id.gridview2);
		gridview.setAdapter(new ImageAdapter1(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(ImagesGridActivity.this, " " + position, Toast.LENGTH_SHORT).show();
				mImageView = v;
				//mScaleSpring.setEndValue(1);

				startActivity(position);

			}
		});

	}

	private class ExampleSpringListener extends SimpleSpringListener {
		@Override
		public void onSpringUpdate(Spring spring) {
			float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0, 1, 1, 0.5);
			mImageView.setScaleX(mappedValue);
			mImageView.setScaleY(mappedValue);
		}
	}

}

class ImageAdapter1 extends BaseAdapter {
	private Context mContext;

	public ImageAdapter1(Context c) {
		mContext = c;
	}

	public int getCount() {
		if( ImagesGridActivity.test != null)
			return ImagesGridActivity.test.size();
		else 
			return 0;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_gridview_item, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.item_text);
			holder.imageItem = (ImageView) row.findViewById(R.id.item_image);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		// Item item = data.get(position);
		holder.txtTitle.setText(ImagesGridActivity.test.get(position));


		String str =ImagesGridActivity.mFileDirPath +"/"+ ImagesGridActivity.test.get(position);
		Log.d("TAG", " " + str);
		Uri uri = Uri.parse(str);
		//   if(!Constants.IMAGE_PATH1.contains(uri))


		// imageView.setImageURI(uri);
		//holder.imageItem.setImageURI(uri);
		 Picasso.with(mContext).load(uri).into(holder.imageItem);
		return row;

	}
	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;

	}

}
