package com.research.Bundles;

import java.util.ArrayList;

public class IsotopeBundle {
	public String name = "<default>";
	ArrayList<Integer> peaks = new ArrayList<Integer>();
	
	IsotopeBundle(String isotopeName){
		name = isotopeName;
	}
	
	public static final IsotopeBundle nullIsotope = new IsotopeBundle("<NULL ISOTOPE>");
}
