package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SpectrumActivity extends Activity {
	
	public static float oldXCoord = 0;
	public static float oldYCoord = 0;
	public static boolean oldCoords = false;
	public static final String TAG = "SpectrumActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.core);
		
		final SpectrumView spectrumView = (SpectrumView) findViewById(R.id.spectrumSurfaceView_id);
		final Button doneButton = (Button) findViewById(R.id.spectrumDoneButton_id);
//		spectrumView.setOnClickListener(new OnClickActivitySwapper(this, SpectrumActivity.class));
		
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				setResult(MainActivity.RESULT_SPECTRUM_DONE);
				finish();
			}
		});
		
		// Create onclick listeners for scaling etc.
		spectrumView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					oldCoords = true;
					oldXCoord = event.getX();
					oldYCoord = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					EchelonBundle.screenBundle.oriNu += (event.getX() - oldXCoord);
					EchelonBundle.screenBundle.oriAda += (event.getY() - oldYCoord);
					oldXCoord = event.getX();
					oldYCoord = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					oldCoords = false;
					break;
				default:
						
				}
				return true;
			}
		});
	}
}
