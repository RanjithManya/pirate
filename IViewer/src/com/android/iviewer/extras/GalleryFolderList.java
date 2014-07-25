package com.android.iviewer.extras;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.android.pirate.iviewer.R;

/**
 * 
 * @author Kingsley Gomes
 *	list all the folders present in the gallery >>>>>>>>-------
 */
public class GalleryFolderList extends Activity {

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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fragment_main1);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
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


			do {
				// Get the field values
				bucket = cursor.getString(bucketColumn);
				id = cursor.getString(idColumn);
				Log.d("TAG", "DATA : " +bucket );

			} while (cursor.moveToNext());
		}

	}




}
