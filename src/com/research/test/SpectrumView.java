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
	private int[] spectrumData = new int[]{
		10,12,14,18,19,19,21,22,27,33,
		37,38,39,40,40,40,41,39,40,41,
		42,41,41,40,39,37,38,38,40,36,
		35,34,35,32,29,31,30,32,33,30,
		30,30,31,29,29,34,29,33,32,30,
		31,30,30,29,30,29,30,32,30,33,
		31,30,31,30,29,30,30,30,30,30,
		33,38,47,59,63,64,63,56,48,42,
		37,35,36,33,32,32,31,31,32,31,
		31,31,27,20,11,6,7,6,7,6,
		6,8,5,4,4,4,6,7,6,9
	};
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
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Do Nothing
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
			drawSpectrum(canvas, spectrumData);
			drawAxisSystem(canvas);
		}
	}
	
	// TODO data -> EchelonData
	public void drawSpectrum(Canvas canvas, int[] data){
		
		// Calculate the width of each bar in the graph
		float width = ((float) canvas.getWidth()) / data.length;
		float max = 0;
		for(int i = 0; i < data.length; i++)
			if(data[i] > max)
				max = data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Set the spectrum color
		paint.setColor(Color.BLUE);
		
		// Draw the bar graph
		for(int i = 0; i < data.length; i++){
			canvas.drawRect(i*width, canvas.getHeight() - data[i]*heightmod, (i+1)*width, canvas.getHeight(), paint);
		}
	}
	
	public void drawAxisSystem(Canvas canvas){
		// TODO - remove the below line later, it should be a user config
		EchelonBundle.configBundle.axisPaint.setColor(Color.GREEN);
		
		if(EchelonBundle.configBundle.xAxisOn){
			// Draw the main x-axis
			canvas.drawLine(0, canvas.getHeight() - 30, canvas.getWidth(), canvas.getHeight() - 30, 
					EchelonBundle.configBundle.axisPaint);
			// Draw tick marks
			for(float i = EchelonBundle.configBundle.xMin; i < EchelonBundle.configBundle.xMax; i += 
					EchelonBundle.configBundle.xTick){
				canvas.drawLine(i, canvas.getHeight(), i, 0, EchelonBundle.configBundle.axisPaint);
			}
		}
		if(EchelonBundle.configBundle.yAxisOn){
			canvas.drawLine(30, 0, 30, canvas.getHeight(), EchelonBundle.configBundle.axisPaint);
		}
	}
}


















