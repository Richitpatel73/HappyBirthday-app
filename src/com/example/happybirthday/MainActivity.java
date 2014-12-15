package com.example.happybirthday;

import com.example.happybirthday.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void playAudio(View view) {
		Intent objIntent = new Intent(this, PlayAudio.class);
		startService(objIntent);
	}

	public void stopAudio(View view) {
		Intent objIntent = new Intent(this, PlayAudio.class);
		stopService(objIntent);
	}

	public void nextActivity(View view) {
		Intent intent = new Intent(getBaseContext(), SecondActivity.class);
		startActivity(intent);
		finish();
	}

}
