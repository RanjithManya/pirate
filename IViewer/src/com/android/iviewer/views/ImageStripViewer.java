package com.android.iviewer.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.pirate.iviewer.R;

public class ImageStripViewer {
	private Context mContext;
	private RelativeLayout mRelativeLayout;
	private PagerContainer mContainer;
	
	public ImageStripViewer()
	{
		
	}

	public RelativeLayout getViewPager(Context context) {

		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRelativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.image_strip_xml, null);
		mContainer = (PagerContainer) mRelativeLayout.findViewById(R.id.pager_container);

		LoopingViewPager pager = mContainer.getViewPager();
		
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		PagerAdapter adapter = new MyPagerAdapter();
		pager.setAdapter(adapter);
		pager.setCurrentItem(2);
		//Necessary or the pager will only have one extra page to show
		// make this at least however many pages you can see
		//pager.setOffscreenPageLimit(adapter.getCount());
		//A little space between pages
		//pager.setPageMargin(15);

		//If hardware acceleration is enabled, you should also remove
		// clipping on the pager for its children.
		pager.setClipChildren(false);
		return mRelativeLayout;
	}
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TextView view = new TextView(mContext);
			view.setText("Item "+position);
			view.setGravity(Gravity.CENTER);
			view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));

			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}
	}
}
