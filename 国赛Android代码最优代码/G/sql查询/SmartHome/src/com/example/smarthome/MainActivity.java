package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private ViewPager pager;
	private RadioGroup rgFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pager = (ViewPager) findViewById(R.id.pager);
		rgFragment = (RadioGroup) findViewById(R.id.rg_fragment);
		fragments.add(new Fragment1());
		fragments.add(new Fragment2());
		fragments.add(new Fragment3());
		fragments.add(new Fragment4());
		for (int i = 0; i < rgFragment.getChildCount(); i++) {
			final int cur = i;
			RadioButton rb = (RadioButton) rgFragment.getChildAt(i);
			rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						pager.setCurrentItem(cur);
					}
				}
			});
		}
		pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return fragments.get(arg0);
			}

			@Override
			public void setPrimaryItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
				RadioButton rb = (RadioButton) rgFragment.getChildAt(position);
				rb.setChecked(true);
				super.setPrimaryItem(container, position, object);
			}
		});
		pager.setOffscreenPageLimit(fragments.size());
	}
}
