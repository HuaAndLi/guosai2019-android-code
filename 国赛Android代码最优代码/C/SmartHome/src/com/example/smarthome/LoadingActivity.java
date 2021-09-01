package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 加载界面
 * 
 * @author Administrator
 * 
 */
public class LoadingActivity extends Activity implements Runnable {

	private TextView tvText;
	private ProgressBar bar;
	private int number = 0;
	private TextView tvAni;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		// 初始化 详情接MyUtils
		MyUtils.init();
		// 绑定控件
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvText = (TextView) findViewById(R.id.textView1);
		tvAni = (TextView) findViewById(R.id.textView2);
		// 设置动画
		TranslateAnimation animation = new TranslateAnimation(600, -600, 0, 0);
		animation.setDuration(7000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		tvAni.startAnimation(animation);
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
			tvText.setText("界面配置加载完成...........");
			break;
		case 60:
			tvText.setText("正在初始化界面...........");
			break;
		case 80:
			tvText.setText("界面初始化完成...........");
			break;
		case 99:
			tvText.setText("进入系统...........");
			finish();
			startActivity(new Intent(LoadingActivity.this, SigninActivity.class));
			break;

		default:
			break;
		}
		number++;
		if (number <= 99) {
			MyUtils.mHandler.postDelayed(this, 80);
		}
	}

}
