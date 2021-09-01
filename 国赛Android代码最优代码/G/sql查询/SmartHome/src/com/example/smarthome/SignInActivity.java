package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText etIp;
	private EditText etUsername;
	private EditText etPassword;
	private Button btnSignIn;
	private CheckBox cbRemem;
	private CheckBox cbIn;
	private boolean isIn = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		etIp = (EditText) findViewById(R.id.et_ip);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		cbRemem = (CheckBox) findViewById(R.id.cb_remem);
		cbIn = (CheckBox) findViewById(R.id.cb_in);
		cbIn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cbRemem.setChecked(true);
				}
			}
		});
		cbRemem.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (!isChecked) {
					cbIn.setChecked(false);
				}
			}
		});
		etUsername.setText(MyUtils.shared.getString("user", ""));
		etPassword.setText(MyUtils.shared.getString("pass", ""));
		if (MyUtils.shared.getBoolean("in", false)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SignInActivity.this);
			builder.setTitle("登录");
			builder.setMessage("3秒直接登录，点击取消可取消");
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							isIn = false;
						}
					});
			builder.setCancelable(false);
			builder.show();
			MyUtils.mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					if (isIn) {
						btnSignIn.performClick();
					}
				}
			}, 3000);
		}
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(ip, username, password)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (username.equals("bizideal")
						&& password.equals(MyUtils.shared.getString("password",
								"123456"))) {
					MyUtils.editor.putString("user",
							cbRemem.isChecked() ? username : "").commit();
					MyUtils.editor.putString("pass",
							cbRemem.isChecked() ? password : "").commit();
					MyUtils.editor.putBoolean("in", cbIn.isChecked()).commit();
					finish();
					startActivity(new Intent(SignInActivity.this,
							MainActivity.class));
				} else {
					MyToast.makeText("用户名或密码不正确，请重新输入");
				}
			}
		});
	}
}
