package com.research.test;

import android.graphics.Paint;

public class EchelonBundle {
	public static ConfigBundle configBundle = new ConfigBundle();
	public static ExportBundle exportBundle = new ExportBundle();
	public static ImportBundle importBundle = new ImportBundle();
	public static int dataBundleCount = 1;
	public static DataBundle[] dataBundles = new DataBundle[1];
	
	public static void addDataBundle(){
		DataBundle[] t = new DataBundle[dataBundleCount++];
		for(int i = 0; i < dataBundleCount-1; i++){
			t[i] = dataBundles[i];
		}
		dataBundles[dataBundleCount-1] = new DataBundle();
		dataBundles = t;
	}
	
	public static void removeDataBundle(int index){
		DataBundle[] t = new DataBundle[dataBundleCount--];
		for(int i = 0; i < index; i++){
			t[i] = dataBundles[i];
		}
		for(int i = index; i < dataBundleCount+1; i++){
			t[i-1] = dataBundles[i];
		}
		dataBundles = t;
	}
	
	public static void clearAll(){
		configBundle = new ConfigBundle();
		exportBundle = new ExportBundle();
		importBundle = new ImportBundle();
		dataBundleCount = 1;
		dataBundles = new DataBundle[1];
	}
}

class ConfigBundle{
	public boolean autoDetectOn = false;
	public boolean autoResizeX = true;
	public boolean autoResizeY = true;
	public boolean xAxisOn = true;
	public float xMin = 0.0f,
			xMax = 1023.0f,
			xTick = 100.0f;
	public float yMin = 0.0f,
			yMax = 5000.0f,
			yTick = 100.0f;
//	public int xMin = 0;
//	public int xMax = 1023;
//	public int xTick = 100;
	public boolean yAxisOn = true;
//	public int yMin = 0;
//	public int yMax = 1023;
//	public int yTick = 100;
	public Paint spectrumPaint = new Paint();
	public Paint axisPaint = new Paint();
}

class ExportBundle{
	public static enum ExportTypes{
		NONE, XML, TXT, PROJECT
	};
	
	public String outFile = new String();
	public ExportTypes exportType = ExportTypes.NONE;
}

class ImportBundle{
	public String inFile = new String();
}

class DataBundle{
	public int bins = 1024;
	public int[] data = new int[bins];
	
	public boolean setBinCount(int numBins){
		if(numBins > 0){
			bins = numBins;
			return true;
		}else{
			return false;
		}
	}
	
	public void clearData(){
		for(int i = 0; i < bins; i++){
			data[i] = 0;
		}
	}
}
















