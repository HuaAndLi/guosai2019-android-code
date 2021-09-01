package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import com.bizideal.smarthome.socket.ConstantUtil;

import utils.CMD;
import utils.MyUitls;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
	private TextView[] tvs = new TextView[9];
	private CheckBox cbAlert;
	private CheckBox cbCurtain;
	private CheckBox cbLamp;
	private CheckBox cbFan;
	private CheckBox cbDoor;
	private boolean isDestroy;
	private CheckBox cbFang;
	private CheckBox cbBai;
	private CheckBox cbLi;
	private List<CheckBox> cbUsers = new ArrayList<CheckBox>();
	private ChartView chartView;
	private EditText etNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvs[0] = (TextView) findViewById(R.id.tv1);
		tvs[1] = (TextView) findViewById(R.id.tv2);
		tvs[2] = (TextView) findViewById(R.id.tv3);
		tvs[3] = (TextView) findViewById(R.id.tv4);
		tvs[4] = (TextView) findViewById(R.id.tv5);
		tvs[5] = (TextView) findViewById(R.id.tv6);
		tvs[6] = (TextView) findViewById(R.id.tv7);
		tvs[7] = (TextView) findViewById(R.id.tv8);
		tvs[8] = (TextView) findViewById(R.id.tv9);
		chartView  =(ChartView)findViewById(R.id.chartView1);
		cbAlert = (CheckBox) findViewById(R.id.cb_alert);
		cbCurtain = (CheckBox) findViewById(R.id.cb_curtain);
		cbLamp = (CheckBox) findViewById(R.id.cb_lamp);
		cbFan = (CheckBox) findViewById(R.id.cb_fan);
		cbDoor = (CheckBox) findViewById(R.id.cb_door);
		etNumber =(EditText)findViewById(R.id.et_number);
		cbUsers.add(cbFang = (CheckBox) findViewById(R.id.cb_fang));
		cbUsers.add(cbBai = (CheckBox) findViewById(R.id.cb_bai));
		cbUsers.add(cbLi = (CheckBox) findViewById(R.id.cb_li));
		
		setControl(cbAlert, MyUitls.alert);
		setControl(cbCurtain, MyUitls.curtain);
		setControl(cbLamp, MyUitls.lamp);
		setControl(cbFan, MyUitls.fan);
		setControl(cbDoor, MyUitls.door);
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
		findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					CMD.control(ConstantUtil.INFRARED_1_SERVE, etNumber.getText().toString(), "1");
				} catch (Exception e) {
				}
			}
		});
		MyUitls.mHandler.post(this);
	}

	public void setControl(CheckBox cb, final CMD cmd) {
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cmd.setControl(isChecked);
			}
		});
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUitls.values[i]);
		}
		chartView.setValues(MyUitls.temp, MyUitls.smoke, MyUitls.humidity);
		if (cbFang.isChecked()) {
			if (MyUitls.statehuman != 0) {
				cbAlert.setChecked(true);
				cbLamp.setChecked(true);
			}
		}
		if (cbBai.isChecked()) {
			if (MyUitls.ill > 1200) {
				cbCurtain.setChecked(true);
			} else if (MyUitls.ill < 300) {
				cbCurtain.setChecked(false);
				cbLamp.setChecked(true);
			}
		}
		if (cbLi.isChecked()) {
			cbLamp.setChecked(false);
			cbCurtain.setChecked(true);
			if (MyUitls.pm25 > 75) {
				cbFan.setChecked(true);
			}
		}
		if (!isDestroy) {
			MyUitls.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}
}
