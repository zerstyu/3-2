package com.example.minerva;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

public class Camera extends Activity implements OnTouchListener, OnCheckedChangeListener {
	ImageButton up, leftup, left, leftdown, back, rightdown, right, rightup,
			stop;
	Connect connect;
	ImageTask task;
	ImageView image;
	Bitmap imageBit;
	WebView imageW;
	int mState;
	boolean touch;
	Switch switchs;
	boolean switches;

	final static int STATE_DONE = 0;
	final static int STATE_RUNNING = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.camera);

		connect = new Connect();

		leftup = (ImageButton) findViewById(R.id.leftup);
		up = (ImageButton) findViewById(R.id.up);
		rightup = (ImageButton) findViewById(R.id.rightup);
		left = (ImageButton) findViewById(R.id.left);
		stop = (ImageButton) findViewById(R.id.stop);
		right = (ImageButton) findViewById(R.id.right);
		leftdown = (ImageButton) findViewById(R.id.leftdown);
		back = (ImageButton) findViewById(R.id.back);
		rightdown = (ImageButton) findViewById(R.id.rightdown);
		image = (ImageView) findViewById(R.id.ImageView01);
		switchs = (Switch) findViewById(R.id.switchs);
		
		switchs.setOnCheckedChangeListener(this);
		switchs.setChecked(false);

		task = new ImageTask();
		task.execute(1);

		leftup.setOnTouchListener(this);
		left.setOnTouchListener(this);
		leftdown.setOnTouchListener(this);
		up.setOnTouchListener(this);
		rightup.setOnTouchListener(this);
		right.setOnTouchListener(this);
		rightdown.setOnTouchListener(this);
		back.setOnTouchListener(this);
		stop.setOnTouchListener(this);
		/*
		 * leftup.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("j"); } });
		 * 
		 * up.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("y"); } });
		 * 
		 * rightup.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * connect.DataSend("g"); } });
		 * 
		 * left.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("a"); } });
		 * 
		 * stop.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("s"); } });
		 * 
		 * right.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("d"); } });
		 * 
		 * leftdown.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("k"); } });
		 * 
		 * back.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { connect.DataSend("b"); } });
		 * 
		 * rightdown.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) {
		 * 
		 * connect.DataSend("f"); } });
		 */

	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("Minerva 종료하시겠습니까?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).setNegativeButton("No", null).show();
			return true;
		}
		return super.onKeyDown(KeyCode, event);
	}

	protected void onStop() {
		connect.DataSend("s");
		// TODO Auto-generated method stub
		try {
			connect.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onStop();
	}

	public class ImageTask extends AsyncTask<Integer, Integer, Integer> {
		int n;
		boolean updating = false;

		@Override
		protected void onProgressUpdate(Integer... values) {
			imageBit = BitmapFactory.decodeFile("/mnt/sdcard/image" + n
					+ ".jpg");
			image.setImageBitmap(imageBit);
			updating = false;

		}

		@Override
		protected Integer doInBackground(Integer... params) {
			// 스레드가 돌아감
			n = 0;
			while (true) {
				try {
					Log.i("image unknown", "before make sock");

					// 오류
					Socket imageSocket = new Socket("192.168.0.30", 7292);
					Log.i("image unknown", "after make sock");

					InputStream in = imageSocket.getInputStream();

					FileOutputStream output = new FileOutputStream(new File(
							"/mnt/sdcard/image" + n + ".jpg"));

					int i = 0;
					while ((i = in.read()) != -1) {
						output.write((char) i);
					}
					output.close();
					in.close();
					imageSocket.close();

					updating = true;
					publishProgress(n);
					// onProgressUpdate
					while (updating)
						;

					n = (n + 1) % 6;

					Thread.sleep(1000);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	public boolean onTouch(View v, MotionEvent event) {
		if (!switches){
	
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				touch = true;
				Log.i("App", "DOWN");
			}
	
			else if (event.getAction() == MotionEvent.ACTION_MOVE && touch) {
				switch (v.getId()) {
				case R.id.back:
					connect.DataSend("b");
					// 백명령
					break;
				case R.id.leftdown:
					connect.DataSend("k");
					// leftdown 명령
					break;
				case R.id.left:
					connect.DataSend("a");
					// leftdown 명령
					break;
				case R.id.leftup:
					connect.DataSend("j");
					// leftdown 명령
					break;
				case R.id.up:
					connect.DataSend("y");
					// leftdown 명령
					break;
				case R.id.rightdown:
					connect.DataSend("f");
					// leftdown 명령
					break;
				case R.id.right:
					connect.DataSend("d");
					// leftdown 명령
					break;
				case R.id.rightup:
					connect.DataSend("g");
					// leftdown 명령
					break;
				case R.id.stop:
					connect.DataSend("s");
					// leftdown 명령
					break;
				}
				Log.i("App", "SENDED");
			}

			else if (event.getAction() == MotionEvent.ACTION_UP) {
				touch = false;
				connect.DataSend("s");
				Log.i("App", "UP");
			}
		}
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked){ //라인트레이서 모드
			connect.DataSend("w");
			switches = true;
			Log.i("App", "linetracer");
		}
		else{ //조종 모드
			connect.DataSend("e");
			switches = false;
			Log.i("App", "Controller");
		}		
	}

	
}
