package com.example.smarthome;

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
	private ChartView chartView;
	private boolean isDestroy;
	private CheckBox cbShang;
	private CheckBox cbXia;
	private CheckBox cbShui;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment2, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		cbShang = (CheckBox) getActivity().findViewById(R.id.cb_shang);
		cbXia = (CheckBox) getActivity().findViewById(R.id.cb_xia);
		cbShui = (CheckBox) getActivity().findViewById(R.id.cb_shui);
		cbShang.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cbXia.setChecked(false);
					cbShui.setChecked(false);
				}
			}
		});
		cbXia.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cbShang.setChecked(false);
				}
			}
		});
		cbShui.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					cbShang.setChecked(false);
				}
			}
		});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		chartView.addLists(MyUtils.gas);
		if (cbShang.isChecked() || cbXia.isChecked() || cbShui.isChecked()) {
			chartView.setVisibility(View.INVISIBLE);
			if (cbShang.isChecked()) {
				if (MyUtils.statehuman != 0) {
					if (MyUtils.lamp.isCheck != null) {
						MyUtils.lamp.setControl(!MyUtils.lamp.isCheck);
					} else {
						MyUtils.lamp.setControl(true);
					}
					MyUtils.curtain.setControl(true);
				} else {
					MyUtils.curtain.setControl(false);
				}
			}
			if (cbXia.isChecked()) {
				MyUtils.lamp.setControl(true);
				MyUtils.air.setControl(true);
				if (MyUtils.smoke > 600) {
					MyUtils.fan.setControl(true);
				} else {
					MyUtils.fan.setControl(false);
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
			boolean ischeck = false;
			for (int i = 0; i < ChartView.lists.size(); i++) {
				if (ChartView.lists.get(i) > 800) {
					ischeck = true;
				}
			}
			if (MainActivity.pager.getCurrentItem() == 1) {
				MyUtils.alert.setControl(ischeck);
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
