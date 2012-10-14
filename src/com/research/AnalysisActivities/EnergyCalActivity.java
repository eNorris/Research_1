package com.research.AnalysisActivities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.research.Bundles.DataBundle;
import com.research.Bundles.EchelonBundle;
import com.research.Bundles.IsotopeBundle;
import com.research.test.R;

public class EnergyCalActivity extends Activity{
	
	public static final String TAG = "EnergyCalActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v(TAG, "Calling onCreate()");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.analysis_energycal);
		
		final Spinner spectrumSpinner = (Spinner) findViewById(R.id.energyCalSpectrumSpinner_id);
		final Spinner isotopeSpinner = (Spinner) findViewById(R.id.energyCalIsotopeSpinner_id);
		final Button calibrateButton = (Button) findViewById(R.id.energyCalCalibrateButton_id);
		
		ArrayList<String> isotopesArray = new ArrayList<String>();
		ArrayList<String> spectrumsArray = new ArrayList<String>();
		
//		isotopesArray.add("iso1");
//		isotopesArray.add("iso2");
//		spectrumsArray.add("spec1");
//		spectrumsArray.add("spec2");
		
		if(EchelonBundle.isotopes.size() == 0){
			isotopesArray.add("No Isotopes Loaded");
		}else{
			for(IsotopeBundle i : EchelonBundle.isotopes){
				isotopesArray.add(i.name);
			}
		}
		
		if(EchelonBundle.dataBundles.size() == 0){
			isotopesArray.add("No Spectrums Loaded");
		}else{
			for(DataBundle i : EchelonBundle.dataBundles){
				spectrumsArray.add(i.name);
			}
		}
		
		
//		ArrayAdapter<String> spectrumAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spectrumsArray);
		ArrayAdapter<String> spectrumAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spectrumsArray);
		ArrayAdapter<String> isotopeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, isotopesArray);
		
		spectrumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		isotopeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spectrumSpinner.setAdapter(spectrumAdapter);
		isotopeSpinner.setAdapter(isotopeAdapter);
		
		calibrateButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}


//Spinner spinner = (Spinner) findViewById(R.id.spinner);
//Create an ArrayAdapter using the string array and a default spinner layout
//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//     R.array.planets_array, android.R.layout.simple_spinner_item);
//Specify the layout to use when the list of choices appears
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Apply the adapter to the spinner
//spinner.setAdapter(adapter);










