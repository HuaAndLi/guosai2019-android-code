package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etIp;
	private EditText etPort;
	private Button btnSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		// �󶨿ؼ�
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etIp = (EditText) findViewById(R.id.et_ip);
		etPort = (EditText) findViewById(R.id.et_port);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		// ���ü���
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				String port = etPort.getText().toString();
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				if (MyUtils.isEmpty(ip, port, username, password)) {
					MyToast.makeText("������Ϊ��");
					return;
				}
				if (ip.equals(MyUtils.ip) && port.equals("6006")) {
					if (username.equals("bizideal003")
							&& password.equals("123456")) {
						finish();
						startActivity(new Intent(SigninActivity.this,
								SaveActivity.class));
						MyToast.makeText("�˺Ż�������ȷ");
					} else {
						AlertDialog.Builder builder = new AlertDialog.Builder(
								SigninActivity.this);
						builder.setTitle("��¼ʧ��");
						builder.setMessage("������û�������");
						builder.setPositiveButton("Ok", null);
						MyToast.makeText("�˺Ż��������");
					}
				} else {
					MyToast.makeText("ip��˿ںŴ���");
				}
			}
		});
	}
}
