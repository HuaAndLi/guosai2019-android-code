package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private Paint paint = new Paint();
	public static List<Double> lists = new ArrayList<Double>();
	private double max = 100;
	private Paint mPaintShader;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		mPaintShader = new Paint();
		mPaintShader.setAntiAlias(true);
		addLists(0d);
		addLists(0d);
		addLists(0d);
		addLists(110d);
		addLists(30d);
		addLists(10d);
		addLists(550d);
		addLists(30d);
		addLists(200d);
		addLists(10d);
	}

	public void addLists(double value) {
		lists.add(value);
		if (lists.size() > 7) {
			lists.remove(0);
		}
		double maxvalue = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (maxvalue <= lists.get(i)) {
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
		int step = (int) (width * 1.0 / (lists.size() - 1));
		canvas.drawLine(0, height, getWidth(), height, paint);
		canvas.drawLine(40, 0, 40, getHeight(), paint);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(40, height - height / 5 * i, 50, height - height
					/ 5 * i, paint);
			canvas.drawText(max / 5 * i + "", 25, height - height / 5 * i + 15,
					paint);
		}
		Path path = new Path();
		Path stroke = new Path();
		for (int i = 0; i < lists.size(); i++) {
			int x = 40 + step * (i);
			int y = (int) (height - height / max * lists.get(i));
			if (i == 0) {
				stroke.moveTo(x, height);
				path.moveTo(x, y);
				stroke.lineTo(x, y);
			} else {
				int x1 = 40 + step * (i - 1);
				int y1 = (int) (height - height / max * lists.get(i - 1));
				path.cubicTo(x1 + step / 2, y1, x - step / 2, y, x, y);
				stroke.cubicTo(x1 + step / 2, y1, x - step / 2, y, x, y);
			}
			if (i == lists.size() - 1) {
				stroke.lineTo(x, height);
			}
			canvas.drawText(lists.get(i) + "", x, y - 5, paint);
			canvas.drawText(i + 1 + "", x, height + 20, paint);
		}
		canvas.drawPath(path, paint);
		 mPaintShader.setColor(0x33ff00ff);
		canvas.drawPath(stroke, mPaintShader);
	}
}
