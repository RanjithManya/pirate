package com.android.iviewer.views;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.android.iviewer.adapters.ImageAdapter;
import com.android.pirate.iviewer.R;

public class ViewerPage {

	private RelativeLayout mRelativeLayout;
	private Context mContext;

	public RelativeLayout getViewPager(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_vmain, null);
		ViewPager vPager = (ViewPager) mRelativeLayout.findViewById(R.id.pager);
		ImageAdapter iAdapter = new ImageAdapter(mContext);
		vPager.setAdapter(iAdapter);
		
		/*TextView tv = (TextView) mRelativeLayout.findViewById(R.id.textview1);
		tv.setText("IMAGE VIEWER");*/
		return mRelativeLayout;
	}

}
