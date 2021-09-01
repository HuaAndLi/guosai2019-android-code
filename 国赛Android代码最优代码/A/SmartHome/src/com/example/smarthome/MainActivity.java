package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
	private TextView[] tvs = new TextView[9];
	private CheckBox cbAlert;
	private CheckBox cbDoor;
	private CheckBox cbFan;
	private CheckBox cbLamp;
	private List<CheckBox> cbModes = new ArrayList<CheckBox>();
	private CheckBox cbLv;
	private CheckBox cbWen;
	private CheckBox cbAn;
	private CheckBox cbZi;
	private Spinner sp2;
	private Spinner sp1;
	private Spinner sp33;
	private Spinner sp22;
	private CheckBox cb1;
	private CheckBox cb2;
	private EditText etNumber1;
	private EditText etNumber2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 绑定控件
		tvs[0] = (TextView) findViewById(R.id.tv1);
		tvs[1] = (TextView) findViewById(R.id.tv2);
		tvs[2] = (TextView) findViewById(R.id.tv3);
		tvs[3] = (TextView) findViewById(R.id.tv4);
		tvs[4] = (TextView) findViewById(R.id.tv5);
		tvs[5] = (TextView) findViewById(R.id.tv6);
		tvs[6] = (TextView) findViewById(R.id.tv7);
		tvs[7] = (TextView) findViewById(R.id.tv8);
		tvs[8] = (TextView) findViewById(R.id.tv9);
		cbAlert = (CheckBox) findViewById(R.id.cb_alert);
		cbDoor = (CheckBox) findViewById(R.id.cb_door);
		cbFan = (CheckBox) findViewById(R.id.cb_fan);
		cbLamp = (CheckBox) findViewById(R.id.cb_lamp);
		cbModes.add(cbLv = (CheckBox) findViewById(R.id.cb_lv));
		cbModes.add(cbWen = (CheckBox) findViewById(R.id.cb_wen));
		cbModes.add(cbAn = (CheckBox) findViewById(R.id.cb_an));
		cbModes.add(cbZi = (CheckBox) findViewById(R.id.cb_zi));
		sp1 = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp33 = (Spinner) findViewById(R.id.spinner33);
		sp22 = (Spinner) findViewById(R.id.spinner22);
		cb1 = (CheckBox) findViewById(R.id.cb_1);
		cb2 = (CheckBox) findViewById(R.id.cb_2);
		// 设置适配器
		sp1.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item, R.id.textView1, new String[] { "温度", "光照" }));
		sp2.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item, R.id.textView1, new String[] { ">", "<=" }));
		sp33.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item, R.id.textView1, new String[] { "风扇打开", "射灯打开" }));
		sp22.setAdapter(new ArrayAdapter<String>(MainActivity.this,
				R.layout.item, R.id.textView1, new String[] { ">", "<=" }));
		etNumber1 = (EditText) findViewById(R.id.et_number);
		etNumber2 = (EditText) findViewById(R.id.et_number2);
		// 设置控件的监听
		cb1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					cbFan.setChecked(false);
					cbLamp.setChecked(false);
				}
			}
		});
		cb2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					cbFan.setChecked(false);
					cbLamp.setChecked(false);
				}
			}
		});
		for (int i = 0; i < cbModes.size(); i++) {
			final int cur = i;
			cbModes.get(i).setOnCheckedChangeListener(
					new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (cur == 3 && !isChecked) {
								cb1.setChecked(false);
								cb2.setChecked(false);
							}
							if (isChecked) {
								for (int j = 0; j < cbModes.size(); j++) {
									if (cbModes.get(cur) != cbModes.get(j)) {
										cbModes.get(j).setChecked(false);
									}
								}
							} else {
								cbLamp.setChecked(false);
								cbFan.setChecked(false);
								cbDoor.setChecked(false);
								cbAlert.setChecked(false);
								MyUtils.tv.setControl(false);
								MyUtils.air.setControl(false);
								MyUtils.dvd.setControl(false);
								MyUtils.curtain.setControl(false);
							}
						}
					});
		}
		findViewById(R.id.btn_conn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ConnActivity.class));
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
		cbDoor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.door.setControl(isChecked);
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
		cbLamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.lamp.setControl(isChecked);
			}
		});
		findViewById(R.id.btn_tv).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.tv.isCheck = true;
				MyUtils.tv.setControl(false);
			}
		});
		findViewById(R.id.btn_air).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.air.isCheck = true;
				MyUtils.air.setControl(false);
			}
		});
		findViewById(R.id.btn_dvd).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.dvd.isCheck = true;
				MyUtils.dvd.setControl(false);
			}
		});
		findViewById(R.id.btn_kai).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.isCheck = false;
				MyUtils.curtain.setControl(true);
			}
		});
		findViewById(R.id.btn_guan).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.isCheck = true;
				MyUtils.curtain.setControl(false);
			}
		});
		findViewById(R.id.btn_ting).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.setControl(null);
			}
		});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText("" + (MyUtils.statehuman != 0 ? "有人" : "无人"));

		if (cbLv.isChecked()) {
			cbLamp.setChecked(false);
			MyUtils.curtain.setControl(true);
			if (MyUtils.pm25 > 75) {
				cbFan.setChecked(true);
			}
		} else if (cbWen.isChecked()) {
			MyUtils.tv.setControl(true);
			MyUtils.air.setControl(true);
			MyUtils.dvd.setControl(true);
			cbLamp.setChecked(true);
			cbFan.setChecked(true);
			cbAlert.setChecked(true);
		} else if (cbAn.isChecked()) {
			if (MyUtils.statehuman != 0) {
				cbLamp.setChecked(true);
				cbAlert.setChecked(true);
			}
		} else if (cbZi.isChecked()) {
			if (cbZi.isChecked()) {
				if (cb1.isChecked()) {
					String buff = etNumber1.getText().toString();
					if (!TextUtils.isEmpty(buff)) {
						double value = MyUtils.values[sp1
								.getSelectedItemPosition()];
						int number = Integer.parseInt(buff);
						if (sp2.getSelectedItemPosition() == 0) {
							if (value > number) {
								cbFan.setChecked(true);
							} else {
								cbFan.setChecked(false);
							}
						} else {
							if (value <= number) {
								cbFan.setChecked(true);
							} else {
								cbFan.setChecked(false);
							}
						}
					} else {
						cb1.setChecked(false);
						MyToast.makeText("参数不为空");
					}
				}
				if (cb2.isChecked()) {
					String buff = etNumber2.getText().toString();
					if (!TextUtils.isEmpty(buff)) {
						double value = MyUtils.ill;
						int number = Integer.parseInt(buff);
						if (sp22.getSelectedItemPosition() == 0) {
							if (value > number) {
								if (sp33.getSelectedItemPosition() == 0) {
									cbFan.setChecked(true);
								} else {
									cbLamp.setChecked(true);
								}
							} else {
								cbFan.setChecked(false);
								cbLamp.setChecked(false);
							}
						} else {
							if (value <= number) {
								if (sp33.getSelectedItemPosition() == 0) {
									cbFan.setChecked(true);
								} else {
									cbLamp.setChecked(true);
								}
							} else {
								cbFan.setChecked(false);
								cbLamp.setChecked(false);
							}
						}
					} else {
						cb2.setChecked(false);
						MyToast.makeText("参数不为空");
					}
				}
			}
		}
		MyUtils.mHandler.postDelayed(this, 2000);
	}
}
