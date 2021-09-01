package com.example.smarthome;

import utils.MyToast;
import utils.MyUtils;
import android.R.anim;
import android.R.integer;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

/**
 * ��������
 * 
 * @author Administrator
 * 
 */
public class LinkActivity extends Activity implements Runnable {

	private TextView tvName;
	private Spinner sp1;
	private Spinner sp2;
	private Spinner sp3;
	private Spinner sp4;
	private EditText etNumber;
	private Switch swLink;
	private boolean isDestroy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link);
		// �󶨿ؼ�
		tvName = (TextView) findViewById(R.id.tv_name);
		tvName.setText("���ţ�");
		sp1 = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp3 = (Spinner) findViewById(R.id.spinner3);
		sp4 = (Spinner) findViewById(R.id.spinner4);
		etNumber = (EditText) findViewById(R.id.et_number);
		swLink = (Switch) findViewById(R.id.sw_link);
		// ����������
		sp1.setAdapter(new ArrayAdapter<String>(LinkActivity.this,
				android.R.layout.simple_list_item_1, new String[] { "�ն�", "�¶�",
						"ʪ��" }));
		sp2.setAdapter(new ArrayAdapter<String>(LinkActivity.this,
				android.R.layout.simple_list_item_1, new String[] { ">", "<" }));
		sp3.setAdapter(new ArrayAdapter<String>(LinkActivity.this,
				android.R.layout.simple_list_item_1, new String[] { "����", "���",
						"����" }));
		sp4.setAdapter(new ArrayAdapter<String>(LinkActivity.this,
				android.R.layout.simple_list_item_1, new String[] { "��", "��" }));
		// �����߳�
		MyUtils.mHandler.post(this);
	}

	//�ı�ͼƬ�Ŀ���
	public void setControl(final int index, boolean isCheck) {
		switch (index) {
		case 0:
			MyUtils.fan.setControl(isCheck);
			break;
		case 1:
			MyUtils.lamp.setControl(isCheck);
			break;
		case 2:
			MyUtils.curtain.setControl(isCheck);
			break;
		}
	}

	@Override
	public void run() {
		if (swLink.isChecked()) {
			String buff = etNumber.getText().toString();
			if (!TextUtils.isEmpty(buff)) {
				double value = 0;
				int number = Integer.parseInt(buff);
				switch (sp1.getSelectedItemPosition()) {
				case 0:
					value = MyUtils.ill;
					break;
				case 1:
					value = MyUtils.temp;
					break;
				case 2:
					value = MyUtils.humidity;
					break;
				}
				if (sp2.getSelectedItemPosition() == 0) {
					if (value > number) {
						setControl(sp3.getSelectedItemPosition(),
								sp4.getSelectedItemPosition() == 0);
					} else {
						setControl(sp3.getSelectedItemPosition(), false);
					}
				} else {
					if (value < number) {
						setControl(sp3.getSelectedItemPosition(),
								sp4.getSelectedItemPosition() == 0);
					} else {
						setControl(sp3.getSelectedItemPosition(), false);
					}
				}
			} else {
				MyToast.makeText("������Ϊ��");
			}
		}
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
