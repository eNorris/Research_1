package com.research.AnalysisActivities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.research.test.R;

public class IsotopeIdActivity extends Activity{
	
	public static final String TAG = "IsotopIdActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis_isotopeid);
	}
}

