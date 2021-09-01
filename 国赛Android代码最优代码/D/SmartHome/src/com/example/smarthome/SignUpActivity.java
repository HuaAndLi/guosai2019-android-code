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
	private EditText etPasswordre;
	private Button btnSignIn;
	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etPasswordre = (EditText) findViewById(R.id.et_passwordre);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String passwordre = etPasswordre.getText().toString();
				if (MyUtils.isEmpty(username, password)) {
					MyToast.makeText("账号密码不为空");
					return;
				}
				if (!password.equals(passwordre)) {
					MyToast.makeText("两次密码不一致");
					return;
				}
				if (password.length() < 6) {
					MyToast.makeText("密码长度不足6位");
					return;
				}
				if (!password.matches("[0-9]{1,}[a-zA-Z]{1,}")) {
					MyToast.makeText("密码格式为：数字+字母");
					return;
				}
				Cursor cursor = MyUtils.sdb.rawQuery(
						"select * from tb_userInfo where username = ?",
						new String[] { username });
				if (cursor.getCount() > 0) {
					MyToast.makeText("用户名已存在");
				} else {
					MyUtils.sdb.execSQL("insert into tb_userInfo values(?,?)",
							new String[] { username, password });
					btnSignUp.performClick();
					MyToast.makeText("注册成功");
				}
				cursor.close();
			}
		});

		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(SignUpActivity.this,
						SignInActivity.class));
			}
		});
	}

}
