package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HelpActivity extends Activity {
	
	private static final String TAG = "HelpActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		// FIXME delete below line later
		Log.v(TAG, "Help menu created");
		
		// Realize Layout
		final Button doneButton = (Button) findViewById(R.id.helpDoneButton_id);
		
		// Set OnClickListeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}














