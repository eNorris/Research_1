package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImportActivity extends Activity {
	public static final String TAG = "AnalysisActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_layout);
		
		// Realize layout
		Button doneButton = (Button) findViewById(R.id.importDoneButton_id);
		
		// Set On click listeners
//		doneButton.setOnClickListener(new OnClickActivitySwapper(this, MainActivity.class));
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_IMPORT_OK);
				finish();
			}
		});
	}
}
