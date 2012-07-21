package com.research.test;

public class Analyst {

	public static float averageOfData(int index){
		if(index >= EchelonBundle.dataBundles.length)
			return Float.NaN;
		float toReturn = 0.0f;
		for(int i = 0; i < EchelonBundle.dataBundles[index].data.length; i++){
			toReturn += EchelonBundle.dataBundles[index].data[i];
		}
		return toReturn/EchelonBundle.dataBundles[index].data.length;
	}
}
