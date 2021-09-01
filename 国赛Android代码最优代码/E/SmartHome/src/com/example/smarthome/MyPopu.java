package com.example.smarthome;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;

public class MyPopu {
	private ListPopupWindow popupWindow;
	private EditText et;
	private String[] strs;

	public MyPopu(EditText et, String[] strs) {
		super();
		this.et = et;
		this.strs = strs;
	}

	public void init() {
		et.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		et.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					show();
				}
			}
		});
	}

	public void show() {
		popupWindow = new ListPopupWindow(et.getContext());
		popupWindow.setAdapter(new ArrayAdapter<String>(et.getContext(),
				android.R.layout.simple_list_item_1, strs));
		popupWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				et.setText(strs[arg2]);
				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
		popupWindow.setAnchorView(et);
		popupWindow.show();
	}
}
