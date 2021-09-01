package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.CMD;
import utils.MyUtils;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bizideal.smarthome.socket.ConstantUtil;

public class Fragment1 extends Fragment implements Runnable {
	private TextView[] tvs = new TextView[9];
	private CheckBox cbAlert;
	private CheckBox cbLamp;
	private CheckBox cbFan;
	private CheckBox cbTV;
	private CheckBox cbAIR;
	private CheckBox cbDVD;
	private CheckBox cbDoor;
	private Button btnKai;
	private Button btnTing;
	private Button btnGuan;
	private ImageView ivLogo;
	private ImageView ivCl;
	private ImageView ivHw;
	private boolean isDestroy;
	private List<Move> moves = new ArrayList<Move>();
	private AbsoluteLayout al;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		al = (AbsoluteLayout) getActivity().findViewById(R.id.al);
		tvs[0] = (TextView) getActivity().findViewById(R.id.tv1);
		tvs[1] = (TextView) getActivity().findViewById(R.id.tv2);
		tvs[2] = (TextView) getActivity().findViewById(R.id.tv3);
		tvs[3] = (TextView) getActivity().findViewById(R.id.tv4);
		tvs[4] = (TextView) getActivity().findViewById(R.id.tv5);
		tvs[5] = (TextView) getActivity().findViewById(R.id.tv6);
		tvs[6] = (TextView) getActivity().findViewById(R.id.tv7);
		tvs[7] = (TextView) getActivity().findViewById(R.id.tv8);
		tvs[8] = (TextView) getActivity().findViewById(R.id.tv9);
		cbAlert = (CheckBox) getActivity().findViewById(R.id.cb_alert);
		cbLamp = (CheckBox) getActivity().findViewById(R.id.cb_lamp);
		cbFan = (CheckBox) getActivity().findViewById(R.id.cb_fan);
		cbTV = (CheckBox) getActivity().findViewById(R.id.cb_tv);
		cbAIR = (CheckBox) getActivity().findViewById(R.id.cb_air);
		cbDVD = (CheckBox) getActivity().findViewById(R.id.cb_dvd);
		cbDoor = (CheckBox) getActivity().findViewById(R.id.cb_door);
		btnKai = (Button) getActivity().findViewById(R.id.btn_kai);
		btnTing = (Button) getActivity().findViewById(R.id.btn_ting);
		btnGuan = (Button) getActivity().findViewById(R.id.btn_guan);
		ivLogo = (ImageView) getActivity().findViewById(R.id.iv_logo);
		ivCl = (ImageView) getActivity().findViewById(R.id.iv_cl);
		ivHw = (ImageView) getActivity().findViewById(R.id.iv_hw);
		al.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
				startActivity(new Intent(getActivity(), SaveActivity.class));
				return true;
			}
		});
		for (int i = 0; i < tvs.length; i++) {
			moves.add(new Move(tvs[i]));
			tvs[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}
		CallBack hw = new CallBack() {

			@Override
			public boolean call(String suss) {
				// TODO Auto-generated method stub
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ivHw.setVisibility(View.GONE);

					}
				});
				return false;
			}
		};
		CallBack cl = new CallBack() {

			@Override
			public boolean call(String suss) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						ivCl.setVisibility(View.GONE);
					}
				});
				return false;
			}
		};
		moves.add(new Move(cbAlert));
		moves.add(new Move(cbLamp));
		moves.add(new Move(cbDoor));
		moves.add(new Move(cbFan));

		moves.add(new Move(cbAIR, hw));
		moves.add(new Move(cbDVD, hw));
		moves.add(new Move(cbTV, hw));

		moves.add(new Move(btnKai, cl));
		moves.add(new Move(btnGuan, cl));
		moves.add(new Move(btnTing, cl));
		ivLogo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < moves.size(); i++) {
					moves.get(i).reSet();
				}
				ivHw.setVisibility(View.VISIBLE);
				ivCl.setVisibility(View.VISIBLE);
			}
		});
		btnGuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.curtain.isCheck = true;
				MyUtils.curtain.setControl(false);
			}
		});
		btnKai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyUtils.curtain.isCheck = false;
				MyUtils.curtain.setControl(true);
			}
		});
		btnTing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CMD.control(ConstantUtil.Curtain, "4", "3");
			}
		});
		setControl(cbAlert, MyUtils.alert);
		setControl(cbLamp, MyUtils.lamp);
		setControl(cbFan, MyUtils.fan);
		setControl(cbTV, MyUtils.tv);
		setControl(cbAIR, MyUtils.air);
		setControl(cbDVD, MyUtils.dvd);
		setControl(cbDoor, MyUtils.door);
		MyUtils.mHandler.post(this);
	}

	public void setControl(CheckBox cb, final CMD cmd) {
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				cmd.setControl(isChecked);
			}
		});
	}

	@Override
	public void run() {

		for (int i = 0; i < tvs.length; i++) {
			tvs[i].setText("" + MyUtils.values[i]);
		}
		tvs[8].setText(MyUtils.statehuman == 0 ? "有人" : "无人");
		if (!isDestroy) {
			MyUtils.mHandler.postDelayed(this, 3000);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		isDestroy = true;
		super.onDestroy();
	}
}
