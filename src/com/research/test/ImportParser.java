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
		FILE_NOT_PARSABLE,
		UNKNOWN_ERROR
	};
	
	public static ParseReturnCode parse(File file){
		Log.v(TAG, "Begin parse()");
		
		if(!file.exists())
			return ParseReturnCode.FILE_NOT_FOUND;
		if(!file.isFile())
			return ParseReturnCode.FILE_NOT_A_FILE;
		if(!file.canRead())
			return ParseReturnCode.FILE_NOT_READABLE;
		
		Log.v(TAG, "first checks passed");
				
		DataBundle newDataBundle = null;
		
		// TODO - Check indexing incase the name ends in a '.'
		String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1).toLowerCase();
		
		if(ext.equals("sdata")){
			Log.v(TAG, "Parsing SDATA file");
			newDataBundle = parseSDATA(file);
		}else if(ext.equals("csv")){
			Log.v(TAG, "Parsing CSV file");
			newDataBundle = parseCSVTKA(file);
		}else if(ext.equals("tka")){
			Log.v(TAG, "Parsing TKA file");
			newDataBundle = parseCSVTKA(file);
//			newDataBundle = parseTKA(file);
		}else{
			Log.d(TAG, "Unknown filetype");
			return ParseReturnCode.FILE_NOT_PARSABLE;
		}
		
		// Report errors
		if(newDataBundle == null){
			Log.d(TAG, "Could not parse - DataBundle was null");
			return ParseReturnCode.FILE_NOT_PARSABLE;
		}else if(newDataBundle.data == null){
			Log.d(TAG, "Could not parse - DataBundle.data was null");
			return ParseReturnCode.FILE_NOT_PARSABLE;
		}else if(newDataBundle.data.length == 0){
			Log.d(TAG, "Could not parse - DataBundle.data is empty");
			return ParseReturnCode.FILE_NOT_PARSABLE;
		}
		
		Log.v(TAG, "Got " + newDataBundle.data.length + " data points from file " + file.getName());
		Log.v(TAG, "Enabling spectrum view");
		EchelonBundle.dataLoaded = Boolean.TRUE;
		
		// Add the data
		EchelonBundle.dataBundles.add(newDataBundle);
		
		// return success
		return ParseReturnCode.OK;
	}
	
//	static DataBundle parseTKA(File file){
//		
//		// FIXME - Write this
//		
//		return new DataBundle();
//	}
	
	// TODO - Based on SDATA - needs some stuff taken out should blindly seek out commas for splitting
	static DataBundle parseCSVTKA(File file){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file.toString()));
		} catch (FileNotFoundException e) {
			Log.d(TAG, "@ImportParser::parseCSVTKA(): Failed to created BufferedReader for file \"" + file.toString() + "\"");
			e.printStackTrace();
		}
		
		DataBundle toReturn = new DataBundle();
		toReturn.isDrawable = true;
		String thisLine = null;
		
		Log.v(TAG, "Begin line by line parsing");
		try {
			while((thisLine = reader.readLine()) != null){
				Log.v(TAG, "accepted a line");
				thisLine = thisLine.trim();
				if(thisLine.length() == 0){
					Log.v(TAG, "Empty line - skip");
					continue;
				}
				
				Log.v(TAG, "splitting");
				String[] splitLine = thisLine.split(",");
				
				if(splitLine.length == 0){
					Log.d(TAG, "Error parsing " + file.getName()  +"couldn't split line");
					continue;
				}else{
					toReturn.data = new int[splitLine.length];
					for(int i = 0; i < splitLine.length; i++){
						try {
							toReturn.data[i] = Integer.valueOf(splitLine[i]);
						} catch (NumberFormatException e) {
							Log.d(TAG, "Error, could not convert " + splitLine[i] + "to a number");
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			Log.d(TAG, "Caught an IO exception in ImportParser::parseCSVTKA()");
			e.printStackTrace();
		}
		Log.v(TAG, "finished parsing");
			
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
		
		// FIXME - This should be set via the importer
		toReturn.isDrawable = true;
		
		String thisLine = null;
		// TODO - Don't use this intermediate array, just cram everything into toReturn.data since I know
		// after parsing how long it is.
//		Integer[] correspondingInts = null;
		int channelcheck = 0;
		boolean channelchecked = false;
		
		Log.v(TAG, "Begin line by line parsing");
		try {
			while((thisLine = reader.readLine()) != null){
				Log.v(TAG, "accepted a line");
				thisLine = thisLine.trim();
				if(thisLine.length() == 0){
					Log.v(TAG, "Empty line - skip");
					continue;
				}
				if(thisLine.charAt(0) == '#'){
					Log.v(TAG, "Comment - skip");
					continue;
				}
				if(thisLine.contains("=")){
					if(thisLine.subSequence(0, thisLine.lastIndexOf('=')).equals("DATASET")){
						// Do nothing with the DATASET number
						Log.v(TAG, "Parsed DATASET");
						continue;
					}else if(thisLine.subSequence(0, thisLine.lastIndexOf('=')).equals("DATASETNAME")){
						Log.v(TAG, "Parsed DATASETNAME");
						toReturn.name = (String) thisLine.subSequence(thisLine.lastIndexOf('=')+1, thisLine.length());
						continue;
					}else if(thisLine.subSequence(0, thisLine.lastIndexOf('=')).equals("CHANNELCOUNT")){
						channelcheck = Integer.parseInt((String) thisLine.subSequence(thisLine.lastIndexOf('=')+1, thisLine.length()));
						channelchecked = true;
						Log.v(TAG, "Parsed CHANNELCOUNT");
						continue;
					}else if(thisLine.subSequence(0, thisLine.lastIndexOf('=')).equals("DRAWABLE")){
						if(thisLine.subSequence(thisLine.lastIndexOf('=')+1, thisLine.length()).equals('1'))
							toReturn.isDrawable = true;
						else
							toReturn.isDrawable = false;
						Log.v(TAG, "Parsed DRAWABLE");
						continue;
					}
				}
				
				Log.v(TAG, "splitting");
				String[] splitLine = thisLine.split(",");
				
				if(splitLine.length == 0){
					Log.d(TAG, "Error parsing " + file.getName()  +"couldn't split line");
					continue;
				}else{
//					correspondingInts = new Integer[splitLine.length];
					toReturn.data = new int[splitLine.length];
					for(int i = 0; i < splitLine.length; i++){
						try {
//							Integer nextInt = Integer.valueOf(splitLine[i]);
//							correspondingInts[i] = nextInt;
							toReturn.data[i] = Integer.valueOf(splitLine[i]);
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
		
//		toReturn.data = new int[correspondingInts.length];
//		for(int i = 0; i < correspondingInts.length; i++){
//			toReturn.data[i] = correspondingInts[i].intValue();
//		}
		
		if(channelchecked){
			Log.v(TAG, "Running channel check");
			if(toReturn.data.length != channelcheck)
				Log.d(TAG, "Channel check failed: length = " + toReturn.data.length + "  check = " + channelcheck);
			else
				Log.v(TAG, "Channel check passed");
		}
			
		return toReturn;
	}
	
}


























