package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class Fragment3 extends Fragment implements Runnable {
	private boolean isDestroy;
	private CheckBox cbLink;
	private Spinner sp1;
	private Spinner sp2;
	private EditText etNumber;
	private boolean isCurtain, isAlert, isLamp, isFan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment3, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		cbLink = (CheckBox) getActivity().findViewById(R.id.cb_link);
		sp1 = (Spinner) getActivity().findViewById(R.id.spinner1);
		sp2 = (Spinner) getActivity().findViewById(R.id.spinner2);
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		sp1.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] { "温度", "湿度",
						"光照", "气压" }));
		sp2.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1,
				new String[] { "大于", "小于" }));
		getActivity().findViewById(R.id.btn_setcontrol).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final PopupWindow popupWindow = new PopupWindow(
								getActivity());
						popupWindow.setTouchable(true);
						popupWindow.setOutsideTouchable(true);
						popupWindow.setBackgroundDrawable(null);
						popupWindow
								.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
						popupWindow
								.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

						View view = View.inflate(getActivity(),
								R.layout.setlayout, null);
						final CheckBox cbalert = (CheckBox) view
								.findViewById(R.id.cb_alert);
						final CheckBox cbcurtain = (CheckBox) view
								.findViewById(R.id.cb_curtain);
						final CheckBox cblamp = (CheckBox) view
								.findViewById(R.id.cb_lamp);
						final CheckBox cbfan = (CheckBox) view
								.findViewById(R.id.cb_fan);
						view.findViewById(R.id.btn_set).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										isCurtain = cbcurtain.isChecked();
										isAlert = cbalert.isChecked();
										isLamp = cblamp.isChecked();
										isFan = cbfan.isChecked();
										MyToast.makeText("设置成功");
										popupWindow.dismiss();
									}
								});
						popupWindow.setContentView(view);
						popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
					}
				});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}

	@Override
	public void run() {
		if (cbLink.isChecked()) {
			String buff = etNumber.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				double value = 0;
				int number = Integer.parseInt(buff);
				switch (sp1.getSelectedItemPosition()) {
				case 0:
					value = MyUtils.temp;
					break;
				case 1:
					value = MyUtils.humidity;
					break;
				case 2:
					value = MyUtils.ill;
					break;
				case 3:
					value = MyUtils.airp;
					break;
				}
				MyUtils.alert.setControl(isAlert
						&& sp2.getSelectedItemPosition() == 0 ? value > number
						: value < number);
				MyUtils.curtain.setControl(isCurtain
						&& sp2.getSelectedItemPosition() == 0 ? value > number
						: value < number);
				MyUtils.lamp.setControl(isLamp
						&& sp2.getSelectedItemPosition() == 0 ? value > number
						: value < number);
				MyUtils.fan.setControl(isFan
						&& sp2.getSelectedItemPosition() == 0 ? value > number
						: value < number);
			} else {
				cbLink.setChecked(false);
				MyToast.makeText("参数不为空");
			}
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}
}
