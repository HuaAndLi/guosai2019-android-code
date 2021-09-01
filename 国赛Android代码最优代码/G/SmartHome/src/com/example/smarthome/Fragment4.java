package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment4 extends Fragment {
	private EditText etPasswordnew;
	private EditText etPasswordold;

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
				R.id.et_passwordnew);
		etPasswordold = (EditText) getActivity().findViewById(
				R.id.et_passwordold);
		getActivity().findViewById(R.id.btn_change).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						String passwordnew = etPasswordnew.getText().toString();
						String passwordold = etPasswordold.getText().toString();
						if (MyUtils.isEmpty(passwordnew, passwordold)) {
							MyToast.makeText("参数不为空");
							return;
						}
						if (MyUtils.shared.getString("password", "123456")
								.equals(passwordold)) {
							MyUtils.editor.putString("password", passwordnew)
									.commit();
							MyUtils.editor.putBoolean("in", false).commit();
							MyToast.makeText("密码修改成功");
						} else {
							MyToast.makeText("旧密码错误");
						}
					}
				});
		getActivity().findViewById(R.id.btn_exit).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						getActivity().finish();
					}
				});
	}
}
