package com.example.smarthome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private double[] lists = new double[3];
	private int[] colors = new int[] { Color.BLUE, Color.RED, Color.GREEN };
	private Paint paint = new Paint();
	private Paint paint1 = new Paint();

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setAntiAlias(true);
		paint1.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(60);
		setLists(33.d, 40.d, 80d);
	}

	public void setLists(double blue, double red, double green) {
		lists[0] = blue;
		lists[1] = red;
		lists[2] = green;
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(getWidth() / 2, getHeight() / 2);
		RectF rectF = new RectF();
		rectF.top = -100;
		rectF.bottom = 100;
		rectF.left = -100;
		rectF.right = 100;
		double num = 0;
		double startAngle = 0;
		double sweepAngle = 0;
		for (int i = 0; i < lists.length; i++) {
			num += lists[i];
		}
		for (int i = 0; i < lists.length; i++) {
			paint.setColor(colors[i]);
			sweepAngle = lists[i] / num * 360;
			canvas.drawArc(rectF, (float) startAngle, (float) sweepAngle,
					false, paint);
			canvas.drawText((int) (lists[i] / num * 100) + "%", (float) Math
					.cos(Math.toRadians(startAngle + sweepAngle / 2)) * 100,
					(float) Math.sin(Math
							.toRadians(startAngle + sweepAngle / 2)) * 100,
					paint1);
			startAngle += sweepAngle;
		}
	}
}
