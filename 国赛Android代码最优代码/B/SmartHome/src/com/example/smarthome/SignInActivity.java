package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SignInActivity extends Activity {

	private EditText etIp;
	private EditText etUsername;
	private EditText etPassword;
	private Button btnSignIn;
	private SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUtils.init();
		// �󶨿ؼ�
		etIp = (EditText) findViewById(R.id.et_ip);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		// ���ü���
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() != seekBar.getMax()) {
					seekBar.setProgress(0);
					MyToast.makeText("��֤ʧ��");
				} else {
					MyToast.makeText("��֤�ɹ�");
					btnSignIn.performClick();
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(ip)) {
					MyToast.makeText("IP�Ƿ�");
					seekBar.setProgress(0);
					return;
				}
				if (username.equals("admin") && password.equals("123456")) {
					finish();
					startActivity(new Intent(SignInActivity.this,
							LoadingActivity.class));
				} else {
					MyToast.makeText("�˺Ż��������");
					seekBar.setProgress(0);
				}
			}
		});
	}

}
