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
		EchelonBundle.screenBundle.yMin = 0;
		EchelonBundle.screenBundle.height = (float) (EchelonBundle.screenBundle.yMax = getHeight());
		EchelonBundle.screenBundle.width = (float) (EchelonBundle.screenBundle.xMax = getWidth());
		EchelonBundle.screenBundle.oriAda = EchelonBundle.screenBundle.height;
		
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
	
	public void onDraw(Canvas canvas){
		if(canvas != null){
			
			// Reset the canvas to solid black
			canvas.drawColor(Color.BLACK);
			drawSpectrum(canvas);
			drawAxisSystem(canvas);
		}
	}
	
	public void drawSpectrum(Canvas canvas){
		float width = ((float) canvas.getWidth()) / EchelonBundle.dataBundles[0].data.length * EchelonBundle.screenBundle.scaleX;
		float max = 0;
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++)
			if(EchelonBundle.dataBundles[0].data[i] > max)
				max = EchelonBundle.dataBundles[0].data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Set the spectrum color
		// TODO - This should be user set
		paint.setColor(Color.BLUE);
		
		// Draw the bar graph
		float bottom = EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY;//canvas.getHeight();
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++){
			float left = i*width+EchelonBundle.screenBundle.oriX;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriX;
			float top = EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY - EchelonBundle.dataBundles[0].data[i]*heightmod;
			canvas.drawRect(left, top, right, bottom, paint);
		}
	}
	
	public void drawAxisSystem(Canvas canvas){
		// TODO - remove the below line later, it should be a user config
		EchelonBundle.configBundle.axisPaint.setColor(Color.GREEN);
		
		if(EchelonBundle.configBundle.xAxisOn){
			// Draw the main x-axis
			canvas.drawLine(
					0, Util.adaToY(0),
					EchelonBundle.screenBundle.width, Util.adaToY(0),
					EchelonBundle.configBundle.axisPaint
			);
			
			// Draw tick marks to left of origin
			float drawingPoint = EchelonBundle.screenBundle.oriNu;
			while(Util.nuToX(drawingPoint) > 0){
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint -= EchelonBundle.configBundle.tickX;
			}
			
			// Draw tick marks to right of origin
			drawingPoint = EchelonBundle.screenBundle.oriNu;
			while(Util.nuToX(drawingPoint) < EchelonBundle.screenBundle.width){
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint += EchelonBundle.configBundle.tickX;
			}
		}
		
		// Draw main Y axis
		if(EchelonBundle.configBundle.yAxisOn){
			canvas.drawLine(
					Util.nuToX(0), 0,
					Util.nuToX(0), EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint
			);
			
			// Draw tick marks above the origin
			float drawingPoint = EchelonBundle.screenBundle.oriAda;
			while(Util.adaToY(drawingPoint) > 0){
				canvas.drawLine(
						Util.nuToX(0), drawingPoint,
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, drawingPoint, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint += EchelonBundle.configBundle.tickY;
			}
			
			// Draw tick marks below the origin
			drawingPoint = EchelonBundle.screenBundle.oriAda;
			while(Util.adaToY(drawingPoint) < EchelonBundle.screenBundle.height){
				canvas.drawLine(
						Util.nuToX(0), drawingPoint,
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, drawingPoint, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint -= EchelonBundle.configBundle.tickX;
			}
		}
	}
}


















