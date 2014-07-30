package com.android.iviewer;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pirate.iviewer.R;

public class GridViewActivity extends Activity {

	final String[] projection = {
			
			MediaStore.Images.Media.BUCKET_ID,
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME
	};

	String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
	String BUCKET_ORDER_BY = "MAX(datetaken) DESC";
	private Cursor cursor;
	private int mSize;
	private String bucket;
	private String id;

	private int mIndex;

	private Uri mUri1;


	static ArrayList<String> mFoldernames = new ArrayList<String>();
	static ArrayList<Integer> mFolderIds = new ArrayList<Integer>();


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid);

		init();
		GridView gridview = (GridView) findViewById(R.id.gridview1);
		gridview.setAdapter(new ImageAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			private Uri mUri;

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(GridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
				
				
				// mUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" +GridViewActivity.mFoldernames.get(position));
//				 init1(GridViewActivity.mFoldernames.get(position));
				
			}
		});
	}
	
	
	private void init() {

		// TODO Auto-generated method stub
		mFoldernames.clear();

		cursor = this.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				projection,
				BUCKET_GROUP_BY,
				null,
				BUCKET_ORDER_BY
				);
		mSize = cursor.getCount();

		if (cursor.moveToFirst()) {

			int bucketColumn = cursor.getColumnIndex(
					MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

			int idColumn = cursor.getColumnIndex(
					MediaStore.Images.Media.BUCKET_ID);
			//int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);

			do {
				// Get the field values
				bucket = cursor.getString(bucketColumn);
				//mIndex = cursor.getInt(index);
				//mFolderIds.add(mIndex);
				mFoldernames.add(bucket);
				id = cursor.getString(idColumn);
				Log.d("TAG", "DATA : " +bucket );

			} while (cursor.moveToNext());
		}


	}
	
	public void getFromSdcard()
	{
		File[] listFile;
		ArrayList<String> f = new ArrayList<String>();// list of file paths
	    File file= new File(android.os.Environment.getExternalStorageDirectory(),"MapleBear");

	        if (file.isDirectory())
	        {
	            listFile = file.listFiles();


	            for (int i = 0; i < listFile.length; i++)
	            {

	                f.add(listFile[i].getAbsolutePath());

	            }
	        }
	}

}	
class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		if( GridViewActivity.mFoldernames != null)
			return GridViewActivity.mFoldernames.size();
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
		TextView textView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			textView.setPadding(8, 8, 8, 8);
		} else {
			textView = (TextView) convertView;
		}

		textView.setText( GridViewActivity.mFoldernames.get(position));
		return textView;
	}


}