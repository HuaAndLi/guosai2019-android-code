package com.example.smarthome;

import utils.CMD;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class Fragment2 extends Fragment {
	private ToggleButton tbAlert;
	private ToggleButton tbCurtain;
	private ToggleButton tbLamp;
	private ToggleButton tbFan;
	private ToggleButton tbTV;
	private ToggleButton tbAIR;
	private ToggleButton tbDVD;
	private ToggleButton tbDoor;

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
		tbAlert = (ToggleButton) getActivity().findViewById(R.id.tb_alert);
		tbCurtain = (ToggleButton) getActivity().findViewById(R.id.tb_curtain);
		tbLamp = (ToggleButton) getActivity().findViewById(R.id.tb_lamp);
		tbFan = (ToggleButton) getActivity().findViewById(R.id.tb_fan);
		tbTV = (ToggleButton) getActivity().findViewById(R.id.tb_tv);
		tbAIR = (ToggleButton) getActivity().findViewById(R.id.tb_air);
		tbDVD = (ToggleButton) getActivity().findViewById(R.id.tb_dvd);
		tbDoor = (ToggleButton) getActivity().findViewById(R.id.tb_door);
		setControl(tbAlert, MyUtils.alert);
		setControl(tbCurtain, MyUtils.curtain);
		setControl(tbLamp, MyUtils.lamp);
		setControl(tbFan, MyUtils.fan);
		setControl(tbTV, MyUtils.tv);
		setControl(tbAIR, MyUtils.air);
		setControl(tbDVD, MyUtils.dvd);
		setControl(tbDoor, MyUtils.door);
	}

	public void setControl(ToggleButton cb, final CMD cmd) {
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				cmd.setControl(isChecked);
			}
		});
	}
}
