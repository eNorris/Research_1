package com.research.test;

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
	public boolean autoDetectOn;
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
















