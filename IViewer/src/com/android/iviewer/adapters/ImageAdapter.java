package com.android.iviewer.adapters;

import java.util.ArrayList;






import com.android.iviewer.AccelometerMain;
import com.android.iviewer.utils.Constants;
import com.android.pirate.iviewer.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends PagerAdapter {

	private Context mContext;
	private int[] imArray = {R.drawable.thumbnail_1,R.drawable.thumbnail_2,R.drawable.thumbnail_3,R.drawable.thumbnail_4,R.drawable.thumbnail_5,R.drawable.thumbnail_6,R.drawable.thumbnail_7,R.drawable.thumbnail_8};

	public ImageAdapter(Context context) {

		mContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Constants.IMAGE_PATH.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == ((View) object);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View layout = inflater.inflate(R.layout.viewpageitem_xml, null);
		ImageView imageView = (ImageView) layout.findViewById(R.id.image1);
		//TextView textView = (TextView) layout.findViewById(R.id.description);
		//Log.d("TAG","imArray[position] = " +imArray[position]);
		//im.setImageResource(imArray[position]);
		Picasso.with(mContext).load(Constants.IMAGE_PATH.get(position)).into(imageView);
//		Log.d("TAG","position = " +position);
		//textView.setText(position.t);
		((ViewPager) container).addView(layout);
		return layout;
	}
	
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
		System.gc();
	}


}
