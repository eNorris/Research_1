package com.research.Activities;

import com.research.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
		
		Log.v(TAG, "calling onCreate()");
		
		// Realize Layout
		final Button doneButton = (Button) findViewById(R.id.helpDoneButton_id);
		final Button aboutButton = (Button) findViewById(R.id.helpAboutButton_id);
		final Button infoButton = (Button) findViewById(R.id.helpGeneralInfoButton_id);
		
		// Set OnClickListeners
		
		aboutButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				new AlertDialog.Builder(HelpActivity.this).setIcon(R.drawable.logo1).setTitle("About").
				setMessage("Author: Edward Norris (etnc6d@mst.edu) \n\n" +
						"Research Advisor: Dr. Xin Liu \n\n" +
						"Missouri University of Science and Technology, " +
						"Department of Mining and Nuclear Engineering \n\n" +
						"Developed For the American Nuclear Society Winter Meeting 2012").setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
			}
			
		});
		
		infoButton.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v) {
				new AlertDialog.Builder(HelpActivity.this).setIcon(R.drawable.logo1).setTitle("About").
				setMessage("This software is designed to work with \n\n" +
						"the AmpTek MCA8000A multichannel analyzer").setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
			}
			
		});
		
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}














