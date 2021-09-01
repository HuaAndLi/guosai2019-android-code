package com.example.smarthome;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义闪烁的文本
 * 
 * @author Administrator
 * 
 */
public class HidenView extends TextView implements Runnable {
	private Handler mHandler = new Handler();

	public HidenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHandler.post(this);
	}

	@Override
	public void run() {
		if (getVisibility() == View.VISIBLE) {
			setVisibility(View.INVISIBLE);
		} else {
			setVisibility(View.VISIBLE);
		}
		mHandler.postDelayed(this, 1000);
	}

}
