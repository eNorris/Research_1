package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main class that runs at startup
 * @author Edward
 *
 */
public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Realize the Layout
		final Button configButton = (Button) findViewById(R.id.mainConfigButton_id);
		final Button exportButton = (Button) findViewById(R.id.mainExportButton_id);
		final Button helpButton = (Button) findViewById(R.id.mainHelpButton_id);
		final TextView currentDetectorTextView = (TextView) findViewById(R.id.mainDetNameTextView_id);
		
		// Add configButton OnClickListener
		configButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		// Add exportButton OnClickListener
		exportButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		// Add helpButton OnClickListener
		helpButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}
}
















