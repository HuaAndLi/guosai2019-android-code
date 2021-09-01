package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
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
	private Point[][] points = new Point[3][3];
	private List<Point> pointlList = new ArrayList<LockView.Point>();
	private Paint paint = new Paint();
	private Paint strokePaint = new Paint();
	private float x, y;
	private boolean isUp;
	private int state = 0;
	public static final int Black = 0;
	public static final int RED = 1;
	public static final int BULE = 2;
	private CallBack callBack;

	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		strokePaint.setAntiAlias(true);
		strokePaint.setStyle(Style.STROKE);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		float width = getWidth() > getHeight() ? getHeight() : getWidth();
		points[0][0] = new Point(width / 4 * 1, width / 4 * 1, "1");
		points[1][0] = new Point(width / 4 * 2, width / 4 * 1, "2");
		points[2][0] = new Point(width / 4 * 3, width / 4 * 1, "3");

		points[0][1] = new Point(width / 4 * 1, width / 4 * 2, "4");
		points[1][1] = new Point(width / 4 * 2, width / 4 * 2, "5");
		points[2][1] = new Point(width / 4 * 3, width / 4 * 2, "6");

		points[0][2] = new Point(width / 4 * 1, width / 4 * 3, "7");
		points[1][2] = new Point(width / 4 * 2, width / 4 * 3, "8");
		points[2][2] = new Point(width / 4 * 3, width / 4 * 3, "9");

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				canvas.drawCircle(points[i][j].x, points[i][j].y, 15, paint);
			}
		}
		if (state == Black) {
			strokePaint.setColor(Color.GRAY);
		} else if (state == BULE) {
			strokePaint.setColor(Color.BLUE);
		} else if (state == RED) {
			strokePaint.setColor(Color.RED);
		}
		if (pointlList.size() > 0) {
			Path path = new Path();
			for (int i = 0; i < pointlList.size(); i++) {
				Point point = pointlList.get(i);
				canvas.drawCircle(point.x, point.y, 40, strokePaint);
				if (i == 0) {
					path.moveTo(point.x, point.y);
				} else {
					path.lineTo(point.x, point.y);
				}
			}
			if (!isUp) {
				path.lineTo(x, y);
			}
			canvas.drawPath(path, strokePaint);
		}

	}

	public void setCallBack(CallBack callBack) {
		this.callBack = callBack;
	}

	public Point checkPoint() {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point point = points[i][j];
				if (Point.with(point.x, x, 30, point.y, y)) {
					return point;
				}
			}
		}
		return null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			pointlList.clear();
			isUp = false;
			state = Black;
			break;
		case MotionEvent.ACTION_MOVE:
			Point checkPoint = checkPoint();
			if (checkPoint != null && !pointlList.contains(checkPoint)) {
				pointlList.add(checkPoint);
			}
			break;
		case MotionEvent.ACTION_UP:
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < pointlList.size(); i++) {
				builder.append(pointlList.get(i).name);
			}
			if (callBack.check(builder.toString())) {
				state = BULE;
			} else {
				state = RED;
			}
			isUp = true;
			break;

		default:
			break;
		}
		postInvalidate();
		return true;
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

		public static boolean with(float pointX, float movingX, float r,
				float pointY, float movingY) {
			return Math.sqrt((pointX - movingX) * (pointX - movingX)
					+ (pointY - movingY) * (pointY - movingY)) < r;
		}
	}
}
