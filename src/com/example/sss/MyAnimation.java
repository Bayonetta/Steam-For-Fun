package com.example.sss;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;

public class MyAnimation {
	// 入动画
	public static void flyIn(ViewGroup group, int duration) {

		Animation animation;
		/**
		 * 旋转动画 RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue,
		 * pivotYType, pivotYValue) fromDegrees 开始旋转角度 toDegrees 旋转到的角度
		 * pivotXType X轴 参照物 pivotXValue x轴 旋转的参考点 pivotYType Y轴 参照物 pivotYValue
		 * Y轴 旋转的参考点
		 */
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true); // 动画完成将保持当前状态
		animation.setDuration(duration); // 设置动画持续时间
		group.startAnimation(animation);
		group.setVisibility(View.VISIBLE);
	}

	// 出动画
	public static void flyOut(final ViewGroup viewGroup, int duration,
			int startOffSet) {
		Animation animation;
		animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true);// 停留在动画结束位置
		animation.setDuration(duration);
		animation.setStartOffset(startOffSet);
		animation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			public void onAnimationEnd(Animation animation) {
				viewGroup.setVisibility(View.INVISIBLE);

			}
		});

		viewGroup.startAnimation(animation);
	}
}
