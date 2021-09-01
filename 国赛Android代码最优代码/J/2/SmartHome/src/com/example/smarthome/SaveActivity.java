package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

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

	private LockView loackView;
	private ListView listView;
	private Button btnCha;
	private int number = 0;
	private List<String> lists = new ArrayList<String>();
	private Spinner sp;
	private String[] valuenames = new String[] { "温度", "湿度", "光照", "烟雾", "燃气",
			"CO2", "PM25", "气压", "人体", "全部" };
	private ArrayAdapter<String> myAapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		loackView = (LockView) findViewById(R.id.lockView1);
		listView = (ListView) findViewById(R.id.listView1);
		btnCha = (Button) findViewById(R.id.btn_cha);
		listView.setAdapter(myAapter = new ArrayAdapter<String>(
				SaveActivity.this, android.R.layout.simple_list_item_1, lists));
		sp = (Spinner) findViewById(R.id.spinner1);
		sp.setAdapter(new ArrayAdapter<String>(SaveActivity.this,
				android.R.layout.simple_list_item_1, valuenames));

		// btnCha.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// JSONObject jsonObject = new JSONObject();
		// for (int i = 0; i < 9; i++) {
		// try {
		// jsonObject.put(valuenames[i], MyUtils.values[i]);
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// MyUtils.sdb.execSQL("insert into datas values(?)",
		// new String[] { jsonObject.toString() });
		//
		// setText();
		// }
		// });

		setText();
		btnCha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int number = MyUtils.shared.getInt("number", 1);
				for (int i = 0; i < valuenames.length - 1; i++) {
					if (sp.getSelectedItemPosition() < 9) {
						if (i == sp.getSelectedItemPosition()) {
							MyUtils.editor.putString(
									"v" + i,
									number + i + "		" + valuenames[i] + "		"
											+ MyUtils.values[i]).commit();
						} else {
							MyUtils.editor.putString("v" + i, null).commit();
						}
					} else {
						MyUtils.editor.putString(
								"v" + i,
								number + i + "		" + valuenames[i] + "		"
										+ MyUtils.values[i]).commit();
					}
				}
				setText();
				MyUtils.editor.putInt("number", number + 9).commit();
			}
		});
		loackView.setCallBack(new CallBack() {

			@Override
			public boolean check(String messege) {
				if (messege.equals("123456")) {
					btnCha.setEnabled(true);
					number = 0;
					return true;
				}
				if (messege.equals("789")) {
					number = 0;
					finish();
					startActivity(new Intent(SaveActivity.this,
							MainActivity.class));
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

	// public void setText() {
	// lists.clear();
	// int number = 1;
	// Cursor cursor = MyUtils.sdb.rawQuery("select * from datas ", null);
	// if (cursor.getCount() > 0) {
	// while (cursor.moveToNext()) {
	// if (sp.getSelectedItemPosition() == 9) {
	// try {
	// JSONObject jsonObject = new JSONObject(
	// cursor.getString(0));
	// for (int i = 0; i < 9; i++) {
	// lists.add(number + i + "		" + valuenames[i] + " 	"
	// + jsonObject.getDouble(valuenames[i]));
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// } else {
	// try {
	// JSONObject jsonObject = new JSONObject(
	// cursor.getString(0));
	// lists.add(number
	// + sp.getSelectedItemPosition()
	// + "		"
	// + valuenames[sp.getSelectedItemPosition()]
	// + " 	"
	// + jsonObject.getDouble(valuenames[sp
	// .getSelectedItemPosition()]));
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// number += 9;
	// }
	// }
	// cursor.close();
	// myAapter.notifyDataSetChanged();
	// }
	
	public void setText() {
		lists.clear();
		for (int i = 0; i < valuenames.length - 1; i++) {
			if (MyUtils.shared.getString("v" + i, null) != null) {
				lists.add(MyUtils.shared.getString("v" + i, ""));
			}
		}
		myAapter.notifyDataSetInvalidated();
	}
}
