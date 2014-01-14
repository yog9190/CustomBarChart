package com.example.custombarchart;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SampleActivity extends Activity {
	boolean flag = true;
	//Values to be displayed on y axis of the chart
	String[] yAxisMeasurs = { "bad", "good", "better", "best", "excel" };
	
	//values for bar widths in pixel
	int[] pointsX = { 70, 100, 60, 70, 50, 50, 60, 50, 70, 55 };
	
	//values for bar heights as an index of bars unit in yAxisMeasurs
	//3=better
	//2=good
	//5=excel and so on
	int[] pointsY = { 3, 2, 5, 3, 4, 3, 2, 4, 5, 2 };
							
	//values for bars colors		
	int[] colors = { R.drawable.c1, R.drawable.c2, R.drawable.c3,
			R.drawable.c4, R.drawable.c5, R.drawable.c4, R.drawable.c2,
			R.drawable.c1, R.drawable.c3, R.drawable.c5 };
	
	
	//Different values for another set of bars 
	int[] pointsXX = { 70, 70, 70, 70, 70, 70, 70, 70, 70, 70 };
	int[] pointsYY = { 3, 2, 3, 4, 1, 3, 5, 4, 1, 2 };
	String[] colorsS = { "#bbc0362c", "#bb6A6A02", "#bb2262f3", "#bb660693", "#bb006233",
			"#bbc0362c", "#bb6A6A02", "#bb2262f3", "#bb660693", "#bb006233" };

	
	BarChart objBarChart;
	ArrayList<Bar> listOfBars = new ArrayList<Bar>();
	ArrayList<Bar> listOfBars2 = new ArrayList<Bar>();
	Button b;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sample);
		objBarChart = (BarChart) findViewById(R.id.barChart1);
		//objBarChart.setBackgroundColor(Color.parseColor("#ffffC0C0"));
		objBarChart.setBackgroundResource(R.drawable.c6);
		
		//Creating two different lists of bars.
		for (int k = 0; k < pointsX.length; k++) {
			int j = k;
			
			Bar b = new Bar(this, pointsX[j], pointsY[j]);
			Bar.SubBar subBar1 = b.new SubBar(40f);
			Bar.SubBar subBar2 = b.new SubBar(120f);
			subBar1.setBarResourceDrawable((colors[j]));
			b.addSubBar(subBar1);
			subBar2.setBarResourceDrawable((colors[(colors.length - 1) - j]));
			b.addSubBar(subBar2);
			b.setTitle("Bar"+k);
			listOfBars.add(b);

			b = new Bar(this, pointsXX[j], pointsYY[j]);
			subBar1 = b.new SubBar(40f);
			subBar2 = b.new SubBar(120f);
			subBar1.setBarColor(Color.parseColor(colorsS[j]));
			b.addSubBar(subBar1);
			subBar2.setBarColor(Color.parseColor(colorsS[(colors.length - 1)- j]));
			b.addSubBar(subBar2);			
			b.setTitle("Bar"+k);			
			listOfBars2.add(b);
		}
		
		//initializing BarChart
		objBarChart.initializeChart(yAxisMeasurs, listOfBars);

		b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					objBarChart.updateChart(listOfBars2);
					flag = false;
				} else {
					objBarChart.updateChart(listOfBars);
					flag = true;
				}
			}
		});		
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
