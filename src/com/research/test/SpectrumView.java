package com.research.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpectrumView extends SurfaceView implements SurfaceHolder.Callback{
	
	private static final String TAG = "GraphView";
	private SpectrumThread m_graphThread;
//	private int[] spectrumData = new int[]{
//			
//	};
	private Paint paint = new Paint();
	
	public int xMax, xMin, yMin, yMax;
	
	public SpectrumView(Context context) {
		super(context);
		init();
	}
	
	public SpectrumView(Context context, AttributeSet attr){
		super(context, attr);
		init();
	}
	public SpectrumView(Context context, AttributeSet attr, int defStyle){
		super(context, attr, defStyle);
		init();
	}
	
	public void init(){
		getHolder().addCallback(this);
		m_graphThread = new SpectrumThread(getHolder(), this);
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(m_graphThread.getState() == Thread.State.TERMINATED){
			m_graphThread = new SpectrumThread(getHolder(), this);
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}else{
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}
		Log.v(TAG, "Created surface");
		
		if(EchelonBundle.dataBundleCount == 0){
			EchelonBundle.addDataBundle();
		}
		Log.v(TAG, "Initialized first DataBundle");
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		EchelonBundle.screenBundle.xMin = 0;
//		EchelonBundle.screenBundle.xMax = getWidth();
		EchelonBundle.screenBundle.yMin = 0;
//		EchelonBundle.screenBundle.yMax = getHeight();
		EchelonBundle.screenBundle.height = (float) (EchelonBundle.screenBundle.yMax = getHeight());
		EchelonBundle.screenBundle.width = (float) (EchelonBundle.screenBundle.xMax = getWidth());
		
		// TODO - make sure these are correct and not zero
		Log.d(TAG, "Caught height = " + EchelonBundle.screenBundle.height);
		Log.d(TAG, "Caught width = " + EchelonBundle.screenBundle.width);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		m_graphThread.setRunning(false);
		
		while(retry){
			try{
				m_graphThread.join();
				retry = false;
			}catch(InterruptedException e){
				// Try again...
			}
		}
	}
	
	// TODO get rid of spectrumData - this will eventually be gotten from
	// EchelonBundle instead
	public void onDraw(Canvas canvas){
		if(canvas != null){
			
			// Reset the canvas to solid black
			canvas.drawColor(Color.BLACK);
			
//			EchelonBundle.screenBundle.oriX += 1;
//			EchelonBundle.screenBundle.oriY += 1;
			
			drawSpectrum(canvas);
			drawAxisSystem(canvas);
		}
	}
	
//	// TODO data -> EchelonData
	public void drawSpectrum(Canvas canvas){
		
		// Calculate the width of each bar in the graph
//		if(EchelonBundle.dataBundles == null){
//			Log.e(TAG, "dataBundles is null!");
//		}else if(EchelonBundle.dataBundles.length == 0){
//			Log.e(TAG, "length is zero!");
//		}else if(EchelonBundle.dataBundles[0] == null){
//			Log.e(TAG, "dataBundle[0] is null!");
//		}else if(EchelonBundle.dataBundles[0].data == null){
//			Log.e(TAG,  "The first dataBundle is empty!");
//		}else if(EchelonBundle.dataBundles[0].data.length == 0){
//			Log.e(TAG, "first data has length = 0");
//		}
		
		float width = ((float) canvas.getWidth()) / EchelonBundle.dataBundles[0].data.length * EchelonBundle.screenBundle.scaleX;
		float max = 0;
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++)
			if(EchelonBundle.dataBundles[0].data[i] > max)
				max = EchelonBundle.dataBundles[0].data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Set the spectrum color
		paint.setColor(Color.BLUE);
		
		// Draw the bar graph
		
		float bottom = canvas.getHeight();
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++){
			// TODO updated x, not y yet
			float left = i*width+EchelonBundle.screenBundle.oriX;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriX;
			
			float top = canvas.getHeight() - EchelonBundle.dataBundles[0].data[i]*heightmod;
			canvas.drawRect(left, top, right, bottom, paint);
		}
	}
	
	public void drawAxisSystem(Canvas canvas){
		// TODO - remove the below line later, it should be a user config
		EchelonBundle.configBundle.axisPaint.setColor(Color.GREEN);
		
		if(EchelonBundle.configBundle.xAxisOn){
			// Draw the main x-axis
			canvas.drawLine(0, absY(EchelonBundle.screenBundle.oriY), 
					canvas.getWidth(), absY(EchelonBundle.screenBundle.oriY), 
					EchelonBundle.configBundle.axisPaint);
	
			// Draw tick marks
			float startPoint = EchelonBundle.screenBundle.oriX;
			float moveBy = EchelonBundle.configBundle.tickX / EchelonBundle.screenBundle.scaleX;
			float goal = EchelonBundle.screenBundle.width;
			
			// Set the printhead
			while(absX(startPoint) > 0){
				startPoint -= moveBy;
			}
			
			while(absX(startPoint) < goal){
				canvas.drawLine(
						absX(startPoint),						// start x
						absY(EchelonBundle.screenBundle.oriY), 	// start y
						absX(startPoint), 						// stop x
						absY(EchelonBundle.screenBundle.oriY) - EchelonBundle.configBundle.tickHeight, // stop y
						EchelonBundle.configBundle.axisPaint);	// paint
				startPoint += moveBy;
			}
		}
		if(EchelonBundle.configBundle.yAxisOn){
			// Draw the main y-axis
			canvas.drawLine(absX(EchelonBundle.screenBundle.oriX), 0, 
					absX(EchelonBundle.screenBundle.oriX), EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint);
Log.d(TAG, "At SpectrumView::[177]: oriX = " + EchelonBundle.screenBundle.oriX);
			// Draw tick marks
			float startPoint = EchelonBundle.screenBundle.oriY;
			float moveBy = EchelonBundle.configBundle.tickY / EchelonBundle.screenBundle.scaleY;
			float goal = EchelonBundle.screenBundle.height;
			while(absY(startPoint) > 0){
				startPoint += moveBy;
			}
			
			while(absY(startPoint) < goal){
				canvas.drawLine(
						absX(EchelonBundle.screenBundle.oriX),		// start x
						absY(startPoint), 						// start y
						absX(EchelonBundle.screenBundle.oriX) + EchelonBundle.configBundle.tickHeight, 	// stop x
						absY(startPoint), 						// stop y
						EchelonBundle.configBundle.axisPaint);	// paint
				startPoint -= moveBy;
			}
		}
	}
	
	public float absX(float x){
		return EchelonBundle.screenBundle.oriX + EchelonBundle.screenBundle.scaleX * x;
	}
	
	public float absY(float y){
		return EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY - EchelonBundle.screenBundle.scaleY * y;
	}
}


















