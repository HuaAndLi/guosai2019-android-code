package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LockView extends View {
	private List<Point> pointlists = new ArrayList<LockView.Point>();
	private Point[][] points = new Point[3][3];
	private float x, y;
	private int state = 0;
	private Paint paint = new Paint();
	private Paint strokePaint = new Paint();
	private boolean isUp;
	private CallBack callBack;

	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		strokePaint.setAntiAlias(true);
		strokePaint.setStyle(Style.STROKE);
	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		float wigth = getWidth() > getHeight() ? getHeight() : getWidth();
		points[0][0] = new Point(wigth / 4 * 1, wigth / 4 * 1, "1");
		points[1][0] = new Point(wigth / 4 * 2, wigth / 4 * 1, "2");
		points[2][0] = new Point(wigth / 4 * 3, wigth / 4 * 1, "3");

		points[0][1] = new Point(wigth / 4 * 1, wigth / 4 * 2, "4");
		points[1][1] = new Point(wigth / 4 * 2, wigth / 4 * 2, "5");
		points[2][1] = new Point(wigth / 4 * 3, wigth / 4 * 2, "6");

		points[0][2] = new Point(wigth / 4 * 1, wigth / 4 * 3, "7");
		points[1][2] = new Point(wigth / 4 * 2, wigth / 4 * 3, "8");
		points[2][2] = new Point(wigth / 4 * 3, wigth / 4 * 3, "9");

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point point = points[i][j];
				canvas.drawCircle(point.x, point.y, 10, paint);
			}
		}
		if (state == 0) {
			strokePaint.setColor(Color.GRAY);
		} else if (state == 1) {
			strokePaint.setColor(Color.RED);
		} else {
			strokePaint.setColor(Color.BLUE);
		}
//
		Path path = new Path();
		if (pointlists.size() > 0) {
			for (int i = 0; i < pointlists.size(); i++) {
				if (i == 0) {
					path.moveTo(pointlists.get(i).x, pointlists.get(i).y);
				} else {
					path.lineTo(pointlists.get(i).x, pointlists.get(i).y);
				}
				canvas.drawCircle(pointlists.get(i).x, pointlists.get(i).y, 30,
						strokePaint);
			}
			if (!isUp) {
				path.lineTo(x, y);
			}
			canvas.drawPath(path, strokePaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isUp = false;
			state = 0;
			pointlists.clear();
			break;
		case MotionEvent.ACTION_MOVE:
			//
			Point point = checkPoint();
			if (point != null && !pointlists.contains(point)) {
				pointlists.add(point);
			}
			break;
		case MotionEvent.ACTION_UP:
			//
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < pointlists.size(); i++) {
				builder.append(pointlists.get(i).name);
			}
			//
			state = callBack != null && callBack.call(builder.toString()) ? 2
					: 1;
			isUp = true;
			break;

		default:
			break;
		}
		postInvalidate();
		return true;
	}

	public Point checkPoint() {
		//
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point point = points[i][j];
				if (Point.with(point.x, point.y, 20, x, y)) {
					return point;
				}
			}
		}
		return null;
	}

	public static class Point {
		public float x, y;
		public String name;

		public Point(float x, float y, String name) {
			super();
			this.x = x;
			this.y = y;
			this.name = name;
		}

		public static boolean with(float pointX, float pointY, float r,
				float movingX, float movingY) {
			return Math.sqrt((pointX - movingX) * (pointX - movingX)
					+ (pointY - movingY) * (pointY - movingY)) < r;

		}
	}
}
