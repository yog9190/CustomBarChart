package com.example.custombarchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class GraphBackground extends View {
	int noCol = 2, noRows = 1;
	int cellWidth = 40, cellHieght = 20;
	int base = 20;
	int width, hieght;
	Context ctx;
	Paint paint;

	public GraphBackground(Context context) {
		super(context);
		ctx = context;
		init();
	}

	public GraphBackground(Context context, AttributeSet attrs) {
		super(context, attrs);
		ctx = context;
		init();
	}

	public GraphBackground(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ctx = context;
		init();
	}

	public void setCellHeight(int c) {
		cellHieght=c;
		noRows = (hieght-40 / cellHieght)+2;
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = w;
		hieght = h - 01;
		noCol = (width / cellWidth)+1;
		noRows = (hieght / cellHieght)+2;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void init() {		
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.parseColor("#892358"));
		paint.setStrokeWidth(1);
		paint.setStyle(Paint.Style.STROKE);
		int pointx = 0, pointy = hieght;
		int cwidth = cellWidth, cheight = cellHieght;
		for (int i = 1; i <= noRows; i++) {
			if (i < 3) {
				cwidth = cellWidth;
				cheight = base;
				paint.setColor(Color.parseColor("#00892358"));
				pointy = hieght - ((i) * cheight);
			} else {
				cwidth = cellWidth;
				cheight = cellHieght;
				paint.setColor(Color.parseColor("#892358"));
				pointy = hieght - (((i-2) * cheight)+((2) * base));
			}			
			
			for (int j = 1; j <= noCol; j++) {
				pointx = (j - 1) * cwidth;
				if (j == noCol)
					canvas.drawRect(pointx, pointy, pointx + cwidth - 1,
							pointy + cheight, paint);
				else
					canvas.drawRect(pointx, pointy, pointx + cwidth, pointy
							+ cheight, paint);
				}
		}
		super.onDraw(canvas);
	}
}