package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUitls;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class SignActivity extends Activity {

	private EditText etPassword;
	private EditText etUsername;
	private EditText etPort;
	private EditText etIp;
	private Button btnSignIn;
	private Button btnSignUp;
	private Button btnChange;
	private Button btnSave;
	private Spinner spUser;
	private CheckBox cbRemem;
	private List<String> lists = new ArrayList<String>();
	private ArrayAdapter<String> myAdapter;
	private RadioGroup rgUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		MyUitls.init();
		etIp = (EditText) findViewById(R.id.et_ip);
		etPort = (EditText) findViewById(R.id.et_port);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnChange = (Button) findViewById(R.id.btn_change);
		btnSave = (Button) findViewById(R.id.btn_save);
		spUser = (Spinner) findViewById(R.id.sp_user);
		spUser.setAdapter(myAdapter = new ArrayAdapter<String>(
				SignActivity.this, android.R.layout.simple_list_item_1, lists));
		cbRemem = (CheckBox) findViewById(R.id.cb_remem);
		rgUser = (RadioGroup) findViewById(R.id.rg_user);
		reUser();
		etUsername.setText(MyUitls.shared.getString("username", ""));
		etPassword.setText(MyUitls.shared.getString("password", ""));
		etUsername.setEnabled(true);
		spUser.setEnabled(false);
		rgUser.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio0) {
					etUsername.setEnabled(false);
					spUser.setEnabled(true);
					reUser();
				} else {
					etUsername.setEnabled(true);
					spUser.setEnabled(false);
				}
			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ip = etIp.getText().toString();
				String port = etPort.getText().toString();
				String username = null;
				String password = etPassword.getText().toString();
				if (rgUser.getCheckedRadioButtonId() == R.id.radio0) {
					if (lists.size() > 0) {
						username = lists.get(spUser.getSelectedItemPosition());
					}
				} else {
					username = etUsername.getText().toString();
				}
				if (MyUitls.isEmpty(username, password, ip, port)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (MyUitls.ip.equals(ip) && port.equals("6006")) {
					Cursor cursor = MyUitls.sdb
							.rawQuery(
									"select * from user where username = ? and password = ?",
									new String[] { username, password });
					if (cursor.getCount() > 0) {
						if (cbRemem.isChecked()) {
							MyUitls.editor.putString("username", username)
									.commit();
							MyUitls.editor.putString("password", password)
									.commit();
						}
						finish();
						startActivity(new Intent(SignActivity.this,
								MainActivity.class));
					} else {
						MyToast.makeText("账号或密码错误");
					}
					cursor.close();
				} else {
					MyToast.makeText("ip或端口号错误");
				}
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignActivity.this);
				View view = View.inflate(SignActivity.this, R.layout.signup,
						null);
				final EditText etusername = (EditText) view
						.findViewById(R.id.et_username);
				final EditText etpassword = (EditText) view
						.findViewById(R.id.et_password);
				final EditText etpasswordre = (EditText) view
						.findViewById(R.id.et_passwordre);
				final EditText etsave = (EditText) view
						.findViewById(R.id.et_save);
				builder.setNegativeButton("注册",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String username = etusername.getText()
										.toString();
								String password = etpassword.getText()
										.toString();
								String passwordre = etpasswordre.getText()
										.toString();
								String save = etsave.getText().toString();
								if (MyUitls.isEmpty(username, password,
										passwordre, save)) {
									MyToast.makeText("参数不为空");
									return;
								}
								if (!password.equals(passwordre)) {
									MyToast.makeText("确认密码不一致");
									return;
								}
								Cursor cursor = MyUitls.sdb
										.rawQuery(
												"select * from user where username = ?",
												new String[] { username, });
								if (cursor.getCount() > 0) {
									MyToast.makeText("用户名已存在");
								} else {
									MyUitls.sdb.execSQL(
											"insert into user values(?,?,?)",
											new String[] { username, password,
													save });
									reUser();
									MyToast.makeText("注册成功");
								}
								cursor.close();
							}
						});
				builder.setView(view);
				builder.show();
			}
		});
		btnChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignActivity.this);
				View view = View.inflate(SignActivity.this, R.layout.change,
						null);
				final EditText etusername = (EditText) view
						.findViewById(R.id.et_username);
				final EditText etpasswordold = (EditText) view
						.findViewById(R.id.et_passwordold);
				final EditText etpasswordre = (EditText) view
						.findViewById(R.id.et_passwordre);
				final EditText etpasswordnew = (EditText) view
						.findViewById(R.id.et_passwordnew);
				builder.setNegativeButton("修改密码",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String username = etusername.getText()
										.toString();
								String passwordold = etpasswordold.getText()
										.toString();
								String passwordre = etpasswordre.getText()
										.toString();
								String passwordnew = etpasswordnew.getText()
										.toString();
								if (MyUitls.isEmpty(username, passwordold,
										passwordre, passwordnew)) {
									MyToast.makeText("参数不为空");
									return;
								}
								if (!passwordnew.equals(passwordre)) {
									MyToast.makeText("确认密码不一致");
									return;
								}
								Cursor cursor = MyUitls.sdb
										.rawQuery(
												"select * from user where username = ?",
												new String[] { username });
								if (cursor.getCount() > 0) {
									cursor.moveToNext();
									if (passwordold.equals(cursor.getString(1))) {
										MyUitls.sdb
												.execSQL(
														"update user set password = ? where username = ?",
														new String[] {
																passwordnew,
																username });
										MyToast.makeText("修改成功");
									} else {
										MyToast.makeText("原密码错误");
									}
								} else {
									MyToast.makeText("账号不存在");
								}
								cursor.close();
							}
						});
				builder.setView(view);
				builder.show();
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignActivity.this);
				View view = View
						.inflate(SignActivity.this, R.layout.save, null);
				final EditText etusername = (EditText) view
						.findViewById(R.id.et_username);
				final EditText etsave = (EditText) view
						.findViewById(R.id.et_save);
				builder.setNegativeButton("找回密码",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String username = etusername.getText()
										.toString();
								String save = etsave.getText().toString();
								if (MyUitls.isEmpty(username, save)) {
									MyToast.makeText("参数不为空");
									return;
								}

								Cursor cursor = MyUitls.sdb
										.rawQuery(
												"select * from user where username = ? and save =?",
												new String[] { username, save });
								if (cursor.getCount() > 0) {
									cursor.moveToNext();
									AlertDialog.Builder builder = new AlertDialog.Builder(
											SignActivity.this);
									builder.setTitle("找回密码");
									builder.setMessage("当前账号的密码是："
											+ cursor.getString(1));
									builder.setNegativeButton("ok", null);
									builder.show();
								} else {
									MyToast.makeText("账号或二级密码错误");
								}
								cursor.close();
							}
						});
				builder.setView(view);
				builder.show();
			}
		});
	}

	public void reUser() {
		lists.clear();
		Cursor cursor = MyUitls.sdb.rawQuery("select * from user", null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				lists.add(cursor.getString(0));
			}
		}
		myAdapter.notifyDataSetChanged();
		cursor.close();
	}
}
