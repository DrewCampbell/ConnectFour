package com.mti.connectfour;

import android.widget.Toast;

public class Match {

	private int[][] boardArray;
	private final int numberOfColumns = 7;
	private final int numberOfRows = 6;
	
 	private int tileValue;
	private int lastTileValue;
	private int tileStringLength;
	private boolean winnerFound;
	private String playerMove;
	private String[] winningArray;
	private String[] losingArray;	
	
	private String name;
	
	public Match(){
		
	}
	
	public Match(int[][] boardArray) {
		this.boardArray = new int[numberOfRows][numberOfColumns];
		
		
		winningArray = new String[7];
		losingArray = new String[7];		
		
		for(int i=0; i<numberOfRows; i++) {
			for(int j=0; j<numberOfColumns; j++) {
				this.boardArray[i][j]=boardArray[i][j];
			}			
		}
	
		
		// set the default for winning and losing arrays
		for(int i=0; i<7; i++) {
			winningArray[i]="0";
			losingArray[i]="0";			
		}
		
	}
	
	
	public boolean calculateIfWinner() {
		
		winnerFound = false;
		
		// Check each column
		for(int column=0; column < numberOfColumns; column++) {
			
			tileStringLength=0;
			lastTileValue=0;
			for(int row=0; row < numberOfRows; row++) {
				tileValue = boardArray[row][column];
				
				if(tileValue==lastTileValue&&tileValue!=0) {
					tileStringLength++;
				} else {
					tileStringLength=1;
				}

				if(tileStringLength>3) {
					winnerFound = true;
				}
				
				lastTileValue = tileValue;
			}			
		}

		// Check each row
		for(int row=0; row < numberOfRows; row++) {
			
			tileStringLength=0;
			lastTileValue=0;
			for(int column=0; column < numberOfColumns; column++) {
				tileValue = boardArray[row][column];
				
				if(tileValue==lastTileValue&&tileValue!=0) {
					tileStringLength++;
				} else {
					tileStringLength=1;
				}

				if(tileStringLength>3) {
					winnerFound = true;
				}
				
				lastTileValue = tileValue;
			}			
		}
		
		//  Check for diagonals
		//  First bottom left to top right 	

			tileStringLength=0;
			lastTileValue=0;
		

			int startRow =  numberOfRows - 4;
			int startColumn = 0;
			int endColumn = numberOfColumns - 4;


			int currentRow=startRow;
			int currentColumn=0;			
		
			while(startColumn<=numberOfColumns-4){
				
				currentRow = startRow;
				currentColumn = startColumn; 
				
				// This should be for each point in diagonal line
				while((currentRow<numberOfRows)&&(currentColumn<numberOfColumns)) {
					tileValue = boardArray[currentRow][currentColumn];

					if(tileValue==lastTileValue&&tileValue!=0) {
						tileStringLength++;
					} else {
						tileStringLength=1;
					}

					if(tileStringLength>3) {
						winnerFound = true;
					}
				
					lastTileValue = tileValue;

					currentRow++;
					currentColumn++;
				}

				if(startRow>0) {
					startRow--;
				} else {
					startColumn++;
				}
				
			}

			
			//  Now bottom right to top left 

			tileStringLength=0;
			lastTileValue=0;
		

			startRow =  numberOfRows - 4;
			startColumn = numberOfColumns-1;
			endColumn = 3;


			currentRow=startRow;
			currentColumn=0;			
		
			while(startColumn>=3){
				
				currentRow = startRow;
				currentColumn = startColumn; 
				
				// This should be for each point in diagonal line
				while((currentRow<numberOfRows)&&(currentColumn>=0)) {
					tileValue = boardArray[currentRow][currentColumn];

					if(tileValue==lastTileValue&&tileValue!=0) {
						tileStringLength++;
					} else {
						tileStringLength=1;
					}

					if(tileStringLength>3) {
						winnerFound = true;
					}
				
					lastTileValue = tileValue;

					currentRow++;
					currentColumn--;
				}

				if(startRow>0) {
					startRow--;
				} else {
					startColumn--;
				}
				
			}
						

		return winnerFound;
	}

	
	public int getValueAtPosition(int x, int y) {
		return boardArray[x][y];
	}

	
	public boolean checkForMatchedBoard(int[][] board) {
		
		boolean patternsMatch = true;
		//  This will check to see if the current board matches this board.

		for(int i = 0; i<numberOfRows; i++) {
			for(int j = 0; j<numberOfColumns; j++) {
				if(board[i][j]!=this.boardArray[i][j]) {
					patternsMatch = false;
				}
			}			
		}
		
		
		return patternsMatch;
	}


	public int[][] getBoard(){
		return boardArray;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPlayerMove(String playerMove) {
		this.playerMove = playerMove;
	}

	public String getPlayerMove() {
		return playerMove;
	}
	

	public void updateWinningArray(String[] winningArray) {
		this.winningArray = winningArray;
	}
	
	public String[] getWinningArray() {
		return winningArray;
	}
	
	public void updateLosingArray(String[] winningArray) {
		this.losingArray = winningArray;
	}
	
	public String[] getLosingArray() {
		return losingArray;
	}	

	
}
