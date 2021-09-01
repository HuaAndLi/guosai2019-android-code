package com.example.smarthome;

import utils.CMD;
import utils.MyUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.bizideal.smarthome.socket.ConstantUtil;

public class ControlActivity extends Activity {

	public static CheckBox cbLamp;
	public static CheckBox cbDoor;
	public static CheckBox cbFan;
	public static CheckBox cbAlert;
	private boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		cbLamp = (CheckBox) findViewById(R.id.cb_lamp);
		cbDoor = (CheckBox) findViewById(R.id.cb_door);
		cbFan = (CheckBox) findViewById(R.id.cb_fan);
		cbAlert = (CheckBox) findViewById(R.id.cb_alert);
		cbLamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.lamp.setControl(isChecked);
			}
		});
		cbDoor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (!flag) {
					flag = true;
					MyUtils.door.setControl(true);
					MyUtils.door.isCheck = null;
					cbDoor.setBackgroundResource(R.drawable.swon);
					MyUtils.mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							cbDoor.setBackgroundResource(R.drawable.swoff);
							flag = false;
						}
					}, 3000);
				}
			}
		});
		cbFan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.fan.setControl(isChecked);
			}
		});
		cbAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.alert.setControl(isChecked);
			}
		});
		findViewById(R.id.btn_tvoff).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.tv.setControl(true);
				MyUtils.tv.isCheck = null;
			}
		});
		findViewById(R.id.btn_tvon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.tv.setControl(true);
				MyUtils.tv.isCheck = null;
			}
		});

		findViewById(R.id.btn_dvdoff).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.dvd.setControl(true);
				MyUtils.dvd.isCheck = null;
			}
		});
		findViewById(R.id.btn_dvdon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.dvd.setControl(true);
				MyUtils.dvd.isCheck = null;
			}
		});

		findViewById(R.id.btn_airoff).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.air.setControl(true);
				MyUtils.air.isCheck = null;
			}
		});
		findViewById(R.id.btn_airon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.air.setControl(true);
				MyUtils.air.isCheck = null;
			}
		});

		findViewById(R.id.btn_kai).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.curtain.setControl(true);
				MyUtils.curtain.isCheck = null;
			}
		});
		findViewById(R.id.btn_guan).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.curtain.setControl(false);
				MyUtils.curtain.isCheck = null;
			}
		});
		findViewById(R.id.btn_ting).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CMD.control(ConstantUtil.Curtain, "4", "3");
				MyUtils.curtain.isCheck = null;
			}
		});
		findViewById(R.id.imageView1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public static void setControl(final CheckBox cb, final boolean ischeck) {
		try {
			if (cb != null) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						cb.setBackgroundResource(ischeck ? R.drawable.swon
								: R.drawable.swoff);
					}
				});
			}
		} catch (Exception e) {
		}
	}
}
