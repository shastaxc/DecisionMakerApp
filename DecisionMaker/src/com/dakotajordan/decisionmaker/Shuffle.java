package com.dakotajordan.decisionmaker;

import android.os.Handler;

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
	
	public static void shuffleItems(){
		Handler handler = new Handler();
		listSize = Randomize.PlaceholderFragment.getListCount();
		Randomize.PlaceholderFragment.selection = -1;
		jCounter = listSize;
		delay_multiplier = 1;
		//Randomly select number to end on
		selectHolder = Randomize.PlaceholderFragment.previousSelection;
		while(selectHolder == Randomize.PlaceholderFragment.previousSelection){
		  		selectHolder = Selector.makeChoice();
		}
		//Record end selection so next roll won't repeat the same value
		Randomize.PlaceholderFragment.previousSelection = selectHolder;
		
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
				    	//Select item on list (scrolls view to this item)
				    	Randomize.PlaceholderFragment.list.setSelection(Randomize.PlaceholderFragment.selection);
				    	//Change selector & update adapter (highlights selection)
				    	Randomize.PlaceholderFragment.adapterList.notifyDataSetChanged();
				    }
				}, 100*delay_multiplier);
				//Increment multiplier
	    		delay_multiplier ++;
	    	}
		}
	}
}
