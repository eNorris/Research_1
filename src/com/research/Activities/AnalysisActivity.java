package com.research.Activities;

import com.research.AnalysisActivities.AnalysisCommandActivity;
import com.research.AnalysisActivities.EnergyCalActivity;
import com.research.AnalysisActivities.IsotopeIdActivity;
import com.research.AnalysisActivities.NoContentActivity;
import com.research.AnalysisActivities.PeakAnalysisActivity;
import com.research.Bundles.ConfigBundle;
import com.research.Bundles.EchelonBundle;
import com.research.test.R;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
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
		tabHost.setup(getLocalActivityManager());
		
		// Build the gradients the tabs will use
		m_selectedTab = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.RED, Color.GRAY});
		m_selectedTab.setShape(GradientDrawable.RECTANGLE);
		m_selectedTab.setCornerRadius(10);
		
		m_unselectedTab = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{Color.BLACK, Color.GRAY});
		m_unselectedTab.setShape(GradientDrawable.RECTANGLE);
		m_unselectedTab.setCornerRadius(10);
		
		// Build tabs
		TabSpec mainTab = tabHost.newTabSpec("tab1");
		TabSpec peakAnalysisTab = tabHost.newTabSpec("tab2");
		TabSpec isotopeIdTab = tabHost.newTabSpec("tab3");
		TabSpec energyCalTab = tabHost.newTabSpec("tab4");
		TabSpec comparisonTab = tabHost.newTabSpec("tab5");
		TabSpec tabSpec6 = tabHost.newTabSpec("tab6");
		TabSpec tabSpec7 = tabHost.newTabSpec("tab7");
		TabSpec tabSpec8 = tabHost.newTabSpec("tab8");
		TabSpec tabSpec9 = tabHost.newTabSpec("tab9");
		
		// Set Indicators (The label on the tab itself)
		mainTab.setIndicator(new TabTextView(this, "Main", R.drawable.analysisenmain));
		peakAnalysisTab.setIndicator(new TabTextView(this, "Peak Analysis", R.drawable.analysisenpeakid));
		isotopeIdTab.setIndicator(new TabTextView(this, "Isotope Identification", R.drawable.analysisencisotopeid));
		energyCalTab.setIndicator(new TabTextView(this, "Energy Calibration", R.drawable.analysisenergycal));
		comparisonTab.setIndicator(new TabTextView(this, "Spectrum Comparison", R.drawable.analysisencomparison));
		tabSpec6.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec7.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec8.setIndicator(new TabTextView(this, "Not Used"));
		tabSpec9.setIndicator(new TabTextView(this, "Under Construction"));
		
		// Set the tab content
		if(EchelonBundle.dataBundles.size() == 0){
			mainTab.setContent(new Intent(this, NoContentActivity.class));
			peakAnalysisTab.setContent(new Intent(this, NoContentActivity.class));
			isotopeIdTab.setContent(new Intent(this, NoContentActivity.class));
			energyCalTab.setContent(new Intent(this, NoContentActivity.class));
			comparisonTab.setContent(new Intent(this, NoContentActivity.class));
			tabSpec6.setContent(new Intent(this, NoContentActivity.class));
			tabSpec7.setContent(new Intent(this, NoContentActivity.class));
			tabSpec8.setContent(new Intent(this, NoContentActivity.class));
			tabSpec9.setContent(new Intent(this, NoContentActivity.class));
		}else{
			mainTab.setContent(new Intent(this, AnalysisCommandActivity.class));
			peakAnalysisTab.setContent(new Intent(this, PeakAnalysisActivity.class));
			isotopeIdTab.setContent(new Intent(this, IsotopeIdActivity.class));
			energyCalTab.setContent(new Intent(this, EnergyCalActivity.class));
			comparisonTab.setContent(new Intent(this, PeakAnalysisActivity.class));
			tabSpec6.setContent(new Intent(this, NoContentActivity.class));
			tabSpec7.setContent(new Intent(this, NoContentActivity.class));
			tabSpec8.setContent(new Intent(this, NoContentActivity.class));
			tabSpec9.setContent(new Intent(this, NoContentActivity.class));
		}
		
		tabHost.addTab(mainTab);
		tabHost.addTab(peakAnalysisTab);
		tabHost.addTab(isotopeIdTab);
		tabHost.addTab(energyCalTab);
		tabHost.addTab(comparisonTab);
		tabHost.addTab(tabSpec6);
		tabHost.addTab(tabSpec7);
		tabHost.addTab(tabSpec8);
		tabHost.addTab(tabSpec9);
		
		for(int i = 0; i < tabHost.getTabWidget().getChildCount(); i++){
			((LinearLayout.LayoutParams) tabHost.getTabWidget().getChildAt(i).getLayoutParams()).setMargins(1, 5, 1, 0);
		}
		tabHost.getTabWidget().requestLayout();
		
		// Set the listener for the done button
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_ANALYSIS_OK);
				finish();
			}
		});
	}
	
//	/**
//	 * Implements the TabContentFactory interface and adds a protected element named m_context of type Context.
//	 * This allows views crated in the TabContentContextFactory's createTabContent() function to be able to
//	 * be constructed with m_context as an argument since the TabContentFactory was origionally intended to work
//	 * with the TabActivity class, allowing access to the current context via the activity. This class allows
//	 * access when a TabHost is being used outside a TabActivity.
//	 * 
//	 * @author Edward Norris (etnc6d)
//	 *
//	 */
//	private abstract class TabContentContextFactory implements TabContentFactory{
//		
//		protected Context m_context;
//		
//		TabContentContextFactory(Context context){
//			m_context = context;
//		}
//
//		public abstract View createTabContent(String tag);
//	}
	
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
			setPadding(10,20,10,20);
			setGravity(Gravity.CENTER);
			
			// Set the tab states
			int[][] textStates = new int[][]{
					new int[]{android.R.attr.state_pressed}, 
					new int[]{android.R.attr.state_selected},
					StateSet.WILD_CARD
			};
			int[] textColors = new int[]{
//					Color.argb(255, 128, 128, 255), // <- Old colors
//					Color.argb(255, 128, 128, 255),
					Color.argb(255, 65, 190, 210), // <- match new icons
					Color.argb(255, 65, 190, 210),
					Color.WHITE
			};
			
			// Set font color to light cyan if selected, whit otherwise
			this.setTextColor(new ColorStateList(textStates, textColors));
			
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
				if(EchelonBundle.configBundle.resolution == ConfigBundle.RESOLUTION_LOW)
					bitmap.setTargetDensity(DisplayMetrics.DENSITY_LOW);
				else if(EchelonBundle.configBundle.resolution == ConfigBundle.RESOLUTION_MED)
					bitmap.setTargetDensity(DisplayMetrics.DENSITY_MEDIUM);
				else if(EchelonBundle.configBundle.resolution == ConfigBundle.RESOLUTION_HIGH)
					bitmap.setTargetDensity(DisplayMetrics.DENSITY_HIGH);
				else if(EchelonBundle.configBundle.resolution == ConfigBundle.RESOLUTION_XHIGH)
					bitmap.setTargetDensity(DisplayMetrics.DENSITY_XHIGH);
				setCompoundDrawablesWithIntrinsicBounds(bitmap, null, null, null);
			}
		}
	}
}











