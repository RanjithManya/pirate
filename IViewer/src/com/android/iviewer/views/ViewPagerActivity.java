package com.android.iviewer.views;

import android.app.Activity;
import android.os.Bundle;

public class ViewPagerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ImageStripViewer imgStripViwer = new ImageStripViewer();
		setContentView(imgStripViwer.getViewPager(this));
	}

}
