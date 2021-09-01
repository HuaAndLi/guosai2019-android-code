package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class Fragment2 extends Fragment implements Runnable {
	private CheckBox cbAn;
	private CheckBox cbLi;
	private CheckBox cbZi;
	private Spinner sp;
	private EditText etNumber;
	private List<CheckBox> cbs = new ArrayList<CheckBox>();

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
		cbAn = (CheckBox) getActivity().findViewById(R.id.cb_an);
		cbLi = (CheckBox) getActivity().findViewById(R.id.cb_li);
		cbZi = (CheckBox) getActivity().findViewById(R.id.cb_zi);
		sp = (Spinner) getActivity().findViewById(R.id.spf2);
		sp.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] { ">", "<" }));
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_alert));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_curtain));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_lamp));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_fan));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_tv));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_air));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_dvd));
		cbs.add((CheckBox) getActivity().findViewById(R.id.cb_door));
		cbZi.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				for (int i = 0; i < cbs.size(); i++) {
					cbs.get(i).setChecked(false);
					MyUtils.controls.get(i).setControl(false);
				}
			}
		});
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
		if (cbZi.isChecked()) {
			String buff = etNumber.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				if (sp.getSelectedItemPosition() == 0) {
					if (MyUtils.temp > Integer.parseInt(buff)) {
						for (int i = 0; i < cbs.size(); i++) {
							MyUtils.controls.get(i).setControl(
									cbs.get(i).isChecked());
						}
					}
				} else {
					if (MyUtils.temp < Integer.parseInt(buff)) {
						for (int i = 0; i < cbs.size(); i++) {
							MyUtils.controls.get(i).setControl(
									cbs.get(i).isChecked());
						}
					}

				}
			} else {
				MyToast.makeText("参数不为空");
			}
		}
		if (!MainActivity.isDestroy) {
			MyUtils.mHandler.postDelayed(this, 3000);
		}
	}
}
