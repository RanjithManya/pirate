package com.android.iviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.android.pirate.iviewer.R;

public class IViewerMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iviewer_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.iviewer_main, menu);
		return true;
	}

}
