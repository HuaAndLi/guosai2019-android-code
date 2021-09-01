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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class SignInActivity extends Activity {

	private EditText etIp;
	private EditText etUsername;
	private EditText etPassword;
	private CheckBox cbRemem;
	private CheckBox cbIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		etIp = (EditText) findViewById(R.id.et_ip);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		cbRemem = (CheckBox) findViewById(R.id.cb_remem);
		cbIn = (CheckBox) findViewById(R.id.cb_in);
		etUsername.setText(MyUtils.shared.getString("user", ""));
		etPassword.setText(MyUtils.shared.getString("pass", ""));
		if (MyUtils.shared.getBoolean("in", false)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SignInActivity.this);
			builder.setTitle("µÇÂ¼");
			builder.setMessage("ÊÇ·ñ×Ô¶¯µÇÂ¼");
			builder.setNegativeButton("ÊÇ",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							finish();
							startActivity(new Intent(SignInActivity.this,
									MainActivity.class));
						}
					});
			builder.setPositiveButton("·ñ", null);
			builder.setCancelable(false);
			builder.show();
		}
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
		findViewById(R.id.btn_signin).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ip = etIp.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(username, password, ip)) {
					MyToast.makeText("²ÎÊý²»Îª¿Õ");
					return;
				}
				if (ip.equals(MyUtils.ip)) {
					if (username.equals("bizideal")
							&& password.equals(MyUtils.shared.getString(
									"password", "123456"))) {
						MyUtils.editor.putString("user",
								cbRemem.isChecked() ? username : "").commit();
						MyUtils.editor.putString("pass",
								cbRemem.isChecked() ? password : "").commit();
						MyUtils.editor.putBoolean("in", cbIn.isChecked())
								.commit();
						finish();
						startActivity(new Intent(SignInActivity.this,
								MainActivity.class));
					} else {
						MyToast.makeText("ÕËºÅ»òÃÜÂë´íÎó");
					}
				} else {
					MyToast.makeText("ip´íÎó");
				}
			}
		});
	}
}
