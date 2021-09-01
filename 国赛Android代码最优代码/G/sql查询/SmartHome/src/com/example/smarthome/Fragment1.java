package com.example.smarthome;

import utils.CMD;
import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class Fragment1 extends Fragment implements Runnable {
	private TabHost tabhost;
	private RadioGroup rgFragment;
	private TextView[] tvs = new TextView[9];
	private boolean isDestroy;
	private Switch swAlert;
	private Switch swFan;
	private Switch swLamp;
	private Switch swDoor;
	private RadioGroup rgCurtain;
	private EditText etNumber;
	private Button btnSend;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tabhost = (TabHost) getActivity().findViewById(android.R.id.tabhost);
		rgFragment = (RadioGroup) getActivity().findViewById(R.id.rgf1);
		rgCurtain = (RadioGroup) getActivity().findViewById(R.id.rg_curtain);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		tvs[8] = (TextView) getActivity().findViewById(R.id.tv9);
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		btnSend = (Button) getActivity().findViewById(R.id.btn_send);
		swAlert = (Switch) getActivity().findViewById(R.id.sw_alert);
		swDoor = (Switch) getActivity().findViewById(R.id.sw_door);
		swFan = (Switch) getActivity().findViewById(R.id.sw_fan);
		swLamp = (Switch) getActivity().findViewById(R.id.sw_lamp);
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("").setContent(R.id.tab1)
				.setIndicator(""));
		tabhost.addTab(tabhost.newTabSpec("").setContent(R.id.tab2)
				.setIndicator(""));
		tabhost.setCurrentTab(0);
		setControl(swAlert, MyUtils.alert);
		setControl(swDoor, MyUtils.door);
		setControl(swFan, MyUtils.fan);
		setControl(swLamp, MyUtils.lamp);

		rgCurtain.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio01:
					MyUtils.curtian.isCheck = null;
					MyUtils.curtian.setControl(true);
					break;
				case R.id.radio12:
					MyUtils.curtian.isCheck = null;
					MyUtils.curtian.setControl(false);
					break;
				case R.id.radio23:
					MyUtils.curtian.isCheck = null;
					CMD.control(ConstantUtil.Curtain, "4", "3");
					break;

				default:
					break;
				}
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String buff = etNumber.getText().toString();
				if (!TextUtils.isEmpty(buff)) {
					CMD.control(ConstantUtil.INFRARED_1_SERVE, buff, "1");
				} else {
					MyToast.makeText("参数不为空");
				}
			}
		});
		rgFragment.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					tabhost.setCurrentTab(0);
				} else {
					tabhost.setCurrentTab(1);
				}
			}
		});

		MyUtils.mHandler.post(this);
	}

	public void setControl(Switch sw, final CMD cmd) {
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText(MyUtils.statehuman != 0 ? "有人" : "无人");
		MyUtils.sdb.execSQL(
				"delete from datas where time > ? or time < ?",
				new String[] { System.currentTimeMillis() + "",
						System.currentTimeMillis() - 60 * 60 * 1000 + "" });
		MyUtils.sdb.execSQL("insert into datas values(?,?,?)", new String[] {
				System.currentTimeMillis() + "", MyUtils.ill + "",
				MyUtils.temp + "" });
//		MyToast.makeText(""+MyUtils.ill);
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 5000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
