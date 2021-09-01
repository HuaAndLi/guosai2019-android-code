package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

public class MainActivity extends FragmentActivity {

	public static ViewPager pager;
	private List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.pager);
		fragments.add(new Fragment1());
		fragments.add(new Fragment2());
		pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});
		pager.setOffscreenPageLimit(fragments.size());
		conn();
	}

	public void conn() {
		SocketClient.getInstance().login(new LoginCallback() {

			@Override
			public void onEvent(final String arg0) {
				MyUtils.mHandler.post(new Runnable() {

					@Override
					public void run() {
						if (ConstantUtil.SUCCESS.equals(arg0)) {
							MyToast.makeText("连接成功");
						} else {
							MyToast.makeText("重连中......");
							MyUtils.mHandler.postDelayed(new Runnable() {

								@Override
								public void run() {
									conn();
								}
							}, 2000);
						}
					}
				});
			}
		});
		SocketClient.getInstance().disConnect();
		SocketClient.getInstance().creatConnect();
	}
}
