package com.example.smarthome;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class ChartView extends View {
	private double[] values = new double[3];
	private String[] names = new String[] { "ÎÂ¶È", "ÑÌÎí", "Êª¶È" };
	private int size = 3;
	private double max = 100;
	private Paint paint = new Paint();
	private Drawable drawable;

	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		drawable = context.getResources().getDrawable(R.drawable.rectsss);
		setValues(33.0, 500, 899);
	}

	public void setValues(double temp, double smoke, double humidity) {
		values[0] = temp;
		values[1] = smoke;
		values[2] = humidity;
		double maxvalue = 0;
		for (int i = 0; i < values.length; i++) {
			if (maxvalue < values[i]) {
				maxvalue = values[i];
			}
		}
		if (maxvalue < 90) {
			max = 100;
		} else if (maxvalue < 900) {
			max = 1000;
		} else {
			max = 2000;
		}
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = getWidth() - 40;
		int height = getHeight() - 40;
		int step = (int) (width * 1.0 / (size + 1));
		canvas.drawLine(0, height, getWidth(), height, paint);
		Rect rect = new Rect();
		for (int i = 0; i < values.length; i++) {
			int x = 40 + step * (i + 1);
			int y = (int) (height - height / max * values[i]);
			rect.top = y;
			rect.bottom = height;
			rect.left = x - step / 6;
			rect.right = x + step / 6;
			drawable.setBounds(rect);
			drawable.draw(canvas);
			canvas.drawText(values[i] + "", x, y - 5, paint);
			canvas.drawText(names[i], x, height + 20, paint);
		}
	}
}
