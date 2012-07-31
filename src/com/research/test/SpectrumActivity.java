package com.research.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
//import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.View.OnDragListener;
import android.widget.Button;
//import android.widget.SeekBar;

public class SpectrumActivity extends Activity {
	
	public static float oldXCoord = 0;
	public static float oldYCoord = 0;
	public static boolean oldCoords = false;
	public static final String TAG = "SpectrumActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "calling onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.core);
		
		final SpectrumView spectrumView = (SpectrumView) findViewById(R.id.spectrumSurfaceView_id);
		final Button doneButton = (Button) findViewById(R.id.spectrumDoneButton_id);
//		final SeekBar seekBar = (SeekBar) findViewById(R.id.spectrumHorizSeekBar_id);
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
		
		// Create the scaling for the seek bar
//		seekBar.setOnDragListener(new OnDragListener(){
//			private float dragLoc = 0.0f;
//			public boolean onDrag(View v, DragEvent event) {
//				if(event.getAction() == DragEvent.ACTION_DRAG_STARTED){
//					Log.d(TAG, "Drag started");
//					dragLoc = event.getX();
//				}else if(event.getAction() == DragEvent.ACTION_DRAG_LOCATION){
//					EchelonBundle.screenBundle.scaleNu = event.getX()/dragLoc;
//				}else if(event.getAction() == DragEvent.ACTION_DRAG_ENDED){
//					Log.d(TAG, "Drag ended");
//				}else{
//					Log.v(TAG, "Recieved unknown event action:" + event.toString());
//				}
//	Log.d(TAG, "event.x = " + event.getX());
//				return false;
//			}
//			
//		});
	}
}


















