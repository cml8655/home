package com.cml.product.home.anim;

import java.util.List;

import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.View;

/**
 * 系统动画效果
 * 
 * @author teamlab
 *
 */
public abstract class BaseAnimator {

	protected Context context;
	protected Integer duration;

	public BaseAnimator(Context context, Integer duration) {
		this.context = context;
		this.duration = duration;
	}

	public BaseAnimator(Context context) {
		this.context = context;
	}

	/**
	 * 启动动画
	 */
	public abstract void start(View v);

	/**
	 * 启动动画
	 */
	public abstract void start(View v, AnimatorListener listener);

	public abstract void start(List<View> views);

	public abstract void start(List<View> views, AnimatorListener listener);

	public static enum Animations {

		SHAKE("shake", ShakeAnimator.class);

		private String key;
		private Class<? extends BaseAnimator> anim;

		private Animations(String key, Class<? extends BaseAnimator> anim) {
			this.key = key;
			this.anim = anim;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public BaseAnimator getAnim() {
			try {
				return anim.newInstance();
			} catch (Exception e) {
			}
			return null;
		}

		public void setAnim(Class<? extends BaseAnimator> anim) {
			this.anim = anim;
		}

	}

}
