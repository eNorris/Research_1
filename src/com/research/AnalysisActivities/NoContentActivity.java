package com.research.AnalysisActivities;

import com.research.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NoContentActivity extends Activity{

	public static final String TAG = "NoContentActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noresources);
	}
}


