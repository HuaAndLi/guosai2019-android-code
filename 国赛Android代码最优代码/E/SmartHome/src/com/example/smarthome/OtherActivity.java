package com.example.smarthome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.CMD;
import utils.MyToast;
import utils.MyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class OtherActivity extends Activity implements Runnable {

	private CheckBox cbMode;
	private CheckBox cbLink;
	private CheckBox cbZhi;
	private List<CheckBox> cbs = new ArrayList<CheckBox>();
	private boolean isDestroy;
	private Switch swLi;
	private Spinner sp1;
	private Spinner sp2;
	private Spinner sp3;
	private EditText etNumber;
	private AbsoluteLayout al;
	private Button btnCun;
	private Button btnQu;
	public static String[] names = new String[] { "报警灯", "窗帘", "射灯", "风扇",
			"电视", "空调", "DVD", "门禁" };
	private List<String> lists = new ArrayList<String>();
	private ListView listview;
	private ArrayAdapter<String> myAdapter;
	private EditText etBian;
	private TextView tvIll;
	private ChartView chartView;
	private EditText etText;
	private EditText etZ1;
	private EditText etZ2;
	public static String[] strs = new String[] { "开", "关", "停" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		al = (AbsoluteLayout) findViewById(R.id.al);
		cbs.add(cbMode = (CheckBox) findViewById(R.id.cb_mode));
		cbs.add(cbLink = (CheckBox) findViewById(R.id.cb_link));
		cbs.add(cbZhi = (CheckBox) findViewById(R.id.cb_zhi));
		sp1 = (Spinner) findViewById(R.id.sp1);
		sp2 = (Spinner) findViewById(R.id.sp2);
		sp3 = (Spinner) findViewById(R.id.sp3);
		listview = (ListView) findViewById(R.id.listView1);
		etNumber = (EditText) findViewById(R.id.et_number);
		etBian = (EditText) findViewById(R.id.et_bian);
		etZ1 = (EditText) findViewById(R.id.etz1);
		etZ2 = (EditText) findViewById(R.id.etz2);
		btnCun = (Button) findViewById(R.id.btn_cun);
		btnQu = (Button) findViewById(R.id.btn_qu);
		tvIll = (TextView) findViewById(R.id.tv_chart);
		chartView = (ChartView) findViewById(R.id.chartView1);
		listview.setAdapter(myAdapter = new ArrayAdapter<String>(
				OtherActivity.this, android.R.layout.simple_list_item_1, lists));
		sp1.setAdapter(new ArrayAdapter<String>(OtherActivity.this,
				android.R.layout.simple_list_item_1,
				new String[] { "温度", "湿度" }));
		sp2.setAdapter(new ArrayAdapter<String>(OtherActivity.this,
				android.R.layout.simple_list_item_1, new String[] { ">=", "<" }));
		sp3.setAdapter(new ArrayAdapter<String>(OtherActivity.this,
				android.R.layout.simple_list_item_1, new String[] { "开", "关" }));
		swLi = (Switch) findViewById(R.id.sw_isli);
		new MyPopu(etZ1, names).init();
		new MyPopu(etZ2, strs).init();
		tvIll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chartView.setVisibility(View.INVISIBLE);
				MyUtils.mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						chartView.setVisibility(View.VISIBLE);
						chartView.setLists(MyUtils.ills);
						chartView.postInvalidate();
					}
				}, 80);

			}
		});
		btnCun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub\
				String z1 = etZ1.getText().toString();
				String z2 = etZ2.getText().toString();
				if (cbZhi.isChecked()) {
					List<String> listname = new ArrayList<String>(Arrays
							.asList(names));
					List<String> liststr = new ArrayList<String>(Arrays
							.asList(strs));
					if (listname.contains(z1) && liststr.contains(z2)) {
						if (!z1.equals(names[1]) && z2.equals(strs[2])) {
							MyToast.makeText("只有窗帘可以停，请重新选择");
						} else {
							MyUtils.editor.putInt("bian",
									MyUtils.shared.getInt("bian", 0) + 1)
									.commit();
							MyUtils.sdb.execSQL(
									"insert into datas values (?,?,?)",
									new String[] {
											MyUtils.shared.getInt("bian", 0)
													+ "", z1, z2 });
							AlertDialog.Builder builder = new AlertDialog.Builder(
									OtherActivity.this);
							builder.setTitle("提示");
							builder.setMessage("当前编号："
									+ MyUtils.shared.getInt("bian", 0));
							builder.show();
						}
					} else {
						MyToast.makeText("文本不正确，可点击选择文本");
					}
				} else {
					MyToast.makeText("请先勾选指令控制");
				}
			}
		});
		btnQu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cbZhi.isChecked()) {
					String buff = etBian.getText().toString();
					if (!TextUtils.isEmpty(buff)) {
						Cursor cursor = MyUtils.sdb.rawQuery(
								"select * from datas where bian = ? ",
								new String[] { buff });
						if (cursor.getCount() > 0) {
							cursor.moveToNext();
							lists.add(cursor.getString(0) + "    "
									+ cursor.getString(1) + "    "
									+ cursor.getString(2));
							myAdapter.notifyDataSetChanged();
							if (cursor.getString(2).equals("开")) {
								for (int i = 0; i < names.length; i++) {
									if (names[i].equals(cursor.getString(1))) {
										MyUtils.controls.get(i)
												.setControl(true);
										break;
									}
								}
							} else if (cursor.getString(2).equals("关")) {
								for (int i = 0; i < names.length; i++) {
									if (names[i].equals(cursor.getString(1))) {
										MyUtils.controls.get(i).setControl(
												false);
										break;
									}
								}
							} else if (cursor.getString(2).equals("停")) {
								CMD.control(ConstantUtil.Curtain, "4", "3");
							}
						} else {
							MyToast.makeText("没有此编号");
						}
						cursor.close();
					} else {
						MyToast.makeText("参数不为空");
					}
				} else {
					MyToast.makeText("请先勾选指令控制");
				}
			}
		});
		al.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(OtherActivity.this, MainActivity.class));
				return true;
			}
		});
		for (int i = 0; i < cbs.size(); i++) {
			final int cur = i;
			cbs.get(cur).setOnCheckedChangeListener(
					new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							// TODO Auto-generated method stub
							if (isChecked) {
								for (int j = 0; j < cbs.size(); j++) {
									if (cur != j) {
										cbs.get(j).setChecked(false);
									}
								}
							}
						}
					});
		}

		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		if (cbMode.isChecked()) {
			if (swLi.isChecked()) {
				if (MyUtils.smoke > 600 || MyUtils.statehuman != 0) {
					MyUtils.alert.setControl(true);
					MyUtils.curtain.setControl(true);
					MyUtils.fan.setControl(true);
				}
			} else {
				MyUtils.air.setControl(true);
				MyUtils.lamp.setControl(true);
				MyUtils.curtain.setControl(false);
			}
		}
		if (cbLink.isChecked()) {
			String buff = etNumber.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				double value = 0;
				switch (sp1.getSelectedItemPosition()) {
				case 0:
					value = MyUtils.temp;
					break;
				case 1:
					value = MyUtils.humidity;
					break;
				default:
					break;
				}
				if (sp2.getSelectedItemPosition() == 0) {
					if (value >= Integer.parseInt(buff)) {
						MyUtils.fan
								.setControl(sp3.getSelectedItemPosition() == 0);
					}
				} else {
					if (value < Integer.parseInt(buff)) {
						MyUtils.fan
								.setControl(sp3.getSelectedItemPosition() == 0);
					}
				}
			} else {
				MyToast.makeText("参数不为空");
			}

		}

		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	protected void onDestroy() {
		isDestroy = true;
		super.onDestroy();
	}
}
