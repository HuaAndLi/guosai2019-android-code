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
		// ��ʼ��
		MyUtils.init();
		// �󶨿ؼ�
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvText = (TextView) findViewById(R.id.textView1);
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
			tvText.setText("����������...........");
			break;
		case 60:
			tvText.setText("���ڳ�ʼ������...........");
			break;
		case 80:
			tvText.setText("�����ʼ�����...........");
			break;
		case 100:
			tvText.setText("����ϵͳ...........");
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
