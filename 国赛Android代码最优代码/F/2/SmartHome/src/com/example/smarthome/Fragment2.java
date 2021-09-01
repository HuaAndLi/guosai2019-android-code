package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ControlUtils;

public class Fragment2 extends Fragment implements Runnable {
	private ListView lvValue;
	private ListView lvSign;
	private ListView lvControl;
	private ListView lvMode;
	private EditText etNumber;
	private TextView etText;
	private boolean isDestroy;
	public static String valuename[] = new String[] { "温度", "湿度", "光照", "烟雾", "燃气",
			"CO2", "PM25", "气压", "人体" };
	private String controlname[] = new String[] { "空调", "DVD", "电视", "射灯全开",
			"射灯全关", "报警灯开" };
	private String signname[] = new String[] { ">", "<", "=" };
	private List<String> modenames = new ArrayList<String>();
	private ArrayAdapter<String> myAdapter;
	private List<Mode> modes = new ArrayList<Mode>();
	private Button btnAdd;

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
		lvValue = (ListView) getActivity().findViewById(R.id.lv_value);
		lvSign = (ListView) getActivity().findViewById(R.id.lv_sign);
		lvControl = (ListView) getActivity().findViewById(R.id.lv_control);
		lvMode = (ListView) getActivity().findViewById(R.id.lv_mode);
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		etText = (TextView) getActivity().findViewById(R.id.et_text);
		btnAdd = (Button) getActivity().findViewById(R.id.btn_add);
		lvValue.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvSign.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvControl.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvMode.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lvValue.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, valuename));
		lvSign.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, signname));
		lvControl.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, controlname));
		lvMode.setAdapter(myAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, modenames));
		lvMode.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				MyToast.makeText(modenames.get(arg2) + "模式："
						+ valuename[modes.get(arg2).value]
						+ signname[modes.get(arg2).sign]
						+ modes.get(arg2).number
						+ controlname[modes.get(arg2).control]);
			}
		});
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String buff = etNumber.getText().toString();
				String text = etText.getText().toString();
				if (lvValue.getCheckedItemPosition() >= 0
						&& lvSign.getCheckedItemPosition() >= 0
						&& lvControl.getCheckedItemPosition() >= 0
						&& !TextUtils.isEmpty(buff) && !TextUtils.isEmpty(text)) {
					if (modenames.contains(text)) {
						MyToast.makeText("改模式已存在请改名字");
						return;
					}
					modenames.add(text);
					Mode mode = new Mode();
					mode.value = lvValue.getCheckedItemPosition();
					mode.sign = lvSign.getCheckedItemPosition();
					mode.number = Integer.parseInt(buff);
					mode.control = lvControl.getCheckedItemPosition();
					modes.add(mode);
					myAdapter.notifyDataSetChanged();
				} else {
					MyToast.makeText("条件或参数未选");
				}
			}
		});
		MyUtils.mHandler.post(this);
	}

	public void control(int index, boolean ischeck) {
		switch (index) {
		case 0:
			MyUtils.air.setControl(ischeck);
			break;
		case 1:
			MyUtils.dvd.setControl(ischeck);
			break;
		case 2:
			MyUtils.tv.setControl(ischeck);
			break;
		case 3:
			MyUtils.lamp.setControl(ischeck);
			break;
		case 4:
			MyUtils.lamp.setControl(!ischeck);
			break;
		case 5:
			MyUtils.alert.setControl(ischeck);
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		ControlUtils.control(MainActivity.pager.getCurrentItem() == 0 ? "1"
				: "2", "0", "0");
		SparseBooleanArray checkedItemPositions = lvMode
				.getCheckedItemPositions();
		for (int i = 0; i < modes.size(); i++) {
			if (checkedItemPositions.get(i)) {
				Mode mode = modes.get(i);
				double value = MyUtils.values[mode.value];
				int number = mode.number;
				if (mode.sign == 0) {
					control(mode.control, value>number);
				} else if (mode.sign == 1) {
					control(mode.control, value<number);
				} else {
					control(mode.control, value==number);
				}
			}
		}
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
