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

public class SignUpActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private Button btnSignIn;
	private Button btnExit;
	private EditText etPasswordre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etPasswordre = (EditText) findViewById(R.id.et_passwordre);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnExit = (Button) findViewById(R.id.btn_exit);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String passwordre = etPasswordre.getText().toString();
				if (MyUtils.isEmpty(username, password, passwordre)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (!password.equals(passwordre)) {
					MyUtils.showAlert(SignUpActivity.this, "注册失败", "重复密码不一致");
					return;
				}
				Cursor cursor = MyUtils.sdb.rawQuery(
						"select * from user where username = ?",
						new String[] { username });
				if (cursor.getCount() > 0) {
					MyUtils.showAlert(SignUpActivity.this, "注册失败",
							"用户名已存在，请重新注册");
				} else {
					MyUtils.sdb.execSQL("insert into user values(?,?)",
							new String[] { username, password });
					MyUtils.showAlert(SignUpActivity.this, "注册成功", "用户注册成功");
				}
				cursor.close();
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent(SignUpActivity.this,
						SignInActivity.class));
			}
		});
	}

}
