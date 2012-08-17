package com.research.AnalysisActivities;

import java.util.ArrayList;

import com.research.Bundles.EchelonBundle;
import com.research.Utilities.IconArrayAdapter;
import com.research.test.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

public class PeakAnalysisActivity extends Activity{
	
	public static final String TAG = "PeakAnalysisActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.peakanalysis);
		
		Spinner spinner = (Spinner) findViewById(R.id.peakanalysisSpinner_id);
		
		ArrayList<String> spectrumNames = new ArrayList<String>();
		for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
			spectrumNames.add(EchelonBundle.dataBundles.get(i).name);
		}
		
		ArrayList<Drawable> spectrumColors = new ArrayList<Drawable>();
		for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
			PaintDrawable p = new PaintDrawable(Color.RED);
			spectrumColors.add(p);
		}
		
		
		
		IconArrayAdapter adapter = new IconArrayAdapter(this, R.layout.component_iconarrayadapter, spectrumNames);
		adapter.setDropDownViewResource(R.layout.component_iconarrayadapter);
		spinner.setAdapter(adapter);
		

	}
}



















