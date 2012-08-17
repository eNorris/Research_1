package com.research.Bundles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

public class ImportBundle{
	public String inFile = "/";
	
	public List<String> items = new ArrayList<String>();
	public List<String> importFilePath = new ArrayList<String>();
	public List<File> files = new ArrayList<File>();
	
	public int lastImportedColor = Color.BLUE;
	
}