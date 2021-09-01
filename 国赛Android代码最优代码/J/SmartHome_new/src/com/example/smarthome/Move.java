package com.example.smarthome;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout.LayoutParams;

public class Move {
	private LayoutParams params;
	private LayoutParams oldparams;
	private View view;
	private float x, y;
	private CallBack callBack;

	public Move(final View view) {
		this.view = view;
		params = new LayoutParams(view.getLayoutParams());
		oldparams = (LayoutParams) view.getLayoutParams();
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.e("33", "down");
					x = event.getRawX();
					y = event.getRawY();
					MyViewPager.isMoving = false;
					break;
				case MotionEvent.ACTION_MOVE:
					Log.e("33", "move");
					if (event.getRawX() < x - 5 || event.getRawX() > x + 5
							|| event.getRawY() < y - 5
							|| event.getRawY() > y + 5) {
						params.x = (int) (event.getRawX()-view.getWidth()/2);
						params.y = (int) (event.getRawY()-view.getHeight());
						view.setLayoutParams(params);
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.e("33", "up");
					MyViewPager.isMoving = true;
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public Move(final View view, CallBack back) {
		this.view = view;
		this.callBack = back;
		params = new LayoutParams(view.getLayoutParams());
		oldparams = (LayoutParams) view.getLayoutParams();
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.e("33", "down");
					x = event.getRawX();
					y = event.getRawY();
					MyViewPager.isMoving = false;
					break;
				case MotionEvent.ACTION_MOVE:
					Log.e("33", "move");
					if (event.getRawX() < x - 5 || event.getRawX() > x + 5
							|| event.getRawY() < y - 5
							|| event.getRawY() > y + 5) {
						params.x = (int) (event.getRawX()-view.getWidth()/2);
						params.y = (int) (event.getRawY()-view.getHeight());
						view.setLayoutParams(params);
						callBack.call("");
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.e("33", "up");
					MyViewPager.isMoving = true;
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	public void reSet() {
		view.setLayoutParams(oldparams);
	}
}
