package com.research.Bundles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportBundle{
	
	/**
	 * The initial directory that is loaded at startup - In this case, the root directory
	 */
	public String initialLoadFile = "/";
	
	/**
	 * A list of strings that contain what will be shown as the text for the import list
	 */
	public List<String> importPathFileNames = new ArrayList<String>();
	
	/** 
	 * A list of files that correspond those shown in the import list
	 * Must contain the full file name
	 */
	public List<File> importPathFiles = new ArrayList<File>();
	
	/**
	 * File containing the current directory
	 */
	public File currentDir = new File(initialLoadFile);
	
	/**
	 * File containing the current directory's parent
	 */
	public File parentDir = new File(initialLoadFile);
	
//	public int lastImportedColor = Color.BLUE;
	
}