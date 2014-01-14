package com.example.custombarchart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class MyScrollView extends HorizontalScrollView {

	CustomZoomImgView imgView;
	int mwidth, mhiegth;
	final int SCROLL = 1, ZOOM = 2;

	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public void setImageView(CustomZoomImgView imgView) {
		this.imgView = imgView;
		mwidth = imgView.getWidth();
		mhiegth = imgView.getHeight();
		imgView.setBackgroundColor(Color.parseColor("#00000000"));
	}

	int mode = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.d("trace", "ontouchScrollview");
		boolean flag=false;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = SCROLL;
			flag=super.onTouchEvent(event);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			flag=imgView.dispatchTouchEvent(event);
			mode = ZOOM;
			break;
		case MotionEvent.ACTION_UP:
			flag=super.onTouchEvent(event);
			mode = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			flag=super.onTouchEvent(event);
			mode = SCROLL;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == SCROLL) {
				
				flag=super.onTouchEvent(event);
			} else if (mode == ZOOM) {
				
				flag=imgView.dispatchTouchEvent(event);
			}
			break;
		default:
			flag=super.onTouchEvent(event);
		}
		return flag;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	//	Log.d("trace", "DispatchScrollview ");
		boolean flag = super.dispatchTouchEvent(ev);
		return flag;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	//	Log.d("traceAPI", "Intercept");
		return true;
	}
}
