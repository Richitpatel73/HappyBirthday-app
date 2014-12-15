package com.example.happybirthday;

import com.example.happybirthday.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class SecondActivity extends Activity implements SensorEventListener {
	private static final double SHAKE_THRESHOLD = 5;
	SensorManager manager;
	Sensor accelerometer;
	boolean changeFlag;

	private long now = 0;
	private long timeDiff = 0;
	private long lastUpdate = 0;
	private long lastShake = 0;

	private float x = 0;
	private float y = 0;
	private float z = 0;
	private float lastX = 0;
	private float lastY = 0;
	private float lastZ = 0;
	private float force = 0;

	int image_index = 0;
	private static final int MAX_IMAGE_COUNT = 5;
	private Integer[] mImageIds = { R.drawable.zero, R.drawable.extra,
			R.drawable.one, R.drawable.two, R.drawable.three };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);
		showImage();
		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	@Override
	protected void onResume() {
		manager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}

	@Override
	protected void onPause() {
		manager.unregisterListener(this, accelerometer);
		super.onPause();
	}

	private void showImage() {
		ImageView imgView = (ImageView) findViewById(R.id.myimage);
		imgView.setImageResource(mImageIds[image_index]);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.ibPrevious):
			image_index--;
			if (image_index == -1) {
				image_index = MAX_IMAGE_COUNT - 1;
			}
			showImage();
			break;

		case (R.id.ibNext):
			image_index++;
			if (image_index == MAX_IMAGE_COUNT) {
				image_index = 0;
			}
			showImage();
			break;
		}
	}

	public void close(View V) {
		finish();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		now = event.timestamp;

		x = event.values[0];
		y = event.values[1];
		z = event.values[2];

		// if not interesting in shake events
		// just remove the whole if then else block
		if (lastUpdate == 0) {
			lastUpdate = now;
			lastShake = now;
			lastX = x;
			lastY = y;
			lastZ = z;
			Toast.makeText(this, "No Motion detected", Toast.LENGTH_SHORT)
					.show();

		} else {
			timeDiff = now - lastUpdate;

			if (timeDiff > 0) {

				/*
				 * force = Math.abs(x + y + z - lastX - lastY - lastZ) /
				 * timeDiff;
				 */
				force = Math.abs(x + y + z - lastX - lastY - lastZ);

				float threshold = 3.5f;

				if (Float.compare(force, threshold) > 0) {
					// Toast.makeText(Accelerometer.getContext(),
					// (now-lastShake)+"  >= "+interval, 1000).show();

					long interval = 4000;
					if (now - lastShake >= interval) {

						// trigger shake event
						// Toast.makeText(this, "shake detected", 1000).show();

						changeImage();

					} else {

						Toast.makeText(this, "No shake detected", 1000).show();

					}
					lastShake = now;
				}
				lastX = x;
				lastY = y;
				lastZ = z;
				lastUpdate = now;
			} else {
				Toast.makeText(this, "No Motion detected", Toast.LENGTH_SHORT)
						.show();

			}
		}

	}

	private void changeImage() {
		// TODO Auto-generated method stub
		image_index++;
		if (image_index == MAX_IMAGE_COUNT) {
			image_index = 0;
		}
		showImage();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

}
