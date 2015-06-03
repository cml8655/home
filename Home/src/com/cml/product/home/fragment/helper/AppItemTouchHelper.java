package com.cml.product.home.fragment.helper;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cml.product.home.anim.BaseAnimator;
import com.cml.product.home.anim.ShakeAnimator;
import com.cml.product.home.model.AppModel;
import com.cml.product.home.ui.CategoryItemView;
import com.cml.product.home.util.ToastUtil;

/**
 * app 触摸helper，包含点击，长按等事件
 * 
 * @author teamlab
 *
 */
public class AppItemTouchHelper implements CategoryItemView.OnItemTouchListener {

	private Context context;
	private BaseAnimator clickAnimator;

	public AppItemTouchHelper(Context context) {
		this.context = context;
		clickAnimator = new ShakeAnimator(context);
	}

	@Override
	public void onClick(View v, final AppModel data) {

		clickAnimator.start(v, new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Intent intent = new Intent();
				ComponentName cm = new ComponentName(data.getPackageName(),
						data.getActivityName());
				intent.setComponent(cm);
				context.startActivity(intent);
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});

	}

	@Override
	public boolean onLongClick(View v, AppModel data) {
		ToastUtil.show(context, "hhh onLongClick:" + data.getAppName());
		return false;
	}

}
