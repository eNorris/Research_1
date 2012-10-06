package com.research.Bundles;

import android.graphics.Paint;

public class ConfigBundle{
	
	public static final int RESOLUTION_LOW = 0;
	public static final int RESOLUTION_MED = 1;
	public static final int RESOLUTION_HIGH = 2;
	public static final int RESOLUTION_XHIGH = 3;
	
	public boolean autoDetectOn = false;
	public boolean autoResizeX = true;
	public boolean autoResizeY = true;
	public boolean xAxisOn = true;
	public boolean yAxisOn = true;
	public boolean yAxisLog = false;
	public boolean xAxisLog = false;
	public float tickX = 500;
	public float tickY = 500;
	public float tickHeight = 10;
	public Paint spectrumPaint = new Paint();
	public Paint axisPaint = new Paint();
	
	public int initialXPos = 10;
	public int initalYPos = 10;
	
	public int resolution = RESOLUTION_LOW;
}