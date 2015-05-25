package com.cml.product.home.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.cml.product.home.R;

public class PushShowView implements OnClickListener {

	private static final String TAG = PushShowView.class.getSimpleName();

	private Context context;
	private View itemContainerView;
	private Animator showAnimator;

	public PushShowView(Context context) {
		super();
		this.context = context;
	}

	public View getView() {

		LayoutInflater infalter = LayoutInflater.from(context);
		View view = infalter.inflate(R.layout.view_push_show, null);

		View clickRootView = view.findViewById(R.id.pushview_root);
		clickRootView.setOnClickListener(this);

		itemContainerView = view.findViewById(R.id.pushview_container);

		return view;
	}

	private Animator getShowAnimator(float start, float end, Object target) {
		ObjectAnimator animator = (ObjectAnimator) AnimatorInflater
				.loadAnimator(context, R.animator.pushview_hide);
		animator.setFloatValues(start, end);
		animator.setTarget(target);
		return animator;
	}

	@Override
	public void onClick(View v) {

		if (null == showAnimator) {
			// …Ë÷√∂Øª≠
			showAnimator = this.getShowAnimator(v.getMeasuredHeight(),
					itemContainerView.getMeasuredHeight(), itemContainerView);
		}

		Log.i(TAG,
				"start height:" + v.getMeasuredHeight() + "," + v.getHeight()
						+ "," + v.getY());

		showAnimator.start();

		if (itemContainerView.getVisibility() == View.VISIBLE) {
			itemContainerView.setVisibility(View.GONE);
		} else {
			itemContainerView.setVisibility(View.VISIBLE);
		}
	}
}
