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
			Log.v(TAG, "Attempting to launch next Activity");
			m_callingActivity.startActivityForResult(nextIntent, 0);
			Log.v(TAG, "Launched new activity");
		}catch(ActivityNotFoundException e){
			Log.wtf(TAG, "Could not find the requested Activity", new RuntimeException());
		}
	}
}


class OnBoolClickActivitySwapper extends OnClickActivitySwapper{
	
	private final static String TAG = "OnBoolclickActivitySwapper";
	private Boolean m_bool;
	
	public OnBoolClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass){
		super(callingActivity, nextActivityClass);
		m_bool = Boolean.TRUE;
	}

	public OnBoolClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass, Boolean bool){
		super(callingActivity, nextActivityClass);
		m_bool = bool;
	}
	
	public void activate(){
		m_bool = Boolean.TRUE;
	}
	
	public void deactivate(){
		m_bool = Boolean.FALSE;
	}
	
	public void linkActivation(Boolean bool){
		m_bool = bool;
	}
	
	public void onClick(View v) {
		
Log.d(TAG, "Recieved click: Echelon = " + EchelonBundle.dataLoaded.toString() + "     bool = " + m_bool.toString());
		
		
		
		
		if(m_bool.booleanValue()){
			super.onClick(v);
		}else{
			Log.v(TAG, "Bool decision averted launching activity");
		}
	}
}







