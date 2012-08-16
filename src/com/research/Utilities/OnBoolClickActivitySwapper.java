package com.research.Utilities;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.research.Bundles.EchelonBundle;

public class OnBoolClickActivitySwapper extends OnClickActivitySwapper{
	
	private final static String TAG = "OnBoolclickActivitySwapper";
//	public static Boolean m_bool;
	private AtomicBoolean m_bool;
	
	public OnBoolClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass){
		super(callingActivity, nextActivityClass);
		m_bool.set(true);
	}

	public OnBoolClickActivitySwapper(Activity callingActivity, Class<?> nextActivityClass, AtomicBoolean bool){
		super(callingActivity, nextActivityClass);
		m_bool = bool;
	}
	
	public void activate(){
		m_bool = new AtomicBoolean(true);
	}
	
	public void deactivate(){
		m_bool = new AtomicBoolean(false);
	}
	
	public void link(AtomicBoolean bool){
		m_bool = bool;
	}
	
	public void unlink(){
		m_bool = new AtomicBoolean(m_bool.get());
	}
	
	public void onClick(View v) {
		
Log.d(TAG, "Recieved click: Echelon = " + EchelonBundle.dataLoaded.toString() + "     bool = " + m_bool.toString());
		
		
		
		
		if(m_bool.get()){
			super.onClick(v);
		}else{
			Log.v(TAG, "Bool decision averted launching activity");
		}
	}
}