package com.example.smarthome;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout.LayoutParams;

public class Move {

	private View view;
	private LayoutParams params;
	private LayoutParams oldParams;
	private float x, y;
	private CallBack callBack;

	public Move(final View view) {
		this.view = view;
		oldParams = (LayoutParams) view.getLayoutParams();
		params = new LayoutParams(view.getLayoutParams());
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					MyPager.isMoving = false;
					x = event.getRawX();
					y = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getRawX() - 10 > x || event.getRawX() + 10 < x
							|| event.getRawY() - 10 > y
							|| event.getRawY() + 10 < y) {
						params.x = (int) event.getRawX() - view.getWidth() / 2;
						params.y = (int) event.getRawY() - view.getHeight();
						view.setLayoutParams(params);
					}
					break;
				case MotionEvent.ACTION_UP:
					MyPager.isMoving = true;
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public Move(final View view, final CallBack callBack) {
		this.view = view;
		this.callBack = callBack;
		oldParams = (LayoutParams) view.getLayoutParams();
		params = new LayoutParams(view.getLayoutParams());
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					MyPager.isMoving = false;
					x = event.getRawX();
					y = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getRawX() - 10 > x || event.getRawX() + 10 < x
							|| event.getRawY() - 10 > y
							|| event.getRawY() + 10 < y) {
						params.x = (int) event.getRawX() - view.getWidth() / 2;
						params.y = (int) event.getRawY() - view.getHeight();
						view.setLayoutParams(params);
						callBack.check("");
					}
					break;
				case MotionEvent.ACTION_UP:
					MyPager.isMoving = true;
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public void reSet() {
		view.setLayoutParams(oldParams);
	}
}
