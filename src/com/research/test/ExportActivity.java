package com.research.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.research.test.ExportBundle.FileExtension;
import com.research.test.ExportBundle.NewLineCode;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
//import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class ExportActivity extends Activity{	//ListActivity{
	
	public static final String TAG = "ExportActivity";
	public static final String ROOT = "/";
	public static final String PARENTFOLDER = "../";
	public String currentFolder = Environment.getExternalStorageDirectory().getPath();
//	public ListView listView;
	
	public static final int EXTERNAL_STORAGE_OK = 1;
	public static final int EXTERNAL_STORAGE_NOT_AVAILABLE = 2;
	public static final int EXTERNAL_STORAGE_READ_ONLY = 3;
	public static final int EXTERNAL_STORAGE_ERROR = 4;
	
	// Export Dynamic Layout Elements
	public static String exportTimeStamp = "timestamp";
	public static CheckBox timeStampCheckBox = null;
	public static TextView filenameTextView = null;
	public static EditText userFilenameEditText = null;
	
	// Import Dynamic Layout Elements
	public static TextView importFilePath = null;
	public static ListView importListView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export);
		
		// Realize Layout
		final Button doneButton = (Button) findViewById(R.id.exportDoneButton_id);
//		final ListView listView = (ListView) findViewById(R.id.listView_id);
		final Button exportButton = (Button) findViewById(R.id.exportExportButton_id);
		
		// Realize export components
		filenameTextView = (TextView) findViewById(R.id.exportFilenameFullTextView_id);
		timeStampCheckBox = (CheckBox) findViewById(R.id.exportTimeStampCheckBox_id);
		userFilenameEditText = (EditText) findViewById(R.id.exportFilenameEditText_id);
		
		// Realize import components
		importFilePath = (TextView) findViewById(R.id.importCurrentDirPathTextView_id);
		importListView = (ListView) findViewById(R.id.importListView_id);
		
		// Add OnClickListeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
		userFilenameEditText.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable arg0){
				updateFilenames();
			}
			
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}
		});
		
		timeStampCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				updateFilenames();
			}
		});
	
		exportButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				exportTimeStamp = timeDateStamp();
				int storageState = externalStorageState();
				switch(storageState){
				case EXTERNAL_STORAGE_OK:
					Log.v(TAG, "Accessing internal storage");
					
					// Create the base folder if it doesn't already exist
					String baseDirectoryName = Environment.getExternalStorageDirectory().toString() + "/SpectrumAnalysis";
					File baseDirectory = new File(baseDirectoryName);
					
					// Create a new filename with a time/date stamp in the name
					boolean successCreatingFile = false;
					String newFilename = baseDirectory.getAbsolutePath() + "/" + EchelonBundle.exportBundle.outFile;
					
// FIXME - check for file existance first
					Log.v(TAG, "Creating file: " + newFilename);
					File newFile = new File(newFilename);
					if(!newFile.getParentFile().mkdirs()){
						Log.d(TAG, "Did not create the parent folder");
					}
					
					// If the parent already exists, failure will be returned, so try making the file anyway
					Log.v(TAG, "Created parent folder(s) successfully");
					try {
						newFile.createNewFile();
						successCreatingFile = true;
						Toast.makeText(ExportActivity.this, "Exported to: " + newFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
					} catch (IOException e) {
						Toast.makeText(ExportActivity.this, "Failed to created new file... try again.", Toast.LENGTH_LONG).show();
						Log.d(TAG, "File creation failed");
						e.printStackTrace();
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
		
		////////////////////////////////////////////////////////////////////////////////////////
		
		
		getDir(EchelonBundle.importBundle.inFile);
		
		
		
		filenameTextView.setText(EchelonBundle.exportBundle.outFile);
		userFilenameEditText.setText(EchelonBundle.exportBundle.userInput);
	}// END - onCreate()
	
	
	public int externalStorageState(){
		String state = Environment.getExternalStorageState();
	
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    return EXTERNAL_STORAGE_OK;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    return EXTERNAL_STORAGE_READ_ONLY;
		} else {
		    return EXTERNAL_STORAGE_ERROR;
		}
	}
	
	public static String exportString(){
		String toReturn = "";
	
		if(EchelonBundle.exportBundle.fileExtension == FileExtension.SDATA){
			toReturn += "#   =====  Spectrum Analysis Software  =====   " + endl();
			toReturn += "# Authors: Dr. Xin Liu and Edward Norris" + endl();
			toReturn += "# Missouri University of Science and Technology" + endl();
			toReturn += "# " + exportTimeStamp + endl() + endl();
			for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
				toReturn += "DATASET=" + i + endl();
				toReturn += "CHANNELCOUNT=" + EchelonBundle.dataBundles.get(i).data.length + endl();
				for(int j = 0; j < EchelonBundle.dataBundles.get(i).data.length; j++){
					toReturn += (EchelonBundle.dataBundles.get(i).data[j] + ",");
				}
			}
		}else if(EchelonBundle.exportBundle.fileExtension == FileExtension.CSV){
			for(int i = 0; i < EchelonBundle.dataBundles.size(); i++){
				toReturn += "DATASET=" + i + endl();
				toReturn += "CHANNELCOUNT=" + EchelonBundle.dataBundles.get(i).data.length + endl();
				for(int j = 0; j < EchelonBundle.dataBundles.get(i).data.length; j++){
					toReturn += (EchelonBundle.dataBundles.get(i).data[j] + ",");
				}
			}
		}else{
			Log.d(TAG, "Unknown file extension requested(" + EchelonBundle.exportBundle.fileExtension.toString() + ")");
		}
		
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
	
	public static String endl(){
		if(EchelonBundle.exportBundle.newLineCode == NewLineCode.WINDOWS)
			return "\r\n";
		if(EchelonBundle.exportBundle.newLineCode == NewLineCode.UNIX)
			return "\n";
		if(EchelonBundle.exportBundle.newLineCode == NewLineCode.ACORN)
			return "\n\r";
		if(EchelonBundle.exportBundle.newLineCode == NewLineCode.COMMODORE)
			return "\r";
		Log.d(TAG, "Unknown new line code (" + EchelonBundle.exportBundle.newLineCode + ")");
		return "\n";
	}

	public static void updateFilenames(){

		EchelonBundle.exportBundle.userInput = userFilenameEditText.getText().toString();
		if(timeStampCheckBox.isChecked()){
			EchelonBundle.exportBundle.outFile = EchelonBundle.exportBundle.userInput + "_" + ExportActivity.timeDateStamp() + ".sdata";
		}else{
			EchelonBundle.exportBundle.outFile = EchelonBundle.exportBundle.userInput + ".sdata";
		}
		filenameTextView.setText(EchelonBundle.exportBundle.outFile);
	}
	
	private void getDir(String dirPath)
	{
		importFilePath.setText("Location: " + dirPath);
		EchelonBundle.importBundle.items = new ArrayList<String>();
		EchelonBundle.importBundle.importFilePath = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(ROOT))
		{
			EchelonBundle.importBundle.items.add(ROOT);
			EchelonBundle.importBundle.importFilePath.add(ROOT);
			
			EchelonBundle.importBundle.items.add(PARENTFOLDER);
			EchelonBundle.importBundle.importFilePath.add(f.getParent());
		}
		
		for(int i=0; i < files.length; i++)
		{
			File file = files[i];
			EchelonBundle.importBundle.importFilePath.add(file.getPath());
			if(file.isDirectory())
				EchelonBundle.importBundle.items.add(file.getName() + "/");
			else
				EchelonBundle.importBundle.items.add(file.getName());
	 	}
		
		for(int i = 0; i < EchelonBundle.importBundle.items.size(); i++){
			if(EchelonBundle.importBundle.items.get(i) == null)
				Log.d(TAG, "item " + i + " is null!");
			else
				Log.v(TAG, "Got: " + EchelonBundle.importBundle.items.get(i));
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,android.R.id.text1, 
				EchelonBundle.importBundle.items);
		
		
		
		importListView.setAdapter(fileList);

	
//ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, mylist);   
//listView.setAdapter(arrayAdapter);
		
		
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////




/*

public class AndroidExplorer extends ListActivity {

private List<String> item = null;
private List<String> path = null;
private String root="/";
private TextView myPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		myPath = (TextView)findViewById(R.id.path);
		getDir(root);
	}

	private void getDir(String dirPath)
	{
		myPath.setText("Location: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(root))
		{
			item.add(root);
			path.add(root);
			
			item.add("../");
			path.add(f.getParent());
		}
		
		for(int i=0; i < files.length; i++)
		{
			File file = files[i];
			path.add(file.getPath());
			if(file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
	 	}
	
		ArrayAdapter<String> fileList =
		new ArrayAdapter<String>(this, R.layout.row, item);
		setListAdapter(fileList);
	}

	 @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	
		File file = new File(path.get(position));
		
		if (file.isDirectory())
		{
			if(file.canRead())
				getDir(path.get(position));
			else
			{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("[" + file.getName() + "] folder can't be read!")
				.setPositiveButton("OK", 
				  new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {}
				}).show();
			}
		}
		else
		{
			new AlertDialog.Builder(this)
			.setIcon(R.drawable.icon)
			.setTitle("[" + file.getName() + "]")
			.setPositiveButton("OK", 
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).show();
		}
	}
}
*/









