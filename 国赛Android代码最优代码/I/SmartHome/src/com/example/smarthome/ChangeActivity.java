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
import android.widget.EditText;

public class ChangeActivity extends Activity {

	private EditText etUsername;
	private Button btnSignIn;
	private Button btnExit;
	private EditText etPasswordnew;
	private EditText etPasswordold;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPasswordnew = (EditText) findViewById(R.id.et_passwordnew);
		etPasswordold = (EditText) findViewById(R.id.et_passwordold);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnExit = (Button) findViewById(R.id.btn_exit);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = etUsername.getText().toString();
				String passwordnew = etPasswordnew.getText().toString();
				String passwordold = etPasswordold.getText().toString();
				if (MyUtils.isEmpty(username, passwordold, passwordnew)) {
					MyToast.makeText("������Ϊ��");
					return;
				}
				Cursor cursor = MyUtils.sdb.rawQuery(
						"select * from user where username = ?",
						new String[] { username });
				if (cursor.getCount() > 0) {
					cursor.moveToNext();
					if (cursor.getString(1).equals(passwordold)) {
						MyUtils.sdb
								.execSQL(
										"update user set password = ? where username =?",
										new String[] { passwordnew, username });
						MyUtils.showAlert(ChangeActivity.this, "�޸ĳɹ�", "�����޸ĳɹ�");
					} else {
						MyUtils.showAlert(ChangeActivity.this, "�޸�ʧ��", "���������");
					}
				} else {
					MyUtils.showAlert(ChangeActivity.this, "�޸�ʧ��",
							"�޸�ʧ�ܣ�������û���������");
				}
				cursor.close();
			}
		});
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				startActivity(new Intent(ChangeActivity.this,
						SignInActivity.class));
			}
		});
	}

}
