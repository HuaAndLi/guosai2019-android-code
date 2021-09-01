package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.location.Address;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义绘图
 * 
 * @author Administrator
 * 
 */
public class ChartView extends View {
	private Paint paint = new Paint();
	private List<double[]> lists = new ArrayList<double[]>();
	private double max = 100;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
		lists.add(new double[] { 10.d, 50.d });
		lists.add(new double[] { 10.d, 50.d });
		lists.add(new double[] { 10.d, 50.d });
		lists.add(new double[] { 10.d, 50.d });
	}

	// 添加数据
	public void addLists(double[] value) {
		if (lists.size() >= 4) {
			lists.remove(0);
		}
		lists.add(value);
		double maxvalue = 0;
		for (int i = 0; i < lists.size(); i++) {
			if (lists.get(i)[0] > lists.get(i)[1]) {
				if (maxvalue < lists.get(i)[0]) {
					maxvalue = lists.get(i)[0];
				}
			} else {
				if (maxvalue < lists.get(i)[1]) {
					maxvalue = lists.get(i)[1];
				}
			}
		}
		if (maxvalue < 100) {
			max = 100;
		} else {
			max = 1000;
		}
		postInvalidate();
	}

	// 绘制
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getWidth() - 40;
		int height = getHeight() - 40;
		int step = (int) (width * 1.0 / (lists.size() + 1));
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(40, height - height / 5 * i, getWidth(), height
					- height / 5 * i, paint);
			canvas.drawText(max / 5 * i + "", 25, height - height / 5 * i + 10,
					paint);
		}
		Rect rect = new Rect();
		for (int i = 0; i < lists.size(); i++) {
			int x = 40 + step * (i + 1);
			int y1 = (int) (height - height / max * lists.get(i)[0]);
			int y2 = (int) (height - height / max * lists.get(i)[1]);
			rect.top = y1;
			rect.bottom = height;
			rect.right = (int) (x - step / 8 * 0.3);
			rect.left = (int) (x - step / 8 * 3);
			paint.setColor(Color.BLUE);
			canvas.drawRect(rect, paint);
			rect.top = y2;
			rect.bottom = height;
			rect.left = (int) (x + step / 8 * 0.3);
			rect.right = (int) (x + step / 8 * 3);
			paint.setColor(Color.RED);
			canvas.drawRect(rect, paint);
			paint.setColor(Color.BLACK);
			canvas.drawText(i + 1 + "", x, height + 20, paint);
		}
	}
}
