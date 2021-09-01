package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Fragment4 extends Fragment {
	private EditText etPasswordnew;
	private EditText etPasswordold;
	private Button btnChange;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment4, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		etPasswordnew = (EditText) getActivity().findViewById(
				R.id.et_passwordmew);
		etPasswordold = (EditText) getActivity().findViewById(
				R.id.et_passwordold);
		btnChange = (Button) getActivity().findViewById(R.id.btn_change);
		getActivity().findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		btnChange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String passwordold = etPasswordold.getText().toString();
				String passwordnew = etPasswordnew.getText().toString();
				if (MyUtils.isEmpty(passwordnew, passwordold)) {
					MyToast.makeText("参数不为空");
					return;
				}
				if (MyUtils.shared.getString("password", "123456").equals(
						passwordold)) {
					MyUtils.editor.putString("password", passwordnew).commit();
					MyToast.makeText("修改成功");
				} else {
					MyToast.makeText("旧密码不正确，请重新输入");
				}
			}
		});
	}

}
