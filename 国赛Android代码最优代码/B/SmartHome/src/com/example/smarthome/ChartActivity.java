package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * 绘图界面
 * 
 * @author Administrator
 * 
 */
public class ChartActivity extends Activity implements Runnable {

	private TextView tvName;
	private ChartView chartView;
	private boolean isDestroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		//绑定控件
		tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText("房号：");
		chartView = (ChartView) findViewById(R.id.chartView1);
		//启动线程
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		chartView.addLists(MyUtils.temp);
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}

}
