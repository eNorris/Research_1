package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConfigActivity extends Activity {
	
	private static final String TAG = "ConfigActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		// Realize layout
		final CheckBox autoDetectCheckBox = (CheckBox) findViewById(R.id.configAutoDetectCheckBox_id);
		final Button manualDetectButton = (Button) findViewById(R.id.configManualDetectButton_id);
		final TextView NumBinsTextView = (TextView) findViewById(R.id.configNumBinsTextView_id);
		final Button doneButton = (Button) findViewById(R.id.configDoneButton_id);
		final Spinner binSelectSpinner = (Spinner) findViewById(R.id.configBinSelectorSpinner_id);
		
		// Enable the Spinner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bins, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binSelectSpinner.setAdapter(adapter);
		
		// Add OnClick Listeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// TODO return to the calling activity
			}
		});
		
		autoDetectCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				EchelonBundle.configBundle.autoDetectOn = autoDetectCheckBox.isChecked();
			}
		});
		
		manualDetectButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		
		
		// Implement the spinner listener
        binSelectSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Log.i(TAG + "Selected Item", "pos = " + pos);
				switch(pos){
				case 0: // 256
					// TODO do something
					break;
				case 1: // 512
					// TODO do something else
					break;
				default:
					// TODO take some defaul action should nothing else work
				};
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING
			}
        });
	}
}

