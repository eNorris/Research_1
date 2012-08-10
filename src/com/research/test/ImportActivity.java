package com.research.test;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ImportActivity extends Activity {
	
	public static final String TAG = "AnalysisActivity";
	public static final String ROOT = "/";
	public static final String PARENTFOLDER = "../";
	
	public static TextView importFilePath = null;
	public static ListView importListView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.import_layout);
		
		// Realize layout
		importFilePath = (TextView) findViewById(R.id.importCurrentDirPathTextView_id);
		importListView = (ListView) findViewById(R.id.importListView_id);
		Button doneButton = (Button) findViewById(R.id.importDoneButton_id);
		
		// Set On click listeners
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setResult(MainActivity.RESULT_IMPORT_OK);
				finish();
			}
		});
		
		importListView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
				
				File newFile = new File(EchelonBundle.importBundle.importFilePath.get(pos));
				
				if(newFile.isDirectory()){
					if(newFile.canRead()){
						loadDirectory(EchelonBundle.importBundle.importFilePath.get(pos));
					}else{
						// If the directory is not readable
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("[" + newFile.getName() + "] folder can't be read!").setPositiveButton("OK", 
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {}
							}).show();
					}
				}else{ // Handle selecting a file	
					switch(ImportParser.parse(newFile)){
					case OK:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.logo1).setTitle("Import Succeeded").
								setMessage("Imported: " + newFile.getName() + " successfully").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
						break;
					case FILE_NOT_A_FILE:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": FILE_NOT_A_FILE error (Did you select a directory?)").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
						break;
					case FILE_NOT_FOUND:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": FILE_NOT_FOUND error (Go create it!)").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
						break;
					case FILE_NOT_READABLE:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": FILE_NOT_READABLE error (Is locked by something else?)").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
						break;
					case UNKNOWN_ERROR:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": UNKNOWN_ERROR error (No clue what happened.)").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
						break;
					default:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": Uncaught Error. This is the developer's fault. Sorry.").setPositiveButton("OK", 
										new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						}).show();
					}
				}
			}
		});
		
		// Set the directory
		loadDirectory(EchelonBundle.importBundle.inFile);
	}
	
	
	private void loadDirectory(String dirPath)
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
				android.R.layout.simple_list_item_1,
				android.R.id.text1, 
				EchelonBundle.importBundle.items);
		
		importListView.setAdapter(fileList);
		importListView.setBackgroundColor(Color.RED);
		
		Log.d(TAG, "list size: "+ importListView.getCount());
	}
}










