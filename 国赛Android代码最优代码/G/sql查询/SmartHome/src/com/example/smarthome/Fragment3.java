package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class Fragment3 extends Fragment implements Runnable {
	private RadioGroup rgF3;
	private ChartView chartView;
	private boolean isDetroy;

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
		rgF3 = (RadioGroup) getActivity().findViewById(R.id.rg_f3);
		chartView =(ChartView)getActivity().findViewById(R.id.chartView1);
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		if (rgF3.getCheckedRadioButtonId() == R.id.radio1) {
			Cursor cursorbule = MyUtils.sdb.rawQuery("select * from datas where temp>= ? and temp<= ?", new String[]{"0","19"});
			Cursor cursorred = MyUtils.sdb.rawQuery("select * from datas where temp>= ? and temp<= ?", new String[]{"20","29"});
			Cursor cursorgreen = MyUtils.sdb.rawQuery("select * from datas where temp>= ? and temp<= ?", new String[]{"30","39"});
			chartView.setLists(cursorbule.getCount(), cursorred.getCount(), cursorgreen.getCount(),1);
			cursorbule.close();
			cursorred.close();
			cursorgreen.close();
		} else {
			Cursor cursorbule = MyUtils.sdb.rawQuery("select * from datas where ill>= ? and ill<= ?", new String[]{"0","300"});
			Cursor cursorred = MyUtils.sdb.rawQuery("select * from datas where ill>= ? and ill<= ?", new String[]{"301","699"});
			Cursor cursorgreen = MyUtils.sdb.rawQuery("select * from datas where ill>= ? and ill<= ?", new String[]{"700","1500"});
			chartView.setLists(cursorbule.getCount(), cursorred.getCount(), cursorgreen.getCount(),0);
			cursorbule.close();
			cursorred.close();
			cursorgreen.close();
		}
		if(!isDetroy){
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDetroy = true;
		super.onDestroy();
	}
}
