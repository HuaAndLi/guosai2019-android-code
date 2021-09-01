package com.example.smarthome;

import com.bizideal.smarthome.socket.ConstantUtil;

import utils.CMD;
import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 基本界面
 * 
 * @author Administrator
 * 
 */
public class Fragment2 extends Fragment implements Runnable {
	private TextView[] tvs = new TextView[9];
	private ToggleButton tbCurtain;
	private ToggleButton tbAlert;
	private ToggleButton tbLamp1;
	private ToggleButton tbLamp2, tbLamp;
	private ToggleButton tbFan;
	private ToggleButton tbDoor;
	private EditText etNubmer;
	private Button btnSend;
	private boolean isDestroy;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment2, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 绑定控件
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		tvs[8] = (TextView) getActivity().findViewById(R.id.tv9);
		tbCurtain = (ToggleButton) getActivity().findViewById(R.id.tb_curtain);
		tbAlert = (ToggleButton) getActivity().findViewById(R.id.tb_alert);
		tbLamp1 = (ToggleButton) getActivity().findViewById(R.id.tb_lamp1);
		tbLamp2 = (ToggleButton) getActivity().findViewById(R.id.tb_lamp2);
		tbFan = (ToggleButton) getActivity().findViewById(R.id.tb_fan);
		tbDoor = (ToggleButton) getActivity().findViewById(R.id.tb_door);
		etNubmer = (EditText) getActivity().findViewById(R.id.et_number);
		btnSend = (Button) getActivity().findViewById(R.id.btn_send);
		tbLamp = (ToggleButton) getActivity().findViewById(R.id.tb_lamp);
		// 设置控件的监听
		setControl(tbCurtain, MyUtils.curtain);
		setControl(tbAlert, MyUtils.alert);
		setControl(tbLamp1, MyUtils.lamp1);
		setControl(tbLamp2, MyUtils.lamp2);
		setControl(tbFan, MyUtils.fan);
		setControl(tbDoor, MyUtils.door);
		tbLamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				MyUtils.lamp.setControl(isChecked);
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String buff = etNubmer.getText().toString();
				if (!TextUtils.isEmpty(buff)) {
					CMD.control(ConstantUtil.INFRARED_1_SERVE, buff, "1");
				} else {
					MyToast.makeText("参数不为空");
				}
			}
		});
		// 启动线程
		MyUtils.mHandler.postDelayed(this, 1000);
	}

	// 设置ToggleButton的setOnCheckedChangeListener监听控制的方法
	public void setControl(ToggleButton cb, final CMD cmd) {
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (cmd == MyUtils.lamp1 || cmd == MyUtils.lamp2) {
					MyUtils.lamp.isCheck = null;
				}
				cmd.setControl(isChecked);
			}
		});
	}

	@Override
	public void run() {
		// 把全局数据显示到控件上
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText(MyUtils.values[i] + "");
		}
		// 绘图
		if (Fragment5.tbChart.isChecked()) {
			MyUtils.sdb.execSQL("insert into datas values(?,?,?)",
					new String[] { System.currentTimeMillis() + "",
							MyUtils.temp + "", MyUtils.ill + "" });
			Fragment5.chartView.addLists(new double[] { MyUtils.temp,
					MyUtils.ill });
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 5000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
