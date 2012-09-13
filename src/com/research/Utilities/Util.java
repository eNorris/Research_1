package com.research.Utilities;

import java.io.File;
import java.util.Random;

import android.graphics.Color;
//import android.util.Log;

import com.research.Bundles.EchelonBundle;

public class Util {
	
	public static final String TAG = "Util";
	
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
	
	static public float nuLeft(){
		return xToNu(0);
	}
	
	static public float nuRight(){
		return xToNu(EchelonBundle.screenBundle.width);
	}
	
	static public float adaTop(){
		return yToAda(0);
	}
	
	static public float adaBottom(){
		return yToAda(EchelonBundle.screenBundle.height);
	}
	
	static public float nuOrigin(){
		return nuToX(0);
	}
	
	static public float adaOrigin(){
		return adaToY(0);
	}
	
	static public String getFileExtension(File file){
		return file.getName().substring(file.getName().lastIndexOf('.') + 1).toLowerCase();
	}
	
	static public String removeFilenameWithoutExtension(File file){
		return file.getName().substring(0, file.getName().lastIndexOf('.'));
	}
	
	static public int randomHue(){
		return Color.HSVToColor(new float[]{rand.nextInt(361),1,1});
	}
	
	static public int lightenHue(int hue, double amount){
		
		float nextGreen = Color.green(hue);
		float nextRed = Color.red(hue);
		float nextBlue = Color.blue(hue);
		
		nextGreen += (255 - nextGreen) * amount;
		nextRed += (255 - nextRed) * amount;
		nextBlue += (255 - nextBlue) * amount;
		
		int finalRed = ((int)nextRed) << 16;
		int finalGreen = ((int)nextGreen) << 8;
		int finalBlue = ((int)nextBlue);
	
		return (0xff000000 + finalRed + finalGreen + finalBlue);
	}
	
	static public int setTransparancy(int hue, float alpha){
		int  newalpha = (int) alpha*255 << 24;
		return hue << 8 >> 8 + newalpha;
	}
}









