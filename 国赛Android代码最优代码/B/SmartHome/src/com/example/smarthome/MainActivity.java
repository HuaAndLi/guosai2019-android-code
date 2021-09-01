package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements Runnable {
	private List<Button> btns = new ArrayList<Button>();
	private List<TextView> tvrs = new ArrayList<TextView>();
	private List<Button> btnrs = new ArrayList<Button>();
	private int index = 0;
	public static String name = "";
	private TextView[] tvs = new TextView[9];
	private boolean isDestroy;
	private static String[] valuenames = new String[] { "温度", "湿度", "光照", "烟雾",
			"燃气", "CO2", "PM25", "气压", "人体红外" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 绑定控件
		btns.add((Button) findViewById(R.id.btnr1));
		btns.add((Button) findViewById(R.id.btnr2));
		btns.add((Button) findViewById(R.id.btnr3));
		btns.add((Button) findViewById(R.id.btnr4));

		tvrs.add((TextView) findViewById(R.id.tvr1));
		tvrs.add((TextView) findViewById(R.id.tvr2));
		tvrs.add((TextView) findViewById(R.id.tvr3));
		tvrs.add((TextView) findViewById(R.id.tvr4));

		btnrs.add((Button) findViewById(R.id.btn1));
		btnrs.add((Button) findViewById(R.id.btn2));
		btnrs.add((Button) findViewById(R.id.btn3));
		btnrs.add((Button) findViewById(R.id.btn4));
		btnrs.add((Button) findViewById(R.id.btn5));
		btnrs.add((Button) findViewById(R.id.btn6));
		btnrs.add((Button) findViewById(R.id.btn7));
		btnrs.add((Button) findViewById(R.id.btn8));
		btnrs.add((Button) findViewById(R.id.btn9));
		btnrs.add((Button) findViewById(R.id.btn10));
		btnrs.add((Button) findViewById(R.id.btn11));
		btnrs.add((Button) findViewById(R.id.btn12));
		btnrs.add((Button) findViewById(R.id.btn13));
		btnrs.add((Button) findViewById(R.id.btn14));
		btnrs.add((Button) findViewById(R.id.btn15));
		btnrs.add((Button) findViewById(R.id.btn16));
		setText();
		// 设置监听
		for (int i = 0; i < btns.size(); i++) {
			final int cur = i;
			btns.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (index != cur) {
						index = cur;
						for (int j = 0; j < tvrs.size(); j++) {
							if (cur == j) {
								tvrs.get(j).setVisibility(View.VISIBLE);
							} else {
								tvrs.get(j).setVisibility(View.INVISIBLE);
							}
						}
						MyToast.makeText("切换成功");
					}
				}
			});
		}
		for (int i = 0; i < btnrs.size(); i++) {
			final int cur = i;
			btnrs.get(cur).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					name = btnrs.get(cur).getText().toString();
					for (int j = 0; j < MyUtils.controls.size(); j++) {
						MyUtils.controls.get(j).setControl(
								MyUtils.shared.getBoolean(name
										+ MyUtils.controls.get(j).name
										+ MyUtils.controls.get(j).channel,
										false));
					}
					switch (index) {
					case 0:
						AlertDialog.Builder builder = new AlertDialog.Builder(
								MainActivity.this);
						builder.setTitle("房间管理");
						View view = View.inflate(MainActivity.this,
								R.layout.set, null);
						TextView tView = (TextView) view
								.findViewById(R.id.tv_name);
						tView.setText("房号：" + name);
						tvs[0] = (TextView) view.findViewById(R.id.tv1);
						tvs[1] = (TextView) view.findViewById(R.id.tv2);
						tvs[2] = (TextView) view.findViewById(R.id.tv3);
						tvs[3] = (TextView) view.findViewById(R.id.tv4);
						tvs[4] = (TextView) view.findViewById(R.id.tv5);
						tvs[5] = (TextView) view.findViewById(R.id.tv6);
						tvs[6] = (TextView) view.findViewById(R.id.tv7);
						tvs[7] = (TextView) view.findViewById(R.id.tv8);
						tvs[8] = (TextView) view.findViewById(R.id.tv9);
						view.findViewById(R.id.btn_red).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Aut o-generated method stub
										MyUtils.editor.putInt("r" + name,
												Color.RED).commit();
										setText();
									}
								});
						view.findViewById(R.id.btn_black).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										MyUtils.editor.putInt("r" + name,
												Color.GRAY).commit();
										setText();
									}
								});
						view.findViewById(R.id.btn_green).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										MyUtils.editor.putInt("r" + name,
												Color.GREEN).commit();
										setText();
									}
								});
						builder.setView(view);
						builder.setNegativeButton("关闭", null);
						builder.show();
						break;
					case 1:
						startActivity(new Intent(MainActivity.this,
								ControlActivity.class));
						break;
					case 2:
						startActivity(new Intent(MainActivity.this,
								LinkActivity.class));
						break;
					case 3:
						startActivity(new Intent(MainActivity.this,
								ChartActivity.class));
						break;
					}
				}
			});
		}
		// 启动线程
		MyUtils.mHandler.post(this);
	}

	public void setText() {
		for (int i = 0; i < btnrs.size(); i++) {
			btnrs.get(i).setBackgroundColor(
					MyUtils.shared.getInt("r"
							+ btnrs.get(i).getText().toString(), Color.GREEN));
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < tvs.length; i++) {
			if (tvs[i] != null) {
				try {
					tvs[i].setText(valuenames[i] + ":" + MyUtils.values[i]);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		if (tvs[8] != null) {
			try {
				tvs[8].setText(valuenames[8] + ":"
						+ (MyUtils.statehuman != 0 ? "有人" : "无人"));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}

}
