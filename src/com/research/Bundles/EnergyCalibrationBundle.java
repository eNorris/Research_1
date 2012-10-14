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
}
