package com.example.smarthome;

import com.bizideal.smarthome.socket.ConstantUtil;

import utils.CMD;
import utils.MyUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 控制界面
 * 
 * @author Administrator
 * 
 */
public class ControlActivity extends Activity {

	private TextView tvName;
	private ToggleButton tbAlert;
	private ToggleButton tbFan;
	private ToggleButton tbLamp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		// 绑定控件
		tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText("房号：");
		tbAlert = (ToggleButton) findViewById(R.id.tb_alert);
		tbFan = (ToggleButton) findViewById(R.id.tb_fan);
		tbLamp = (ToggleButton) findViewById(R.id.tb_lamp);
		// 设置监听
		tbAlert.setChecked(MyUtils.alert.isCheck);
		tbFan.setChecked(MyUtils.fan.isCheck);
		tbLamp.setChecked(MyUtils.lamp.isCheck);
		setControl(tbAlert, MyUtils.alert);
		setControl(tbFan, MyUtils.fan);
		setControl(tbLamp, MyUtils.lamp);
		setCheck(findViewById(R.id.btn_tv), MyUtils.tv, !MyUtils.tv.isCheck);
		setCheck(findViewById(R.id.btn_air), MyUtils.air, !MyUtils.air.isCheck);
		setCheck(findViewById(R.id.btn_dvd), MyUtils.dvd, !MyUtils.dvd.isCheck);
		setCheck(findViewById(R.id.btn_door), MyUtils.door,
				!MyUtils.door.isCheck);
		setCheck(findViewById(R.id.btn_kai), MyUtils.curtain, true);
		setCheck(findViewById(R.id.btn_guan), MyUtils.curtain, false);
		findViewById(R.id.btn_ting).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CMD.control(ConstantUtil.Curtain, "4", "3");
			}
		});
	}

	// 设置ToggleButton控制监听的方法
	public void setControl(ToggleButton tb, final CMD cmd) {
		tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cmd.setControl(isChecked);
			}
		});
	}

	// 设置setOnClickListener控制监听的方法
	public void setCheck(View btn, final CMD cmd, final boolean isCheck) {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cmd.isCheck = !isCheck;
				cmd.setControl(isCheck);
			}
		});
	}
}
