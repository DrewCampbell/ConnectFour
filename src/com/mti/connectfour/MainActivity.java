package com.mti.connectfour;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	boolean winnerYesNo=false;
	
	private SharedPreferences sharedPreferences;
	
	//int[][] boardArray;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //int[][] boardArray = new int[7][6];

        
        ArrayList<Match> matches = new ArrayList<Match>();
        
 
        
        //  This is an upside down view of our board
        int[][] boardArray = {{0,0,0,0,0,0,0},
        			  		  {0,0,0,0,0,0,0},
        			  		  {0,0,0,0,0,0,0},        	
        			  		  {0,0,0,0,0,0,0},
        			  		  {0,0,0,0,0,0,0},
        			  		  {0,0,0,0,0,0,0}};
        
        String playerMove = "r";
        
        // gotta make a listview of matches instead of just one match object
        //Match board1 = new Match(boardArray);
        
        //Match board1 = new Match(boardArray);

        int nameIndex=0;
        matches.add(new Match(boardArray));
        //matches.add(new Match());
        //matches.get(matches.size()-1).changeBoard(boardArray);
        matches.get(matches.size()-1).setName("match" + nameIndex);        
        matches.get(matches.size()-1).setPlayerMove(playerMove);
        
        
        int randomNum;
    	Random rand = new Random();        
    	int chipValue = 1;
    	boolean chipAddedSuccessfully;
   
    	while(winnerYesNo==false){

        	//randomNum = rand.nextInt(7);    		

        	chipAddedSuccessfully = false;
        	while(chipAddedSuccessfully==false) {	

            	randomNum = rand.nextInt(7); 
            	chipAddedSuccessfully = addChipAtLocation(boardArray, randomNum, chipValue);
        	}   	        	

        	nameIndex++;
        	if(playerMove=="r") {
        		playerMove="b";
        	} else {
        		playerMove="r";
        	}
        		
        	matches.add(new Match(boardArray));
    		matches.get(matches.size()-1).setName("match" + nameIndex);
    		matches.get(matches.size()-1).setPlayerMove(playerMove);    		
    		
        	if(chipValue==1) {
        		chipValue=2;
        	} else {
        		chipValue=1;
        	}

            winnerYesNo = matches.get(matches.size()-1).calculateIfWinner();
    	}

    	
    	Toast.makeText(this.getBaseContext(), "Size is " + matches.size(), Toast.LENGTH_LONG).show();

    	
        /*
    	for(int matchIndex=0; matchIndex<matches.size(); matchIndex++) {
    		Match thisMatch = matches.get(matchIndex);
    		displayBoard(thisMatch.getBoard());
            //Toast.makeText(this.getBaseContext(), matches.get(matchIndex).getName(), Toast.LENGTH_LONG).show();
    	}
		*/
    	

    	// stop displaying this for now
    	/*
    	for(int matchIndex=matches.size()-1; matchIndex>=0; matchIndex--) {
    		Match thisMatch = matches.get(matchIndex);
    		displayBoard(thisMatch.getBoard());
            //Toast.makeText(this.getBaseContext(), matches.get(matchIndex).getName(), Toast.LENGTH_LONG).show();
    	}
		*/
    	//  for now just display the last board
		Match thisMatch = matches.get(matches.size()-1);    	
		displayBoard(thisMatch.getBoard());
    	
		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();

		
		//  This seemed to now work
		Gson gson = new Gson();
		
		
		// I will now test these
		String json = gson.toJson(matches);

		editor.putString("board", json);
		editor.commit();
    	
		//  Here let's bring up new view
		setContentView(new BoardView(this));
    	
    }

    private void displayBoard(int[][] board) {

        String boardString="";
        
        for(int i=5; i>=0; i--) {
            for(int j=0; j<7; j++) {
            	boardString = boardString + board[i][j] + " ";
            }
        	boardString = boardString + "\n";
        }
        
        Toast.makeText(this.getBaseContext(), "Board \n" + boardString, Toast.LENGTH_LONG).show(); 
    	
    }

    private boolean addChipAtLocation(int[][] board, int location, int chipValue) {
    	
    	boolean chipAdded= false;
    	int currentRow = 0;
 
    	
    	
    	while(chipAdded == false&&currentRow<6) {
    	
    		if (board[currentRow][location]==0) {
    			//  Add a chip
    			board[currentRow][location]=chipValue;
    			chipAdded = true;
    		} else {
    			// increment counter
    			currentRow++;
    		}
    			
    	}
   
    	//displayBoard(board);
    	if(currentRow==6) {
    		return false;
    	} else {
    		return true;
    	}
    }


    
    
    /*
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
	*/
}
