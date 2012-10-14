package com.research.test;

import com.research.Bundles.DataBundle;
import com.research.Bundles.EchelonBundle;
import com.research.Bundles.IsotopeBundle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;

public class SpectrumIsotopeView extends SpectrumView {
	
	private IsotopeBundle m_isotopeDrawn = null;
	private DataBundle m_dataDrawn = null;

	public SpectrumIsotopeView(Context context) {
		super(context);
	}
	
	public SpectrumIsotopeView(Context context, AttributeSet attr){
		super(context, attr);
	}
	public SpectrumIsotopeView(Context context, AttributeSet attr, int defStyle){
		super(context, attr, defStyle);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		super.surfaceCreated(holder);
	}
		
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		super.surfaceChanged(holder, format, width, height);
	}
		
	public void surfaceDestroyed(SurfaceHolder holder) {
		super.surfaceDestroyed(holder);
	}
	
	public void onDraw(Canvas canvas){
//		super.onDraw(canvas);
		
		
		
		
		if(canvas != null){
			canvas.drawColor(Color.BLACK);
			
			if(m_dataDrawn != null && m_dataDrawn.isDrawable){
				drawPolygonSpectrum(canvas, m_dataDrawn);
			}
			
			if(m_isotopeDrawn != null){
				drawIsotope(canvas, m_isotopeDrawn);
			}
			drawAxisSystem(canvas);
			
//			if(EchelonBundle.dataBundles != null){
//				for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
//					if(EchelonBundle.dataBundles.get(i).isDrawable){
//	//					drawSpectrum(canvas, EchelonBundle.dataBundles.get(i));
//						drawPolygonSpectrum(canvas, EchelonBundle.dataBundles.get(i));
//	//					drawLineSpectrum(canvas, EchelonBundle.dataBundles.get(i));
//						
//					}
//				}
//			}
//			drawAxisSystem(canvas);
		}
	}
	
	public void setIsotopeDrawn(IsotopeBundle isotope){
		m_isotopeDrawn = isotope;
	}
	
	public void setDataDrawn(DataBundle dataBundle){
		m_dataDrawn = dataBundle;
	}
	
	public void drawIsotope(Canvas canvas, IsotopeBundle isotope){
		for(int i = 0; i < isotope.peakEnergyKeV.size(); i++){
			
		}
	}

}
