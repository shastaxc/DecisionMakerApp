package com.dakotajordan.decisionmaker;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

	private final Activity context1;
	private ArrayList<String> items;
	
	public CustomAdapter(Activity context, ArrayList<String> items){
		super(context,R.layout.row_item,items);
		this.context1 = context;
		this.items = items;
	}
	static class ViewHolder{
		public TextView itemTextView;		
	}
	
	/*@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		ViewHolder holder;
		View rowView = convertView;
		
		if(rowView==null){
			LayoutInflater vi = context1.getLayoutInflater();
			rowView = vi.inflate(R.layout.row_item, null, true);
			holder = new ViewHolder();
			holder.itemText = (TextView) rowView.findViewById(R.id.text);
			String taskText = items.get(position);
			holder.itemText.setText(taskText);
			rowView.setTag(holder);
		}
		else{
			holder = (ViewHolder) rowView.getTag();
		}
		
		return rowView;
	}*/
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        View rowView = convertView;
        if (rowView == null){
        	LayoutInflater inflater = context1.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_item, null);
            holder = new ViewHolder();
            //TODO: Add drawable selector to row_item.xml textview
            holder.itemTextView = (TextView)rowView.findViewById(R.id.text);
            holder.itemTextView.setText(items.get(position));
            rowView.setTag(holder);
        }
        else if (position == Randomize.PlaceholderFragment.selection){
        	holder = (ViewHolder) rowView.getTag();
            //set the proper selection color here:
            //holder.itemTextView.setBackgroundResource(R.drawable.item_pressed);
            holder.itemTextView.setTextColor(Color.rgb(245,216,169));
        }
        else{
        	holder = (ViewHolder) rowView.getTag();
        	//holder.itemTextView.setBackgroundResource(R.drawable.item_unpressed);
        	holder.itemTextView.setTextColor(Color.WHITE);
        }
        
        return rowView;
    }
}