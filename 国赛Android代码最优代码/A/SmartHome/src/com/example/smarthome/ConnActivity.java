package com.example.smarthome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConnActivity extends Activity implements Runnable {
	private TextView[] tvcuos = new TextView[10];
	private TextView[] tvs = new TextView[10];
	private boolean isDestory;
	private List<double[]> lists = new ArrayList<double[]>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conn);
		// 绑定控件
		tvs[0] = (TextView) findViewById(R.id.tt1);
		tvs[1] = (TextView) findViewById(R.id.tt2);
		tvs[2] = (TextView) findViewById(R.id.tt3);
		tvs[3] = (TextView) findViewById(R.id.tt4);
		tvs[4] = (TextView) findViewById(R.id.tt5);
		tvs[5] = (TextView) findViewById(R.id.tt6);
		tvs[6] = (TextView) findViewById(R.id.tt7);
		tvs[7] = (TextView) findViewById(R.id.tt8);
		tvs[8] = (TextView) findViewById(R.id.tt9);
		tvs[9] = (TextView) findViewById(R.id.tt10);
		tvcuos[0] = (TextView) findViewById(R.id.TextView1);
		tvcuos[1] = (TextView) findViewById(R.id.textView2);
		tvcuos[2] = (TextView) findViewById(R.id.TextView3);
		tvcuos[3] = (TextView) findViewById(R.id.TextView4);
		tvcuos[4] = (TextView) findViewById(R.id.TextView5);
		tvcuos[5] = (TextView) findViewById(R.id.TextView6);
		tvcuos[6] = (TextView) findViewById(R.id.TextView7);
		tvcuos[7] = (TextView) findViewById(R.id.TextView8);
		tvcuos[8] = (TextView) findViewById(R.id.TextView9);
		tvcuos[9] = (TextView) findViewById(R.id.TextView10);
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	public void setText(final boolean isConn, final int index) {
		MyUtils.mHandler.post(new Runnable() {

			@Override
			public void run() {
				tvs[index].setText(isConn ? "在线" : "离线");
				tvcuos[index].setVisibility(isConn ? View.INVISIBLE
						: View.VISIBLE);
			}
		});

	}

	@Override
	protected void onDestroy() {
		isDestory = true;
		super.onDestroy();
	}

	@Override
	public void run() {
		lists.add(Arrays.copyOf(MyUtils.values, 9));
		if (lists.size() > 10) {
			lists.remove(0);
		}
		boolean isAll = false;
		for (int i = 0; i < 9; i++) {
			boolean isIn = false;
			double d = lists.get(lists.size() - 1)[i];
			for (int j = 0; j < lists.size(); j++) {
				if (d != lists.get(j)[i]) {
					isIn = true;
					isAll = true;
					break;
				}
			}
			setText(isIn, i);
		}
		if (!isAll) {
			MyToast.makeText("请改变数值");
		}
		setText(isAll, 9);
		if (!isDestory) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}
}
