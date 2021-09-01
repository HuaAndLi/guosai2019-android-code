package com.example.smarthome;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private Paint paint = new Paint();
	private int color[] = new int[] { Color.RED, Color.BLACK, Color.BLUE,
			Color.GREEN, Color.WHITE, Color.YELLOW, 0xff00ffff };
	private List<Double> lists = new ArrayList<Double>();

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);
//		lists.add(10d);
//		lists.add(20d);
//		lists.add(30d);
//		lists.add(40d);
//		lists.add(50d);
//		lists.add(60d);
//		lists.add(70d);
	}

	public void setLists(List<Double> lists) {
		this.lists = lists;
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.translate(getWidth() / 2, getHeight() / 2);
		RectF rectF = new RectF(-80, -80, 80, 80);
		double num = 0;
		double startAngle = 0;
		double sweepAngle = 0;
		for (int i = 0; i < lists.size(); i++) {
			num += lists.get(i);
		}
		for (int i = 0; i < lists.size(); i++) {
			paint.setColor(color[i]);
			sweepAngle = lists.get(i) / num * 360;
			canvas.drawArc(rectF, (float) startAngle, (float) sweepAngle,
					true, paint);
			paint.setColor(Color.LTGRAY);
			canvas.drawText(lists.get(i) + "", (float) Math.cos(Math
					.toRadians(startAngle + sweepAngle / 2)) * 50, (float) Math
					.sin(Math.toRadians(startAngle + sweepAngle / 2)) * 50,
					paint);
			startAngle += sweepAngle;
		}
	}
}
