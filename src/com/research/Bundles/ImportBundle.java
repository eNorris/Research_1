package com.research.Bundles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportBundle{
	public String inFile = "/";
	
	// A list of strings that contain what will be shown as the text for the import list
	// Arbitrary
	public List<String> importPathFileNames = new ArrayList<String>();
	
	// A list of files that correspond those shown in the import list
	// Must contain the full file name
	public List<File> importPathFiles = new ArrayList<File>();
	
//	public int lastImportedColor = Color.BLUE;
	
}