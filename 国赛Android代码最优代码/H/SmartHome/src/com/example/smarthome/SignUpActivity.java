package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUitls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SignUpActivity extends Activity {

	private Button btnSignUp;
	private Button btnExit;
	private CheckBox cbP1;
	private CheckBox cbP2;
	private CheckBox cbP3;
	private CheckBox cbP4;
	private List<CheckBox> cbUsers = new ArrayList<CheckBox>();
	private boolean isFlag;
	private TextView tvText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnExit = (Button) findViewById(R.id.btn_exit);
		cbUsers.add(cbP1 = (CheckBox) findViewById(R.id.cb_p1));
		cbUsers.add(cbP2 = (CheckBox) findViewById(R.id.cb_p2));
		cbUsers.add(cbP3 = (CheckBox) findViewById(R.id.cb_p3));
		cbUsers.add(cbP4 = (CheckBox) findViewById(R.id.cb_p4));
		tvText = (TextView) findViewById(R.id.tv_text);
		for (int i = 0; i < cbUsers.size(); i++) {
			final int cur = i;
			cbUsers.get(i).setOnCheckedChangeListener(
					new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								for (int j = 0; j < cbUsers.size(); j++) {
									if (cur != j) {
										cbUsers.get(j).setChecked(false);
									}
								}
							}
						}
					});
		}
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isFlag) {
					finish();
					startActivity(new Intent(SignUpActivity.this,
							SignInActivity.class));
				} else {
					MyToast.makeText("����ע���У����Եȡ���");
				}
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isFlag) {
					isFlag = true;
					for (int j = 0; j < cbUsers.size(); j++) {
						cbUsers.get(j).setEnabled(false);
						if (cbUsers.get(j).isChecked()) {
							MyUitls.editor.putBoolean("p" + j, true).commit();
						}
					}

					tvText.setText("����ע���û���Ϣ");
					MyUitls.mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							tvText.setText("�û���Ϣע��ɹ�");
							MyUitls.mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									finish();
									startActivity(new Intent(
											SignUpActivity.this,
											SignInActivity.class));
								}
							}, 2000);
						}
					}, 2000);
				} else {
					MyToast.makeText("����ע���У����Եȡ���");
				}
			}
		});
	}

}
