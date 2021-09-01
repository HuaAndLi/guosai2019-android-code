package com.example.smarthome;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity implements Runnable {

	private ProgressBar bar;
	private TextView tvNumber;
	private int number = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		// 绑定控件
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tvNumber = (TextView) findViewById(R.id.textView1);
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		bar.setProgress(number);
		tvNumber.setText(number + "%");
		if (number == 100) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LoadingActivity.this);
			builder.setTitle("登录成功");
			builder.setMessage("欢迎回来");
			builder.setNegativeButton("Ok", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					startActivity(new Intent(LoadingActivity.this,
							MainActivity.class));
					SocketClient.getInstance().login(new LoginCallback() {

						@Override
						public void onEvent(final String arg0) {
							MyUtils.mHandler.post(new Runnable() {

								@Override
								public void run() {
									if (ConstantUtil.SUCCESS.equals(arg0)) {
										MyToast.makeText("组网成功");
									} else {
										MyToast.makeText("组网失败");
									}
								}
							});
						}
					});
					SocketClient.getInstance().disConnect();
					SocketClient.getInstance().creatConnect();
				}
			});
			builder.setCancelable(false);
			builder.show();
		}

		number++;
		if (number <= 100) {
			MyUtils.mHandler.postDelayed(this, 80);
		}
	}
}
