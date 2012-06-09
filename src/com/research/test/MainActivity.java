package com.research.test;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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
		
		// Add OnClick Listeners
		configButton.setOnClickListener(new OnClickActivitySwapper(this, ConfigActivity.class));
		exportButton.setOnClickListener(new OnClickActivitySwapper(this, ExportActivity.class));
		helpButton.setOnClickListener(new OnClickActivitySwapper(this, helpActivity.class));
	}
}

class OnClickActivitySwapper implements OnClickListener{

	private Activity m_callingActivity;
	private Class<?> m_nextActivityClass;
	private static final String TAG = "OnClickActivitySwapper";
	
	public OnClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass){
		m_callingActivity = callingActivity;
		m_nextActivityClass = nextActivityClass;
	}
	public void onClick(View v) {
		Intent nextIntent = new Intent(m_callingActivity, m_nextActivityClass);
		
		try{
			m_callingActivity.startActivityForResult(nextIntent, 0);
		}catch(ActivityNotFoundException e){
			Log.wtf(TAG, "Could not find the requested Activity", new RuntimeException());
		}
	}
}



















