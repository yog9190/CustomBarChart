package com.example.custombarchart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <b>LinearLayout<br/>
 *        └Bar<br/>
 * </b>
 * 
 * Bar class is the view that represents a bar in {@link BarChart}. 
 * @author Yogesh Pangam
 * */
public class Bar extends LinearLayout {
	private LayoutInflater inflater;
	ViewTreeObserver observer;
	final int barHieght, barWidth;
	LinearLayout rootBar;
	Context context;
	int cnt = 1;
	int max = 0;
	TextView caption = null;
	int unit = 0;
	String title="Title";
	/**
	 * <b>public Bar(Context context, int width, int hieght)</b><br/>
	 * Constructor creating a Bar object.
	 * @param context :Current context
	 * @param width :width in pixel
	 * @param height : height as an index of bar's height unit in the array {@link BarChart#initializeChart(Object[], java.util.ArrayList)}yAxisMeasure of the {@link BarChart}   
	 */
	public Bar(Context context, int width, int height) {
		super(context, null);
		this.context = context;
		this.barHieght = height;
		this.barWidth = width;

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(barWidth,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(lp);
		this.setOrientation(LinearLayout.VERTICAL);
		this.setGravity(Gravity.BOTTOM);

		inflater = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.bar, this);
		rootBar = (LinearLayout) findViewById(R.id.root);
	}

	/**
	 * <b>public void setPixelPerUnitHeight(int pixelPerUnit)</b><br/>
	 * sets number of pixel per height unit.	
	 */
	public void setPixelPerUnitHeight(int pixelPerUnit) {
		this.unit = pixelPerUnit;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rootBar
				.getLayoutParams();
		lp.height = barHieght * pixelPerUnit;
		rootBar.setLayoutParams(lp);
	}

	/**
	 * <b>public void addSubBar(SubBar objSubBar)</b><br/>
	 * Adds subbar to the bar.	
	 */
	public void addSubBar(SubBar objSubBar) {
		rootBar.addView(objSubBar.subBar, 0);
	}
	

	/**
	 * <b>public void setTitle(String title)</b><br/>
	 * sets title of the bar.
	 */
	public void setTitle(String title){
		this.title=title;
	}

	/**
	 *SubBar class  represents a SubBar in {@link Bar}. 
	 */
	class SubBar {
		private View subBar;
		
		/**
		 * <b>public SubBar(float hieght) </b><br/>
		 * Constructor creating a Bar object.		 
		 * @param height : height as an percentage/weight of the space it occupies from its parent Bar    
		 */
		public SubBar(float hieght) {
			subBar = new View(Bar.this.getContext());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, hieght);
			lp.setMargins(3, 0, 3, 0);
			subBar.setLayoutParams(lp);
		}

		/**
		 * <b>public void setBarColor(int color) </b><br/>
		    sets color of the SubBar
		 */
		public void setBarColor(int color) {
			subBar.setBackgroundColor(color);
		}

		/**
		 * <b>public void setBarDrawable(Drawable background) </b><br/>
		    sets Drawable of the SubBar
		 */
		public void setBarDrawable(Drawable background) {
			subBar.setBackgroundDrawable(background);
		}
		/**
		 * <b>public void setBarResourceDrawable(int resid) </b><br/>
		    sets ResourceDrawable of the SubBar
		 */
		public void setBarResourceDrawable(int resid) {
			subBar.setBackgroundResource(resid);
		}
		
	}
}