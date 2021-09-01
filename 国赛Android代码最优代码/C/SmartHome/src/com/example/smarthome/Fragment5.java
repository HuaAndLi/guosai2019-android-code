package com.example.smarthome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class Fragment5 extends Fragment {
	public static ChartView chartView;
	public static RadioGroup rgChart;
	public static ToggleButton tbChart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment5, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 绑定控件，添加数据的代码见Fragment1里Run（）
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		tbChart = (ToggleButton) getActivity().findViewById(R.id.tb_chart);
		rgChart = (RadioGroup) getActivity().findViewById(R.id.rg_chart);
	}
}
