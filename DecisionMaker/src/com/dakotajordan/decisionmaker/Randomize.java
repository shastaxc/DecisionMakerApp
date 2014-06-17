package com.dakotajordan.decisionmaker;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Randomize extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_randomize);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener{

		public PlaceholderFragment() {
		}

		//Declare global variables
		private final ArrayList<String> displayList = new ArrayList<String>();
		private final ArrayList<String> displayQuickList = new ArrayList<String>();
		protected static ArrayAdapter<String> adapterList;
		protected static ArrayAdapter<String> adapterQuickList;
		protected static ListView list;
		protected static ListView quickList;
		protected static int selection = -1;
		protected static int previousSelection = -1;
	    final SwipeDetector swipeDetector = new SwipeDetector();
	    //For use with context menu commands
	    private String selectedWord;
	    //Declare global toast variable - no longer used as of v1.3
		//private Toast toast;
	    
	    @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_randomize,
					container, false);

			//Initialize quicklist
			displayQuickList.add("1");
			displayQuickList.add("2");
			
            populateListView(rootView);
            registerClickCallback();
            registerForContextMenu((ListView)rootView.findViewById(R.id.listChoices));
           
            //No longer using toasts as of v1.3
            //toast = new Toast(getActivity().getApplicationContext());
            
			Button btnClear = (Button)rootView.findViewById(R.id.btnClear);
			btnClear.setOnClickListener(this);
			
			Button btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
            btnAdd.setOnClickListener(this);
			
			Button roll = (Button)rootView.findViewById(R.id.btnRoll);
            roll.setOnClickListener(this);
			
			return rootView;
		}

		@Override
		public void onClick(View v) {
			selection = -1;
			switch(v.getId()){
	    		case R.id.btnRoll:
	    			btnRollClick();
	    			break;
	    		case R.id.btnAdd:
	    			btnAddClick();
	    			break;
	    		case R.id.btnClear:
	    			btnClearClick();
	    			break;
	    	}
		}
		
		//========================================================================
		//Button onClick handlers
		private void btnAddClick(){
			EditText enterChoices = (EditText)getActivity().findViewById(R.id.inputDecision);
			String choice = enterChoices.getText().toString();
			
			list.setVisibility(View.VISIBLE);
			quickList.setVisibility(View.GONE);
			adapterList.add(choice);
		    list.setSelection(adapterList.getCount()-1);
		    //empty edittext box
			clear(enterChoices);
		}
		
		private void btnRollClick(){
			//Hide soft keyboard
			hideKeyboard(getView().getWindowToken());
			//Clear input box
			clear((EditText)getActivity().findViewById(R.id.inputDecision));
			
			if(!adapterList.isEmpty()){
				previousSelection = selection;
				if(adapterList.getCount() > 1){
					Shuffle.shuffleItems();
					//Loop so that the same selection is never made twice in a row
					//while(selection == previousSelection){
					//	selection = Selector.makeChoice();
					//}
				}
				else{
					selection = 0;
					adapterList.notifyDataSetChanged();
				    list.setSelection(selection);
				}
				
				//=============================================
				//Displays toast with text of selection
			    /*Toast no longer being used, commenting out
				
				LayoutInflater inflater = getActivity().getLayoutInflater();
				//Assign the custom layout to view
				//Parameter 1 - Custom layout XML
				//Parameter 2 - Custom layout ID present in linearlayout tag of XML
				View layout = inflater.inflate(R.layout.toast_custom_layout,
				            (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
				//Return the application context
				TextView text = (TextView) layout.findViewById(R.id.textView1);
				text.setText(adapterList.getItem(selection));
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(Toast.LENGTH_SHORT);
				//Set the custom layout to Toast
				toast.setView(layout);
				//Display toast
				toast.show();*/
			}
		}
		
		private void btnClearClick(){
			adapterList.clear();
		    //adapterList.notifyDataSetChanged();
			clear((EditText)getActivity().findViewById(R.id.inputDecision));
			list.setVisibility(View.GONE);
			quickList.setVisibility(View.VISIBLE);
			//No longer used
			//toast.cancel();
		}

		//========================================================================
		private void hideKeyboard(IBinder window_token) {
			//Declare imm
		    final InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(window_token, 0);
		}
		
		private void clear(EditText e) {
		     e.setText("");
		 } 
		
		//=========================================================================
		//Backend Functionality
		
		private void registerClickCallback(){
            list.setOnTouchListener(swipeDetector);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id){
					hideKeyboard(getView().getWindowToken());
					if (swipeDetector.swipeDetected()){
	                    // do the onSwipe action
					    adapterList.remove(adapterList.getItem(position));
					    selection = -1;
					    adapterList.notifyDataSetChanged();
	                } else {
	                    // do the onItemClick action
	        			hideKeyboard(getView().getWindowToken());
	                }      
				}
			});
			list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		        @Override
		        public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
		        	if (swipeDetector.swipeDetected()){
	                    // do the onSwipe action
		        		//swipe removes item from listview
					    adapterList.remove(adapterList.getItem(position));
					    selection = -1;
					    adapterList.notifyDataSetChanged();
		            } else {
		                // do the onItemLongClick action
		            	//pops up context menu with remove option
		            	selectedWord = adapterList.getItem(position);
		            	selection = position;
					    adapterList.notifyDataSetChanged();
		            }
					return false;
		        }
		    });
		}
		
		private void populateListView(View rootView){
			
			//Build Adapter
			adapterList = new CustomAdapter(
					getActivity(), //Context for the activity.
					displayList); //Items to be displayed
			
			//Build Adapter
			adapterQuickList = new CustomAdapter(
					getActivity(), //Context for the activity.
					displayQuickList); //Items to be displayed
			
			//configure listview
			list = (ListView)rootView.findViewById(R.id.listChoices);
			list.setAdapter(adapterList);
			list.setVisibility(View.GONE);
			
			quickList = (ListView)rootView.findViewById(R.id.quickList);
			quickList.setAdapter(adapterQuickList);
			quickList.setVisibility(View.VISIBLE);
		}
		
		//For other classes to see # of items in list
		public static int getListCount(){
			return adapterList.getCount();
		}
		
		@Override  
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
			super.onCreateContextMenu(menu, v, menuInfo);
		    menu.add(0, v.getId(), 0, "Remove");
		}
		@Override  
		public boolean onContextItemSelected(MenuItem item) {
			if(item.getTitle()=="Remove"){
				adapterList.remove(selectedWord);
				selection = -1;
			    adapterList.notifyDataSetChanged();
			}
			return false;
		}
	}

}
