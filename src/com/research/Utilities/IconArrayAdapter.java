package com.research.Utilities;

import java.util.ArrayList;

import com.research.test.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconArrayAdapter extends ArrayAdapter<String>{
	public static final String TAG = "IconArrayAdapter";
	private Context context = null;
	private ArrayList<String> m_labels = null;
	
	public IconArrayAdapter(Context context, int textViewResourceId, ArrayList<String> strings){
		super(context, textViewResourceId, strings);
		m_labels = strings;
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
		label.setText(m_labels.get(position));
//		label.setCompoundDrawables(context.getResources().getDrawable(R.drawable.logo3), null, null, null);
		label.setPadding(5, 5, 5, 5);
		label.setTextColor(Color.RED);
		
		// Get the color box working later
		ImageView icon = (ImageView) row.findViewById(R.id.component_iconarrayadapterImageview);
		PaintDrawable p = new PaintDrawable(Color.RED);
		p.setBounds(new Rect(0, 0, 20, 20));
//		ColorDrawable c = new ColorDrawable(Color.RED);
		GradientDrawable g = new GradientDrawable();
		g.setColor(Color.RED);
//		ShapeDrawable s = new ShapeDrawable();
		g = (GradientDrawable) context.getResources().getDrawable(R.drawable.box);
		Drawable draw = context.getResources().getDrawable(R.drawable.broken);
	
	
		
//		icon.setImageDrawable(context.getResources().getDrawable(R.drawable.logo1));
//		icon.setImageDrawable(g);
		icon.setImageDrawable(draw);
		
		
		return row;
	}
}






























