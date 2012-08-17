package com.research.Bundles;

import android.graphics.Color;

public class DataBundle{
	public int[] data = null;
	public double energyCalibration = 1.0;
	public double resolution = 1.0;
	public boolean isDrawable = false;
	public String name = "Data Set Name";
	public int color = Color.BLUE;
	
	public DataBundle(){
		// Do Nothing
	}
	
	public void clearData(){
		if(data != null && data.length > 0)
			data = new int[data.length];
	}

}