package com.research.test;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ExportActivity extends Activity{
	
	public static final String TAG = "ExportActivity";
	public String currentFolder = "/sdcard";
	public static ExportActivity self = null;
	public ListView listView;
	
	public static final int EXTERNAL_STORAGE_OK = 1;
	public static final int EXTERNAL_STORAGE_NOT_AVAILABLE = 2;
	public static final int EXTERNAL_STORAGE_READ_ONLY = 3;
	public static final int EXTERNAL_STORAGE_ERROR = 4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export);
		self = this;
		
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
				int storageState = externalStorage();
				switch(storageState){
				case EXTERNAL_STORAGE_OK:
					Log.v(TAG, "Accessing internal storage available");
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
						listView.setAdapter(new ArrayAdapter<String>(ExportActivity.self, android.R.layout.simple_list_item_1,list));
					}
				}
			}
		});
		
		

		
//		listView.setOnClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3){
//				
//			}
//		});
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

}











