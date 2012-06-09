package com.research.test;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * @author Edward Norris (etnc6d@mst.edu)
 *
 */
class OnClickActivitySwapper implements OnClickListener{

	public Activity m_callingActivity;
	public Class<?> m_nextActivityClass;
	private static final String TAG = "OnClickActivitySwapper";
	
	public OnClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass){
		m_callingActivity = callingActivity;
		m_nextActivityClass = nextActivityClass;
	}
	
	public void onClick(View v) {
		Intent nextIntent = new Intent(m_callingActivity, m_nextActivityClass);
		
		try{
			Log.v(TAG, "Attempting to launch next Activity");
			m_callingActivity.startActivityForResult(nextIntent, 0);
			Log.v(TAG, "Launched!");
		}catch(ActivityNotFoundException e){
			Log.wtf(TAG, "Could not find the requested Activity", new RuntimeException());
		}
	}
}