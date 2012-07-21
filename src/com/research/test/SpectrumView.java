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
//		EchelonBundle.screenBundle.xMax = getWidth();
		EchelonBundle.screenBundle.yMin = 0;
//		EchelonBundle.screenBundle.yMax = getHeight();
		EchelonBundle.screenBundle.height = (float) (EchelonBundle.screenBundle.yMax = getHeight());
		EchelonBundle.screenBundle.width = (float) (EchelonBundle.screenBundle.xMax = getWidth());
		// TODO - Added - verify
		EchelonBundle.screenBundle.oriAda = EchelonBundle.screenBundle.height;
		
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
//			canvas.drawLine(0, 
//					EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY, 
//					canvas.getWidth(), 
//					EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY, 
//					EchelonBundle.configBundle.axisPaint
//			);
//Log.d(TAG, "yToAda(0) = " + Util.adaToY(0));
//Log.d(TAG, "oriAda = " + EchelonBundle.screenBundle.oriAda);
			canvas.drawLine(
					0, Util.adaToY(0),
					EchelonBundle.screenBundle.width, Util.adaToY(0),
					EchelonBundle.configBundle.axisPaint
			);
			
			// Draw tick marks
//			float tickXLength = EchelonBundle.configBundle.tickX * EchelonBundle.screenBundle.scaleX;
//			float indexer = EchelonBundle.screenBundle.oriNu / EchelonBundle.configBundle.tickX - 
//					(float) Math.floor((double) (EchelonBundle.screenBundle.oriNu / EchelonBundle.configBundle.tickX));
			
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
//Log.d(TAG, "width = " + EchelonBundle.screenBundle.width);
			while(Util.nuToX(drawingPoint) < EchelonBundle.screenBundle.width){
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint += EchelonBundle.configBundle.tickX;
			}
			
//			// Draw tick marks
//			float startPoint = EchelonBundle.screenBundle.oriX;
//			float moveBy = EchelonBundle.configBundle.tickX / EchelonBundle.screenBundle.scaleX;
//			float goal = EchelonBundle.screenBundle.width;
//			
//			// Set the printhead
//			while(startPoint > 0){
//				startPoint -= moveBy;
//			}
//			
//			while(startPoint < goal){
//				canvas.drawLine(
//						startPoint,						// start x
//						EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY, 	// start y
//						startPoint, 						// stop x
//						EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY - EchelonBundle.configBundle.tickHeight, // stop y
//						EchelonBundle.configBundle.axisPaint);	// paint
//				startPoint += moveBy;
//			}
		}
		
		
		if(EchelonBundle.configBundle.yAxisOn){
			// Draw the main y-axis
//			canvas.drawLine(EchelonBundle.screenBundle.oriX, 0, 
//					EchelonBundle.screenBundle.oriX, EchelonBundle.screenBundle.height,
//					EchelonBundle.configBundle.axisPaint);
			canvas.drawLine(
					Util.nuToX(0), 0,
					Util.nuToX(0), EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint
			);
			
			float drawingPoint = EchelonBundle.screenBundle.oriAda;
			while(Util.adaToY(drawingPoint) > 0){
				canvas.drawLine(
						Util.nuToX(0), drawingPoint,
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, drawingPoint, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint += EchelonBundle.configBundle.tickY;
			}
			
			// Draw tick marks to right of origin
			drawingPoint = EchelonBundle.screenBundle.oriAda;
			while(Util.adaToY(drawingPoint) < EchelonBundle.screenBundle.height){
				canvas.drawLine(
						Util.nuToX(0), drawingPoint,
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, drawingPoint, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint -= EchelonBundle.configBundle.tickX;
			}
			
			////////???????????????????????

			// Draw tick marks
//			float startPoint = EchelonBundle.screenBundle.oriY;
//			float moveBy = EchelonBundle.configBundle.tickY / EchelonBundle.screenBundle.scaleY;
//			float goal = EchelonBundle.screenBundle.height;
//			
////Log.d(TAG, "start = " + startPoint);
////Log.d(TAG, "goal = " + goal);
////Log.d(TAG, "move = " + moveBy);
//			
//			float starter = EchelonBundle.screenBundle.oriY;
//			while(starter > 0){
//				starter -= moveBy;
//				canvas.drawLine(
//						EchelonBundle.screenBundle.oriX,		// start x
//						startPoint, 						// start y
//						EchelonBundle.screenBundle.oriX + EchelonBundle.configBundle.tickHeight, 	// stop x
//						startPoint, 						// stop y
//						EchelonBundle.configBundle.axisPaint);	// paint
//			}
//			starter = EchelonBundle.screenBundle.oriY;
//			while(starter < goal){
//				starter += moveBy;
//				canvas.drawLine(
//						EchelonBundle.screenBundle.oriX,		// start x
//						startPoint, 						// start y
//						EchelonBundle.screenBundle.oriX + EchelonBundle.configBundle.tickHeight, 	// stop x
//						startPoint, 						// stop y
//						EchelonBundle.configBundle.axisPaint);	// paint
//			}
			

//			while(startPoint < EchelonBundle.screenBundle.height){
//				startPoint += moveBy;
//			}
//			
//			while(startPoint > 0){
//				canvas.drawLine(
//						EchelonBundle.screenBundle.oriX,		// start x
//						startPoint, 						// start y
//						EchelonBundle.screenBundle.oriX + EchelonBundle.configBundle.tickHeight, 	// stop x
//						startPoint, 						// stop y
//						EchelonBundle.configBundle.axisPaint);	// paint
//				startPoint -= moveBy;
//			}
			///////////////////////////////////
		}
	}
	
	public float absX(float x){
		return EchelonBundle.screenBundle.oriX + EchelonBundle.screenBundle.scaleX * x;
	}
	
	public float absY(float y){
		return EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY - EchelonBundle.screenBundle.scaleY * y;
	}
}


















