package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnalysisActivity extends Activity{
	
	public static final String TAG = "AnalysisActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis);
		
		// Realize layout
		Button doneButton = (Button) findViewById(R.id.analysisDoneButton_id);
		
		// Set On Click Listeners
//		doneButton.setOnClickListener(new OnClickActivitySwapper(this, MainActivity.class));
		
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_ANALYSIS_OK);
				finish();
			}
		});
	}
	
	
}
