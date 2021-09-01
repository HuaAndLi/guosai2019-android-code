package com.example.smarthome;

import utils.CMD;
import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

public class MainActivity extends Activity implements Runnable {
	private EditText[] ets = new EditText[9];
	private CheckBox cbAlert;
	private CheckBox cbCurtain;
	private CheckBox cbLamp;
	private CheckBox cbFan;
	private CheckBox cbDoor;
	private LinearLayout ll;
	private boolean isDestroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ll = (LinearLayout) findViewById(R.id.ll);
		ets[0] = (EditText) findViewById(R.id.et1);
		ets[1] = (EditText) findViewById(R.id.et2);
		ets[2] = (EditText) findViewById(R.id.et3);
		ets[3] = (EditText) findViewById(R.id.et4);
		ets[4] = (EditText) findViewById(R.id.et5);
		ets[5] = (EditText) findViewById(R.id.et6);
		ets[6] = (EditText) findViewById(R.id.et7);
		ets[7] = (EditText) findViewById(R.id.et8);
		ets[8] = (EditText) findViewById(R.id.et9);
		cbAlert = (CheckBox) findViewById(R.id.cb_alert);
		cbCurtain = (CheckBox) findViewById(R.id.cb_curtain);
		cbLamp = (CheckBox) findViewById(R.id.cb_lamp);
		cbFan = (CheckBox) findViewById(R.id.cb_fan);
		cbDoor = (CheckBox) findViewById(R.id.cb_door);
		setControl(cbAlert, MyUtils.alert);
		setControl(cbCurtain, MyUtils.curtain);
		setControl(cbLamp, MyUtils.lamp);
		setControl(cbFan, MyUtils.fan);
		ll.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(MainActivity.this, OtherActivity.class));
				return true;
			}
		});
		cbCurtain.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				CMD.control(ConstantUtil.Curtain, "4", "1");
				MyUtils.curtain.isCheck = null;
				return true;
			}
		});
		findViewById(R.id.btn_tv).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyUtils.tv.isCheck = null;
						MyUtils.tv.setControl(false);
					}
				});
		findViewById(R.id.btn_air).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyUtils.air.isCheck = null;
						MyUtils.air.setControl(false);
					}
				});
		findViewById(R.id.btn_dvd).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyUtils.dvd.isCheck = null;
						MyUtils.dvd.setControl(false);
					}
				});
		findViewById(R.id.cb_door).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyUtils.door.isCheck = null;
						MyUtils.door.setControl(false);
					}
				});
		SocketClient.getInstance().login(new LoginCallback() {

			@Override
			public void onEvent(final String arg0) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (ConstantUtil.SUCCESS.equals(arg0)) {
							MyToast.makeText("网络连接成功");
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									MainActivity.this);
							builder.setCancelable(false);
							builder.setTitle("提示");
							builder.setMessage("网络连接失败，是否返回登录界面");
							builder.setNegativeButton("确定",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											finish();
											startActivity(new Intent(
													MainActivity.this,
													SignInActivity.class));
										}
									});
							builder.setPositiveButton("取消", null);
							builder.show();
						}
					}
				});
			}
		});
		SocketClient.getInstance().disConnect();
		SocketClient.getInstance().creatConnect();
		MyUtils.mHandler.post(this);
	}

	public void setControl(CheckBox cb, final CMD cmd) {
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				cmd.setControl(isChecked);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SocketClient.getInstance().login(null);
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public void run() {
		for (int i = 0; i < ets.length; i++) {
			ets[i].setText("" + MyUtils.values[i]);
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}
}
