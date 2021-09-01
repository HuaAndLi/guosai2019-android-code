package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.CMD;
import utils.MyToast;
import utils.MyUitls;
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
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Fragment1 extends Fragment implements Runnable {
	private int min = 0;
	private SeekBar seekbar;
	private TextView tvMin;
	private TextView tvMax;
	private RadioGroup rgCheck;
	private RadioGroup rgName;
	private ChartView chartView;
	private TextView tvName;
	private boolean isDestroy;
	private int index = 0;
	private List<double[]> lists = new ArrayList<double[]>();
	public static String[] names = new String[] { "温度", "湿度", "光照", "烟雾", "燃气",
			"CO2", "PM25", "气压", "人体" };
	private TextView[] tvbs = new TextView[8];
	private TextView[] tvs = new TextView[8];
	private LinearLayout ll;

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
		tvbs[0] = (TextView) getActivity().findViewById(R.id.tvb1);
		tvbs[1] = (TextView) getActivity().findViewById(R.id.tvb2);
		tvbs[2] = (TextView) getActivity().findViewById(R.id.tvb3);
		tvbs[3] = (TextView) getActivity().findViewById(R.id.tvb4);
		tvbs[4] = (TextView) getActivity().findViewById(R.id.tvb5);
		tvbs[5] = (TextView) getActivity().findViewById(R.id.tvb6);
		tvbs[6] = (TextView) getActivity().findViewById(R.id.tvb7);
		tvbs[7] = (TextView) getActivity().findViewById(R.id.tvb8);
		ll = (LinearLayout) getActivity().findViewById(R.id.l1);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		tvName = (TextView) getActivity().findViewById(R.id.tv_valuename);
		seekbar = (SeekBar) getActivity().findViewById(R.id.seekBar1);
		tvMin = (TextView) getActivity().findViewById(R.id.tv_min);
		tvMax = (TextView) getActivity().findViewById(R.id.tv_max);
		rgName = (RadioGroup) getActivity().findViewById(R.id.rg_name);
		rgCheck = (RadioGroup) getActivity().findViewById(R.id.rg_check);

		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("选择传感器");
				View view = View.inflate(getActivity(), R.layout.sp, null);
				final Spinner sp = (Spinner) view.findViewById(R.id.spinner1);
				sp.setAdapter(new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, names));
				builder.setView(view);
				builder.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (index != sp.getSelectedItemPosition()) {
									index = sp.getSelectedItemPosition();
									MyToast.makeText("切换成功");
								}
							}
						});
				builder.show();
			}
		});
		ll.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				chartView.setMode(chartView.getMode() == 0 ? 1 : 0);
				return true;
			}
		});
		RadioButton rb = (RadioButton) rgCheck.getChildAt(2);
		rb.setEnabled(false);
		rb = null;
		rgName.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rb = (RadioButton) rgCheck.getChildAt(2);
				rb.setEnabled(checkedId == R.id.radio5);
				rb = null;
				CMD cmd = null;
				switch (rgName.getCheckedRadioButtonId()) {
				case R.id.radio0:
					cmd = MyUitls.tv;
					break;
				case R.id.radio1:
					cmd = MyUitls.dvd;
					break;
				case R.id.radio2:
					cmd = MyUitls.air;
					break;
				case R.id.radio3:
					cmd = MyUitls.lamp1;
					break;
				case R.id.radio4:
					cmd = MyUitls.lamp2;
					break;
				case R.id.radio5:
					cmd = MyUitls.curtain;
					break;
				case R.id.radio6:
					cmd = MyUitls.door;
					break;
				case R.id.radio7:
					cmd = MyUitls.fan;
					break;
				}
				if (cmd != null) {
					if (cmd.isCheck == null) {
						rb = (RadioButton) rgCheck.getChildAt(2);
						rb.setChecked(true);
					} else if (cmd.isCheck == true) {
						rb = (RadioButton) rgCheck.getChildAt(0);
						rb.setChecked(true);
					} else {
						rb = (RadioButton) rgCheck.getChildAt(1);
						rb.setChecked(true);
					}
				}
				rb = null;
			}
		});
		rgCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (rgName.getCheckedRadioButtonId()) {
				case R.id.radio0:
					MyUitls.tv.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio1:
					MyUitls.dvd.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio2:
					MyUitls.air.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio3:
					MyUitls.lamp1.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio4:
					MyUitls.lamp2.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio5:
					if (rgCheck.getCheckedRadioButtonId() != R.id.radio2) {
						MyUitls.curtain.setControl(rgCheck
								.getCheckedRadioButtonId() == R.id.radio0);
					} else {
						MyUitls.curtain.setControl(null);
					}
					break;
				case R.id.radio6:
					MyUitls.door.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				case R.id.radio7:
					MyUitls.fan.setControl(rgCheck.getCheckedRadioButtonId() == R.id.radio0);
					break;
				}
			}
		});
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if (seekbar.getProgress() == 0) {
					if (min > 0) {
						min -= 100;
						tvMin.setText("" + min);
						tvMax.setText(min + 100 + "");
						seekBar.setProgress(99);
					}
				} else if (seekBar.getProgress() == 100) {
					min += 100;
					tvMin.setText("" + min);
					tvMax.setText(min + 100 + "");
					seekBar.setProgress(1);
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

		MyUitls.mHandler.post(this);
	}

	@Override
	public void run() {
		lists.add(new double[] { MyUitls.temp, MyUitls.humidity, MyUitls.ill,
				MyUitls.smoke, MyUitls.gas, MyUitls.co2, MyUitls.pm25,
				MyUitls.airp, MyUitls.statehuman });
		tvName.setText(names[index]);
		if (lists.size() > 8) {
			lists.remove(0);
		}
		for (int i = 0; i < lists.size(); i++) {
			tvbs[i].setText(min + seekbar.getProgress() + i + "");
			tvs[i].setText("" + lists.get(i)[index]);
			chartView.addList(lists.get(i)[index], min + seekbar.getProgress());
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
