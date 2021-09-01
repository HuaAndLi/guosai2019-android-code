package com.example.smarthome;

import utils.MyUitls;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SignInActivity extends Activity {

	private Button btnSignUp;
	private RadioGroup rgUser;
	private TextView tvText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUitls.init();
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		rgUser = (RadioGroup) findViewById(R.id.rg_user);
		tvText = (TextView) findViewById(R.id.tv_toast);
		for (int i = 0; i < rgUser.getChildCount(); i++) {
			if(MyUitls.shared.getBoolean("p"+i, false)){
				rgUser.getChildAt(i).setVisibility(View.VISIBLE);
			}
		}
		rgUser.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for (int i = 0; i < rgUser.getChildCount(); i++) {
					rgUser.getChildAt(i).setEnabled(false);
				}
				tvText.setText("正在校验用户信息。。。");
				MyUitls.mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						tvText.setText("用户信息正确，正在进入系统。。。");
						MyUitls.mHandler.postDelayed(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								finish();
								startActivity(new Intent(SignInActivity.this,
										MainActivity.class));
							}
						}, 2000);
					}
				}, 2000);
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
