package com.research.AnalysisActivities;

import com.research.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class AnalysisCommandActivity extends Activity{
	
	public static final String TAG = "AnalysisCommand";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis_command);
	}
}
