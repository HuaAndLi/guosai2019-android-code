package com.example.smarthome;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private Paint paint = new Paint();
	private Paint paint1 = new Paint();
	private double[] lists = new double[] { 5, 8, 1 };
	private int[] colors = new int[] { Color.BLUE, Color.RED, Color.GREEN };
	private int index = 0;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setTextAlign(Align.CENTER);
		paint.setStrokeWidth(35);
		paint1.setTextAlign(Align.CENTER);
		paint1.setTextSize(25);
	}

	public void setLists(double blue, double red, double green, int index) {
		lists[0] = blue;
		lists[1] = red;
		lists[2] = green;
		this.index = index;
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (index == 0) {
			canvas.drawText("光照一小时统计图", getWidth() / 2, 20, paint1);
			canvas.drawText("0-300", getWidth() - 100, 100, paint1);
			canvas.drawText("301-699", getWidth() - 100, 200, paint1);
			canvas.drawText("700-1500", getWidth() - 100, 300, paint1);
			// canvas.drawRect(left, top, right, bottom, paint);
			paint1.setColor(Color.BLUE);
			canvas.drawRect(getWidth()-170, 100-10, getWidth()-150, 100+10, paint1);
			paint1.setColor(Color.RED);
			canvas.drawRect(getWidth()-170, 200-10, getWidth()-150, 200+10, paint1);
			paint1.setColor(Color.GREEN);
			canvas.drawRect(getWidth()-170, 300-10, getWidth()-150, 300+10, paint1);
			paint1.setColor(Color.BLACK);
		} else {
			canvas.drawText("温度一小时统计图", getWidth() / 2, 20, paint1);
			canvas.drawText("0-19", getWidth() / 5 * 4, getHeight() / 4 * 1,
					paint1);
			canvas.drawText("20-29", getWidth() / 5 * 4, getHeight() / 4 * 2,
					paint1);
			canvas.drawText("30-39", getWidth() / 5 * 4, getHeight() / 4 * 3,
					paint1);
			// canvas.drawRect(left, top, right, bottom, paint);
			paint1.setColor(Color.BLUE);
			canvas.drawRect(getWidth() / 5 * 4 - 70, getHeight() / 4 * 1 - 10,
					getWidth() / 5 * 4 - 50, getHeight() / 4 * 1 + 10, paint1);
			paint1.setColor(Color.RED);
			canvas.drawRect(getWidth() / 5 * 4 - 70, getHeight() / 4 * 2 - 10,
					getWidth() / 5 * 4 - 50, getHeight() / 4 * 2 + 10, paint1);
			paint1.setColor(Color.GREEN);
			canvas.drawRect(getWidth() / 5 * 4 - 70, getHeight() / 4 * 3 - 10,
					getWidth() / 5 * 4 - 50, getHeight() / 4 * 3 + 10, paint1);
			paint1.setColor(Color.BLACK);
		}
		canvas.translate(getWidth() / 2, getHeight() / 2);
		RectF rectF = new RectF(-80, -80, 80, 80);
		double startAngle = 0;
		double sweepAngle = 0;
		double num = 0;
		for (int i = 0; i < lists.length; i++) {
			num += lists[i];
		}
		for (int i = 0; i < lists.length; i++) {
			paint.setColor(colors[i]);
			sweepAngle = lists[i] / num * 360;
			canvas.drawArc(rectF, (float) startAngle, (float) sweepAngle,
					false, paint);
			canvas.drawText((int) (lists[i] / num * 100) + "%", (float) Math
					.cos(Math.toRadians(startAngle + sweepAngle / 2)) * 80,
					(float) Math.sin(Math
							.toRadians(startAngle + sweepAngle / 2)) * 80,
					paint1);
			startAngle += sweepAngle;
		}
	}
}
