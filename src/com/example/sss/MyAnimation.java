package com.example.sss;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;

public class MyAnimation {
	// �붯��
	public static void flyIn(ViewGroup group, int duration) {

		Animation animation;
		/**
		 * ��ת���� RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue,
		 * pivotYType, pivotYValue) fromDegrees ��ʼ��ת�Ƕ� toDegrees ��ת���ĽǶ�
		 * pivotXType X�� ������ pivotXValue x�� ��ת�Ĳο��� pivotYType Y�� ������ pivotYValue
		 * Y�� ��ת�Ĳο���
		 */
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true); // ������ɽ����ֵ�ǰ״̬
		animation.setDuration(duration); // ���ö�������ʱ��
		group.startAnimation(animation);
		group.setVisibility(View.VISIBLE);
	}

	// ������
	public static void flyOut(final ViewGroup viewGroup, int duration,
			int startOffSet) {
		Animation animation;
		animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true);// ͣ���ڶ�������λ��
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
