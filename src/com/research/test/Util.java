package com.research.test;

import java.util.Random;

public class Util {
	
	public static Random rand = new Random();
	
	// x-y <-> nu-ada transforms
	static public float nuToX(float nuCoord){
		return EchelonBundle.screenBundle.oriNu + nuCoord * EchelonBundle.screenBundle.scaleNu;
	}
	static public float adaToY(float adaCoord){
		return EchelonBundle.screenBundle.oriAda - adaCoord * EchelonBundle.screenBundle.scaleAda;
	}
	static public float xToNu(float xCoord){
		return (xCoord - EchelonBundle.screenBundle.oriNu)/EchelonBundle.screenBundle.scaleNu;
	}
	static public float yToAda(float yCoord){
		return (EchelonBundle.screenBundle.oriAda - yCoord)/EchelonBundle.screenBundle.scaleAda;
	}
	
	// shortcuts to nu and ada
//	static public float nu(){
//		return EchelonBundle.screenBundle.oriNu;
//	}
//	static public float ada(){
//		return EchelonBundle.screenBundle.oriAda;
//	}
}
