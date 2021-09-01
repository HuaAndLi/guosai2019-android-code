package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity implements Runnable {

	private ProgressBar bar;
	private TextView tvText;
	private TextView tvNumber;
	private int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		MyUtils.init();
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvText = (TextView) findViewById(R.id.textView1);
		tvNumber = (TextView) findViewById(R.id.textView2);
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		bar.setProgress(number);
		tvNumber.setText(number + "%");
		switch (number) {
		case 0:
			tvText.setText("正在初始化");
			break;
		case 21:
			tvText.setText("正在载入系统");
			break;
		case 51:
			tvText.setText("正在连接服务器");
			break;
		case 100:
			finish();
			startActivity(new Intent(LoadingActivity.this, SignInActivity.class));
			break;

		default:
			break;
		}
		number++;
		if (number <= 100) {
			MyUtils.mHandler.postDelayed(this, 80);
		}
	}
}
