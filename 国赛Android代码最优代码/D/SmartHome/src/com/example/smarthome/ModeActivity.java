package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyUtils;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ModeActivity extends Activity implements Runnable {
	private CheckManger valueManger, signManger, oManger;
	private EditText etNumber;
	private List<CheckBox> cbs = new ArrayList<CheckBox>();
	private boolean isDestroy;
	private TextView tvMode;
	private CheckBox cbMode;
	private String[] valuenames = new String[] { "温度", "湿度", "光照", "烟雾", "燃气",
			"co2", "pm25", "气压", "人体" };
	private String[] controlnames = new String[] { "报警灯", "窗帘", "射灯", "风扇",
			"电视", "空调", "dvd", "门禁" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mode);
		etNumber = (EditText) findViewById(R.id.et_number);
		tvMode = (TextView) findViewById(R.id.tv_mode);
		cbMode = (CheckBox) findViewById(R.id.cb_mode);
		valueManger = new CheckManger();
		signManger = new CheckManger();
		oManger = new CheckManger();
		valueManger.add((CheckBox) findViewById(R.id.cbv1));
		valueManger.add((CheckBox) findViewById(R.id.cbv2));
		valueManger.add((CheckBox) findViewById(R.id.cbv3));
		valueManger.add((CheckBox) findViewById(R.id.cbv4));
		valueManger.add((CheckBox) findViewById(R.id.cbv5));
		valueManger.add((CheckBox) findViewById(R.id.cbv6));
		valueManger.add((CheckBox) findViewById(R.id.cbv7));
		valueManger.add((CheckBox) findViewById(R.id.cbv8));
		valueManger.add((CheckBox) findViewById(R.id.cbv9));
		signManger.add((CheckBox) findViewById(R.id.cb_s1));
		signManger.add((CheckBox) findViewById(R.id.cb_s2));
		oManger.add((CheckBox) findViewById(R.id.cb_o1));
		oManger.add((CheckBox) findViewById(R.id.cb_o2));
		cbs.add((CheckBox) findViewById(R.id.cb_c1));
		cbs.add((CheckBox) findViewById(R.id.cb_c2));
		cbs.add((CheckBox) findViewById(R.id.cb_c3));
		cbs.add((CheckBox) findViewById(R.id.cb_c4));
		cbs.add((CheckBox) findViewById(R.id.cb_c5));
		cbs.add((CheckBox) findViewById(R.id.cb_c6));
		cbs.add((CheckBox) findViewById(R.id.cb_c7));
		cbs.add((CheckBox) findViewById(R.id.cb_c8));
		valueManger.init();
		signManger.init();
		oManger.init();
		valueManger.setCheck(MyUtils.shared.getInt("value", -1));
		signManger.setCheck(MyUtils.shared.getInt("sign", -1));
		oManger.setCheck(MyUtils.shared.getInt("o", -1));
		etNumber.setText(MyUtils.shared.getString("number", ""));
		for (int i = 0; i < cbs.size(); i++) {
			cbs.get(i).setChecked(MyUtils.shared.getBoolean("c" + i, false));
		}
		findViewById(R.id.imageView1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		findViewById(R.id.imageView2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		MyUtils.mHandler.post(this);
	}

	@Override
	protected void onDestroy() {
		isDestroy = true;
		MyUtils.editor.putInt("value", valueManger.getIndex()).commit();
		MyUtils.editor.putInt("sign", signManger.getIndex()).commit();
		MyUtils.editor.putInt("o", oManger.getIndex()).commit();
		String buff = etNumber.getText().toString();
		if (!TextUtils.isEmpty(buff)) {
			MyUtils.editor.putString("number", buff).commit();
		}
		MyUtils.editor.putInt("sign", signManger.getIndex()).commit();
		for (int i = 0; i < cbs.size(); i++) {
			MyUtils.editor.putBoolean("c" + i, cbs.get(i).isChecked()).commit();
		}
		super.onDestroy();
	}

	@Override
	public void run() {
		if (cbMode.isChecked()) {
			String buff = etNumber.getText().toString();
			StringBuilder builder = new StringBuilder();
			if (valueManger.getIndex() == -1 || signManger.getIndex() == -1
					|| TextUtils.isEmpty(buff)) {
				builder.append(oManger.getIndex()==0?"开启":"关闭");
				for (int i = 0; i < cbs.size(); i++) {
					if (cbs.get(i).isChecked()) {
						builder.append(controlnames[i]);
						MyUtils.controls.get(i).setControl(
								oManger.getIndex() == 0);
					}
				}
				cbMode.setChecked(false);
			} else {
				double value = MyUtils.values[valueManger.getIndex()];
				int number = Integer.parseInt(buff);
				builder.append(valuenames[valueManger.getIndex()]);
				builder.append(signManger.getIndex()==0?"大于":"小于");
				builder.append(buff);
				builder.append(oManger.getIndex()==0?",开启":",关闭");
				if (signManger.getIndex() == 0) {
					for (int i = 0; i < cbs.size(); i++) {
						if (cbs.get(i).isChecked()) {
							builder.append(controlnames[i]);
						}
					}
					if (value > number) {
						for (int i = 0; i < cbs.size(); i++) {
							if (cbs.get(i).isChecked()) {
								MyUtils.controls.get(i).setControl(
										oManger.getIndex() == 0);
							}
						}
					}
				} else {
					if (value < number) {
						for (int i = 0; i < cbs.size(); i++) {
							if (cbs.get(i).isChecked()) {
								MyUtils.controls.get(i).setControl(
										oManger.getIndex() == 0);
							}
						}
					}
				}
			}
			tvMode.setText(builder.toString());
		}
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 1000);
		}
	}
}
