package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ConfigActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		// Realize layout
		final CheckBox autoDetectCheckBox = (CheckBox) findViewById(R.id.configAutoDetectCheckBox_id);
		final Button manualDetectButton = (Button) findViewById(R.id.configManualDetectButton_id);
		final TextView NumBinsTextView = (TextView) findViewById(R.id.configNumBinsTextView_id);
		final Button doneButton = (Button) findViewById(R.id.configDoneButton_id);
		
		// Add OnClick Listeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// TODO return to the calling activity
			}
		});
	}
}
