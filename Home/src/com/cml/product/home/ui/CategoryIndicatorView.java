package com.cml.product.home.ui;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cml.product.home.R;

public class CategoryIndicatorView extends ViewGroup implements
		OnTouchListener, OnClickListener, OnLongClickListener {

	private static final String TAG = CategoryIndicatorView.class
			.getSimpleName();
	private static final int ITEM_PADDING = 30;
	public static final int FLYING_DISTANCE = 50;

	private List<Indicator> data;
	private IndicatorDirection direction;

	private List<View> leftViewGroup = new ArrayList<View>();
	private List<View> rightViewGroup = new ArrayList<View>();

	// 整个菜单栏宽度
	private int viewWith;
	// 整个菜单栏高度
	private int viewHeight;

	// 子元素的宽度
	private int itemWitdh;
	// 菜单栏露在外面的宽度
	private int itemIndicatorWidth = 20;

	// 是否可以滑动切换菜单
	private boolean flyable = false;

	// 手势
	private GestureDetector dector;

	// 点击事件
	private OnItemClickListener itemClickListener;
	private OnItemLongClickListener itemLongClickListener;

	public CategoryIndicatorView(Context context, List<Indicator> data,
			IndicatorDirection direction) {
		super(context);
		this.data = data;
		this.direction = direction;
		// 添加子元素
		initItems(context);
		dector = new GestureDetector(context, new GestureDectorListener());
		setOnTouchListener(this);
	}

	public CategoryIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		dector = new GestureDetector(context, new GestureDectorListener());
		setOnTouchListener(this);
	}

	private final class FlyableAnimatorListener implements AnimatorListener {

		@Override
		public void onAnimationStart(Animator animation) {
			flyable = false;
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			flyable = true;
		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

	}

	private final class GestureDectorListener implements OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			float distance = e2.getX() - e1.getX();

			if (flyable && Math.abs(distance) >= FLYING_DISTANCE) {

				// 左往右滑动
				if (distance > 0) {
					onFlyingAnimator(IndicatorDirection.RIGHT);
				} else {
					// 右往左活动
					onFlyingAnimator(IndicatorDirection.LEFT);
				}
			}

			return true;
		}

		private void onFlyingAnimator(IndicatorDirection direction) {

			AnimatorSet set = new AnimatorSet();

			List<Animator> animators = new ArrayList<Animator>();

			// 左边菜单
			for (View v : leftViewGroup) {

				ObjectAnimator anim = (ObjectAnimator) AnimatorInflater
						.loadAnimator(getContext(),
								R.animator.indicator_animator);

				if (direction == IndicatorDirection.LEFT) {
					anim.setFloatValues(-v.getWidth() + itemIndicatorWidth);
				} else {
					anim.setFloatValues(0);
				}
				anim.setTarget(v);
				animators.add(anim);
			}

			// 右边菜单
			for (View v : rightViewGroup) {

				ObjectAnimator anim = (ObjectAnimator) AnimatorInflater
						.loadAnimator(getContext(),
								R.animator.indicator_animator);

				if (direction == IndicatorDirection.LEFT) {
					anim.setFloatValues(viewWith - v.getWidth());
				} else {
					anim.setFloatValues(viewWith - itemIndicatorWidth);
				}
				anim.setTarget(v);
				animators.add(anim);
			}

			// set.addListener(new FlyableAnimatorListener());

			set.playSequentially(animators);
			// set.playTogether(animators);
			set.start();
		}

	}

	/**
	 * 隐藏菜单栏
	 */
	public void hideIndicators() {

		AnimatorSet set = new AnimatorSet();

		List<Animator> animators = new ArrayList<Animator>();

		// 隐藏左边菜单
		for (View v : leftViewGroup) {
			ObjectAnimator anim = (ObjectAnimator) AnimatorInflater
					.loadAnimator(getContext(), R.animator.indicator_animator);
			anim.setTarget(v);
			anim.setFloatValues(-v.getWidth() + itemIndicatorWidth);
			animators.add(anim);
		}

		// 隐藏右边
		for (View v : rightViewGroup) {

			ObjectAnimator anim = (ObjectAnimator) AnimatorInflater
					.loadAnimator(getContext(), R.animator.indicator_animator);
			anim.setTarget(v);
			anim.setFloatValues(viewWith - itemIndicatorWidth);
			animators.add(anim);
		}

		set.addListener(new FlyableAnimatorListener());
		set.playSequentially(animators);
		set.start();
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	private void initItems(Context context) {

		for (Indicator indicator : data) {

			View item = inflate(context, R.layout.view_category_indicator_item,
					null);

			TextView txtView = (TextView) item.findViewById(R.id.indicator_txt);

			txtView.setText(indicator.title);

			item.setOnClickListener(this);
			item.setOnLongClickListener(this);
			item.setTag(indicator);

			this.addView(item);
		}

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		Log.i(TAG, "onlayout:" + changed + ",l:" + l + ",t:" + t + ",r:" + r
				+ ",b:" + b);

		if (changed) {
			return;
		}

		viewWith = getMeasuredWidth();
		viewHeight = getMeasuredHeight();

		int childCount = getChildCount();

		if (childCount == 0) {
			return;
		}

		View firstChild = getChildAt(0);

		int height = firstChild.getMeasuredHeight();
		int width = firstChild.getMeasuredWidth();

		itemWitdh = width;

		// 计算容器的高度，和最大能容数量
		int maxCount = b / (height + ITEM_PADDING);

		int len = Math.min(maxCount, childCount);

		int y = ITEM_PADDING;

		for (int i = 0; i < len; i++) {

			View child = getChildAt(i);

			if (direction == IndicatorDirection.LEFT) {
				child.layout(0, y, width, y + height);
				leftViewGroup.add(child);
			} else {
				child.layout(getMeasuredWidth() - width, y, getMeasuredWidth(),
						y + height);
				rightViewGroup.add(child);
			}

			y += ITEM_PADDING + height;

		}

		// 多出的部分切换到对面上
		if (childCount > len) {

			y -= ITEM_PADDING + height;

			for (int i = len; i < childCount; i++) {

				View child = getChildAt(i);

				TextView tx = (TextView) child.findViewById(R.id.indicator_txt);
				tx.setText("后面》》");
				if (direction == IndicatorDirection.LEFT) {
					child.layout(getMeasuredWidth() - width, y,
							getMeasuredWidth(), y + height);
					rightViewGroup.add(child);
				} else {
					child.layout(0, y, width, y + height);
					leftViewGroup.add(child);
				}

				y -= (height + ITEM_PADDING);
			}
		}

		Log.d(TAG,
				"左边元素量：" + leftViewGroup.size() + ",右边："
						+ rightViewGroup.size());

		hideIndicators();
	}

	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}

	}

	@Override
	public boolean onLongClick(View v) {

		if (null != itemLongClickListener) {

			Indicator data = (Indicator) v.getTag();

			if (null != data) {
				return itemLongClickListener.onLongClick(v, data.title,
						data.type);
			}

		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if (null != itemClickListener) {
			Indicator data = (Indicator) v.getTag();

			if (null != data) {
				itemClickListener.onClick(v, data.title, data.type);
				hideIndicators();
			}
		}
	}

	public void setItemClickListener(OnItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public void setItemLongClickListener(
			OnItemLongClickListener itemLongClickListener) {
		this.itemLongClickListener = itemLongClickListener;
	}

	public List<Indicator> getData() {
		return data;
	}

	public void setData(List<Indicator> data) {
		this.data = data;
		this.initItems(getContext());
	}

	public IndicatorDirection getDirection() {
		return direction;
	}

	public void setDirection(IndicatorDirection direction) {
		this.direction = direction;
	}

	public static enum IndicatorDirection {
		LEFT, RIGHT
	}

	public static class Indicator {
		public String title;
		public Integer type;// 分类
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return dector.onTouchEvent(event);
	}

	public static interface OnItemClickListener {
		public void onClick(View v, String title, Integer type);
	}

	public static interface OnItemLongClickListener {
		/**
		 * 
		 * @param v
		 * @param title
		 * @param type
		 * @return true 事件被处理了，不需要冒泡，false 未处理
		 */
		public boolean onLongClick(View v, String title, Integer type);
	}

}
