package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SignInActivity extends Activity {

	private EditText etIp;
	private Button btnSignIn;
	private SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		// 绑定控件
		etIp = (EditText) findViewById(R.id.et_ip);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		// 设置控件的监听
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekBar.getProgress() == seekBar.getMax()) {
					MyToast.makeText("验证成功");
					seekBar.setEnabled(false);
					MyUtils.startActivity(SignInActivity.this,
							MainActivity.class);
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

			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ip = etIp.getText().toString();
				if (MyUtils.ip.equals(ip)) {
					MyToast.makeText("服务器ip输入错误");
					return;
				}
				MyUtils.startActivity(SignInActivity.this, MainActivity.class);
			}
		});
	}
}
