package com.research.Bundles;

import java.util.ArrayList;

public class EnergyCalibrationBundle {
	ArrayList<Double> expectedEnergyKeV = new ArrayList<Double>();
	ArrayList<Double> experimentalChannel = new ArrayList<Double>();
	
	public double energyCalScale = 0.0;
	public double energyCalTrans = 0.0;
	public double rSquared = 0.0;
	
	public void addDataPoint(double energyKeV, int channel){
		expectedEnergyKeV.add(energyKeV);
		experimentalChannel.add((double) channel);
	}
	
	// FIXME - Double check
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
		calcRSquared();
	}
	
	public double channelToEnergy(int channelNo){
		return energyCalScale * (double) channelNo + energyCalTrans;
	}
	
	public double channelFromEnergyD(double energyKeV){
		return (energyKeV - energyCalTrans) / energyCalScale;
	}
	
	public int channelFromEnergy(double energyKeV){
		return (int) Math.round(channelFromEnergyD(energyKeV));
	}
	
	// FIXME - Check this 
	private void calcRSquared(){
		double ssTot = 0.0;
		double ssErr = 0.0;
		double yBar = 0.0;
		
		for(double d : expectedEnergyKeV)
			yBar += d;
		yBar /= (double) expectedEnergyKeV.size();
		
		for(int i = 0; i < expectedEnergyKeV.size(); i++){
			double dd = expectedEnergyKeV.get(i) - yBar;
			ssTot += dd * dd;
			
			double ee = expectedEnergyKeV.get(i) - (energyCalScale * (double)experimentalChannel.get(i) + energyCalTrans);
			ssErr += ee * ee;
		}
		
		rSquared = 1 - (ssErr / ssTot);
	}
}
