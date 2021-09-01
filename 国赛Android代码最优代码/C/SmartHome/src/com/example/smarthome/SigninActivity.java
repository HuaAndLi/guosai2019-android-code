package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etIp;
	private EditText etPort;
	private Button btnSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		// 绑定控件
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etIp = (EditText) findViewById(R.id.et_ip);
		etPort = (EditText) findViewById(R.id.et_port);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		// 设置监听
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				String port = etPort.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(ip, port, username, password)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (ip.equals(MyUtils.ip) && port.equals("6006")) {
					if (username.equals("bizideal003")
							&& password.equals("123456")) {
						finish();
						startActivity(new Intent(SigninActivity.this,
								SaveActivity.class));
						MyToast.makeText("账号或密码正确");
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								SigninActivity.this);
						builder.setTitle("登录失败");
						builder.setMessage("密码或用户名错误");
						builder.setPositiveButton("Ok", null);
						MyToast.makeText("账号或密码错误");
					}
				} else {
					MyToast.makeText("ip或端口号错误");
				}
			}
		});
	}
}
