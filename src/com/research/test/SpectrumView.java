package com.research.test;

import com.research.Bundles.DataBundle;
import com.research.Bundles.EchelonBundle;
import com.research.Utilities.SpectrumThread;
import com.research.Utilities.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
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
			if(EchelonBundle.dataBundles != null){
				for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
					if(EchelonBundle.dataBundles.get(i).isDrawable){
//						drawSpectrum(canvas, EchelonBundle.dataBundles.get(i));
//						drawLineSpectrum(canvas, EchelonBundle.dataBundles.get(i));
						drawPolygonSpectrum(canvas, EchelonBundle.dataBundles.get(i));
					}
				}
			}
			drawAxisSystem(canvas);
		}
	}
	
	public void drawSpectrum(Canvas canvas, DataBundle bundle){
		float width = EchelonBundle.screenBundle.width / bundle.data.length * EchelonBundle.screenBundle.scaleNu;
		float max = 0;
		for(int i = 0; i < bundle.data.length; i++)
			if(bundle.data[i] > max)
				max = bundle.data[i];
		float heightmod = canvas.getHeight() / max;
		
			// Draw the spectrum
		float bottom = Util.adaToY(0);
		for(int i = 0; i < bundle.data.length; i++){
			float left = i*width+EchelonBundle.screenBundle.oriNu;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriNu;
			float top = Util.adaToY(bundle.data[i]*heightmod);
//			canvas.drawRect(left, top, right, bottom, spectrumPaint);
			canvas.drawRect(left, top, right, bottom, bundle.paint);
		}
	}
	
	public void drawLineSpectrum(Canvas canvas, DataBundle bundle){
		float width = EchelonBundle.screenBundle.width / bundle.data.length * EchelonBundle.screenBundle.scaleNu;
		float max = 0;
		for(int i = 0; i < bundle.data.length; i++)
			if(bundle.data[i] > max)
				max = bundle.data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Draw the line graph
		for(int i = 0; i < bundle.data.length - 1; i++){
			float left = i*width+EchelonBundle.screenBundle.oriNu;
			float right = (i+1)*width + EchelonBundle.screenBundle.oriNu;
//			canvas.drawLine(left, Util.adaToY(bundle.data[i]*heightmod),
//					right, Util.adaToY(bundle.data[i+1]*heightmod), 
//					spectrumLinePaint
//			);
			canvas.drawLine(left, Util.adaToY(bundle.data[i]*heightmod),
			right, Util.adaToY(bundle.data[i+1]*heightmod), 
			bundle.linePaint
	);
		}
	}
	
	public void drawPolygonSpectrum(Canvas canvas, DataBundle bundle){
		float width = EchelonBundle.screenBundle.width / bundle.data.length * EchelonBundle.screenBundle.scaleNu;
		float max = 0;
		for(int i = 0; i < bundle.data.length; i++)
			if(bundle.data[i] > max)
				max = bundle.data[i];
		float heightmod = canvas.getHeight() / max;
		
		Path polygon = new Path();
		
		float xPos = EchelonBundle.screenBundle.oriNu;
		float yPos = EchelonBundle.screenBundle.oriAda;
		
		polygon.moveTo(xPos, yPos);
		for(int i = 0; i < bundle.data.length; i++){
			xPos += width;
			yPos = Util.adaToY(bundle.data[i]*heightmod);
			polygon.lineTo(xPos, yPos);
		}
		xPos+= width;
		polygon.lineTo(xPos, EchelonBundle.screenBundle.oriAda);
		
//		polygon.lineTo(Util.nuToX(Util.nuRight()), Util.adaToY(Util.adaBottom()));
		
		canvas.drawPath(polygon, bundle.paint);
		
//		
//		wallpaint.setColor(Color.GRAY);
//	      wallpaint.setStyle(Style.FILL);
//	      wallpath.reset(); // only needed when reusing this path for a new build
//	      wallpath.moveTo(x[0], y[0]); // used for first point
//	wallpath.lineTo(x[1], y[1]);
//	      wallpath.lineTo(x[2], y[2]);
//	      wallpath.lineTo(x[3], y[3]);
//	      wallpath.lineTo(x[0], y[0]); // there is a setLastPoint action but i found it not to work as expected
//	      canvas.drawPath(wallpath, wallpaint);

	}
	
	public void drawAxisSystem(Canvas canvas){
		if(EchelonBundle.dataBundles.size() == 0 || EchelonBundle.dataBundles.get(0).data == null || EchelonBundle.dataBundles.get(0).data.length == 0)
			return;

		if(EchelonBundle.configBundle.xAxisOn){
			canvas.drawLine(
					0, Util.adaToY(0),
					EchelonBundle.screenBundle.width, Util.adaToY(0),
					EchelonBundle.configBundle.axisPaint
			);
			
			

			float dataPointWidth = EchelonBundle.screenBundle.width * EchelonBundle.screenBundle.scaleNu / (float) EchelonBundle.dataBundles.get(0).data.length;
			
				// Draw tick marks to left of origin
			float drawingPoint = EchelonBundle.screenBundle.oriNu;
			int tickValue = 1;
			
			while(Util.nuToX(drawingPoint) > 0){
				canvas.drawLine(
						drawingPoint, Util.adaToY(0), 
						drawingPoint, Util.adaToY(0) - EchelonBundle.configBundle.tickHeight, 
						EchelonBundle.configBundle.axisPaint
				);
				drawingPoint -= EchelonBundle.configBundle.tickX * dataPointWidth;
				
					// Draw the values on the axis
				canvas.drawText("" + -1 * tickValue * EchelonBundle.configBundle.tickX, 	// Text
						drawingPoint, Util.adaToY(0) + 20, 									// (x,y)
						EchelonBundle.configBundle.axisPaint								// paint
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
					Util.nuToX(0), Util.adaToY(Util.adaTop()),//Util.adaTop(),
					Util.nuToX(0), Util.adaToY(Util.adaBottom()),//Util.adaBottom(),//EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint
			);
			
			int maxYValue = 0;
			for(int i = 0; i < EchelonBundle.dataBundles.get(0).data.length; i++){
				if(EchelonBundle.dataBundles.get(0).data[i] > maxYValue)
					maxYValue = EchelonBundle.dataBundles.get(0).data[i];
			}
			
// FIXME - What about divide by zero error?
			float yTickDistance = EchelonBundle.screenBundle.height / ((float) maxYValue / EchelonBundle.configBundle.tickY);
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
						Util.nuToX(0) - 50, Util.adaToY(upperAdaBound), 				// (x,y)
						EchelonBundle.configBundle.axisPaint							// paint
				);
				
				canvas.drawLine(
						Util.nuToX(0), Util.adaToY(lowerAdaBound),
						Util.nuToX(0) + EchelonBundle.configBundle.tickHeight, Util.adaToY(lowerAdaBound), 
						EchelonBundle.configBundle.axisPaint
				);
				canvas.drawText("" + -1* currentTick * EchelonBundle.configBundle.tickY, 	// Text
						Util.nuToX(0) - 50, Util.adaToY(lowerAdaBound), 					// (x,y)
						EchelonBundle.configBundle.axisPaint								// paint
				);
				currentTick++;
				upperAdaBound += yTickDistance;
				lowerAdaBound -= yTickDistance;
			}
		}
	}
}


















