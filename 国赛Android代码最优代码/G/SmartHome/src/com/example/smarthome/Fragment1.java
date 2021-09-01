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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class Fragment1 extends Fragment implements Runnable {

	private Switch swAlert;
	private Switch swDoor;
	private Switch swFan;
	private Switch swLamp;
	private RadioGroup rgCurtain;
	private EditText etNumber;
	private RadioGroup rgF1;
	private LinearLayout llControl;
	private LinearLayout llValue;
	private TextView[] tvs = new TextView[9];

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
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		tvs[8] = (TextView) getActivity().findViewById(R.id.tv9);
		swAlert = (Switch) getActivity().findViewById(R.id.sw_alert);
		swDoor = (Switch) getActivity().findViewById(R.id.sw_door);
		swFan = (Switch) getActivity().findViewById(R.id.sw_fan);
		swLamp = (Switch) getActivity().findViewById(R.id.sw_lamp);
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		rgCurtain = (RadioGroup) getActivity().findViewById(R.id.rg_curtain);
		rgF1 = (RadioGroup) getActivity().findViewById(R.id.rg_f1);
		llControl = (LinearLayout) getActivity().findViewById(R.id.ll_control);
		llValue = (LinearLayout) getActivity().findViewById(R.id.ll_value);
		rgF1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					llControl.setVisibility(View.GONE);
					llValue.setVisibility(View.VISIBLE);
				} else {
					llControl.setVisibility(View.VISIBLE);
					llValue.setVisibility(View.GONE);
				}
			}
		});
		getActivity().findViewById(R.id.btn_send).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String buff = etNumber.getText().toString();
						if (!TextUtils.isEmpty(buff)) {
							CMD.control(ConstantUtil.INFRARED_1_SERVE, buff,
									"1");
						} else {
							MyToast.makeText("参数不为空");
						}
					}
				});
		rgCurtain
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == R.id.radio0) {
							MyUtils.curtain.setControl(true);
						} else if (checkedId == R.id.radio1) {
							MyUtils.curtain.setControl(false);
						} else {
							CMD.control(ConstantUtil.Curtain, "4", "3");
						}
					}
				});
		swAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.alert.setControl(isChecked);
			}
		});
		swDoor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.door.setControl(isChecked);
			}
		});
		swFan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.fan.setControl(isChecked);
			}
		});
		swLamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.lamp.setControl(isChecked);
			}
		});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText(MyUtils.statehuman != 0 ? "有人" : "无人");
		if (!MainActivity.isDestroy) {
			MyUtils.mHandler.postDelayed(this, 5000);
		}
	}

}
