package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

public class SignInActivity extends Activity {

	private EditText etUsername;
	private EditText etPassword;
	private EditText etIp;
	private EditText etPort;
	private Button btnSignIn;
	private Button btnSignUp;
	private Button btnChange;
	private Button btnSave;
	private CheckBox cbRemem;
	private RadioGroup rgUser;
	private Spinner spUser;
	private List<String> lists = new ArrayList<String>();
	private ArrayAdapter<String> myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		MyUtils.init();
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etIp = (EditText) findViewById(R.id.et_ip);
		etPort = (EditText) findViewById(R.id.et_port);
		btnSignIn = (Button) findViewById(R.id.btn_signin);
		btnSignUp = (Button) findViewById(R.id.btn_signup);
		btnChange = (Button) findViewById(R.id.btn_change);
		btnSave = (Button) findViewById(R.id.btn_save);
		cbRemem = (CheckBox) findViewById(R.id.cb_remem);
		rgUser = (RadioGroup) findViewById(R.id.rg_user);
		spUser = (Spinner) findViewById(R.id.sp_user);
		spUser.setAdapter(myAdapter = new ArrayAdapter<String>(
				SignInActivity.this, android.R.layout.simple_list_item_1, lists));
		etUsername.setText(MyUtils.shared.getString("user", ""));
		etPassword.setText(MyUtils.shared.getString("pass", ""));
		setUser();
		spUser.setEnabled(false);
		etUsername.setEnabled(false);
		rgUser.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					spUser.setEnabled(true);
					etUsername.setEnabled(false);
				} else {
					spUser.setEnabled(false);
					etUsername.setEnabled(true);
				}
			}
		});
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String ip = etIp.getText().toString();
				String port = etPort.getText().toString();
				if(rgUser.getCheckedRadioButtonId()==R.id.radio0){
					username =lists.get(spUser.getSelectedItemPosition());
				}
				if (MyUtils.isEmpty(ip, port, username, password)) {
					MyToast.makeText("������Ϊ��");
					return;
				}
				if(ip.equals(MyUtils.ip)&&port.equals("6006")){
					Cursor cursor =MyUtils.sdb.rawQuery("select * from user where username = ? and password = ?", new String[]{username,password});
					if(cursor.getCount()>0){
						MyUtils.editor.putString("user", cbRemem.isChecked()?username:"").commit();
						MyUtils.editor.putString("pass", cbRemem.isChecked()?password:"").commit();
						finish();
						startActivity(new Intent(SignInActivity.this,MainActivity.class));
					}else{
						MyToast.makeText("�˺Ż��������");
					}
					cursor.close();
				}else{
					MyToast.makeText("ip��˿ڴ���");
				}
				
			}
		});
		btnSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignInActivity.this);
				View view = View.inflate(SignInActivity.this, R.layout.signup,
						null);
				final EditText etusername = (EditText) view.findViewById(R.id.et_username);
				final EditText etpassword = (EditText) view.findViewById(R.id.et_password);
				final EditText etpasswordre = (EditText) view.findViewById(R.id.et_passwordre);
				final EditText eter = (EditText) view.findViewById(R.id.et_er);
				builder.setView(view);
				builder.setNegativeButton("ע��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String username = etusername.getText().toString();
						String password = etpassword.getText().toString();
						String passwordre = etpasswordre.getText().toString();
						String er = eter.getText().toString();
						if (MyUtils.isEmpty(username, password, passwordre, er)) {
							MyToast.makeText("������Ϊ��");
							return;
						}
						if(!password.equals(passwordre)){
							MyToast.makeText("�������벻һ��");
							return;
						}
						Cursor cursor =MyUtils.sdb.rawQuery("select * from user where username = ?", new String[]{username});
						if(cursor.getCount()>0){
							MyToast.makeText("�û����Ѵ���");
						}else{
							setUser();
							MyUtils.sdb.execSQL("insert into user values(?,?,?)",new String[]{username,password,er});
							MyToast.makeText("ע��ɹ�");
						}
						cursor.close();
					}
				});
				builder.show();
			}
		});
		btnChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignInActivity.this);
				View view = View.inflate(SignInActivity.this, R.layout.change,
						null);
				final EditText etusername = (EditText) view.findViewById(R.id.et_username);
				final EditText etpasswordnew = (EditText) view.findViewById(R.id.et_passwordnew);
				final EditText etpasswordold = (EditText) view.findViewById(R.id.et_passwordold);
				final EditText etpasswordre = (EditText) view.findViewById(R.id.et_passwordre);
				builder.setView(view);
				builder.setNegativeButton("�޸�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String username = etusername.getText().toString();
						String passwordnew = etpasswordnew.getText().toString();
						String passwordold = etpasswordold.getText().toString();
						String passwordre = etpasswordre.getText().toString();
						if (MyUtils.isEmpty(username, passwordnew, passwordre, passwordold)) {
							MyToast.makeText("������Ϊ��");
							return;
						}
						if(!passwordnew.equals(passwordre)){
							MyToast.makeText("���������벻һ��");
							return;
						}
						Cursor cursor =MyUtils.sdb.rawQuery("select * from user where username = ?", new String[]{username});
						if(cursor.getCount()>0){
							cursor.moveToNext();
							if(cursor.getString(1).equals(passwordold)){
								MyUtils.sdb.execSQL("update user set password = ? where username = ?",new String[]{passwordnew,username});
								MyToast.makeText("�޸ĳɹ�");
							}else{
								MyToast.makeText("���벻��ȷ");
							}
						}else{
							MyToast.makeText("�˺Ų�����");
						}
						cursor.close();
					}
				});
				builder.show();
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SignInActivity.this);
				View view = View.inflate(SignInActivity.this, R.layout.save,
						null);
				final EditText etusername = (EditText) view.findViewById(R.id.et_username);
				final EditText eter = (EditText) view.findViewById(R.id.et_er);
				builder.setView(view);
				builder.setNegativeButton("�޸�����", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String username = etusername.getText().toString();
						String er = eter.getText().toString();
						if (MyUtils.isEmpty(username, er)) {
							MyToast.makeText("������Ϊ��");
							return;
						}
						Cursor cursor =MyUtils.sdb.rawQuery("select * from user where username = ?", new String[]{username});
						if(cursor.getCount()>0){
							cursor.moveToNext();
							if(cursor.getString(2).equals(er)){
								AlertDialog.Builder builder =new AlertDialog.Builder(SignInActivity.this);
								builder.setTitle("�һ�����");
								builder.setMessage("��ǰ�һص����룺"+cursor.getString(1));
								builder.setNegativeButton("ȷ��", null);
								builder.show();
							}else{
								MyToast.makeText("�����������");
							}
						}else{
							MyToast.makeText("�˺Ų�����");
						}
						cursor.close();
					}
				});
				builder.show();
			}
		});
	}

	public void setUser() {
		lists.clear();
		Cursor cursor = MyUtils.sdb.rawQuery("select * from user", null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				lists.add(cursor.getString(0));
			}
			myAdapter.notifyDataSetChanged();
		}
		cursor.close();
	}
}
