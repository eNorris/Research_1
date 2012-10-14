package com.research.Bundles;

import java.util.ArrayList;

public class EnergyCalibrationBundle {
	ArrayList<Double> expectedEnergyKeV = new ArrayList<Double>();
	ArrayList<Double> experimentalChannel = new ArrayList<Double>();
	
	public double energyCalScale = 0.0;
	public double energyCalTrans = 0.0;
	
	public void addDataPoint(double energyKeV, int channel){
		expectedEnergyKeV.add(energyKeV);
		experimentalChannel.add((double) channel);
	}
	
	public void calculate(){

		double n = (double) expectedEnergyKeV.size();
		double sumXY = 0.0;
		double sumX = 0.0;
		double sumY = 0.0;
		double sumXpow2 = 0.0;
		
		for(int i = 0; i < expectedEnergyKeV.size(); i++){
			double x = experimentalChannel.get(i);
			double y = expectedEnergyKeV.get(i);
			sumX += x;
			sumY += y;
			sumXY += x*y;
			sumXpow2 += x*x;
		}
		
		energyCalScale = (n*sumXY - sumX*sumY) / (n*sumXpow2 - sumX*sumX);
		energyCalTrans = (sumY - energyCalScale*sumX) / n;
	}
}
