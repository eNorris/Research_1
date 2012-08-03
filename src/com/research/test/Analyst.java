package com.research.test;

import java.util.ArrayList;

public class Analyst {
	
	public static enum Isotope{
		CS137,CO60
	}

	public static float averageOfData(int index){
		if(index >= EchelonBundle.dataBundles.length)
			return Float.NaN;
		float toReturn = 0.0f;
		for(int i = 0; i < EchelonBundle.dataBundles[index].data.length; i++){
			toReturn += EchelonBundle.dataBundles[index].data[i];
		}
		return toReturn/EchelonBundle.dataBundles[index].data.length;
	}
	
	public static float devBetweenSpectrums(int dataset1, int dataset2){
		return 1.0f;
	}
	
	public static ArrayList<Integer> autoPeakDetect(int dataset){
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		
		if(dataset >= EchelonBundle.dataBundles.length)
			return null;
		
		if(EchelonBundle.dataBundles[dataset] == null)
			return null;
		
		for(int i = 5; i < EchelonBundle.dataBundles[dataset].data.length; i++){
			if(EchelonBundle.dataBundles[dataset].data[i] > EchelonBundle.dataBundles[dataset].data[i-5] + 50){
				toReturn.add(i);
				toReturn.remove(i-5);
			}
		}
		
		return toReturn;
	}
	
	// Assumes Cs-137
	public static float energyCalibrate(int dataset, ArrayList<Integer> peaks){
		float toReturn = 1.0f;
		
		if(peaks.size() == 1){
			toReturn = EchelonBundle.dataBundles[dataset].data[peaks.get(0)] / 662;
		}
		
		return toReturn;
	}
	
	public static Isotope isotopeId(){
		return Isotope.CS137;
	}
}












