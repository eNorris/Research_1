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

	private Paint spectrumPaint = new Paint();
	private Paint spectrumLinePaint = new Paint();
	
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
//		EchelonBundle.screenBundle.xMin = 0;
//		EchelonBundle.screenBundle.yMin = 0;
//		EchelonBundle.screenBundle.height = (float) (EchelonBundle.screenBundle.yMax = getHeight());
//		EchelonBundle.screenBundle.width = (float) (EchelonBundle.screenBundle.xMax = getWidth());
		EchelonBundle.screenBundle.height = getHeight();
		EchelonBundle.screenBundle.width = getWidth();
		EchelonBundle.screenBundle.oriAda = EchelonBundle.screenBundle.height - 30;
		EchelonBundle.screenBundle.oriNu = 60;
		
		// Set the spectrum color
		// TODO - This should be user set
		spectrumPaint.setColor(Color.BLUE);
		spectrumLinePaint.setColor(Color.YELLOW);
		EchelonBundle.configBundle.axisPaint.setColor(Color.GREEN);
		
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
		
		// Uncomment to test random scaling
//		EchelonBundle.screenBundle.scaleNu += Util.rand.nextFloat()/100.0f * (Util.rand.nextBoolean() == true ? 1 : -1);
//		EchelonBundle.screenBundle.scaleAda += Util.rand.nextFloat()/100.0f * (Util.rand.nextBoolean() == true ? 1 : -1);
		
		if(canvas != null){
				// Reset the canvas to solid black
			canvas.drawColor(Color.BLACK);
				// If there is nothin to draw, skip this phase
			if(EchelonBundle.dataBundles != null && EchelonBundle.dataBundles.length != 0){
				drawSpectrum(canvas);
				drawLineSpectrum(canvas);
			}
			drawAxisSystem(canvas);
		}
	}
	
	public void drawSpectrum(Canvas canvas){
		float width = EchelonBundle.screenBundle.width / EchelonBundle.dataBundles[0].data.length * EchelonBundle.screenBundle.scaleNu;
		float max = 0;
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++)
			if(EchelonBundle.dataBundles[0].data[i] > max)
				max = EchelonBundle.dataBundles[0].data[i];
		float heightmod = canvas.getHeight() / max;
		
			// Draw the bar graph
		float bottom = Util.adaToY(0);
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++){
			float left = i*width+EchelonBundle.screenBundle.oriNu;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriNu;
			float top = Util.adaToY(EchelonBundle.dataBundles[0].data[i]*heightmod);
			canvas.drawRect(left, top, right, bottom, spectrumPaint);
		}
	}
	
	public void drawLineSpectrum(Canvas canvas){
		float width = EchelonBundle.screenBundle.width / EchelonBundle.dataBundles[0].data.length * EchelonBundle.screenBundle.scaleNu;
		float max = 0;
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++)
			if(EchelonBundle.dataBundles[0].data[i] > max)
				max = EchelonBundle.dataBundles[0].data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Draw the line graph
		for(int i = 0; i < EchelonBundle.dataBundles[0].data.length - 1; i++){
			float left = i*width+EchelonBundle.screenBundle.oriNu;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriNu;
			canvas.drawLine(left, Util.adaToY(EchelonBundle.dataBundles[0].data[i]*heightmod),
					right, Util.adaToY(EchelonBundle.dataBundles[0].data[i+1]*heightmod), 
					spectrumLinePaint
			);
		}
	}
	
	public void drawAxisSystem(Canvas canvas){
		if(EchelonBundle.configBundle.xAxisOn){
				// Draw the main x-axis
			canvas.drawLine(
					0, Util.adaToY(0),
					EchelonBundle.screenBundle.width, Util.adaToY(0),
					EchelonBundle.configBundle.axisPaint
			);
			
			float dataPointWidth = EchelonBundle.screenBundle.width * EchelonBundle.screenBundle.scaleNu / (float) EchelonBundle.dataBundles[0].data.length;
			
				// Draw tick marks to left of origin
			float drawingPoint = EchelonBundle.screenBundle.oriNu;
			int tickValue = 1;
			while(Util.nuToX(drawingPoint) > 0){
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
//				drawingPoint -= EchelonBundle.configBundle.tickX * EchelonBundle.screenBundle.scaleNu;
				drawingPoint -= EchelonBundle.configBundle.tickX * dataPointWidth;
				
					// Draw the values on the axis
				canvas.drawText("" + -1 * tickValue * EchelonBundle.configBundle.tickX, 	// Text
						drawingPoint, Util.adaToY(0) + 20, 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				tickValue += 1;
			}
			
				// Draw tick marks to right of origin
			drawingPoint = EchelonBundle.screenBundle.oriNu;
			tickValue = 1;
			
			while(drawingPoint < EchelonBundle.screenBundle.width){	
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
//				drawingPoint += EchelonBundle.configBundle.tickX * EchelonBundle.screenBundle.scaleNu;
				drawingPoint += EchelonBundle.configBundle.tickX * dataPointWidth;
				
				canvas.drawText("" + tickValue * EchelonBundle.configBundle.tickX, 	// Text
						drawingPoint, Util.adaToY(0) + 20, 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				tickValue += 1;
			}
		}
		
			// Draw main Y axis
		if(EchelonBundle.configBundle.yAxisOn){
			canvas.drawLine(
					Util.nuToX(0), 0,
					Util.nuToX(0), EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint
			);
			
			int maxYValue = 0;
			for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++){
				if(EchelonBundle.dataBundles[0].data[i] > maxYValue)
					maxYValue = EchelonBundle.dataBundles[0].data[i];
			}
			
// FIXME - What about divide by zero error?
			float yTickDistance = EchelonBundle.screenBundle.height / ((float) maxYValue / EchelonBundle.configBundle.tickY);
Log.d(TAG, "yTickDist = " + yTickDistance);
			float currentTick = 1;
			float lowerAdaBound = -1*yTickDistance;
			float upperAdaBound = yTickDistance;
			
			while(upperAdaBound < Util.adaTop() || lowerAdaBound > Util.adaBottom()){
				canvas.drawLine(
						Util.nuToX(0), Util.adaToY(upperAdaBound),
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, Util.adaToY(upperAdaBound), 
						EchelonBundle.configBundle.axisPaint
				);
				canvas.drawText("" + currentTick * EchelonBundle.configBundle.tickY, 	// Text
						Util.nuToX(0) - 50, Util.adaToY(upperAdaBound), 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				
				canvas.drawLine(
						Util.nuToX(0), Util.adaToY(lowerAdaBound),
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, Util.adaToY(lowerAdaBound), 
						EchelonBundle.configBundle.axisPaint
				);
				canvas.drawText("" + -1* currentTick * EchelonBundle.configBundle.tickY, 	// Text
						Util.nuToX(0) - 50, Util.adaToY(lowerAdaBound), 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				currentTick++;
				upperAdaBound += yTickDistance;
				lowerAdaBound -= yTickDistance;
			}
			
			
			
			
			
			
			
/*// FIXME = do this somewhere else where it is more efficient!
			int mymax = 5000;
//			for(int i = 0; i < EchelonBundle.dataBundles[0].data.length; i++){
//				if(EchelonBundle.dataBundles[0].data[i] > mymax)
//					mymax = EchelonBundle.dataBundles[0].data[i];
//			}
			float distBetweenYTicks = EchelonBundle.screenBundle.height * EchelonBundle.screenBundle.scaleAda / (float) mymax;
			
				// Draw tick marks above the origin
			float drawingPoint = 0;//EchelonBundle.screenBundle.oriAda;
			int tickValue = 1;
			while(Util.adaToY(drawingPoint) > 0){
				canvas.drawLine(
						Util.nuToX(0), Util.adaToY(drawingPoint),
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, Util.adaToY(drawingPoint), 
						EchelonBundle.configBundle.axisPaint
				);
//				drawingPoint += EchelonBundle.configBundle.tickY * EchelonBundle.screenBundle.scaleAda;
				drawingPoint += EchelonBundle.configBundle.tickY * distBetweenYTicks;
				
// FIXME - Correct offset to be dynamic
				canvas.drawText("" + tickValue * EchelonBundle.configBundle.tickY, 	// Text
						Util.nuToX(0) - 50, drawingPoint, 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				tickValue++;
			}
			
				// Draw tick marks below the origin
			tickValue = -1;
			drawingPoint = 0;//EchelonBundle.screenBundle.oriAda;
			while(Util.adaToY(drawingPoint) < EchelonBundle.screenBundle.height){
				canvas.drawLine(
						Util.nuToX(0), Util.adaToY(drawingPoint),
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, Util.adaToY(drawingPoint), 
						EchelonBundle.configBundle.axisPaint
				);
//				drawingPoint -= EchelonBundle.configBundle.tickY * EchelonBundle.screenBundle.scaleAda;
				drawingPoint -= EchelonBundle.configBundle.tickY * distBetweenYTicks;
				
				canvas.drawText("" + tickValue * EchelonBundle.configBundle.tickY, 	// Text
						Util.nuToX(0) - 50, drawingPoint, 							// (x,y)
						EchelonBundle.configBundle.axisPaint						// paint
				);
				tickValue--;
			}*/
			
			
			
			
			
			
			
			
			
		}
	}
}


















