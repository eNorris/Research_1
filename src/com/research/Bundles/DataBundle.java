package com.research.Bundles;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class DataBundle{
	public int[] data = null;
	public double energyCalibration = 1.0;
	public double resolution = 1.0;
	public boolean isDrawable = false;
	public String name = "Data Set Name";
	public Paint paint = new Paint();
	public Paint linePaint = new Paint();
	public ArrayList<DataBundleRoi> rois = new ArrayList<DataBundleRoi>();
	
	public DataBundle(){
		paint.setColor(Color.BLUE);
		paint.setColor(Color.YELLOW);
		paint.setStyle(Style.FILL);
	}
	
	public void clearData(){
		if(data != null && data.length > 0)
			data = new int[data.length];
		rois.clear();
	}
}

class DataBundleRoi{
	int startChannel = 0;
	int stopChannel = 0;
	public Paint paint = new Paint();
	
	public DataBundleRoi(){
		paint.setColor(Color.RED);
	}
}