package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import com.bizideal.smarthome.socket.ControlUtils;

import utils.MyToast;
import utils.MyUitls;
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

public class Fragment2 extends Fragment implements Runnable {
	private Button btnAdd;
	private ListView lvValue;
	private ListView lvSign;
	private ListView lvControl;
	private ListView lvMode;
	private List<String> names = new ArrayList<String>();
	private ArrayAdapter<String> myadapter;
	private EditText etNumber;
	private EditText etMode;
	private List<Mode> modes = new ArrayList<Mode>();
	private boolean isDestroy;

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
		etNumber = (EditText) getActivity().findViewById(R.id.et_number);
		etMode = (EditText) getActivity().findViewById(R.id.et_mode);
		btnAdd = (Button) getActivity().findViewById(R.id.btn_add);
		lvValue = (ListView) getActivity().findViewById(R.id.lv_value);
		lvSign = (ListView) getActivity().findViewById(R.id.lv_sign);
		lvControl = (ListView) getActivity().findViewById(R.id.lv_control);
		lvMode = (ListView) getActivity().findViewById(R.id.lv_mode);
		lvValue.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvSign.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvControl.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lvMode.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		lvValue.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice,
				Fragment1.names));
		lvSign.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, new String[] {
						">", "<", "=" }));
		lvControl.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, new String[] {
						"Â¿ÃÂµÃ·", "DVD", "ÂµÃ§ÃÃ", "ÃÃ¤ÂµÃÃÂ«Â¿Âª", "ÃÃ¤ÂµÃÃÂ«Â¹Ã", "Â±Â¨Â¾Â¯ÂµÃÂ¿Âª" }));
		lvMode.setAdapter(myadapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_multiple_choice, names));
		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String buff = etMode.getText().toString();
				String number = etNumber.getText().toString();
				if (!TextUtils.isEmpty(buff) && !TextUtils.isEmpty(number)
						&& lvValue.getCheckedItemPosition() != -1
						&& lvSign.getCheckedItemPosition() != -1
						&& lvControl.getCheckedItemPosition() != -1) {
					Mode mode = new Mode();
					mode.value = lvValue.getCheckedItemPosition();
					mode.sign = lvSign.getCheckedItemPosition();
					mode.number = Integer.parseInt(number);
					mode.control = lvControl.getCheckedItemPosition();
					modes.add(mode);
					names.add(buff);
					myadapter.notifyDataSetChanged();
				} else {
					MyToast.makeText("Â²ÃÃÃ½Â²Â»ÃÂªÂ¿Ã");
				}
			}
		});

		MyUitls.mHandler.post(this);
	}

	public boolean check(Mode mode) {
		if (mode.sign == 0) {
			if (MyUitls.values[mode.value] > mode.number) {
				return true;
			}
		} else if (mode.sign == 1) {
			if (MyUitls.values[mode.value] < mode.number) {
				return true;
			}
		} else {
			if (MyUitls.values[mode.value] == mode.number) {
				return true;
			}
		}
		return false;
	}

	public void control(Mode mode, boolean ischeck) {
		switch (mode.control) {
		case 0:
			MyUitls.air.setControl(ischeck);
			break;
		case 1:
			MyUitls.dvd.setControl(ischeck);
			break;
		case 2:
			MyUitls.tv.setControl(ischeck);
			break;
		case 3:
			MyUitls.lamp.setControl(ischeck);
			break;
		case 4:
			MyUitls.lamp.setControl(!ischeck);
			break;
		case 5:
			MyUitls.alert.setControl(ischeck);
			break;
		}
	}

	@Override
	public void run() {
		SparseBooleanArray arrays = lvMode.getCheckedItemPositions();
		for (int i = 0; i < arrays.size(); i++) {
			if (arrays.get(i)) {
				control(modes.get(i), check(modes.get(i)));
			}
		}
		if (!isDestroy) {
			MyUitls.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
