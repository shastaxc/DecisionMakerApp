package com.dakotajordan.decisionmaker;

import android.os.Handler;
import android.widget.ListView;

//Need to insert checks inside the runnables to limit the height that selection can increase
//creates a shuffling/rolling effect when "roll" button is pressed
//increment 'selection' by 1 for size of adapterList
/*loop 4 times (i), slowing down waithandler each time
 *on each iteration (j): 
 *	selection = j;
 *	adapterList.notifyDataSetChanged();
 *	list.setSelection(selection);
 *	waithandler
*/
public class Shuffle {
	private static int listSize;
	private static int i, j, selectHolder, delay_multiplier, jCounter;
	
	public static void shuffleItems(final ListView list){
		Handler handler = new Handler();
		listSize = list.getCount();
		Randomize.PlaceholderFragment.selection = -1;
		jCounter = listSize;
		delay_multiplier = 1;
		//Randomly select number to end on
		selectHolder = Selector.makeChoice(list);
		
		//Begin rolling animation
		for( i = 0; i < 4; i++){
			if(i == 3){
				jCounter = selectHolder+1;
			}
	    	for(j = 0; j < jCounter; j++){
	    		handler.postDelayed(new Runnable() {
				    @Override
				    public void run(){
				    	if(Randomize.PlaceholderFragment.selection==listSize-1 || 
				    			Randomize.PlaceholderFragment.selection == -1){
				        	Randomize.PlaceholderFragment.selection=0;
				    	}
				    	else{
					    	Randomize.PlaceholderFragment.selection++;
				    	}
				    	if(list == Randomize.PlaceholderFragment.list){
					    	//Select item on list (scrolls view to this item)
					    	Randomize.PlaceholderFragment.list.setSelection(Randomize.PlaceholderFragment.selection);
					    	//Change selector & update adapter (highlights selection)
					    	Randomize.PlaceholderFragment.adapterList.notifyDataSetChanged();
				    	}
				    	else{
					    	//Select item on list (scrolls view to this item)
					    	Randomize.PlaceholderFragment.quickList.setSelection(Randomize.PlaceholderFragment.selection);
					    	//Change selector & update adapter (highlights selection)
					    	Randomize.PlaceholderFragment.adapterQuickList.notifyDataSetChanged();
				    	}
				    }
				}, 100*delay_multiplier);
				//Increment multiplier
	    		delay_multiplier ++;
	    	}
		}
	}
}
