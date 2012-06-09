package com.research.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SpectrumActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.core);
		
		final SpectrumView spectrumView = (SpectrumView) findViewById(R.id.spectrumSurfaceView_id);
		
//		spectrumView.setOnClickListener(new OnClickActivitySwapper(this, SpectrumActivity.class));
		
		// Create onclick listeners for scaling etc.
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
