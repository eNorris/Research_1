package com.research.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class AnalysisActivity extends Activity{
	
	public static final String TAG = "AnalysisActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis);
		
		// Realize layout
		Button doneButton = (Button) findViewById(R.id.analysisDoneButton_id);
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		// Initialize the TabHost
		tabHost.setup();
		
		// Build tabs
		TabSpec tabSpec = tabHost.newTabSpec("tab1");
		TabSpec tabSpec2 = tabHost.newTabSpec("tab2");
		TabSpec tabSpec3 = tabHost.newTabSpec("tab3");
		TabSpec tabSpec4 = tabHost.newTabSpec("tab4");
		TabSpec tabSpec5 = tabHost.newTabSpec("tab5");
		TabSpec tabSpec6 = tabHost.newTabSpec("tab6");
		TabSpec tabSpec7 = tabHost.newTabSpec("tab7");
		TabSpec tabSpec8 = tabHost.newTabSpec("tab8");
		TabSpec tabSpec9 = tabHost.newTabSpec("tab9");
		
		// Set Indicators (The label on the tab itself)
		tabSpec.setIndicator("hi", this.getResources().getDrawable(R.drawable.placeholder));
		tabSpec2.setIndicator("tab2");
		tabSpec3.setIndicator("", getResources().getDrawable(R.drawable.logo1));
		tabSpec4.setIndicator("tab4");
		tabSpec5.setIndicator("tab5");
		tabSpec6.setIndicator("tab6");
		
		
		
		
//		tabSpec7.setIndicator("tab7");
		
		TextView tmp = new TextView(this);
		tmp.setText("tab7");
		tmp.setPadding(10, 10, 10, 10);
		Drawable bitmap = getResources().getDrawable(R.drawable.logo3);
		bitmap.setBounds(0, 20, 30, 0);
		tmp.setCompoundDrawablesWithIntrinsicBounds(bitmap, null, null, null);
		tmp.setGravity(Gravity.CENTER);
		
		tabSpec7.setIndicator(tmp);
		
		
		
		
		tabSpec8.setIndicator("tab8");
		tabSpec9.setIndicator("tab9");
		
		
		// Build the views from factories
		TabContentContextFactory tabContentFactory = new TabContentContextFactory(this){
			public View createTabContent(String tag) {
				TextView textView = new TextView(m_context);
				textView.setText("this is text!");
				return textView;
			}
		};
		
		TabContentContextFactory tabContentFactory2 = new TabContentContextFactory(this){
			public View createTabContent(String tag) {
				TextView textView = new TextView(m_context);
				textView.setText("that is text!");
				return textView;
			}
		};
		
		TabContentContextFactory tabContentFactory3 = new TabContentContextFactory(this){
			public View createTabContent(String tag) {
				TextView textView = new TextView(m_context);
				textView.setText("this isn't text!");
				return textView;
			}
		};
		
		// Set the tab content
		tabSpec.setContent(tabContentFactory);
		tabSpec2.setContent(tabContentFactory2);
		tabSpec3.setContent(tabContentFactory3);
		tabSpec4.setContent(tabContentFactory);
		tabSpec5.setContent(tabContentFactory2);
		tabSpec6.setContent(tabContentFactory3);
		tabSpec7.setContent(tabContentFactory);
		tabSpec8.setContent(tabContentFactory2);
		tabSpec9.setContent(tabContentFactory3);
		
		// Add the tabs to the TabHost
		tabHost.addTab(tabSpec);
		tabHost.addTab(tabSpec2);
		tabHost.addTab(tabSpec3);
		tabHost.addTab(tabSpec4);
		tabHost.addTab(tabSpec5);
		tabHost.addTab(tabSpec6);
		tabHost.addTab(tabSpec7);
		tabHost.addTab(tabSpec8);
		tabHost.addTab(tabSpec9);
		
		// Set the listener for the done button
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_ANALYSIS_OK);
				finish();
			}
		});
	}
	
	/**
	 * Implements the TabContentFactory interface and adds a protected element named m_context of type Context.
	 * This allows views crated in the TabContentContextFactory's createTabContent() function to be able to
	 * be constructed with m_context as an argument since the TabContentFactory was origionally intended to work
	 * with the TabActivity class, allowing access to the current context via the activity. This class allows
	 * access when a TabHost is being used outside a TabActivity.
	 * 
	 * @author Edward Norris (etnc6d)
	 *
	 */
	private abstract class TabContentContextFactory implements TabContentFactory{
		protected Context m_context;
		TabContentContextFactory(Context context){
			m_context = context;
		}

		public abstract View createTabContent(String tag);
	}
}
