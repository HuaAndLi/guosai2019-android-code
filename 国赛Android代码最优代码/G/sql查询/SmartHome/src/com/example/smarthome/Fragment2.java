package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.R.anim;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class Fragment2 extends Fragment implements Runnable {
	private CheckBox cbAn;
	private CheckBox cbLi;
	private CheckBox cbZi;
	private List<CheckBox> cbModes = new ArrayList<CheckBox>();
	private Switch swZi;
	private List<CheckBox> cbs = new ArrayList<CheckBox>();
	private boolean idDestroy;
	private Spinner sp1;
	private EditText etNum;

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
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_alert));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_curtain));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_lamp));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_fan));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_tv));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_air));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_dvd));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_door));
		sp1 = (Spinner) getActivity().findViewById(R.id.spinner1);
		cbModes.add(cbAn = (CheckBox) getActivity().findViewById(R.id.cb_an));
		cbModes.add(cbLi = (CheckBox) getActivity().findViewById(R.id.cb_li));
		cbModes.add(cbZi = (CheckBox) getActivity().findViewById(R.id.cb_zi));
		etNum = (EditText) getActivity().findViewById(R.id.et_mun);
		swZi = (Switch) getActivity().findViewById(R.id.sw_zi);
		sp1.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] { ">", "<" }));
		cbZi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if (!swZi.isChecked()) {
						swZi.performClick();
					}
				}
			}
		});
		swZi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					if (!cbZi.isChecked()) {
						MyToast.makeText("请勾选自定义模式");
					}
				} else {
					for (int i = 0; i < cbs.size(); i++) {
						cbs.get(i).setChecked(false);
						MyUtils.controls.get(i).setControl(false);
					}
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
							if (isChecked) {
								for (int j = 0; j < cbModes.size(); j++) {
									if (j != cur) {
										cbModes.get(j).setChecked(false);
									}
								}
							} else {
								for (int j = 0; j < MyUtils.controls.size(); j++) {
									MyUtils.controls.get(j).setControl(false);
								}
							}
						}
					});
		}

		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		if (cbAn.isChecked()) {
			if (MyUtils.statehuman != 0) {
				MyUtils.alert.setControl(true);
			}
		}
		if (cbLi.isChecked()) {
			if (MyUtils.statehuman != 0 || MyUtils.gas > 800) {
				MyUtils.alert.setControl(true);
			}
		}
		if (cbZi.isChecked() && swZi.isChecked()) {
			String buff = etNum.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				if (sp1.getSelectedItemPosition() == 0) {
					if (MyUtils.temp > Integer.parseInt(buff)) {
						for (int i = 0; i < MyUtils.controls.size(); i++) {
							MyUtils.controls.get(i).setControl(
									cbs.get(i).isChecked());
						}
					}
				} else {
					if (MyUtils.temp < Integer.parseInt(buff)) {
						for (int i = 0; i < MyUtils.controls.size(); i++) {
							MyUtils.controls.get(i).setControl(
									cbs.get(i).isChecked());
						}
					}
				}
			} else {
				MyToast.makeText("参数不为空");
			}
		} else {
			swZi.setChecked(false);
		}
		if (!idDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		idDestroy = true;
		super.onDestroy();
	}
}
