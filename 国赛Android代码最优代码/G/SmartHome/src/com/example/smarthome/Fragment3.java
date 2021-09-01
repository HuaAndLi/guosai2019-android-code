package com.example.smarthome;

import utils.MyUtils;
import android.database.Cursor;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class Fragment3 extends Fragment implements Runnable {
	private RadioGroup rgF3;
	private LinearLayout llTemp;
	private LinearLayout llIll;
	private ChartView chartView;
	private int red, green, blue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment3, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		chartView = (ChartView) getActivity().findViewById(R.id.chartView1);
		rgF3 = (RadioGroup) getActivity().findViewById(R.id.rg_f3);
		llTemp = (LinearLayout) getActivity().findViewById(R.id.ll_temp);
		llIll = (LinearLayout) getActivity().findViewById(R.id.ll_ill);
		rgF3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio0) {
					llTemp.setVisibility(View.GONE);
					llIll.setVisibility(View.VISIBLE);
				} else {
					llTemp.setVisibility(View.VISIBLE);
					llIll.setVisibility(View.GONE);
				}
			}
		});

		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		red = 0;
		green = 0;
		blue = 0;
		MyUtils.sdb.execSQL("insert into data values(?,?,?)", new String[] {
				System.currentTimeMillis() + "", MyUtils.temp + "",
				MyUtils.ill + "" });
		MyUtils.sdb
				.execSQL("delete from data where time < ?",
						new String[] { System.currentTimeMillis() - 60 * 60
								* 1000 + "" });
		Cursor cursor = MyUtils.sdb.rawQuery("select * from data", null);
		if (rgF3.getCheckedRadioButtonId() == R.id.radio0) {
			while (cursor.moveToNext()) {
				double double1 = cursor.getDouble(2);
				if (double1 >= 0 && double1 <= 300) {
					blue++;
				} else if (double1 >= 301 &&double1 <= 699) {
					red++;
				} else if (double1 >= 700 &&double1 <= 1500) {
					green++;
				}
			}
		} else {
			while (cursor.moveToNext()) {
				double double1 = cursor.getDouble(1);
				if (double1 >= 0 && double1 <= 19) {
					blue++;
				} else if (double1 >= 20 &&double1 <= 29) {
					red++;
				} else if (double1 >= 30 &&double1 <= 39) {
					green++;
				}
			}
		}
		cursor.close();
		chartView.setLists(blue, red, green);
		if (!MainActivity.isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}
}
