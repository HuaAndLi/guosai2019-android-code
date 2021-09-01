package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment1 extends Fragment implements Runnable {
	private TextView[] tvs = new TextView[9];
	private ImageView ivTemp;
	private ImageView ivHum;
	private TextView tvTemp;
	private TextView tvHum;
	private boolean isDestroy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		tvs[8] = (TextView) getActivity().findViewById(R.id.tv9);
		ivTemp = (ImageView) getActivity().findViewById(R.id.iv_temp);
		ivHum = (ImageView) getActivity().findViewById(R.id.iv_hum);
		tvTemp = (TextView) getActivity().findViewById(R.id.tv_temp);
		tvHum = (TextView) getActivity().findViewById(R.id.tv_hum);
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvTemp.setText(MyUtils.temp + "℃");
		tvHum.setText(MyUtils.humidity + "%");
		ivHum.setRotation((float) (MyUtils.humidity / 120f * 180));
		ivTemp.setRotation((float) (MyUtils.temp / 120f * 180));
		tvs[8].setText(MyUtils.statehuman != 0 ? "有人" : "无人");
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}
}
