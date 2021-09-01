package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private List<Double> lists = new ArrayList<Double>();
	private double max = 100;
	private Paint paint = new Paint();
	private int mode = 0;
	public static final int LINE = 0;
	private int number = 0;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
	}

	public void setMode(int mode) {
		this.mode = mode;
		postInvalidate();
	}

	public int getMode() {
		return mode;
	}

	public void addList(double value, int number) {
		lists.add(value);
		this.number = number;
		if (lists.size() > 8) {
			lists.remove(0);
		}
		double maxvalue = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (maxvalue < lists.get(i)) {
				maxvalue = lists.get(i);
			}
		}
		if (maxvalue < 100) {
			max = 100;
		} else if (maxvalue < 1000) {
			max = 1000;
		} else {
			max = 2000;
		}
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth() - 40;
		int height = getHeight() - 40;
		int step = (int) (width * 1.0 / (lists.size() + 1));
		canvas.drawLine(0, height, getWidth(), height, paint);
		canvas.drawLine(40, 0, 40, getHeight(), paint);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(40, height - height / 5 * i, 45, height - height
					/ 5 * i, paint);
			canvas.drawText(max / 5 * i + "", 25, height - height / 5 * i + 20,
					paint);
		}
		if (mode == LINE) {
			paint.setStyle(Style.STROKE);
			Path path = new Path();
			for (int i = 0; i < lists.size(); i++) {
				int x = 40 + step * (i + 1);
				int y = (int) (height - height / max * lists.get(i));
				if (i == 0) {
					path.moveTo(x, y);
				} else {
					path.lineTo(x, y);
				}
				canvas.drawText(number + i + "", x, height + 20, paint);
				canvas.drawText("" + lists.get(i), x, y, paint);
			}
			canvas.drawPath(path, paint);
		} else {
			paint.setStyle(Style.FILL);
			Rect rect = new Rect();
			for (int i = 0; i < lists.size(); i++) {
				int x = 40 + step * (i + 1);
				int y = (int) (height - height / max * lists.get(i));
				rect.top = y;
				rect.bottom = height;
				rect.left = x - step / 4;
				rect.right = x + step / 4;
				canvas.drawRect(rect, paint);
				canvas.drawText(number + i + "", x, height + 20, paint);
				canvas.drawText("" + lists.get(i), x, y, paint);
			}
		}
	}
}
