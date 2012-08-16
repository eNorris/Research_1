package com.research.test;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class AnalysisActivity extends ActivityGroup{
	
	public static final String TAG = "AnalysisActivity";
	private static GradientDrawable m_selectedTab = null;
	private static GradientDrawable m_unselectedTab = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis);
		
		// Realize layout
		final Button doneButton = (Button) findViewById(R.id.analysisDoneButton_id);
		final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		// Initialize the TabHost
//		tabHost.setup(this.getLocalActivityManager());
		tabHost.setup(getLocalActivityManager());
		
		// Build the gradients the tabs will use
		m_selectedTab = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED, Color.GRAY});
		m_selectedTab.setShape(GradientDrawable.RECTANGLE);
		m_selectedTab.setCornerRadius(10);
		
		m_unselectedTab = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.BLACK, Color.GRAY});
		m_unselectedTab.setShape(GradientDrawable.RECTANGLE);
		m_unselectedTab.setCornerRadius(10);
		
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
//		tabSpec.setIndicator("hi", this.getResources().getDrawable(R.drawable.placeholder));
//		tabSpec2.setIndicator("tab2");
//		tabSpec3.setIndicator("", getResources().getDrawable(R.drawable.logo1));
//		tabSpec4.setIndicator("tab4");
//		tabSpec5.setIndicator("tab5");
//		tabSpec6.setIndicator("tab6");
//		tabSpec7.setIndicator(new TabTextView(this, "tab7", R.drawable.logo3));
//		tabSpec8.setIndicator("tab8");
//		tabSpec9.setIndicator("tab9");
		tabSpec.setIndicator(new TabTextView(this, "Main", R.drawable.logo3));
		tabSpec2.setIndicator(new TabTextView(this, "Peak Analysis"));
		tabSpec3.setIndicator(new TabTextView(this, "Isotope Identification"));
		tabSpec4.setIndicator(new TabTextView(this, "Energy Calibration"));
		tabSpec5.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec6.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec7.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec8.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec9.setIndicator(new TabTextView(this, "Under Construction"));
		
		
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
				
Log.d(TAG, "crateTabContent()");
				
				// Set the layout
				LinearLayout ll = new LinearLayout(m_context);
				ll.setOrientation(LinearLayout.VERTICAL);
				
				// Build the spinner
				Spinner spinner = new Spinner(m_context);
				
				ArrayList<String> stringers = new ArrayList<String>();
				stringers.add("one");
				stringers.add("two");
				stringers.add("three");
				
				ArrayList<Drawable> drawers = new ArrayList<Drawable>();
				drawers.add(this.m_context.getResources().getDrawable(R.drawable.ic_launcher));
				drawers.add(null);
				drawers.add(this.m_context.getResources().getDrawable(R.drawable.broken));
				
				IconArrayAdapter adapter = new IconArrayAdapter(m_context, stringers, drawers);
				adapter.setDropDownViewResource(R.layout.component_iconarrayadapter);
				
				
//				adapter.setDropDownViewResource(R.layout.component_iconarrayadapter);
//				ArrayAdapter adapter = new ArrayAdapter()
				Log.d(TAG, "enabled: " + spinner.isEnabled());
				Log.d(TAG, "focused: " + spinner.isFocused());
				Log.d(TAG, "shown: " + spinner.isShown());
				Log.d(TAG, "selected: " + spinner.isSelected());
				
				spinner.setAdapter(adapter);
				
				ll.addView(spinner);
				
				View v = null;
				ll.addView(spinner.getAdapter().getView(0, v, ll));
				
				return ll;
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
//		tabSpec2.setContent(tabContentFactory2);
		
		tabSpec2.setContent(new Intent(this, PeakAnalysisActivity.class));
		
		tabSpec3.setContent(tabContentFactory3);
		tabSpec4.setContent(tabContentFactory);
		tabSpec5.setContent(tabContentFactory2);
		tabSpec6.setContent(tabContentFactory3);
		tabSpec7.setContent(tabContentFactory);
		tabSpec8.setContent(tabContentFactory2);
		tabSpec9.setContent(tabContentFactory3);
		
		// Add the tabs to the TabHost
//		tabHost.setup();
		
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
	
	/**
	 * Exteds the TextView class.
	 * 
	 * Adds a StateListDrawable linked to two gradients that change based on the state of the TextView.
	 * Also adds a drawable to the left of the text if an id for a drawable is sent
	 * 
	 * @author Edward
	 *
	 */
	private class TabTextView extends TextView{
		
		public TabTextView(Context context, String tabTitle){
			this(context, tabTitle, 0);
		}

		public TabTextView(Context context, String tabTitle, int drawableResId) {
			super(context);
			
			// Set the TextView general information
			setText(tabTitle);
			setPadding(10,0,10,0);
			setGravity(Gravity.CENTER);
			
			int[][] textStates = new int[][]{
					new int[]{android.R.attr.state_pressed}, 
					new int[]{android.R.attr.state_selected},
					StateSet.WILD_CARD
			};
			int[] textColors = new int[]{
					Color.argb(255, 128, 128, 255),
					Color.argb(255, 128, 128, 255),
					Color.WHITE
			};
			this.setTextColor(new ColorStateList(textStates, textColors));
//			this.set
			
			// Build the needed gradients
			GradientDrawable emptyGradient = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.BLACK, Color.DKGRAY});
			emptyGradient.setShape(GradientDrawable.RECTANGLE);
			emptyGradient.setCornerRadii(new float[]{10,10,10,10,0,0,0,0});
			
			GradientDrawable selectedGradient = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.BLACK, Color.GRAY});
			selectedGradient.setShape(GradientDrawable.RECTANGLE);
			selectedGradient.setCornerRadii(new float[]{10,10,10,10,0,0,0,0});
			
			// Set the tab states to the necessary gradients
			StateListDrawable tabBackgroundDrawable = new StateListDrawable();
			tabBackgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedGradient);
			tabBackgroundDrawable.addState(new int[]{android.R.attr.state_selected}, selectedGradient);
			tabBackgroundDrawable.addState(StateSet.WILD_CARD, emptyGradient);
			setBackgroundDrawable(tabBackgroundDrawable);
			
			// Add the bitmap if one was sent
			if(drawableResId != 0){
				BitmapDrawable bitmap = (BitmapDrawable) getResources().getDrawable(drawableResId);
				bitmap.setTargetDensity(DisplayMetrics.DENSITY_LOW);
				setCompoundDrawablesWithIntrinsicBounds(bitmap, null, null, null);
			}
		}
	}
}











