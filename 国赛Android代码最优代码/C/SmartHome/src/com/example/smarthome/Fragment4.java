package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ToggleButton;

/**
 * 模式界面
 * 
 * @author Administrator
 * 
 */
public class Fragment4 extends Fragment implements Runnable {
	private RadioGroup rgMode;
	private ToggleButton tbMode;
	private boolean isDestroy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment4, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 绑定控件
		tbMode = (ToggleButton) getActivity().findViewById(R.id.tb_mode);
		rgMode = (RadioGroup) getActivity().findViewById(R.id.rg_mode);
		// 设置监听
		rgMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				for (int i = 0; i < MyUtils.controls.size(); i++) {
					MyUtils.controls.get(i).setControl(false);
				}
			}
		});
		tbMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
					for (int i = 0; i < MyUtils.controls.size(); i++) {
						MyUtils.controls.get(i).setControl(false);
					}
				}
			}
		});
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		// 模式控制
		if (tbMode.isChecked()) {
			switch (rgMode.getCheckedRadioButtonId()) {
			case R.id.radio0:
				MyUtils.lamp.setControl(false);
				MyUtils.curtain.setControl(true);
				if (MyUtils.smoke > 400) {
					MyUtils.fan.setControl(true);
				}
				break;
			case R.id.radio1:
				MyUtils.curtain.setControl(false);
				if (MyUtils.ill < 200) {
					MyUtils.lamp1.setControl(true);
					MyUtils.lamp2.setControl(false);
					MyUtils.lamp.isCheck = null;
				} else if (MyUtils.ill > 500) {
					MyUtils.lamp.setControl(false);
					MyUtils.lamp1.isCheck = false;
					MyUtils.lamp2.isCheck = false;
				}
				break;
			case R.id.radio2:
				MyUtils.air.setControl(true);
				if (MyUtils.lamp.isCheck != null) {
					MyUtils.lamp.setControl(!MyUtils.lamp.isCheck);
				} else {
					MyUtils.lamp.setControl(true);
				}
				break;
			case R.id.radio3:
				if (MyUtils.statehuman != 0) {
					MyUtils.alert.setControl(true);
					MyUtils.lamp.setControl(true);
				}
				break;
			}
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
