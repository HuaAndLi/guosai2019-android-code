package com.example.smarthome;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimeView extends TextView implements Runnable {
	private Handler mHandler = new Handler();

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHandler.post(this);
	}

	@Override
	public void run() {
		setText(new SimpleDateFormat("HH:mm").format(new Date()));
		mHandler.postDelayed(this, 1000);
	}

}
