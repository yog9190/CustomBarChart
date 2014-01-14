package com.example.custombarchart;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.custombarchart.CustomZoomImgView.PivotPoint;
import com.example.custombarchart.CustomZoomImgView.ZoomStyle;

public class MainActivity extends Activity {
	LinearLayout linearChart;
	
	HorizontalScrollView hsc;
	CustomZoomImgView img;
	MyScrollView objMyScrollView ;
	int[] points = { 50, 50, 50, 50, 50};
	String[] colors = { "#ff6233", "#40f233", "#2262f3", "#660693", "#006233" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		linearChart = (LinearLayout) findViewById(R.id.linearChart);	
		hsc=(HorizontalScrollView)findViewById(R.id.hsc);
		//linearChart.setVisibility(View.INVISIBLE);
		drawChart(25);	
		img = (CustomZoomImgView) findViewById(R.id.imageView1);
		img.setMinZoomLevel(25f);
		img.setZoomStyle(ZoomStyle.HORIZONTALONLY);
		img.setPivotPoint(PivotPoint.BOTTOMLEFT);
		objMyScrollView=(MyScrollView)findViewById(R.id.horizontalScrollView1);
		new Handler().postDelayed(new Runnable() {
	        public void run() {
	        	Log.d("trace","image set");
	        	img.setImageBitmap(getBitmapFromView(linearChart,linearChart.getWidth(),linearChart.getHeight()));
	        	objMyScrollView.setImageView(img);
	        	linearChart.setVisibility(View.GONE);
	        	hsc.setVisibility(View.GONE);
	        }
	    }, 1000);
	}

	@Override
	protected void onResume() {		
		super.onResume();		
	}

	public void drawChart(int count) {
		System.out.println(count);
		for (int k = 0; k < count; k++) {
			int j=k%5;
			Bar b = new Bar(this, points[j],200);		
			
		
			
			Bar.SubBar subBar1 = b.new SubBar(0.7f);			
			Bar.SubBar subBar2 = b.new SubBar(0.3f);	
			
			subBar1.setBarColor(Color.parseColor(colors[j]));		
			b.addSubBar(subBar1);	
			
			subBar2.setBarColor(Color.parseColor(colors[4-j]));
			b.addSubBar(subBar2);	
			
			linearChart.addView(b);
		}
		linearChart.invalidate();
	}

	public Bitmap getBitmapFromView(View view, int width, int height) {
		Bitmap returnedBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);		
		view.draw(canvas);
		return returnedBitmap;
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		boolean flag=super.dispatchTouchEvent(ev);
		
		return flag;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return super.onTouchEvent(event);
	}	
}
