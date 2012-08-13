package com.research.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
		
		for(int i = 0; i < EchelonBundle.importBundle.items.size(); i++){
			if(EchelonBundle.importBundle.items.get(i) == null)
				Log.d(TAG, "item " + i + " is null!");
			else
				Log.v(TAG, "Got: " + EchelonBundle.importBundle.items.get(i));
		}

		
		// FIXME - Commented out for testing
//		ArrayAdapter<String> fileList = new ArrayAdapter<String>(
//				this, 
//				android.R.layout.simple_list_item_1,
//				android.R.id.text1, 
//				EchelonBundle.importBundle.items);
		
		//ArrayAdapter<String> fileList = new ArrayAdapter<String>(
//		FileAdapter fileList = new FileAdapter(
//				this,
//				R.layout.importlistitems,
////				android.R.layout.simple_list_item_1,
////				android.R.id.text1, 
//				EchelonBundle.importBundle.items);
		
		FileAdapter fileList = new FileAdapter(
				this,
				R.layout.importlistitems,
//				android.R.layout.simple_list_item_1,
//				android.R.id.text1, 
				EchelonBundle.importBundle.items);
		
//		TextView ttextView = new TextView(this);
		
		importListView.setAdapter(fileList);
		importListView.setBackgroundColor(Color.RED);
//		importListView.set
		
		Log.d(TAG, "list size: "+ importListView.getCount());
	}
}

class FileAdapter extends ArrayAdapter<String>{
	
	public static final String TAG = "FileSysAdapter";
	private TextView rowText = null;
	private ImageView rowImage = null;
	private String rowString = null;
	private Context context = null;

	public FileAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	public View getView(int pos, View view, ViewGroup parent){
		
		View toReturn = view;
		
		// Inflate the View
		if(toReturn == null){
			Log.d(TAG, "Begin inflation");
//			this.getContext();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			toReturn = inflater.inflate(R.layout.importlistitems, parent, false);
	//		inflater.infl
			Log.d(TAG, "End inflation");
		}
		
		int imgResource = 0;
		
//		Log.d(TAG, "Switching on: " + Util.getFileExtension(EchelonBundle.importBundle.files.get(pos)));
		
		if(EchelonBundle.importBundle.files.get(pos).isDirectory()){
			imgResource = R.drawable.folder;
		}else if(Util.getFileExtension(EchelonBundle.importBundle.files.get(pos)).equals("csv")){
			imgResource = R.drawable.csvfiletype;
		}else if(Util.getFileExtension(EchelonBundle.importBundle.files.get(pos)).equals("csv")){
			imgResource = R.drawable.sdatafiletype;
		}else{
			imgResource = R.drawable.unknownfiletype;
		}
		
		rowImage = (ImageView) toReturn.findViewById(R.id.importIconImageView);
		rowText = (TextView) toReturn.findViewById(R.id.importIconTextView);
		rowString = "Temporary - 214";
		
//		String imageFilePath = "images/folder.png";
		Bitmap bitmap;
		//			bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets().open(imageFilePath));
		bitmap = BitmapFactory.decodeResource(context.getResources(), imgResource);
		if(bitmap == null){
			Log.d(TAG, "Error, bitmap is null!");
		}
		Log.d(TAG, "Attempting to set bitmap");
		if(rowImage == null){
			Log.d(TAG, "rowImage is still null!");
		}
		rowImage.setImageBitmap(bitmap);
		rowText.setText(EchelonBundle.importBundle.items.get(pos));
		Log.d(TAG, "Success");
		
		
		return toReturn;
	}
	
	/*
	public View getView(int position, View convertView, ViewGroup parent) {
		40	        View row = convertView;
		41	        if (row == null) {
		42	            // ROW INFLATION
		43	            Log.d(tag, "Starting XML Row Inflation ... ");
		44	            LayoutInflater inflater = (LayoutInflater) this.getContext()
		45	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		46	            row = inflater.inflate(R.layout.country_listitem, parent, false);
		47	            Log.d(tag, "Successfully completed XML Row Inflation!");
		48	        }
		49	 
		50	        // Get item
		51	        Country country = getItem(position);
		52	         
		53	        // Get reference to ImageView
		54	        countryIcon = (ImageView) row.findViewById(R.id.country_icon);
		55	         
		56	        // Get reference to TextView - country_name
		57	        countryName = (TextView) row.findViewById(R.id.country_name);
		58	         
		59	        // Get reference to TextView - country_abbrev
		60	        countryAbbrev = (TextView) row.findViewById(R.id.country_abbrev);
		61	 
		62	        //Set country name
		63	        countryName.setText(country.name);
		64	         
		65	        // Set country icon usign File path
		66	        String imgFilePath = ASSETS_DIR + country.resourceId;
		67	        try {
		68	            Bitmap bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets()
		69	                    .open(imgFilePath));
		70	            countryIcon.setImageBitmap(bitmap);
		71	        } catch (IOException e) {
		72	            e.printStackTrace();
		73	        }
		74	         
		75	        // Set country abbreviation
		76	        countryAbbrev.setText(country.abbreviation);
		77	        return row;
		78	    }*/
	
}








