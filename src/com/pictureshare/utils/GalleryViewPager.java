package com.pictureshare.utils;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Toast;

public class GalleryViewPager extends ViewPager implements OnGestureListener {
	/** the last x position */
	private float lastX;

	/**
	 * if the first swipe was from left to right (->), dont listen to swipes
	 * from the right
	 */
	private boolean slidingLeft;

	/**
	 * if the first swipe was from right to left (<-), dont listen to swipes
	 * from the left
	 */
	private boolean slidingRight;
	private GestureDetector df;

	public GalleryViewPager(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		df = new GestureDetector(getContext(), this);
	}

	public GalleryViewPager(final Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(final MotionEvent arg0) {
		//
		curP.x = arg0.getX();
		curP.y = arg0.getY();
		final int action = arg0.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 记录按下时候的坐标
			// 切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
			downP.x = arg0.getX();
			downP.y = arg0.getY();

			// Disallow parent ViewPager to intercept touch events.
			this.getParent().requestDisallowInterceptTouchEvent(true);
			// save the current x position
			this.lastX = arg0.getX();
			break;

		case MotionEvent.ACTION_UP:
			// Allow parent ViewPager to intercept touch events.
			this.getParent().requestDisallowInterceptTouchEvent(false);
			// 在up时判断是否按下和松手的坐标为一个点
			// 如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick

			// save the current x position
			this.lastX = arg0.getX();

			// reset swipe actions
			this.slidingLeft = false;
			this.slidingRight = false;
			int downx = ((Float) downP.x).intValue();
			int downy = ((Float) downP.y).intValue();
			int curx = ((Float) curP.x).intValue();
			int cury = ((Float) curP.y).intValue();
			System.out.println("downx=" + downx);
			System.out.println("curx=" + curx);
			System.out.println("downy=" + downy);
			System.out.println("cury=" + cury);
			// if (downx == curx && downy == cury) {
			// onSingleTouch();
			// // return true;
			// }
			// if (downP.x == curP.x && downP.y == curP.y) {
			// onSingleTouch();
			// return true;
			// }
			boolean x = false;
			if (downx == curx || downx >= curx + 1 || downx < curx - 1)
				x = true;
			boolean y = false;
			if (downy == cury || downy >= cury + 1 || downy < cury - 1)
				y = true;
			if (x && y) {
				onSingleTouch();
			}

			break;

		case MotionEvent.ACTION_MOVE:
			/*
			 * if this is the first item, scrolling from left to right should
			 * navigate in the surrounding ViewPager
			 */
			if (this.getCurrentItem() == 0) {
				// swiping from left to right (->)?
				if (this.lastX <= arg0.getX() && !this.slidingRight) {
					// make the parent touch interception active -> parent pager
					// can swipe
					this.getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					/*
					 * if the first swipe was from right to left, dont listen to
					 * swipes from left to right. this fixes glitches where the
					 * user first swipes right, then left and the scrolling
					 * state gets reset
					 */
					this.slidingRight = true;

					// save the current x position
					this.lastX = arg0.getX();
					this.getParent().requestDisallowInterceptTouchEvent(true);
				}
			} else
			/*
			 * if this is the last item, scrolling from right to left should
			 * navigate in the surrounding ViewPager
			 */
			if (this.getCurrentItem() == this.getAdapter().getCount() - 1) {
				// swiping from right to left (<-)?
				if (this.lastX >= arg0.getX() && !this.slidingLeft) {
					// make the parent touch interception active -> parent pager
					// can swipe
					this.getParent().requestDisallowInterceptTouchEvent(false);
				} else {
					/*
					 * if the first swipe was from left to right, dont listen to
					 * swipes from right to left. this fixes glitches where the
					 * user first swipes left, then right and the scrolling
					 * state gets reset
					 */
					this.slidingLeft = true;

					// save the current x position
					this.lastX = arg0.getX();
					this.getParent().requestDisallowInterceptTouchEvent(true);
				}
			}

			break;
		}

		// super.onTouchEvent(arg0);
		return super.onTouchEvent(arg0);
		// return df.onTouchEvent(arg0);
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent arg0) {
	// // TODO Auto-generated method stub
	// // 当拦截触摸事件到达此位置的时候，返回true，
	// // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
	// return true;
	// }

	/** 触摸时按下的点 **/
	PointF downP = new PointF();
	/** 触摸时当前的点 **/
	PointF curP = new PointF();
	private OnSingleTouchListener onSingleTouchListener;

	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}

	/**
	 * 单击
	 */
	public void onSingleTouch() {
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch();
		}
	}

	/**
	 * 创建点击事件接口
	 * 
	 * @author wanpg
	 * 
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch();
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onDown", 0).show();
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onShowPress", 0).show();
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onSingleTapUp", 0).show();
		onSingleTouch();
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onScroll", 0).show();
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onLongPress", 0).show();
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Toast.makeText(getContext(), "onFling", 0).show();
		return false;
	}

}
