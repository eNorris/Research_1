package com.research.Bundles;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


import android.util.Log;

public class EchelonBundle {
	public static ConfigBundle configBundle = new ConfigBundle();
	public static ExportBundle exportBundle = new ExportBundle();
	public static ImportBundle importBundle = new ImportBundle();
	public static ScreenBundle screenBundle = new ScreenBundle();
	public static ArrayList<DataBundle> dataBundles = new ArrayList<DataBundle>();
	public static DataBundle activeDataBundle = null;
	public static AtomicBoolean dataLoaded = new AtomicBoolean(false);
	
	public static final String TAG = "EchelonBundle";
	
	// You aren't allowed to create an EchelonBundle object
	private EchelonBundle(){}
	
	public static void clearAll(){
		configBundle = new ConfigBundle();
		exportBundle = new ExportBundle();
		importBundle = new ImportBundle();
		dataBundles.clear();
		activeDataBundle = null;
	}
}















