package com.example.sss;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class panel extends View {
	private Bitmap myBitmap = Bitmap.createBitmap(MainActivity.scalX,
			MainActivity.scalY, Config.ARGB_8888);
	private static Canvas myCanvas;
	private Paint myPaint1, myPaint2;
	private ArrayList<Path> myPath = new ArrayList<Path>();
	private ArrayList<Float> myX = new ArrayList<Float>();
	private ArrayList<Float> myY = new ArrayList<Float>();
	private static final float TOUCH_TOLERANCE = 4;
	// public static Handler handler = new Handler();
	private float x[] = new float[5];
	private float y[] = new float[5];

	public panel(Context context) {
		super(context);
		setFocusable(true);
		Resources res = getResources(); // ��ȡ��Դ
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.steam); // ��ȡλͼ
		setUpBmp();
		setScreenWH(bmp);
	}

	public panel(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(myBitmap, 0, 0, null);
		if (myPath.size() != 0) {
			for (int i = 0; i < myPath.size(); i++) {
				myCanvas.drawPath(myPath.get(i), myPaint1);
				myCanvas.drawPath(myPath.get(i), myPaint2);
			}
		}
		super.onDraw(canvas);
	}

	private void setUpBmp() {
		myPaint1 = new Paint(); // ��������
		myPaint1.setAlpha(0);
		myPaint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); // ȡ������ƽ�������ʾ�²㡣
		myPaint1.setAntiAlias(true);
		myPaint1.setDither(true);
		myPaint1.setStyle(Paint.Style.STROKE); // ���û�������
		myPaint1.setStrokeJoin(Paint.Join.ROUND);
		myPaint1.setStrokeCap(Paint.Cap.ROUND); // ���û��ʱ�ΪԲ����
		myPaint1.setStrokeWidth(30);
		myPaint2 = new Paint(); // �����ڶ��㻭�ʣ�ģ������켣
		myPaint2.setAlpha(80);
		myPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		myPaint2.setAntiAlias(true);
		myPaint2.setDither(true);
		myPaint2.setStyle(Paint.Style.STROKE);
		myPaint2.setStrokeJoin(Paint.Join.ROUND);
		myPaint2.setStrokeCap(Paint.Cap.ROUND);
		myPaint2.setStrokeWidth(50);
	}

	private void setScreenWH(Bitmap bmp) {
		Matrix matrix = new Matrix();
		int width = bmp.getWidth();
		int height = bmp.getHeight(); // ��ȡλͼ�߶ȺͿ��
		float w1 = (float) MainActivity.scalX / bmp.getWidth();
		float h1 = (float) MainActivity.scalY / bmp.getHeight(); // ��ȡ���ű���
		matrix.postScale(w1, h1);
		bmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, true); // �������ű�����ȡ�µ��ϲ�λͼ
		Paint vPaint = new Paint();
		vPaint.setStyle(Paint.Style.STROKE); // ����
		vPaint.setAlpha(200); // Bitmap͸����(0 ~ 255)
		myCanvas = new Canvas();
		myCanvas.setBitmap(myBitmap);
		myCanvas.drawBitmap(bmp, 0, 0, vPaint);
	}

	private void touch_down(float x[], float y[], int n) {
		if (myPath.size() != 0) {
			for (int i = 0; i < n; i++) {
				myPath.get(i).reset();
				myPath.get(i).moveTo(x[i], y[i]); // ���ù켣���
			}
			for (int i = myX.size(); i < n; i++) {
				myX.add(x[i]);
				myY.add(y[i]);
			}
		} else {
			System.out.println("down0");
		}
	}

	private void touch_move(float x[], float y[], int n) {
		if (myPath.size() != 0) {
			for (int i = 0; i < n; i++) {
				float dx = Math.abs(x[i] - myX.get(i));
				float dy = Math.abs(y[i] - myY.get(i));
				if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
					myPath.get(i).quadTo(myX.get(i), myY.get(i),
							(x[i] + myX.get(i)) / 2, (y[i] + myY.get(i)) / 2);
					myX.set(i, x[i]);
					myY.set(i, y[i]);
				}
			}
		} else {
			System.out.println("move0");
		}
	}

	private void touch_up(int n) {
		if (myPath.size() != 0) {
			for (int i = 0; i < n; i++) {
				myPath.get(i).lineTo(myX.get(i), myY.get(i));
				myCanvas.drawPath(myPath.get(i), myPaint1);
				myCanvas.drawPath(myPath.get(i), myPaint2);
			}
		} else {
			System.out.println("up0");
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		String s = "null";
		int pointerNumber = event.getPointerCount();
		if (pointerNumber >= 6) {
			pointerNumber = 5;
		} // ǿ��5�㴥��
		Log.e("���ص���", String.valueOf(pointerNumber));
		int action = (event.getAction() & MotionEvent.ACTION_MASK) % 5;// ͳһ����Ͷ��
		int pointerId = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >>> MotionEvent.ACTION_POINTER_ID_SHIFT;
		Log.e("Id", String.valueOf(pointerId));
		if (pointerId == pointerNumber) {
			action = 888;
		} // ǿ��ʹ����5��Ĵ��ز������¼���ֱ������default
		for (int i = 0; i < pointerNumber; i++) {
			x[i] = 0;
			y[i] = 0;
		} // ����
		if (pointerNumber > 1) {
			for (int i = 0; i < pointerNumber; i++) {
				x[i] = event.getX(i);
				y[i] = event.getY(i);
				if (y[i] < MainActivity.scalY - 129
						|| x[i] < (MainActivity.scalX - 287) / 2
						|| x[i] > ((MainActivity.scalX + 287) / 2)) {

					MainActivity.pop.dismiss();
					MainActivity.menu_display = false;
					MainActivity.islevel2show = false;
					MainActivity.level.setVisibility(View.INVISIBLE);
				}
			}
		} // �洢ÿ���������
		else {
			x[0] = event.getX();
			y[0] = event.getY();
			if (y[0] < MainActivity.scalY - 129
					|| x[0] < (MainActivity.scalX - 287) / 2
					|| x[0] > ((MainActivity.scalX + 287) / 2)) {

				MainActivity.pop.dismiss();
				MainActivity.menu_display = false;
				MainActivity.islevel2show = false;
				MainActivity.level.setVisibility(View.INVISIBLE);
			}

		}
		for (int i = myPath.size(); i < pointerNumber; i++) {
			myPath.add(new Path());
		}
		for (int i = 0; i < pointerNumber; i++) {
			Log.e("X", String.valueOf(x[i]));
			Log.e("Y", String.valueOf(y[i]));
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			touch_down(x, y, pointerNumber);
			System.out.println("down");
			invalidate();
			s = "down";
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y, pointerNumber);
			System.out.println("move");
			invalidate();
			s = "move";
			break;
		case MotionEvent.ACTION_UP:
			if (s.equals("down")) {
				if (pointerNumber > 1) {
					for (int i = 0; i < pointerNumber; i++) {
						x[i] = x[i] + 1f;
						y[i] = y[i] + 1f;
					}
				} // �洢ÿ���������
				else {
					x[0] += 1f;
					y[0] += 1f;
				}
				touch_move(x, y, pointerNumber);
			}
			touch_up(pointerNumber);
			myPath.remove(pointerId);
			myX.remove(pointerId);
			myY.remove(pointerId);
			System.out.println("up");
			invalidate();
			break;
		default:
			break;
		}
		return true;
	}
	// static Runnable r = new Runnable(){
	// public void run(){
	//
	// handler.postDelayed(r, 100);
	// }
	// };
}
