package com.example.sss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.R.bool;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	static int scalX;
	static int scalY;
	static panel myView;
	static int num = 0;
	static Bitmap paperBitmap;
	private String mScreenshotPath = Environment.getExternalStorageDirectory()
			+ "/";
	private View view;
	public static PopupWindow pop;
	public static boolean menu_display = false;
	public static boolean islevel2show = false;
	public static RelativeLayout level;
	private static Drawable drawable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // ȥ����Ϣ��
		scalX = this.getWindowManager().getDefaultDisplay().getWidth();
		scalY = this.getWindowManager().getDefaultDisplay().getHeight(); // ��ȡ��Ļ��Ⱥ͸߶�
		drawable = getWallpaper(); // ��ȡ��Ļ��ͼ
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		paperBitmap = bm;
		// System.out.println(scalX);
		// System.out.println(scalY);
		myView = new panel(this);
		myView.setBackgroundDrawable(getWallpaper()); // �Ե�ǰ��ĻΪ����
		view = this.getLayoutInflater().inflate(R.layout.menu, null);
		pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, 129);
		init();
		setContentView(myView);
	}

	private void init() {
		level = (RelativeLayout) view.findViewById(R.id.level2);
		ImageButton bhome = (ImageButton) view.findViewById(R.id.home);
		ImageButton bscreenshot = (ImageButton) view
				.findViewById(R.id.screenshot);
		ImageButton bselect = (ImageButton) view.findViewById(R.id.select);
		ImageButton bclear = (ImageButton) view.findViewById(R.id.clear);

		bhome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!islevel2show) {
					MyAnimation.flyIn(level, 500);
					Log.i("bhome", "in");
				} else {
					MyAnimation.flyOut(level, 500, 0);
					Log.i("bhome", "out");
				}
				islevel2show = !islevel2show;
			}
		});
		bscreenshot.setOnClickListener(bscreenshotClickListener);
		bselect.setOnClickListener(bselectClickListener);
		bclear.setOnClickListener(bclearClickListener);
	}

	// ��������¼�
	private OnClickListener bscreenshotClickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			saveScreenshot();
			Toast.makeText(getApplicationContext(), "����ɹ�", Toast.LENGTH_LONG)
					.show();
			pop.dismiss();
			menu_display = false;
			islevel2show = false;
			level.setVisibility(View.INVISIBLE);
		}
	};
	// �����Զ����ֽ�¼�
	private OnClickListener bselectClickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent pictureSd = new Intent();
			// ����Pictures����Type�趨Ϊimage
			pictureSd.setType("image/*");
			pictureSd.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(pictureSd, 0);
			pop.dismiss();
			menu_display = false;
			islevel2show = false;
			level.setVisibility(View.INVISIBLE);
		}
	};
	// ���������¼�
	private OnClickListener bclearClickListener = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			myView = new panel(MainActivity.this);
			setContentView(myView);
			myView.setBackgroundDrawable(drawable);
			pop.dismiss();
			menu_display = false;
			islevel2show = false;
			level.setVisibility(View.INVISIBLE);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) { // �˴��� RESULT_OK ��ϵͳ�Զ����һ������
			Log.e("11", "ActivityResult resultCode error");
			return;
		}
		Bitmap bm = null;
		// ���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�
		ContentResolver resolver = getContentResolver();
		try {
			Uri originalUri = data.getData(); // ���ͼƬ��uri
			bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); // �Եõ�bitmapͼƬ
			drawable = new BitmapDrawable(bm);
			myView = new panel(MainActivity.this);
			setContentView(myView);
			myView.setBackgroundDrawable(drawable);
			BitmapDrawable bd = (BitmapDrawable) drawable;
			Bitmap b = bd.getBitmap();
			paperBitmap = b;
		} catch (IOException e) {
			Log.e("22", e.toString());
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!menu_display) {
				Log.e("level", String.valueOf(islevel2show));
				pop.showAtLocation(myView, Gravity.BOTTOM
						| Gravity.CENTER_HORIZONTAL, 0, 0); // ����layout��PopupWindow����ʾ��λ��
				menu_display = true;

			} else {
				// �����ǰ�Ѿ�Ϊ��ʾ״̬������������
				pop.dismiss();
				menu_display = false;
				islevel2show = false;
				level.setVisibility(View.INVISIBLE);
			}
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (menu_display) {
				pop.dismiss();
				menu_display = false;
				islevel2show = false;
				level.setVisibility(View.INVISIBLE);
			} else {
				menu_display = false;
				islevel2show = false;
				level.setVisibility(View.INVISIBLE);
				MainActivity.this.finish();
			}
		}
		return false;
	}

	@SuppressLint("WrongCall")
	public void saveScreenshot() {
		if (ensureSDCardAccess()) {
			Bitmap bitmap = Bitmap.createBitmap(paperBitmap);
			Matrix matrix = new Matrix();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight(); // ��ȡλͼ�߶ȺͿ��
			float w = (float) MainActivity.scalX / bitmap.getWidth();
			float h = (float) MainActivity.scalY / bitmap.getHeight(); // ��ȡ���ű���
			matrix.postScale(w, h);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true); // �������ű�����ȡ�µ��ϲ�λͼ
			Canvas canvas = new Canvas(bitmap);
			myView.onDraw(canvas);
			String time = getTime();
			System.out.println(time);
			File file = new File(mScreenshotPath + time + ".png");
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				fos.close();
			} catch (FileNotFoundException e) {
				Log.e("Panel", "FileNotFoundException", e);
			} catch (IOException e) {
				Log.e("Panel", "IOEception", e);
			}
		}
	}

	private boolean ensureSDCardAccess() {
		File file = new File(mScreenshotPath);
		if (file.exists()) {
			return true;
		} else
			try {
				if (file.createNewFile()) {
					return true;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}

	private String getTime() {
		Time time = new Time("GMT+8");
		time.setToNow();
		int year = time.year;
		int month = time.month;
		int day = time.monthDay;
		int minute = time.minute;
		int hour = time.hour;
		int sec = time.second;
		return String.valueOf(year) + String.valueOf(month)
				+ String.valueOf(day) + String.valueOf(hour)
				+ String.valueOf(minute) + String.valueOf(sec);

	}

}
