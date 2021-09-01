package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class CheckManger {
	private List<CheckBox> cbs = new ArrayList<CheckBox>();
	private int index = -1;

	public void add(CheckBox cb) {
		cbs.add(cb);
	}

	public void init() {
		for (int i = 0; i < cbs.size(); i++) {
			final int cur = i;
			cbs.get(i).setOnCheckedChangeListener(
					new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								index = cur;
								for (int j = 0; j < cbs.size(); j++) {
									if (j != cur) {
										cbs.get(j).setEnabled(false);
									}
								}
							} else {
								for (int j = 0; j < cbs.size(); j++) {
									cbs.get(j).setEnabled(true);
									index = -1;
								}
							}
						}
					});
		}
	}

	public void setCheck(int index) {
		if (index != -1) {
			cbs.get(index).setChecked(true);
		}
	}

	public int getIndex() {
		return index;
	}
}
