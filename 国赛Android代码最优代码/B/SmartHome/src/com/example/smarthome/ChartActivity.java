package com.example.smarthome;

import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/**
 * ��ͼ����
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
		//�󶨿ؼ�
		tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText("���ţ�");
		chartView = (ChartView) findViewById(R.id.chartView1);
		//�����߳�
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
