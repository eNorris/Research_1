package com.research.test;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class IconArrayAdapter extends ArrayAdapter<String>{
	public static final String TAG = "IconArrayAdapter";
	private Context context = null;
	private ArrayList<String> labels = null;
	private ArrayList<Drawable> drawables = null;
	
	// TODO - see if you can do this with componentglobalTextview instead of needing a whole XML for a slingle Textview
	public IconArrayAdapter(Context context, ArrayList<String> labels, ArrayList<Drawable> drawables){
		super(context, R.id.component_iconarrayadapterTextView);
		this.context = context;
		this.labels = labels;
		this.drawables = drawables;
Log.d(TAG, "Entered constructor1");
	}
	
	public IconArrayAdapter(Context context, ArrayList<String> labels, int[] drawables){
		super(context, R.id.component_iconarrayadapterTextView);
		this.context = context;
		this.labels = labels;
		
		this.drawables = new ArrayList<Drawable>();
		for(int i = 0; i < drawables.length; i++){
			if(drawables[i] != 0){
				BitmapDrawable bitmap = (BitmapDrawable) context.getResources().getDrawable(drawables[i]);
				this.drawables.add(bitmap);
			}else{
				this.drawables.add(null);
			}
		}
Log.d(TAG, "finished constructor2");
	}
	
	public View getView(int pos, View view, ViewGroup parent){
		
Log.d(TAG, "getView");
		
		View toReturn = view;
		
		// Inflate the View
		if(toReturn == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			toReturn = inflater.inflate(R.layout.component_iconarrayadapter, parent, false);
		}
		
		final TextView textView = (TextView) toReturn.findViewById(R.id.component_iconarrayadapterTextView);
		
		textView.setText(labels.get(pos));
		textView.setGravity(Gravity.CENTER_VERTICAL);
		
		if(drawables.get(pos) != null){
			textView.setCompoundDrawablesWithIntrinsicBounds(drawables.get(pos), null, null, null);
		}

//		rowText.setText(EchelonBundle.importBundle.items.get(pos));
//		rowText.setBackgroundColor(Color.BLACK);
//		toReturn.setBackgroundColor(Color.BLACK);
		
		return toReturn;
	}
}