package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

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
		tbMode = (ToggleButton) getActivity().findViewById(R.id.tb_mode);
		rgMode = (RadioGroup) getActivity().findViewById(R.id.rg_mode);

		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		if (tbMode.isChecked()) {
			switch (rgMode.getCheckedRadioButtonId()) {
			case R.id.radio0:
				MyUtils.lamp.setControl(false);
				if (MyUtils.ill > 150) {
					MyUtils.curtain.setControl(true);
				} else {
					MyUtils.curtain.setControl(false);
				}
				break;
			case R.id.radio1:
				MyUtils.lamp.setControl(true);
				MyUtils.curtain.setControl(false);
				if (MyUtils.ill < 50) {
					MyUtils.fan.setControl(true);
				} else {
					MyUtils.fan.setControl(false);
				}
				break;
			case R.id.radio2:
				if (MyUtils.statehuman != 0) {
					MyUtils.alert.setControl(true);
					MyUtils.lamp.setControl(true);
				} else {
					MyUtils.alert.setControl(false);
					MyUtils.lamp.setControl(false);
				}
				break;
			}
		}
		MyUtils.mHandler.postDelayed(this, 3000);
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}
}
