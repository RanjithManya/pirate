package com.android.iviewer;

import com.android.iviewer.views.ViewerPage;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ViewerPage().getViewPager(this));
		
	}

	

}
