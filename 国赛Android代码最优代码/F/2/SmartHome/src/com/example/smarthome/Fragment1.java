package com.example.smarthome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.CMD;
import utils.MyToast;
import utils.MyUtils;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class Fragment1 extends Fragment implements Runnable {
	private RadioGroup rgName;
	private RadioGroup rgControl;
	private SeekBar seekBar;
	private TextView tvMin;
	private TextView tvMax;
	private int number = 0;
	private LinearLayout ll;
	private ChartView chartView;
	private int index = 0;
	private TextView tvName;
	private TextView[] tvs = new TextView[7];
	private TextView[] tvxs = new TextView[7];
	private List<double[]> lists = new ArrayList<double[]>();
	private boolean isDestroy;
	private RadioButton rbTing;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvxs[0] = (TextView) getActivity().findViewById(R.id.tvx1);
		tvxs[1] = (TextView) getActivity().findViewById(R.id.tvx2);
		tvxs[2] = (TextView) getActivity().findViewById(R.id.tvx3);
		tvxs[3] = (TextView) getActivity().findViewById(R.id.tvx4);
		tvxs[4] = (TextView) getActivity().findViewById(R.id.tvx5);
		tvxs[5] = (TextView) getActivity().findViewById(R.id.tvx6);
		tvxs[6] = (TextView) getActivity().findViewById(R.id.tvx7);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		ll = (LinearLayout) getActivity().findViewById(R.id.ll);
		tvName = (TextView) getActivity().findViewById(R.id.tv_name);
		rgName = (RadioGroup) getActivity().findViewById(R.id.rg_name);
		rgControl = (RadioGroup) getActivity().findViewById(R.id.rg_control);
		seekBar = (SeekBar) getActivity().findViewById(R.id.seekBar1);
		tvMin = (TextView) getActivity().findViewById(R.id.tv_min);
		tvMax = (TextView) getActivity().findViewById(R.id.tv_max);
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		rbTing = (RadioButton) getActivity().findViewById(R.id.radioting);
		rbTing.setEnabled(false);
		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("选择传感器类型");
				View view = View.inflate(getActivity(), R.layout.value, null);
				final Spinner sp = (Spinner) view.findViewById(R.id.spinner1);
				sp.setAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1,
						Fragment2.valuename));
				builder.setView(view);
				builder.setNegativeButton("设置",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								index = sp.getSelectedItemPosition();
								tvName.setText(Fragment2.valuename[index]);
								MyToast.makeText("设置成功");
							}
						});
				builder.show();
			}
		});
		ll.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				chartView.setMode(chartView.getMode() != 0 ? 0 : 1);
				return true;
			}
		});
		rgControl.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				control();
			}
		});
		rgName.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio5) {
					rbTing.setEnabled(true);
				} else {
					rbTing.setEnabled(false);
				}
				switch (checkedId) {
				case R.id.radio0:
					checkName(MyUtils.tv.isCheck);
					break;
				case R.id.radio1:
					checkName(MyUtils.dvd.isCheck);
					break;
				case R.id.radio2:
					checkName(MyUtils.air.isCheck);
					break;
				case R.id.radio3:
					checkName(MyUtils.lamp1.isCheck);
					MyUtils.lamp.isCheck = null;
					break;
				case R.id.radio4:
					checkName(MyUtils.lamp2.isCheck);
					MyUtils.lamp.isCheck = null;
					break;
				case R.id.radio5:
					checkName(MyUtils.curtain.isCheck);
					break;
				case R.id.radio6:
					checkName(MyUtils.door.isCheck);
					break;
				case R.id.radio7:
					checkName(MyUtils.fan.isCheck);
					break;
				}
			}
		});
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (seekBar.getProgress() == 100) {
					number += 100;
					tvMin.setText(number + "");
					tvMax.setText(number + 100 + "");
					seekBar.setProgress(1);
				} else if (seekBar.getProgress() == 0) {
					if (number > 0) {
						number -= 100;
						tvMin.setText(number + "");
						tvMax.setText(number + 100 + "");
						seekBar.setProgress(99);
					}
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}
		});
		MyUtils.mHandler.post(this);
	}

	public void checkName(Boolean ischeck) {
		if (ischeck == null) {
			rgControl.check(R.id.radioting);
		} else if (ischeck) {
			rgControl.check(R.id.radiokai);
		} else {
			rgControl.check(R.id.radioguan);
		}
	}

	public void control() {
		if (rgControl.getCheckedRadioButtonId() != R.id.radioting) {
			switch (rgName.getCheckedRadioButtonId()) {
			case R.id.radio0:
				Toast.makeText(getActivity(), "1", 0).show();
				MyUtils.tv
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio1:
				MyUtils.dvd
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio2:
				MyUtils.air
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio3:
				MyUtils.lamp1
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio4:
				MyUtils.lamp2
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio5:
				MyUtils.curtain
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio6:
				MyUtils.door
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;
			case R.id.radio7:
				MyUtils.fan
						.setControl(rgControl.getCheckedRadioButtonId() == R.id.radiokai);
				break;

			default:
				break;
			}
		} else {
			if (rgName.getCheckedRadioButtonId() == R.id.radio5) {
				CMD.control(ConstantUtil.Curtain, "4", "3");
				MyUtils.curtain.isCheck = null;
			}
		}
	}

	@Override
	public void run() {
		lists.add(Arrays.copyOf(MyUtils.values, 9));
		chartView.addLists(MyUtils.values[index],
				number + seekBar.getProgress());
		if (lists.size() > 7) {
			lists.remove(0);
		}
		for (int i = 0; i < lists.size(); i++) {
			tvxs[i].setText(number + seekBar.getProgress() + i + "");
			tvs[i].setText("" + lists.get(i)[index]);
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
