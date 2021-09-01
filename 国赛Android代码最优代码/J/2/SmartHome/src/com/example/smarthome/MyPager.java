package com.example.smarthome;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyPager extends ViewPager {
	public static boolean isMoving = true;

	public MyPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!isMoving) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}
}
