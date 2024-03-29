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
	/*static class ViewHolder{
		public TextView itemText;		
	}
	
	@Override
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
        // TODO Auto-generated method stub
        View rowView = convertView;
        if (rowView == null){
        	LayoutInflater inflater = context1.getLayoutInflater();
            rowView = inflater.inflate(R.layout.row_item, null);
        }
        TextView text = (TextView) rowView.findViewById(R.id.text);
        text.setText(items.get(position));
        if(parent.getId() == R.id.listChoices){
            if (position == Randomize.PlaceholderFragment.selection){
                //set the proper selection color here:
                rowView.setBackgroundResource(R.drawable.item_pressed);
                text.setTextColor(Color.rgb(245,216,169));
            }
            else{
                rowView.setBackgroundResource(R.drawable.item_bg_selector);
                text.setTextColor(Color.WHITE);
            }
        }
        else{
        	if(position == 0){
            	if (position == Randomize.PlaceholderFragment.selection){
                    //set the proper selection color here:
                    rowView.setBackgroundResource(R.drawable.quick_item1_pressed);
                    text.setTextColor(Color.rgb(255,255,0));
                }
                else{
                    rowView.setBackgroundResource(R.drawable.quick_item1_bg_selector);
                    text.setTextColor(Color.WHITE);
                }
        	}
        	else{
        		if (position == Randomize.PlaceholderFragment.selection){
                    //set the proper selection color here:
                    rowView.setBackgroundResource(R.drawable.quick_item2_pressed);
                    text.setTextColor(Color.rgb(255,255,0));
                }
                else{
                    rowView.setBackgroundResource(R.drawable.quick_item2_bg_selector);
                    text.setTextColor(Color.WHITE);
                }
        	}
        }
        
        return rowView;
    }
}