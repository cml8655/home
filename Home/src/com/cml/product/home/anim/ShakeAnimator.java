package com.cml.product.home.anim;

import java.util.List;

import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ShakeAnimator extends BaseAnimator {

	public static final String PROPERTY_NAME = "translationX";
	public static final Integer DURATION = 1000;
	public static final float[] values = new float[] { -10, 10 };

	public ShakeAnimator(Context context) {
		super(context);
		duration = DURATION;
	}

	public ShakeAnimator(Context context, Integer duration) {
		super(context, duration);
	}

	@Override
	public void start(View v) {
		this.start(v, null);
	}

	@Override
	public void start(View v, AnimatorListener listener) {
		ObjectAnimator animator = new ObjectAnimator();
		animator.addListener(listener);
		animator.setTarget(v);
		animator.setFloatValues(values);
		animator.setDuration(duration);
		animator.setInterpolator(new LinearInterpolator());
		animator.setPropertyName(PROPERTY_NAME);
		animator.setRepeatCount(6);
		animator.start();

	}

	@Override
	public void start(List<View> views) {
		this.start(views, null);
	}

	@Override
	public void start(List<View> views, AnimatorListener listener) {
		for (View v : views) {
			this.start(v, listener);
		}

	}

}
