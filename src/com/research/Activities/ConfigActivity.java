package com.research.Activities;

import com.research.Bundles.EchelonBundle;
import com.research.test.R;
import com.research.test.R.array;
import com.research.test.R.id;
import com.research.test.R.layout;

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

public class ConfigActivity extends Activity {
	
	private static final String TAG = "ConfigActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		// Realize layout
		final CheckBox autoDetectCheckBox = (CheckBox) findViewById(R.id.configAutoDetectCheckBox_id);
		final Button manualDetectButton = (Button) findViewById(R.id.configManualDetectButton_id);
//		final TextView NumBinsTextView = (TextView) findViewById(R.id.configNumBinsTextView_id);
		final Button doneButton = (Button) findViewById(R.id.configDoneButton_id);
		final Spinner binSelectSpinner = (Spinner) findViewById(R.id.configBinSelectorSpinner_id);
		final Spinner autoResizeSelectSpinner = (Spinner) findViewById(R.id.configAutoResizeSpinner_id);
		final Spinner recordUntilSpinner = (Spinner) findViewById(R.id.configRecordUntilSpinner_id);
		
		// TODO - Someday I'd like to implement a color chooser for the spectrum color and 
		// axes, maybe even a gradient generator!
		
		// Enable the Spinner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bins, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binSelectSpinner.setAdapter(adapter);
        
        ArrayAdapter<CharSequence> autoResizeadapter = ArrayAdapter.createFromResource(this, R.array.autoResizeValues, android.R.layout.simple_spinner_item);
        autoResizeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoResizeSelectSpinner.setAdapter(autoResizeadapter);
        
        ArrayAdapter<CharSequence> recroderAdapter = ArrayAdapter.createFromResource(this, R.array.recordUntilValues, android.R.layout.simple_spinner_item);
        recroderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recordUntilSpinner.setAdapter(recroderAdapter);
		
		// Add OnClick Listeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_CONFIG_OK);
				finish();
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
        
        autoResizeSelectSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				switch(pos){
				case 0: // full auto
					EchelonBundle.configBundle.autoResizeX = true;
					EchelonBundle.configBundle.autoResizeY = true;
					break;
				case 1: // auto x
					EchelonBundle.configBundle.autoResizeX = true;
					EchelonBundle.configBundle.autoResizeY = false;
					break;
				case 2: // auto y
					EchelonBundle.configBundle.autoResizeX = false;
					EchelonBundle.configBundle.autoResizeY = true;
					break;
				case 3: // full manual
					EchelonBundle.configBundle.autoResizeX = false;
					EchelonBundle.configBundle.autoResizeY = false;
					break;
				default:
					Log.wtf(TAG, "autoResizeSelectSpinner recieved illegal item (" + pos + ")");
				};
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// DO NOTHING
			}
        });
        
     // Implement the spinner listener
        recordUntilSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Log.i(TAG + "Selected Item", "pos = " + pos);
				switch(pos){
				case 0: // sec
					// TODO do something
					break;
				case 1: // min
					// TODO do something else
					break;
				case 2: // hour
					// TODO
					break;
				case 3: // counts
					// TODO
					break;
				case 4: // max count
					// TODO
					break;
				case 5: // statistic
					// TODO
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

