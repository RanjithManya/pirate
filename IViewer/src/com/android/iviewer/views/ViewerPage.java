package com.android.iviewer.views;


import android.R.integer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.android.iviewer.adapters.ImageAdapter;
import com.android.pirate.iviewer.R;

public class ViewerPage {

	private RelativeLayout mRelativeLayout;
	private Context mContext;
	private ViewPager mVPager;



	public RelativeLayout getViewPager(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRelativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_vmain, null);
		 mVPager = (ViewPager) mRelativeLayout.findViewById(R.id.pager);
		
		ImageAdapter iAdapter = new ImageAdapter(mContext);
		mVPager.setAdapter(iAdapter);
		try {
			mVPager.setPageTransformer(true, new HorizontalParallaxTransformer(R.id.image1));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*TextView tv = (TextView) mRelativeLayout.findViewById(R.id.textview1);
		tv.setText("IMAGE VIEWER");*/
		return mRelativeLayout;
	}
	
	
	
	public void changeItem(int mode,int inPosition){
		int currentPosition = mVPager.getCurrentItem();
		Log.d("TAG", "change item \n" + "position " +currentPosition);
		switch (mode) {
		case 1:
			mVPager.setCurrentItem(currentPosition +1);
			mVPager.invalidate();
			break;

		case 2:
			mVPager.setCurrentItem(currentPosition -1);
			mVPager.invalidate();
			break;
		case 3:
			mVPager.setCurrentItem(inPosition );
			mVPager.invalidate();
			break;
			default:
				mVPager.invalidate();
		}
		
	}
	

}
