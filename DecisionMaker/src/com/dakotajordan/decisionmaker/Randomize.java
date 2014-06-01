package com.dakotajordan.decisionmaker;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
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
import android.widget.TextView;
import android.widget.Toast;

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
		private static ArrayAdapter<String> adapterList;
		private ListView list;
		private int selection;
	    final SwipeDetector swipeDetector = new SwipeDetector();
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_randomize,
					container, false);

            populateListView(rootView);
            registerClickCallback();
            
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
			
			adapterList.add(choice);
		    adapterList.notifyDataSetChanged();
		    list.setSelection(adapterList.getCount()-1);
			clear(enterChoices);
		}
		
		private void btnRollClick(){
			//Hide soft keyboard
			hideKeyboard(getView().getWindowToken());
			//Clear input box
			clear((EditText)getActivity().findViewById(R.id.inputDecision));
			
			if(getListCount() > 0){
				//Randomly select one of the choices and return its index in adapterList
				selection = Selector.makeChoice();
				//Retrieve the layout inflator
				LayoutInflater inflater = getActivity().getLayoutInflater();
				//Assign the custom layout to view
				//Parameter 1 - Custom layout XML
				//Parameter 2 - Custom layout ID present in linearlayout tag of XML
				View layout = inflater.inflate(R.layout.toast_custom_layout,
				            (ViewGroup) getActivity().findViewById(R.id.toast_layout_root));
				//Return the application context
				Toast toast = new Toast(getActivity().getApplicationContext());
				
				TextView text = (TextView) layout.findViewById(R.id.textView1);
				text.setText(adapterList.getItem(selection));
				
				//Set toast gravity to bottom
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				//Set toast duration
				toast.setDuration(Toast.LENGTH_LONG);
				//Set the custom layout to Toast
				toast.setView(layout);
				//Display toast
				toast.show();
			}
		}
		
		private void btnClearClick(){
			adapterList.clear();
		    adapterList.notifyDataSetChanged();
			clear((EditText)getActivity().findViewById(R.id.inputDecision));
			hideKeyboard(getView().getWindowToken());
		}
		
		private void registerClickCallback(){
            list.setOnTouchListener(swipeDetector);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
				public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id){
					hideKeyboard(getView().getWindowToken());
					if (swipeDetector.swipeDetected()){
	                    // do the onSwipe action
					    adapterList.remove(adapterList.getItem(position));
					    adapterList.notifyDataSetChanged();
	                } else {
	                    // do the onItemClick action
	                }      
				}
			});
			/*list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
		        @Override
		        public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
		        	if (swipeDetector.swipeDetected()){
		                // do the onSwipe action 
		            } else {
		                // do the onItemLongClick action
		            }
		        }
		    });*/
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
		
		private void populateListView(View rootView){
			
			//Build Adapter
			adapterList = new ArrayAdapter<String>(
					getActivity(), //Context for the activity.
					R.layout.choices_text_view, //Layout to use(create)
					displayList); //Items to be displayed
			
			//configure listview
			list = (ListView)rootView.findViewById(R.id.listChoices);
			list.setAdapter(adapterList);
		}
		
		//For other classes to see # of items in list
		public static int getListCount(){
			return adapterList.getCount();
		}
	}

}
