package com.example.smarthome;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.bizideal.smarthome.socket.ControlUtils;

public class SignInActivity extends Activity {

	private EditText etPort;
	private EditText etIp;
	private Button btnSignIn;
	private SeekBar seekBar;
	private TextView tvText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUtils.init();
		etPort = (EditText) findViewById(R.id.et_port);
		etIp = (EditText) findViewById(R.id.et_ip);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		tvText = (TextView) findViewById(R.id.tv_text);
		if (MyUtils.shared.getInt("ins", 0) > 0) {
			tvText.setText("之前已有" + MyUtils.shared.getInt("ins", 0)
					+ "次登录，上次登录时间为" + MyUtils.shared.getString("tim", ""));
		}
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() >= 65 && seekBar.getProgress() <= 75) {
					btnSignIn.performClick();
				} else {
					seekBar.setProgress(0);
					MyToast.makeText("验证失败");
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

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
				String port = etPort.getText().toString();
				String ip = etIp.getText().toString();
				if (MyUtils.isEmpty(ip, port)) {
					MyToast.makeText("参数不为空");
					seekBar.setProgress(0);
					return;
				}
				MyUtils.editor.putInt("ins",
						MyUtils.shared.getInt("ins", 0) + 1).commit();
				MyUtils.editor.putString("tim", new SimpleDateFormat("HH:mm").format(new Date())).commit();
				ControlUtils.setUser("bizideal", "123456", ip);
				finish();
				startActivity(new Intent(SignInActivity.this,
						MainActivity.class));
			}
		});
	}

}
