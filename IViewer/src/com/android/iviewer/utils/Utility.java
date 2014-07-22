package com.android.iviewer.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utility {

	public static int getScreenWidth(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
		.getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		return width;
	}
	public static int getScreenHeight(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
		.getMetrics(displayMetrics);
		int height = displayMetrics.heightPixels;
		return height;
	}
}
