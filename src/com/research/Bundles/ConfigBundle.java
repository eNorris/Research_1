package com.research.Bundles;

import android.graphics.Paint;

public class ConfigBundle{
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
}