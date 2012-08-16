package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

public class PeakAnalysisActivity extends Activity{
	
	public static final String TAG = "PeakAnalysisActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		
//		Log.d(TAG, "here");
		
		setContentView(R.layout.peakanalysis);
		
		Spinner spinner = (Spinner) findViewById(R.id.peakanalysisSpinner_id);
		
//		Log.d(TAG, "finished onCreate");
	}
}