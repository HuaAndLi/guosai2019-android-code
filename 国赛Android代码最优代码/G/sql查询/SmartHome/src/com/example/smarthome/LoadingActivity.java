package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingActivity extends Activity implements Runnable {

	private ImageView iv;
	private TextView tvNumber;
	private int number = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		MyUtils.init();
		iv = (ImageView) findViewById(R.id.imageView1);
		tvNumber = (TextView) findViewById(R.id.textView1);
		ScaleAnimation animation =new ScaleAnimation(0f, 10f, 0f, 10f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(2000);
		animation.setFillAfter(true);
		iv.startAnimation(animation);
		MyUtils.mHandler.postDelayed(this, 2000);
	}

	@Override
	public void run() {
		tvNumber.setText(number+"秒后进入登录界面。。。");
		if(number==0){
			finish();
			startActivity(new Intent(LoadingActivity.this,SignInActivity.class));
		}
		number--;
		if (number >= 0) {
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}

}
