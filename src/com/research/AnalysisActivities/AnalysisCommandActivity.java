package com.research.AnalysisActivities;

import com.research.Activities.MainActivity;
import com.research.Bundles.EchelonBundle;
import com.research.test.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnalysisCommandActivity extends Activity{
	
	public static final String TAG = "AnalysisCommand";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis_command);
		
		final Button clearAllButton = (Button) findViewById(R.id.analysis_commandClearAllButton);
		
		clearAllButton.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				EchelonBundle.dataBundles.clear();
				setResult(MainActivity.RESULT_ANALYSIS_OK);
				finish();
			}
			
		});
	}
}
