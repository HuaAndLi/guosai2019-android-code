package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.CMD;
import utils.MyUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class Fragment1 extends Fragment implements Runnable {
	private TextView[] tvs = new TextView[9];
	private boolean isDetroy;
	private ImageView ivLogo;
	private CheckBox cbAlert;
	private CheckBox cbDoor;
	private CheckBox cbLamp;
	private CheckBox cbFan;
	private CheckBox cbTV;
	private CheckBox cbAIR;
	private CheckBox cbDVD;
	private Button btnTing;
	private Button btnGuan;
	private Button btnKai;
	private ImageView ivCl;
	private ImageView ivHw;
	private List<Move> moves = new ArrayList<Move>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.TextView01);
		tvs[2] = (TextView) getActivity().findViewById(R.id.TextView02);
		tvs[3] = (TextView) getActivity().findViewById(R.id.TextView03);
		tvs[4] = (TextView) getActivity().findViewById(R.id.TextView04);
		tvs[5] = (TextView) getActivity().findViewById(R.id.TextView05);
		tvs[6] = (TextView) getActivity().findViewById(R.id.TextView06);
		tvs[7] = (TextView) getActivity().findViewById(R.id.TextView07);
		tvs[8] = (TextView) getActivity().findViewById(R.id.TextView08);
		ivLogo = (ImageView) getActivity().findViewById(R.id.iv_logo);
		ivHw = (ImageView) getActivity().findViewById(R.id.iv_hw);
		ivCl = (ImageView) getActivity().findViewById(R.id.iv_cl);
		cbAlert = (CheckBox) getActivity().findViewById(R.id.cb_alert);
		cbDoor = (CheckBox) getActivity().findViewById(R.id.cb_door);
		cbLamp = (CheckBox) getActivity().findViewById(R.id.cb_lamp);
		cbFan = (CheckBox) getActivity().findViewById(R.id.cb_fan);
		cbTV = (CheckBox) getActivity().findViewById(R.id.cb_tv);
		cbAIR = (CheckBox) getActivity().findViewById(R.id.cb_air);
		cbDVD = (CheckBox) getActivity().findViewById(R.id.cb_dvd);
		btnKai = (Button) getActivity().findViewById(R.id.btn_kai);
		btnGuan = (Button) getActivity().findViewById(R.id.btn_guan);
		btnTing = (Button) getActivity().findViewById(R.id.btn_ting);

		moves.add(new Move(cbAlert));
		moves.add(new Move(cbDoor));
		moves.add(new Move(cbLamp));
		moves.add(new Move(cbFan));
		moves.add(new Move(btnKai, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivCl.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		moves.add(new Move(btnGuan, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivCl.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		moves.add(new Move(btnTing, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivCl.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		moves.add(new Move(cbTV, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivHw.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		moves.add(new Move(cbDVD, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivHw.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		moves.add(new Move(cbAIR, new CallBack() {

			@Override
			public boolean check(String messege) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivHw.setVisibility(View.GONE);
					}
				});
				return false;
			}
		}));
		for (int i = 0; i < tvs.length; i++) {
			moves.add(new Move(tvs[i]));
			tvs[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
		}

		ivLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < moves.size(); i++) {
					moves.get(i).reSet();
				}
				ivCl.setVisibility(View.VISIBLE);
				ivHw.setVisibility(View.VISIBLE);
			}
		});

		btnKai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.isCheck = null;
				MyUtils.curtain.setControl(true);
			}
		});
		btnGuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.isCheck = null;
				MyUtils.curtain.setControl(false);
			}
		});
		btnTing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyUtils.curtain.isCheck = null;
				CMD.control(ConstantUtil.Curtain, "4", "3");
			}
		});
		cbAlert.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.alert.setControl(isChecked);
			}
		});
		cbLamp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.lamp.setControl(isChecked);
			}
		});
		cbFan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.fan.setControl(isChecked);
			}
		});
		cbTV.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.tv.isCheck = null;
				MyUtils.tv.setControl(false);
			}
		});
		cbAIR.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.air.isCheck = null;
				MyUtils.air.setControl(false);
			}
		});
		cbDVD.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.dvd.isCheck = null;
				MyUtils.dvd.setControl(false);
			}
		});
		cbDoor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				MyUtils.door.isCheck = null;
				MyUtils.door.setControl(false);
			}
		});
		MyUtils.mHandler.post(this);
	}

	@Override
	public void run() {
		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText(MyUtils.statehuman != 0 ? "有人" : "无人");
		if (!isDetroy) {
			MyUtils.mHandler.postDelayed(this, 2000);
		}
	}

	@Override
	public void onDestroy() {
		isDetroy = true;
		super.onDestroy();
	}
}
