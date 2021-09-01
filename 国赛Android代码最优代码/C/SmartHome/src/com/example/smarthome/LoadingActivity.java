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
 * ���ؽ���
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
		// ��ʼ�� �����MyUtils
		MyUtils.init();
		// �󶨿ؼ�
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvText = (TextView) findViewById(R.id.textView1);
		tvAni = (TextView) findViewById(R.id.textView2);
		// ���ö���
		TranslateAnimation animation = new TranslateAnimation(600, -600, 0, 0);
		animation.setDuration(7000);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		tvAni.startAnimation(animation);
		// �����߳�
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		bar.setProgress(number);
		switch (number) {
		case 10:
			tvText.setText("���ڼ��ش�������...........");
			break;
		case 20:
			tvText.setText("�������ü������...........");
			break;
		case 30:
			tvText.setText("���ڼ��ؽ�������...........");
			break;
		case 50:
			tvText.setText("�������ü������...........");
			break;
		case 60:
			tvText.setText("���ڳ�ʼ������...........");
			break;
		case 80:
			tvText.setText("�����ʼ�����...........");
			break;
		case 99:
			tvText.setText("����ϵͳ...........");
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
