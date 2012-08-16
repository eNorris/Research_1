package com.research.Activities;

import com.research.Bundles.EchelonBundle;
import com.research.Utilities.OnBoolClickActivitySwapper;
import com.research.Utilities.OnClickActivitySwapper;
import com.research.Utilities.SpectrumView;
import com.research.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.TextView;

/*
 * ===== Important Notes =====
 * 1) Connect the Android-powered device via USB to your computer.
 *    From your SDK platform-tools/ directory, enter adb tcpip 5555 at the command prompt.
 *    Enter adb connect <device-ip-address>:5555 You should now be connected to the 
 *       Android-powered device and can issue the usual adb commands like adb logcat.
 *    To set your device to listen on USB, enter adb usb.
 *    
 * 2) Things to add:
 *    Add in the config menu:
 *    	Diplay values in channel no or Energy val
 *    Change the manifest so each page has its own unique name ie Research App - Export
 *    Prevent reset of scaling and translation when changing SurfaceView, only on creation
 *    Implement e-mailing spectrum data
 *    Multiple spectrums viewed simultaneously - choose spectrum
 *    autofit algorithm
 *    Always show axes
*/


/**
 * Main class that runs at startup
 * @author Edward
 *
 */
public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	public static final int RESULT_CONFIG_OK = 1;
	public static final int RESULT_CONFIG_CANCEL = 2;
	public static final int RESULT_EXPORT_OK = 3;
	public static final int RESULT_EXPORT_CANCEL = 4;
	public static final int RESULT_HELP_OK = 5;
	public static final int RESULT_HELP_CANCEL = 6;
	public static final int RESULT_SPECTRUM_DONE = 7;
	public static final int RESULT_IMPORT_OK = 8;
	public static final int RESULT_ANALYSIS_OK = 9;
	
//	public static SpectrumView smallSpectrumView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Realize the Layout
		final Button configButton = (Button) findViewById(R.id.mainConfigButton_id);
		final Button analysisButton = (Button) findViewById(R.id.mainAnalysisButton_id);
		final Button exportButton = (Button) findViewById(R.id.mainExportButton_id);
		final Button importButton = (Button) findViewById(R.id.mainImportButton_id);
		final Button helpButton = (Button) findViewById(R.id.mainHelpButton_id);
		final Button exitButton = (Button) findViewById(R.id.mainExitButton_id);
		final SpectrumView smallSpectrumView = (SpectrumView) findViewById(R.id.mainSpectrumSurfaceView_id);
//		smallSpectrumView = (SpectrumView) findViewById(R.id.mainSpectrumSurfaceView_id);
		
		// Add OnClick Listeners
		try {
			configButton.setOnClickListener(new OnClickActivitySwapper(this, ConfigActivity.class));
			analysisButton.setOnClickListener(new OnClickActivitySwapper(this, AnalysisActivity.class));
			importButton.setOnClickListener(new OnClickActivitySwapper(this, ImportActivity.class));
			exportButton.setOnClickListener(new OnClickActivitySwapper(this, ExportActivity.class));
			helpButton.setOnClickListener(new OnClickActivitySwapper(this, HelpActivity.class));
			smallSpectrumView.setOnClickListener(new OnBoolClickActivitySwapper(this, SpectrumActivity.class, EchelonBundle.dataLoaded));
		} catch (Exception e) {
			Log.e(TAG, "Failed to link all activities!");
			e.printStackTrace();
		}
		
		exitButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v(TAG, "Got a resultBack");
		Log.v(TAG, "requestCode = " + requestCode);
		Log.v(TAG, "resultCode = " + resultCode);
		Log.v(TAG, "data = " + data);
		
		switch(resultCode){
		case RESULT_CONFIG_OK:
			break;
		case RESULT_CONFIG_CANCEL:
			break;
		case RESULT_EXPORT_OK:
			break;
		case RESULT_EXPORT_CANCEL:
			break;
		case RESULT_HELP_OK:
			break;
		case RESULT_HELP_CANCEL:
			break;
		case RESULT_SPECTRUM_DONE:
			break;
		case RESULT_IMPORT_OK:
			break;
		case RESULT_ANALYSIS_OK:
			break;
		default:
			Log.e(TAG, "Result code (" + resultCode + ") was not found");
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}





















