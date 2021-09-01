package com.example.smarthome;

import utils.MyUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity implements Runnable {
	private int number = 0;
	private ProgressBar bar;
	private TextView tvText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		// 初始化
		MyUtils.init();
		// 绑定控件
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvText = (TextView) findViewById(R.id.textView1);
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		bar.setProgress(number);
		switch (number) {
		case 10:
			tvText.setText("正在加载串口配置...........");
			break;
		case 20:
			tvText.setText("串口配置加载完成...........");
			break;
		case 30:
			tvText.setText("正在加载界面配置...........");
			break;
		case 50:
			tvText.setText("界面加载完成...........");
			break;
		case 60:
			tvText.setText("正在初始化界面...........");
			break;
		case 80:
			tvText.setText("界面初始化完成...........");
			break;
		case 100:
			tvText.setText("进入系统...........");
			MyUtils.startActivity(LoadingActivity.this, SignInActivity.class);
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
