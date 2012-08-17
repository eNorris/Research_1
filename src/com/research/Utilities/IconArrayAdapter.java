package com.research.Utilities;

import java.util.ArrayList;

import com.research.test.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconArrayAdapter extends ArrayAdapter<String>{
	public static final String TAG = "IconArrayAdapter";
	private Context context = null;
	
	public IconArrayAdapter(Context context, int textViewResourceId, ArrayList<String> strings){
		super(context, textViewResourceId, strings);
		this.context = context;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		
		if(row == null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//getLayoutInflater();
			row = inflater.inflate(R.layout.component_iconarrayadapter, parent, false);
		}
		
		TextView label=(TextView)row.findViewById(R.id.component_iconarrayadapterTextView);
		label.setText("hi there");
		label.setCompoundDrawables(context.getResources().getDrawable(R.drawable.logo3), null, null, null);
		
		// Get the color box working later
//		ImageView icon = (ImageView) row.findViewById(R.id.component_iconarrayadapterImageview);
//		PaintDrawable p = new PaintDrawable(Color.RED);
//		p.setBounds(new Rect(0, 0, 20, 20));
//		ColorDrawable c = new ColorDrawable(Color.RED);
//		icon.setImageDrawable(c);
		
		return row;
	}
}



/*
public class MyCustomAdapter extends ArrayAdapter<String>{

	public MyCustomAdapter(Context context, int textViewResourceId,
	String[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getDropDownView(int position, View convertView,
		ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}
	
	@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}
	
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		
		LayoutInflater inflater=getLayoutInflater();
		View row=inflater.inflate(R.layout.row, parent, false);
		TextView label=(TextView)row.findViewById(R.id.weekofday);
		label.setText(DayOfWeek[position]);
		
		ImageView icon=(ImageView)row.findViewById(R.id.icon);
		
		if (DayOfWeek[position]=="Sunday"){
		icon.setImageResource(R.drawable.icon);
		}
		else{
		icon.setImageResource(R.drawable.icongray);
		}
		
		return row;
	}
}

*/


























