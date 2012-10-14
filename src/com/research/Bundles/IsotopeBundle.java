package com.research.Bundles;

import java.util.ArrayList;

public class IsotopeBundle {
	
	private static boolean m_commonIsotopesGenerated = false;
	
	public String name = "<default>";
	public ArrayList<Double> peakEnergyKeV = new ArrayList<Double>();
	public ArrayList<Double> peakEmissionProb = new ArrayList<Double>();
	
	IsotopeBundle(String isotopeName){
		name = isotopeName;
	}
	
	public IsotopeBundle normalize(){
		double sum = 0.0;
		for(double d : peakEmissionProb){
			sum += d;
		}
		for(int i = 0; i < peakEmissionProb.size(); i++){
			peakEmissionProb.set(i, peakEmissionProb.get(i) / sum);
		}
		return this;
	}
	
	public static final IsotopeBundle nullIsotope = new IsotopeBundle("<NULL ISOTOPE>");
	public static final IsotopeBundle cs137Isotope = new IsotopeBundle("<CS-137>");
	public static final IsotopeBundle co60Isotope = new IsotopeBundle("<CO-60>");
	
	public static void buildCommonIsotopes(){
		if(!m_commonIsotopesGenerated){
			cs137Isotope.peakEnergyKeV.add(662.0);
			cs137Isotope.peakEmissionProb.add(1.0);
			co60Isotope.peakEmissionProb.add(0.5);
			co60Isotope.peakEmissionProb.add(0.5);
			co60Isotope.peakEnergyKeV.add(1120.0);
			co60Isotope.peakEnergyKeV.add(1330.0);
			m_commonIsotopesGenerated = true;
		}
	}
}
