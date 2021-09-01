package com.example.smarthome;

import java.util.Timer;

import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Fragment2 extends Fragment implements Runnable {
	private CheckBox cbShang;
	private CheckBox cbXia;
	private CheckBox cbShui;
	private ChartView chartView;
	private boolean isDestroy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment2, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		cbShang = (CheckBox) getActivity().findViewById(R.id.cb_shang);
		cbXia = (CheckBox) getActivity().findViewById(R.id.cb_xia);
		cbShui = (CheckBox) getActivity().findViewById(R.id.cb_shui);
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		cbXia.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbShang.setChecked(false);
				} else {
					MyUtils.alert.setControl(false);
					MyUtils.lamp.setControl(false);
					MyUtils.curtain.setControl(false);
					MyUtils.fan.setControl(false);
					MyUtils.air.setControl(false);
				}
			}
		});
		cbShui.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbShang.setChecked(false);
				} else {
					MyUtils.alert.setControl(false);
					MyUtils.lamp.setControl(false);
					MyUtils.curtain.setControl(false);
					MyUtils.fan.setControl(false);
					MyUtils.air.setControl(false);
				}
			}
		});
		cbShang.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					cbShui.setChecked(false);
					cbXia.setChecked(false);
				} else {
					MyUtils.alert.setControl(false);
					MyUtils.lamp.setControl(false);
					MyUtils.curtain.setControl(false);
					MyUtils.fan.setControl(false);
					MyUtils.air.setControl(false);
				}
			}
		});

		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		chartView.addLists((int)(Math.random()*1000));
		if (cbShang.isChecked() || cbShui.isChecked() || cbXia.isChecked()) {
			chartView.setVisibility(View.INVISIBLE);
			if (cbShang.isChecked()) {
				if (MyUtils.statehuman != 0) {
					MyUtils.curtain.setControl(true);
					if (MyUtils.lamp.isCheck != null) {
						MyUtils.lamp.setControl(!MyUtils.lamp.isCheck);
					} else {
						MyUtils.lamp.setControl(true);
					}
				} else {
					MyUtils.curtain.setControl(false);
				}
			}
			if (cbXia.isChecked()) {
				MyUtils.lamp.setControl(true);
				MyUtils.air.setControl(true);
				if (MyUtils.smoke > 600) {
					MyUtils.fan.setControl(true);
				}
			}
			if (cbShui.isChecked()) {
				MyUtils.curtain.setControl(false);
				if (MyUtils.statehuman != 0) {
					MyUtils.alert.setControl(true);
					MyUtils.lamp.setControl(true);
				}
			}
		} else {
			chartView.setVisibility(View.VISIBLE);
			if (MainActivity.pager.getCurrentItem() == 1) {
				boolean ischeck = false;
				for (int i = 0; i < ChartView.lists.size(); i++) {
					if (ChartView.lists.get(i) > 800) {
						ischeck = true;
						break;
					}
				}
				MyUtils.alert.setControl(ischeck);
			}
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}
}
