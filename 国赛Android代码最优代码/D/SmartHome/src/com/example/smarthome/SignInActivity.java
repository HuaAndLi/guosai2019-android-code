package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etIp;
	private Button btnSignIn;
	private Button btnSignUp;
	private CheckBox cbRemem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUtils.init();
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etIp = (EditText) findViewById(R.id.et_ip);
		cbRemem = (CheckBox) findViewById(R.id.cb_remem);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		etUsername.setText(MyUtils.shared.getString("user", ""));
		etPassword.setText(MyUtils.shared.getString("pass", ""));
		etIp.setText(MyUtils.shared.getString("ip", ""));
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String ip = etIp.getText().toString();
				if (MyUtils.isEmpty(username, password, ip)) {
					MyToast.makeText("参数不为空");
					return;
				}
				Cursor cursor = MyUtils.sdb
						.rawQuery(
								"select * from tb_userInfo where username = ? and password = ?",
								new String[] { username, password });
				if (cursor.getCount() > 0) {
					MyUtils.editor.putString("user",
							cbRemem.isChecked() ? username : "").commit();
					MyUtils.editor.putString("pass",
							cbRemem.isChecked() ? password : "").commit();
					MyUtils.editor.putString("ip",
							cbRemem.isChecked() ? ip : "").commit();
					finish();
					startActivity(new Intent(SignInActivity.this,
							MainActivity.class));
					MyToast.makeText("登录成功");
				} else {
					MyToast.makeText("账号或密码错误");
				}
				cursor.close();
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
	}

}
