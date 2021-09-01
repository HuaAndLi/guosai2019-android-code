package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 选择界面
 * 
 * @author Administrator
 * 
 */
public class Fragment1 extends Fragment {
	private List<ImageView> ivs = new ArrayList<ImageView>();
	private List<TextView> tvs = new ArrayList<TextView>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment1, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 控件的绑定
		tvs.add((TextView) getActivity().findViewById(R.id.textView1));
		tvs.add((TextView) getActivity().findViewById(R.id.textView2));
		tvs.add((TextView) getActivity().findViewById(R.id.textView3));
		tvs.add((TextView) getActivity().findViewById(R.id.textView4));
		ivs.add((ImageView) getActivity().findViewById(R.id.imageView1));
		ivs.add((ImageView) getActivity().findViewById(R.id.imageView2));
		ivs.add((ImageView) getActivity().findViewById(R.id.imageView3));
		ivs.add((ImageView) getActivity().findViewById(R.id.imageView4));
		// 设置控件的监听
		for (int i = 0; i < tvs.size(); i++) {
			final int cur = i;
			tvs.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (ivs.get(cur).getVisibility() == View.VISIBLE) {
						MainActivity.pager.setCurrentItem(1 + cur);
					} else {
						for (int j = 0; j < ivs.size(); j++) {
							if (cur != j) {
								ivs.get(j).setVisibility(View.INVISIBLE);
							} else {
								ivs.get(j).setVisibility(View.VISIBLE);
							}
						}
					}
				}
			});
		}
	}
}
