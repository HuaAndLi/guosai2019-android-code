package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 联动界面
 * 
 * @author Administrator
 * 
 */
public class Fragment3 extends Fragment implements Runnable {
	private Spinner sp2;
	private Spinner sp1;
	private EditText etNum;
	private EditText etMinute;
	private ToggleButton tbLinke;
	private Switch swAlert;
	private Switch swFan;
	private Switch swLamp;
	private Switch swDoor;
	private boolean isDestroy;
	private int second = 0;
	private TextView tvTime;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment3, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 绑定控件
		sp1 = (Spinner) getActivity().findViewById(R.id.sp1);
		sp2 = (Spinner) getActivity().findViewById(R.id.sp2);
		etNum = (EditText) getActivity().findViewById(R.id.etnum);
		etMinute = (EditText) getActivity().findViewById(R.id.etminute);
		tbLinke = (ToggleButton) getActivity().findViewById(R.id.tb_link);
		swAlert = (Switch) getActivity().findViewById(R.id.sw_alert);
		swFan = (Switch) getActivity().findViewById(R.id.sw_fan);
		swLamp = (Switch) getActivity().findViewById(R.id.sw_lamp);
		swDoor = (Switch) getActivity().findViewById(R.id.sw_door);
		tvTime = (TextView) getActivity().findViewById(R.id.tv_time);
		// 设置适配器
		sp1.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] { "温度", "湿度",
						"光照" }));
		sp2.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, new String[] { ">", "<=" }));
		// 设置控件的监听
		tbLinke.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					String mun = etNum.getText().toString();
					String min = etMinute.getText().toString();
					if (MyUtils.isEmpty(mun, min)) {
						MyToast.makeText("参数不为空");
						tbLinke.setChecked(false);
						return;
					}
					second = Integer.parseInt(min) * 60;
					tvTime.setText("联动模式还有" + second / 60 + "分" + second % 60
							+ "秒");
				} else {
					tvTime.setText("联动模式还有x分x秒");
					close();
				}
			}
		});
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		// 联动控制
		if (tbLinke.isChecked() && second >= 0) {
			String buff = etNum.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				double value = MyUtils.values[sp1.getSelectedItemPosition()];
				if (sp2.getSelectedItemPosition() == 0) {
					if (value > Integer.parseInt(buff)) {
						MyUtils.alert.setControl(swAlert.isChecked());
						MyUtils.door.setControl(swDoor.isChecked());
						MyUtils.fan.setControl(swFan.isChecked());
						MyUtils.lamp.setControl(swLamp.isChecked());
					} else {
						close();
					}
				} else {
					if (value <= Integer.parseInt(buff)) {
						MyUtils.alert.setControl(swAlert.isChecked());
						MyUtils.door.setControl(swDoor.isChecked());
						MyUtils.fan.setControl(swFan.isChecked());
						MyUtils.lamp.setControl(swLamp.isChecked());
					} else {
						close();
					}
				}
			} else {
				MyToast.makeText("参数不为空");
			}
			tvTime.setText("联动模式还有" + second / 60 + "分" + second % 60 + "秒");
			if (second == 0) {
				tbLinke.setChecked(false);
				close();
			}
			second--;
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}

	// 关闭相应器件
	public void close() {
		swAlert.setChecked(false);
		swDoor.setChecked(false);
		swFan.setChecked(false);
		swLamp.setChecked(false);
		MyUtils.alert.setControl(false);
		MyUtils.door.setControl(false);
		MyUtils.fan.setControl(false);
		MyUtils.lamp.setControl(false);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
