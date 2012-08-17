package com.research.Activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.research.Bundles.EchelonBundle;
import com.research.Utilities.ImportParser;
import com.research.Utilities.Util;
import com.research.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
					case FILE_NOT_PARSABLE:
						new AlertDialog.Builder(ImportActivity.this).setIcon(R.drawable.broken).setTitle("Import Failed").
								setMessage("Failed to import: " + newFile.getName() + ": FILE_NOT_PARSABLE error (Is this the right type of file?)").setPositiveButton("OK", 
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
		EchelonBundle.importBundle.files = new ArrayList<File>();
		EchelonBundle.importBundle.importFilePath = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();
		
		if(!dirPath.equals(ROOT))
		{
			EchelonBundle.importBundle.items.add(ROOT);
			EchelonBundle.importBundle.files.add(new File(ROOT));
			EchelonBundle.importBundle.importFilePath.add(ROOT);
			
			EchelonBundle.importBundle.items.add(PARENTFOLDER);
			EchelonBundle.importBundle.files.add(new File(PARENTFOLDER));
			EchelonBundle.importBundle.importFilePath.add(f.getParent());
		}
		
		for(int i=0; i < files.length; i++)
		{
			File file = files[i];
			EchelonBundle.importBundle.importFilePath.add(file.getPath());
			if(file.isDirectory()){
				EchelonBundle.importBundle.items.add(file.getName() + "/");
				EchelonBundle.importBundle.files.add(file);
			}else{
				EchelonBundle.importBundle.items.add(file.getName());
				EchelonBundle.importBundle.files.add(file);
			}
	 	}
		
		FileAdapter fileList = new FileAdapter(
				this,
				R.layout.importlistitems,
				EchelonBundle.importBundle.items);
		
		importListView.setAdapter(fileList);
		importListView.setBackgroundColor(Color.BLACK);
	}
}

class FileAdapter extends ArrayAdapter<String>{
	
	public static final String TAG = "FileSysAdapter";
	private TextView rowText = null;
	private ImageView rowImage = null;
	private Context context = null;

	public FileAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	public View getView(int pos, View view, ViewGroup parent){
//		Log.v(TAG, "Building FileAdapter View");
		
		View toReturn = view;
		
		// Inflate the View
		if(toReturn == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			toReturn = inflater.inflate(R.layout.importlistitems, parent, false);
		}
		
		int imgResource = 0;
		
		if(EchelonBundle.importBundle.files.get(pos).isDirectory()){
			imgResource = R.drawable.folder;
		}else if(Util.getFileExtension(EchelonBundle.importBundle.files.get(pos)).equals("csv")){
			imgResource = R.drawable.csvfiletype;
		}else if(Util.getFileExtension(EchelonBundle.importBundle.files.get(pos)).equals("sdata")){
			imgResource = R.drawable.sdatafiletype;
		}else{
			imgResource = R.drawable.unknownfiletype;
		}
		
		rowImage = (ImageView) toReturn.findViewById(R.id.importIconImageView);
		rowText = (TextView) toReturn.findViewById(R.id.importIconTextView);
		
		Bitmap bitmap;
		bitmap = BitmapFactory.decodeResource(context.getResources(), imgResource);

		rowImage.setImageBitmap(bitmap);
// TODO - Find a dynamic way to do this for different screen densities
		rowImage.setMaxHeight(32);
		rowImage.setMaxWidth(32);
		rowText.setText(EchelonBundle.importBundle.items.get(pos));
		rowText.setBackgroundColor(Color.BLACK);
		toReturn.setBackgroundColor(Color.BLACK);
		
		return toReturn;
	}
}







