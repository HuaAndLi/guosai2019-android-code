package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

//自定义绘图
public class ChartView extends View {
	private Paint paint = new Paint();
	private double max = 100;
	private List<Double> lists = new ArrayList<Double>();

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		paint.setStyle(Style.STROKE);
		addLists(0d);
		addLists(0d);
		addLists(0d);
		addLists(0d);
		addLists(0d);
		addLists(0d);
	}

	// 添加数据
	public void addLists(double value) {
		if (lists.size() >= 5) {
			lists.remove(0);
		}
		lists.add(value);
		postInvalidate();
	}

	// 绘制
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth() - 40;
		int height = getHeight() - 40;
		int step = (int) (width * 1.0 / (lists.size() + 1));
		paint.setColor(Color.BLACK);
		canvas.drawLine(0, height, getWidth(), height, paint);
		canvas.drawLine(40, 0, 40, getHeight(), paint);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(40, height - height / 5 * i, 50, height - height
					/ 5 * i, paint);
			canvas.drawText(max / 5 * i + "", 25, height - height / 5 * i + 15,
					paint);
		}
		Path path = new Path();
		for (int i = 0; i < lists.size(); i++) {
			int x = 40 + step * (i + 1);
			int y = (int) (height - height / max * lists.get(i));
			if (i == 0) {
				path.moveTo(x, y);
			} else {
				path.lineTo(x, y);
			}
			canvas.drawText(lists.get(i) + "", x, y - 5, paint);
		}
		canvas.drawPath(path, paint);

		canvas.clipRect(0, 0, getWidth(), (float) (height - height / max * 15));
		paint.setColor(Color.RED);
		canvas.drawPath(path, paint);
	}
}
