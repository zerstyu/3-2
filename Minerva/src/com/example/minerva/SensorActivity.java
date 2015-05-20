package com.example.minerva;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {
	Button speed, stop;
	int speedValue = 1, speedFlag = 0;

	private long lastTime, lastTime2, lastTime3;
	private int startFlag = 0;

	private float x, y, z;

	private SensorManager sensorManager;
	private Sensor accelerormeterSensor;

	public Connect connect;

	ImageView image;
	Bitmap imageBit;
	WebView imageW;

	ImageThread imageThread;
	int mState;
	final static int STATE_DONE = 0;
	final static int STATE_RUNNING = 1;
	int n = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);

		speed = (Button) findViewById(R.id.BtSpeed);
		stop = (Button) findViewById(R.id.BtStop);
		imageW = (WebView) findViewById(R.id.webView1);
		image = (ImageView) findViewById(R.id.ImageView01);

		connect = new Connect();

		imageThread = new ImageThread(handler);
		imageThread.start();

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerormeterSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		speed.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				switch (speedValue) {
				case 1:
					connect.DataSend("2");
					speedValue = 2;
					break;
				case 2:
					if (speedFlag == 0) {
						connect.DataSend("3");
						speedValue = 3;
						speed.setText("Speed Down");
					} else if (speedFlag == 1) {
						connect.DataSend("1");
						speedValue = 1;
						speedFlag = 0;
						speed.setText("Speed Up");
					}
					break;
				case 3:
					connect.DataSend("2");
					speedValue = 2;
					speedFlag = 1;
					break;
				}

			}
		});

		stop.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connect.DataSend("s");
			}
		});

	}

	public void onStart() {
		super.onStart();

		if (accelerormeterSensor != null)
			sensorManager.registerListener(this, accelerormeterSensor,
					SensorManager.SENSOR_DELAY_GAME);
	}

	public void onStop() {
		super.onStop();
		if (sensorManager != null)
			sensorManager.unregisterListener(this);
		connect.DataSend("s");
		try {
			connect.clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("onStop E", "Error");
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	/*
	 * x = 10 y = 0 좌 우측 방향(-왼쪽, +오른쪽) z - 0
	 */
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			long currentTime = System.currentTimeMillis();
			long gabOfTime = (currentTime - lastTime);

			long currTime = System.currentTimeMillis();
			long imageGab = (currTime - lastTime2);

			long checkTime = System.currentTimeMillis();
			long startGab = (checkTime - lastTime3);

			if (startGab > 3000)
				startFlag = 1;

			if (startFlag == 1) {
				if (imageGab > 2500) {
					lastTime2 = currTime;
					imageBit = BitmapFactory.decodeFile("/mnt/sdcard/image" + n
							+ ".jpg");
					image.setImageBitmap(imageBit);
					n++;
					n %= 6;
				}
			}

			if (gabOfTime > 300) {
				lastTime = currentTime;
				x = event.values[SensorManager.DATA_X];
				y = event.values[SensorManager.DATA_Y];
				z = event.values[SensorManager.DATA_Z];

				if (y < -3) {
					if (z < -3) {
						connect.DataSend("f");
						return;
					}
					if (z > 5) {
						connect.DataSend("a");
						return;
					}
					connect.DataSend("j");
					return;
				} else if (y > 3) {
					if (z < -3) {
						connect.DataSend("k");
						return;
					}
					if (z > 5) {
						connect.DataSend("d");
						return;
					}
					connect.DataSend("g");
					return;
				} else if (z > 5) {
					connect.DataSend("y");
					return;
				} else if (z < -3) {
					connect.DataSend("b");
					return;
				}
			}
		}
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// imageBit =
			// BitmapFactory.decodeFile("/data/data/ice.hufs/image0.jpg");
			// imageBit =
			// BitmapFactory.decodeFile("/mnt/sdcard/image"+n+".jpg");
			// image.setImageBitmap(imageBit);
			mState = STATE_RUNNING;
			// Drawable drawable =
			// Drawable.createFromPath("/mnt/sdcard/image.jpg");
			// image.setBackgroundDrawable(drawable);

		}
	};

	private class ImageThread extends Thread {
		Handler mHandler;
		int n = 0;

		ImageThread(Handler h) {
			mHandler = h;
		}

		public void run() {
			try {
				sleep(100);
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			mState = STATE_RUNNING;

			Socket imageSocket = null;

			while (true) {

				while (mState == STATE_RUNNING) {

					try {
						Log.i("image unknown", "before make sock");
						
						// 오류
						imageSocket = new Socket("192.168.0.30", 7292);
						Log.i("image unknown", "after make sock");
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						Log.e("image unknown", "Error");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						Log.e("Image Socket", "open error");
					}

					InputStream in = null;
					try {
						in = imageSocket.getInputStream();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						Log.e("IOEx", "Error");
					}

					File f = new File("/mnt/sdcard/image" + n + ".jpg");
					// File f = new File("/data/data/ice.hufs/image"+ n +
					// ".jpg");
					n++;
					n %= 6;

					FileOutputStream output = null;
					try {
						output = new FileOutputStream(f);
					} catch (FileNotFoundException e2) {
						// TODO Auto-generated catch block
						Log.e("FileNot", "Error");
					}

					int i = 0;
					try {
						while ((i = in.read()) != -1) {
							output.write((char) i);
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						Log.e("read", "Error");
					}
					try {
						output.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						Log.e("IOEx2", "Error");
					}

					try {
						in.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						Log.e("IOEx3", "Error");
					}
					try {
						imageSocket.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						Log.e("IOEx4", "Error");
					}
					// Message msg = mHandler.obtainMessage();
					// mHandler.sendMessage(msg);
					// mState = STATE_DONE;

					 try {
					 sleep(150);
					 } catch (InterruptedException e1) {
					 // TODO Auto-generated catch block
					 Log.e("Sleep", "Error");
					 }

					// }
				}
			}
		}
	}
}
