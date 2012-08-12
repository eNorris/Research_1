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
		
		if(!file.exists())
			return ParseReturnCode.FILE_NOT_FOUND;
		if(!file.isFile())
			return ParseReturnCode.FILE_NOT_A_FILE;
		if(!file.canRead())
			return ParseReturnCode.FILE_NOT_READABLE;
		
		Log.v(TAG, "first checks passed");
				
		
		DataBundle newDataBundle = parseSDATA(file);
		
		String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
		
		if(ext == "sdata"){
			Log.v(TAG, "Parsing SDATA file");
			newDataBundle = parseSDATA(file);
		}else if(ext == "csv"){
			Log.v(TAG, "Parsing CSV file");
			newDataBundle = parseCSV(file);
		}else{
			Log.d(TAG, "Unknown filetype");
		}
		
		if(newDataBundle.data.length == 0){
			Log.d(TAG, "No data was collected!");
		}
		
		Log.v(TAG, "Got " + newDataBundle.data.length + " data points from file " + file.getName());
		
		return ParseReturnCode.OK;
	}
	
	
	static DataBundle parseCSV(File file){
		BufferedReader reader = null;
		DataBundle toReturn = new DataBundle();
		
		// FIXME - WRITE THIS CODE
		
		return toReturn;
	}
	
	
	static DataBundle parseSDATA(File file){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file.toString()));
		} catch (FileNotFoundException e1) {
			Log.d(TAG, "@ImportParser::parseSDATA(): Failed to created BufferedReader for file \"" + file.toString() + "\"");
			e1.printStackTrace();
		}
		DataBundle toReturn = new DataBundle();
		
		String nextLine = null;
		Integer[] correspondingInts = null;
		
		Log.v(TAG, "Begin line by line parsing");
		try {
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
			}
		} catch (IOException e) {
			Log.d(TAG, "Caught an IO exception in ImportParser::parseSDATA()");
			e.printStackTrace();
		}
			Log.v(TAG, "finished parsing");
		return toReturn;
	}
	
}


























