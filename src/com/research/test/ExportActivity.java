package com.research.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ExportActivity extends Activity{
	
	public static final String TAG = "ExportActivity";
	public String currentFolder = "/sdcard";
//	public static ExportActivity self = null;
	public ListView listView;
	
	public static final int EXTERNAL_STORAGE_OK = 1;
	public static final int EXTERNAL_STORAGE_NOT_AVAILABLE = 2;
	public static final int EXTERNAL_STORAGE_READ_ONLY = 3;
	public static final int EXTERNAL_STORAGE_ERROR = 4;
	
	public static String exportTimeStamp = "timestamp";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export);
//		self = this;
		
		// Realize Layout
		final Button doneButton = (Button) findViewById(R.id.exportDoneButton_id);
		final ListView listView = (ListView) findViewById(R.id.listView_id);
		final Button exportButton = (Button) findViewById(R.id.exportExportButton_id);
		
		// Add OnClickListeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
		exportButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				exportTimeStamp = timeDateStamp();
				int storageState = externalStorage();
				switch(storageState){
				case EXTERNAL_STORAGE_OK:
					Log.v(TAG, "Accessing internal storage");
					
					// Create the base folder if it doesn't already exist
// TODO - getExternalStorageDirectory => getExternalStoragePublicDirectory ?
					String baseDirectoryName = Environment.getExternalStorageDirectory().toString() + "/SpectrumAnalysis";
					File baseDirectory = new File(baseDirectoryName);
//					File directory = new File(Environment.getExternalStoragePublicDirectory(STORAGE_SERVICE).toString() + "/SpectrumAnalysis");
	//				if(baseDirectory.mkdir()){
	//					Log.v(TAG, "Created directory: " + baseDirectoryName);
	//				}else{
	//					Log.v(TAG, "Did not create directory: " + baseDirectoryName);
	//				}
					
						// Create a new filename with a time/date stamp in the name
					boolean successCreatingFile = false;
					String newFilename = baseDirectory.getAbsolutePath() + "/spectrumdata_" + exportTimeStamp + ".sdata";
					Log.v(TAG, "Creating file: " + newFilename);
					File newFile = new File(newFilename);
					if(newFile.mkdirs()){
						Log.v(TAG, "Created file successfully");
						successCreatingFile = true;
					}else{
						Log.d(TAG, "Did not create the file...");
						Toast.makeText(ExportActivity.this, "Failed to created Folders and/or new file!!!", Toast.LENGTH_LONG).show();
					}
					
					if(successCreatingFile){
							// Get the contents that will be written to the file
						String logput = exportString();
						
							// Write to the file
						try{
							FileWriter fstream = new FileWriter(newFile.toString());
							BufferedWriter out = new BufferedWriter(fstream);
							out.write(logput);
							out.close();
						}catch (Exception e){
							System.err.println("Error writing to file: " + e.getMessage());
						}
					}
					break;
				case EXTERNAL_STORAGE_READ_ONLY:
					Log.v(TAG, "Can access external media, but it is read-only");
					break;
				case EXTERNAL_STORAGE_ERROR:
					Log.e(TAG, "Error: Could not access any exteral storage media.");
					break;
				default:
					Log.wtf(TAG, "Recieved invalid media state code (" + storageState + ")");
				};
			}
		});
		
		File file = new File("/sdcard");
		String[] files = file.list();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(adapter);
		
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> lista, View arg1, int position, long arg3) {
				File file = new File(currentFolder + "/" + lista.getItemAtPosition(position));
				if(file.canRead()){
					if (file.isDirectory() ) {
						String[] list = file.list();
						currentFolder += "/" + lista.getItemAtPosition(position);
						listView.setAdapter(new ArrayAdapter<String>(ExportActivity.this, android.R.layout.simple_list_item_1,list));
					}
				}
			}
		});
	}
	
	
	public int externalStorage(){
//		boolean mExternalStorageAvailable = false;
//		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
	
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
//		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		    return EXTERNAL_STORAGE_OK;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
//		    mExternalStorageAvailable = true;
//		    mExternalStorageWriteable = false;
		    return EXTERNAL_STORAGE_READ_ONLY;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
//		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		    return EXTERNAL_STORAGE_ERROR;
		}
	}
	
	public static String exportString(){
		String toReturn = "";
		
		toReturn += "#   =====  Spectrum Analysis Software  =====   \n";
		toReturn += "# Authors: Dr. Xin Liu and Edward Norris\n";
		toReturn += "# Missouri University of Science and Technology\n";
		toReturn += "# " + exportTimeStamp + "\n";
		
		return toReturn;
	}
	
	public static String timeDateStamp(){
		String toReturn = "";
		
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		
		toReturn += today.year;
		toReturn += String.format("%02d", today.month);
		toReturn += String.format("%02d", today.monthDay);
		toReturn += "T";
		toReturn += String.format("%02d", today.hour);
		toReturn += String.format("%02d", today.minute);
		toReturn += String.format("%02d", today.second);
		toReturn += today.timezone;
		String.format("%05d", 54);
		
		return toReturn;
	}

}











