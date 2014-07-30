/**
 * 
 */
package com.android.iviewer;

import java.io.File;
import java.util.ArrayList;

import junit.framework.Test;

import com.android.pirate.iviewer.R;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Kingsley Gomes
 *
 */
public class ImagesGridActivity  extends Activity{
	public static String mFileDirPath;
	public static ArrayList<String> test = new ArrayList<String>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_grid);
		  Bundle extras = getIntent().getExtras();
		  mFileDirPath = extras.getString("path");
		  

		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
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
				if(file[i].isDirectory())
					continue;
				Log.d("TAG", "FileName: " + file[i].getName());
				test.add(file[i].getName());
			}

		} 
		inflateGridview();
	}

	private void inflateGridview() {
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ImageAdapter1(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(ImagesGridActivity.this, " " + position, Toast.LENGTH_SHORT).show();

			}
		});
		
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
	/*	TextView textView;
		if (convertView == null) {  // if it's not recycled, initialize some attributes
			textView = new TextView(mContext);
			textView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			textView.setPadding(8, 8, 8, 8);
		} else {
			textView = (TextView) convertView;
		}

		textView.setText(ImagesGridActivity.test.get(position));
		return textView;*/
		 final ImageView imageView; 
         if (convertView == null) { 
             imageView = new ImageView(mContext); 
         } else { 
             imageView = (ImageView) convertView; 
         } 
         imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
         imageView.setPadding(8, 8, 8, 8);
        // imageView.setImageBitmap(photos.get(position).getBitmap());
         String str =ImagesGridActivity.mFileDirPath +"/"+ ImagesGridActivity.test.get(position);
         Log.d("TAG", " " + str);
         Uri uri = Uri.parse(str);
         imageView.setImageURI(uri);
			//Picasso.with(ImagesGridActivity.this).load(uri).into(imageView);

//			Picasso.with(ImagesGridActivity.this).load(ImagesGridActivity.mFileDirPath.into(imageView);
         return imageView; 
	}

}
