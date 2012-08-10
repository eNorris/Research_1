package com.research.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public class ImportParser {
	
	public static final String TAG = "ImportParser";
	
	public static enum ParseReturnCode{
		OK,
		FILE_NOT_FOUND,
		FILE_NOT_A_FILE,
		FILE_NOT_READABLE,
		UNKNOWN_ERROR
	};
	
	public static ParseReturnCode parse(File file){
		Log.v(TAG, "Begin parse");
		
		try {
			if(!file.exists())
				return ParseReturnCode.FILE_NOT_FOUND;
			if(!file.isFile())
				return ParseReturnCode.FILE_NOT_A_FILE;
			if(!file.canRead())
				return ParseReturnCode.FILE_NOT_READABLE;
			
			Log.v(TAG, "first checks passed");
			
			BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
			String nextLine = null;
			Integer[] correspondingInts = null;
			
			Log.v(TAG, "Begin linebylineparsing");
			while((nextLine = reader.readLine()) != null){
				Log.v(TAG, "accepted a line");
				nextLine = nextLine.trim();
				if(nextLine.length() == 0){
					Log.v(TAG, "Empty line - skip");
					continue;
				}
				if(nextLine.charAt(0) == '#'){
					Log.v(TAG, "Comment - skip");
					continue;
				}
				
				Log.v(TAG, "splitting");
				String[] splitLine = nextLine.split(",");
				
				if(splitLine.length == 0){
					Log.d(TAG, "Error parsing " + file.getName()  +"couldn't split line");
					continue;
				}else{
					correspondingInts = new Integer[splitLine.length];
					for(int i = 0; i < splitLine.length; i++){
						try {
							Integer nextInt = Integer.valueOf(splitLine[i]);
							correspondingInts[i] = nextInt;
						} catch (NumberFormatException e) {
							Log.d(TAG, "Error, could not convert " + splitLine[i] + "to a number");
							e.printStackTrace();
						}
					}
				}
				Log.v(TAG, "finished parsing");
			}
			
			if(correspondingInts.length == 0){
				Log.d(TAG, "corresponding data set has length 0!");
			}else{
				DataBundle newBundle = new DataBundle();
				newBundle.bins = correspondingInts.length;
				newBundle.data = new int[correspondingInts.length];
				for(int i = 0; i < newBundle.data.length; i++)
					newBundle.data[i] = correspondingInts[i].intValue();
				EchelonBundle.dataBundles.add(newBundle);
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			Log.e(TAG, "Exception: FileNotFound");
			e.printStackTrace();
			return ParseReturnCode.FILE_NOT_FOUND;
		} catch (IOException e) {
			Log.e(TAG, "Exception: I/O Exception");
			e.printStackTrace();
			return ParseReturnCode.UNKNOWN_ERROR;
		}
		
		return ParseReturnCode.OK;
	}
	
}
