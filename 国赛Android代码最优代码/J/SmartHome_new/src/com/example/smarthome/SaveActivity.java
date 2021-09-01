package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class SaveActivity extends Activity {

	private Spinner sp1;
	private ListView listView;
	private Button btnCha;
	private LockView lockView;
	private int number = 0;
	private List<String> lists = new ArrayList<String>();
	private ArrayAdapter<String> myAdapter;
	private String[] names = new String[] { "温度", "湿度", "光照", "烟雾", "燃气",
			"CO2", "PM25", "气压", "人体", "全部" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		sp1 = (Spinner) findViewById(R.id.spinner1);
		listView = (ListView) findViewById(R.id.listView1);
		btnCha = (Button) findViewById(R.id.btn_cha);
		lockView = (LockView) findViewById(R.id.lockView1);
		sp1.setAdapter(new ArrayAdapter<String>(SaveActivity.this,
				android.R.layout.simple_list_item_1, names));
		listView.setAdapter(myAdapter = new ArrayAdapter<String>(
				SaveActivity.this, android.R.layout.simple_list_item_1, lists));
		setText();
		btnCha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sp1.getSelectedItemPosition() < 9) {
					for (int i = 0; i < MyUtils.values.length; i++) {
						if (i == sp1.getSelectedItemPosition()) {
							MyUtils.editor.putString("v" + i,
									MyUtils.values[i] + "").commit();
						} else {
							MyUtils.editor.putString("v" + i, null).commit();
						}
					}
				} else {
					for (int i = 0; i < MyUtils.values.length; i++) {
						MyUtils.editor.putString("v" + i,
								MyUtils.values[i] + "").commit();
					}
				}
				for (int i = 0; i < MyUtils.values.length; i++) {
					// MyUtils.editor.putInt("i" + i, i + 1).commit();
					//
					MyUtils.editor.putInt("i" + i,
							MyUtils.shared.getInt("i" + i, 0) + 9).commit();
				}
				setText();
				MyToast.makeText("查询成功");
			}
		});
		lockView.setCallBack(new CallBack() {

			@Override
			public boolean call(String suss) {
				if ("123456".equals(suss)) {
					number = 0;
					btnCha.setEnabled(true);
					MyToast.makeText("解锁成功");
					return true;
				}
				if ("789".equals(suss)) {
					finish();
					startActivity(new Intent(SaveActivity.this,
							MainActivity.class));
					MyToast.makeText("验证成功");
					return true;
				}
				number++;
				if (number >= 3) {
					finish();
					startActivity(new Intent(SaveActivity.this,
							SignInActivity.class));
				}
				return false;
			}
		});
	}

	public void setText() {
		lists.clear();
		for (int i = 0; i < MyUtils.values.length; i++) {
			//
			if (MyUtils.shared.getString("v" + i, null) != null) {
				lists.add(MyUtils.shared.getInt("i" + i, 0) + "   " + names[i]
						+ "   " + MyUtils.shared.getString("v" + i, ""));
			}
		}
		myAdapter.notifyDataSetChanged();
	}
}
