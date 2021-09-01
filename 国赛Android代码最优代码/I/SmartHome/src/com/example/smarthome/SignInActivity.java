package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etIp;
	private Button btnSignIn;
	private Button btnSignUp;
	private Button btnChange;
	private Button btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etIp = (EditText) findViewById(R.id.et_ip);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnChange = (Button) findViewById(R.id.btn_change);
		btnExit = (Button) findViewById(R.id.btn_exit);
		etUsername.setText(MyUtils.shared.getString("user", ""));
		etPassword.setText(MyUtils.shared.getString("pass", ""));
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ip = etIp.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(ip, username, password)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (MyUtils.ip.equals(ip)) {
					Cursor cursor = MyUtils.sdb
							.rawQuery(
									"select * from user where username = ? and password =?",
									new String[] { username, password });
					if (cursor.getCount() > 0) {
						MyUtils.editor.putString("user", username).commit();
						MyUtils.editor.putString("pass", password).commit();
						finish();
						startActivity(new Intent(SignInActivity.this,
								MainActivity.class));
					} else {
						MyUtils.showAlert(SignInActivity.this, "登录失败",
								"密码或用户名错误");
					}
					cursor.close();
				} else {
					MyToast.makeText("ip错误");
				}
			}
		});
		btnChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent(SignInActivity.this,
						ChangeActivity.class));
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent(SignInActivity.this,
						SignUpActivity.class));
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
