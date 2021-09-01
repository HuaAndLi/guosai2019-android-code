package com.example.smarthome;

import java.net.Socket;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText etIp;
	private Button btnSignIn;
	private Button btnSave;
	private boolean isConn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUtils.init();
		etIp = (EditText) findViewById(R.id.et_ip);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSave = (Button) findViewById(R.id.btn_save);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent(SignInActivity.this,
						SaveActivity.class));
			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				if (TextUtils.isEmpty(ip)) {
					MyToast.makeText("ip不为空");
					return;
				}
				if (!isConn) {
					isConn = true;
					ControlUtils.setUser("bizideal", "123456", ip);
					SocketClient.getInstance().login(new LoginCallback() {

						@Override
						public void onEvent(final String arg0) {
							// TODO Auto-generated method stub
							isConn = false;
							MyUtils.mHandler.post(new Runnable() {

								@Override
								public void run() {
									if (ConstantUtil.SUCCESS.equals(arg0)) {
										MyToast.makeText("连接成功");
										finish();
										startActivity(new Intent(
												SignInActivity.this,
												MainActivity.class));
									} else {
										AlertDialog.Builder builder = new AlertDialog.Builder(
												SignInActivity.this);
										builder.setTitle("提示");
										builder.setMessage("网络连接失败，是否返回登录界面");
										builder.setNegativeButton("确定", null);
										builder.setPositiveButton(
												"取消",
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														finish();
													}
												});
										builder.setCancelable(false);
										builder.show();
									}
								}
							});
						}
					});
					SocketClient.getInstance().disConnect();
					SocketClient.getInstance().creatConnect();
				}else{
					MyToast.makeText("请稍后");
				}

			}
		});
	}
}
