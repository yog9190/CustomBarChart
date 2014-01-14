
/**
 * Copyright 2014 
 * 
 * Yogesh Pangam  
 *  
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this BarChart software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.custombarchart;



import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.custombarchart.CustomZoomImgView.PivotPoint;
import com.example.custombarchart.CustomZoomImgView.ZoomStyle;

/**
 * <b>RelativeLayout<br/>
 * └BarChart<br/>
 * </b>
 * 
 * BarChart class is the view that represents a barchart.
 * 
 * @author Yogesh Pangam
 * */
@SuppressLint("NewApi")
public class BarChart extends RelativeLayout {
	private Context context;
	private LayoutInflater inflater;
	private ViewTreeObserver observer, chartObeserver;
	private GraphBackground objGraphBackground;
	private LinearLayout linearChart, llYAxis, llCaptions, llContainer;
	private HorizontalScrollView hsc;
	private CustomZoomImgView img;
	private MyScrollView objMyScrollView;
	private Object[] yAxisMeasurs;
	private int max = 0, unit = 0;
	private ArrayList<Bar> listOfBars;	

	public BarChart(Context context) {
		this(context, null, 0);
	}

	public BarChart(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BarChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.bar_chart, this);

		linearChart = (LinearLayout) findViewById(R.id.linearChart);
		hsc = (HorizontalScrollView) findViewById(R.id.hsc);
		llYAxis = (LinearLayout) findViewById(R.id.llYaxis);
		llCaptions = (LinearLayout) findViewById(R.id.llCaptions);
		llContainer = (LinearLayout) findViewById(R.id.llContainer);
		objGraphBackground = (GraphBackground) findViewById(R.id.graphBackground1);
		objMyScrollView = (MyScrollView) findViewById(R.id.horizontalScrollView1);
	}

	@SuppressLint("NewApi")
	private void drawChart() {
		// adding bars and their titles to the chart
		for (Bar b : listOfBars) {

			b.setPixelPerUnitHeight(unit);

			TextView caption = new TextView(context);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 40);
			lp.gravity = Gravity.CENTER | Gravity.TOP;
			lp.weight = b.barWidth;
			caption.setLayoutParams(lp);
			caption.setText(b.title);
			caption.setGravity(Gravity.CENTER | Gravity.TOP);
			caption.setBackgroundColor(Color.parseColor("#00000000"));

			ViewGroup v = ((ViewGroup) b.getParent());
			if (v != null) {
				v.removeView(b);
				Log.d("app", "ViewgroupRemoved");
			}
			linearChart.addView(b);
			llCaptions.addView(caption);
		}

		// adding extra invisible bar and empty title to the chart
		Bar b = new Bar(context, 20, 1);
		Bar.SubBar subBar1 = b.new SubBar(1f);
		subBar1.setBarColor(Color.parseColor("#00FF0000"));
		b.addSubBar(subBar1);
		b.setPixelPerUnitHeight(unit);
		ViewGroup v = ((ViewGroup) b.getParent());
		if (v != null) {
			v.removeView(b);
			Log.d("app", "lastViewgroupRemoved");
		}
		linearChart.addView(b);

		TextView caption = new TextView(context);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 40);
		lp.gravity = Gravity.CENTER | Gravity.TOP;
		lp.weight = 20;
		caption.setLayoutParams(lp);
		caption.setText("");
		caption.setGravity(Gravity.CENTER | Gravity.TOP);
		caption.setBackgroundColor(Color.parseColor("#00000000"));

		llCaptions.addView(caption);

		linearChart.invalidate();

		chartObeserver = linearChart.getViewTreeObserver();		
		chartObeserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				if (chartObeserver.isAlive()) {
					// capturing the image of the chart

					Log.d("traceChart",
							"linearChartHieght" + linearChart.getHeight() + " ");
					llContainer.removeView(img);
					Bitmap bm = getBitmapFromView(linearChart,
							linearChart.getWidth(), linearChart.getHeight());
					img = new CustomZoomImgView(context, bm);
					img.setMinZoomLevel(10f);
					img.setZoomStyle(ZoomStyle.HORIZONTALONLY);
					img.setPivotPoint(PivotPoint.BOTTOMLEFT);
					img.isResizeBM = false;
					img.isScrollEnabled = false;

					// adding the image of the chart to the container
					llContainer.addView(img, 0);
					objMyScrollView.setImageView(img);

					// adding y axis measures
					TextView caption;
					LinearLayout.LayoutParams lp;
					for (int i = 0; i < yAxisMeasurs.length; i++) {
						caption = new TextView(context);
						lp = new LinearLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, unit);
						caption.setLayoutParams(lp);
						caption.setText(""
								+ yAxisMeasurs[(yAxisMeasurs.length - 1) - i]);
						caption.setGravity(Gravity.CENTER_VERTICAL
								| Gravity.RIGHT);
						if (unit < 25)
							caption.setTextSize(unit - 10);
						else
							caption.setTextSize(15);
						caption.setBackgroundColor(Color
								.parseColor("#00000000"));
						llYAxis.addView(caption);
					}

					// adding extra invisible y axis measure at the bottom
					caption = new TextView(context);

					lp = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, unit / 2);

					caption.setLayoutParams(lp);
					caption.setText("");
					caption.setGravity(Gravity.TOP | Gravity.RIGHT);
					caption.setBackgroundColor(Color.parseColor("#00000000"));
					llYAxis.addView(caption);

					linearChart.setVisibility(View.INVISIBLE);
					hsc.setVisibility(View.INVISIBLE);
					if (chartObeserver.isAlive()) {
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
							chartObeserver.removeOnGlobalLayoutListener(this);
							Log.d("traceBarCha", "ChartobserverRemoved");
						} else {
							chartObeserver.removeGlobalOnLayoutListener(this);
							Log.d("traceBarCha", "ChartobserverRemoved");
						}
					}

				}
			}

		});
	}

	private Bitmap getBitmapFromView(View view, int width, int height) {
		Bitmap returnedBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		view.draw(canvas);
		return returnedBitmap;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		observer = this.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				Log.d("traceObs", "BarChartHieght" + BarChart.this.getHeight()
						+ " ");
				max = BarChart.this.getHeight();
				max = max / 20;
				max = max * 20 - 60;
				unit = max / yAxisMeasurs.length;
				objGraphBackground.setCellHeight(unit);
				objGraphBackground.invalidate();
				drawChart();
				if (observer.isAlive()) {
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
						observer.removeOnGlobalLayoutListener(this);
						Log.d("traceObs", "observerRemoved");
					} else {
						observer.removeGlobalOnLayoutListener(this);
						Log.d("traceObs", "observerRemoved");
					}
				}
			}
		});
	}

	/**
	 * <b>public void initializeChart(Object[] yAxisMeasurs, ArrayList<Bar>
	 * listOfBars)</b><br/>
	 * initializes a bar chart with list of bars and array of measurs to be
	 * displayed on y axis. Must be called in on create method of the acivity.
	 * 
	 * @param yAxisMeasurs
	 *            :array of measurs to be displayed on y axis
	 * @param listOfBars
	 *            : list of bars
	 */
	public void initializeChart(Object[] yAxisMeasurs, ArrayList<Bar> listOfBars) {
		this.yAxisMeasurs = yAxisMeasurs;
		this.listOfBars = listOfBars;
	}

	/**
	 * <b>public void setBackgroundColor(int color)</b><br/>
	 * sets background color of the chart.
	 */
	@Override
	public void setBackgroundColor(int color) {
		objGraphBackground.setBackgroundColor(color);
		llYAxis.setBackgroundColor(color);
	}

	/**
	 * <b>public void updateChart(ArrayList<Bar> listOfBars)</b><br/>
	 * updates the chart with new list of bars.
	 */
	public void updateChart(ArrayList<Bar> listOfBars) {
		linearChart.removeAllViews();
		llCaptions.removeAllViews();
		this.listOfBars = listOfBars;
		drawChart();
	}

}
