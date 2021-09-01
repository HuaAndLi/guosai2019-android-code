package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import utils.MyToast;
import utils.MyUtils;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

	private ViewPager pager;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private ActionBar actionBar;
	private List<Tab> tabs = new ArrayList<ActionBar.Tab>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SocketClient.getInstance().login(new LoginCallback() {

			@Override
			public void onEvent(final String arg0) {
				// TODO Auto-generated method stub
				MyUtils.mHandler.post(new Runnable() {
					public void run() {
						if (ConstantUtil.SUCCESS.equals(arg0)) {
							MyToast.makeText("网络连接成功");
						} else {
							MyToast.makeText("网络连接失败");
						}
					}
				});

			}
		});
		SocketClient.getInstance().creatConnect();
		pager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabs.add(actionBar.newTab().setText("传感器数据"));
		tabs.add(actionBar.newTab().setText("电器控制"));
		tabs.add(actionBar.newTab().setText("联动控制"));
		tabs.add(actionBar.newTab().setText("模式控制"));
		fragments.add(new Fragment1());
		fragments.add(new Fragment2());
		fragments.add(new Fragment3());
		fragments.add(new Fragment4());
		for (int i = 0; i < tabs.size(); i++) {
			final int cur = i;
			tabs.get(i).setTabListener(new TabListener() {

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					pager.setCurrentItem(cur);
				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub

				}
			});
			actionBar.addTab(tabs.get(i));
		}
		pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}

			@Override
			public void setPrimaryItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				tabs.get(position).select();
				super.setPrimaryItem(container, position, object);
			}
		});
		pager.setOffscreenPageLimit(fragments.size());
	}

}
