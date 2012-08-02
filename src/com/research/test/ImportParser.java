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
		
		try {
			if(!file.exists())
				return ParseReturnCode.FILE_NOT_FOUND;
			if(!file.isFile())
				return ParseReturnCode.FILE_NOT_A_FILE;
			if(!file.canRead())
				return ParseReturnCode.FILE_NOT_READABLE;
			
			BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
			String nextLine = null;
			
			while((nextLine = reader.readLine()) != null){
				nextLine = nextLine.trim();
				if(nextLine.length() == 0)
					continue;
				if(nextLine.charAt(0) == '#')
					continue;
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
