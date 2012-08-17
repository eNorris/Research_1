package com.research.Utilities;

import java.io.File;
import java.util.Random;

import android.graphics.Color;

import com.research.Bundles.EchelonBundle;

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
		
		return (int) (0xff000000 + nextRed*0x00ff0000 + nextGreen*0x0000ff00 + nextBlue*0x000000ff);
		
//		float[] hsv = new float[3];
//		Color.colorToHSV(hue, hsv);
//		
//		float green = Color.green((int) hsv[0]);
//		
//		return 1;
	}
}
