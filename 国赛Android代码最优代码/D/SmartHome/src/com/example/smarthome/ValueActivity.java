package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ValueActivity extends Activity implements Runnable {
	private TextView[] tvs = new TextView[9];
	private boolean isDestroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_value);
		tvs[0] = (TextView) findViewById(R.id.tv1);
		tvs[1] = (TextView) findViewById(R.id.tv2);
		tvs[2] = (TextView) findViewById(R.id.tv3);
		tvs[3] = (TextView) findViewById(R.id.tv4);
		tvs[4] = (TextView) findViewById(R.id.tv5);
		tvs[5] = (TextView) findViewById(R.id.tv6);
		tvs[6] = (TextView) findViewById(R.id.tv7);
		tvs[7] = (TextView) findViewById(R.id.tv8);
		tvs[8] = (TextView) findViewById(R.id.tv9);
		findViewById(R.id.imageView1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText(MyUtils.statehuman != 0 ? "有人" : "无人");
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
