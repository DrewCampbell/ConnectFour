package com.mti.connectfour;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BoardView  extends SurfaceView implements OnTouchListener {

	Paint paint;
	Paint paintShadow;
	
	int xPartition;
	int yPartition;
	int yBase;
	int numColumns=7;
	int numRows=6;
	int xSemiPartition;
	int ySemiPartition;	


	//  Defines placement of left and right buttons
	int lButtonLeft=800;
	int lButtonRight=950;
	int lButtonTop=1400;
	int lButtonBottom=1500;
	int rButtonLeft=1000;
	int rButtonRight=1150;
	int rButtonTop=1400;
	int rButtonBottom=1500;
	

	
	/*
	private static final int verticesColors[] = {
	    Color.LTGRAY, Color.LTGRAY, Color.LTGRAY, 0xFF000000, 0xFF000000, 0xFF000000
	};
	*/
	
	
	
	float xValue;
	float yValue;	
	
	
	ArrayList<Match> matches;
	String[] winningArray = {"0","0","0","0","0","0","0"};
	String[] losingArray = {"0","0","0","0","0","0","0"};
	
	int currentMatchIndex;
	
	private SharedPreferences sharedPreferences;
	
	public BoardView(Context context) {
		super(context);
		
		paint = new Paint();
		paintShadow = new Paint();

		/*
		xPartition = getWidth()/(numColumns+1); 
		yPartition = getHeight()/2/(numRows);
		yBase = getHeight() * 3/5;		
		xSemiPartition=xPartition/2;
		ySemiPartition=yPartition/2;		
		*/

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		
		Gson gson = new Gson();
		String json = sharedPreferences.getString("board", null);
		Type type = new TypeToken<ArrayList<Match>>() {}.getType();
		matches = gson.fromJson(json, type);		

		currentMatchIndex = matches.size()-1;
		
		setWillNotDraw(false);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
	
		
		//  Test on drawing vertices
		paint.setColor(Color.RED);		
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(2);
		
		
		canvas.drawColor(Color.GRAY);		
		
		
		paint.setTextSize(50);
		paint.setColor(Color.RED);
		
		// draw buttons
		drawButton(canvas, 'l', lButtonLeft, lButtonTop, lButtonRight, lButtonBottom);
		drawButton(canvas, 'r', rButtonLeft, rButtonTop, rButtonRight, rButtonBottom);
		
		//  Write which player's turn it is
		if(matches.get(currentMatchIndex).getPlayerMove().equals("r")) {
			paint.setColor(Color.RED);
			canvas.drawText("Red's turn " + (currentMatchIndex + 1), 100, 200, paint);	
		} else {
			paint.setColor(Color.BLACK);
			canvas.drawText("Black's turn " + (currentMatchIndex + 1), 100, 200, paint);
		}
		
		

		// display the winning and losing arrays
		paint.setTextSize(40);
		// draw winning array		
		
		if(matches.get(currentMatchIndex).getPlayerMove().equals("r")) {
			paint.setColor(Color.RED);			
		} else {
			paint.setColor(Color.BLACK);			
		}

				
		canvas.drawText("Winning Array", xPartition, 1150, paint);		
		winningArray = matches.get(currentMatchIndex).getWinningArray();
		for(int i =0; i<7; i++) {	
			canvas.drawText(winningArray[i], xPartition + xPartition * i, 1200, paint);	
		}

		
		// draw losing array
		if(matches.get(currentMatchIndex).getPlayerMove().equals("r")) {
			paint.setColor(Color.BLACK);			
		} else {
			paint.setColor(Color.RED);			
		}
		
		canvas.drawText("Losing Array", xPartition, 1300, paint);		
		losingArray = matches.get(currentMatchIndex).getLosingArray();
		for(int i =0; i<7; i++) {	
			canvas.drawText(losingArray[i], xPartition + xPartition * i, 1350, paint);	
		}
		
		
		// draw board
		xPartition = getWidth()/(numColumns+1); 
		yPartition = getHeight()/2/(numRows);
		yBase = getHeight() * 3/5;		
		xSemiPartition=xPartition/2;
		ySemiPartition=yPartition/2;
		
		paint.setColor(Color.YELLOW);
		//canvas.drawRect(xPartition+1, yBase - yPartition * (numRows - 1), xPartition*numColumns, yBase ,paint);
		canvas.drawRect(xSemiPartition, yBase - ySemiPartition * (2*numRows - 1), xPartition*(numColumns) + xPartition/2, yBase + yPartition/2 ,paint);
		
		
		for(int row=0; row < numRows; row++) {

			for(int column=0; column < numColumns; column++) {

		
				if(matches.get(currentMatchIndex).getValueAtPosition(row, column)==1) {

					drawChip(canvas, Color.RED, column, row);
				
				}
				if(matches.get(currentMatchIndex).getValueAtPosition(row, column)==2) {
					
					drawChip(canvas, Color.BLACK, column, row);
				
				}
				if(matches.get(currentMatchIndex).getValueAtPosition(row, column)==0) {
					paint.setColor(Color.WHITE);
					canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 50, paint);					
				}
			
			}
			
		}


		RectF rectangle = new RectF(100,100,150,150);


		/*
		paintShadow.setStyle(Paint.Style.STROKE);
		paintShadow.setColor(Color.WHITE);
		canvas.drawArc(rectangle, (float) 135, (float)180, true, paintShadow);
		paintShadow.setColor(Color.BLACK);	
		canvas.drawArc(rectangle, (float) 315, (float)180, true, paintShadow);
		paintShadow.setColor(Color.BLUE);
		paintShadow.setStrokeWidth(2);
		canvas.drawLine(100, 150, 150, 100, paintShadow);
		*/


	}

	
	private void drawChip(Canvas canvas, int color, int column, int row) {

		paint.setColor(color);
	    paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 50, paint);					

		RectF rectangle = new RectF((column+1) * xPartition - 40, yBase - (row) * yPartition -40,(column+1) * xPartition + 40, yBase - (row) * yPartition + 40);
		
		paintShadow.setStyle(Paint.Style.STROKE);
		paintShadow.setStrokeWidth(1);
		paintShadow.setColor(Color.WHITE);
		canvas.drawArc(rectangle, (float) 315, (float)180, true, paintShadow);
		paintShadow.setStrokeWidth(2);
		canvas.drawArc(rectangle, (float) 0, (float)90, true, paintShadow);
		
	
		paintShadow.setStrokeWidth(1);		
		paintShadow.setColor(Color.BLACK);	
		canvas.drawArc(rectangle, (float) 135, (float)180, true, paintShadow);
		paintShadow.setStrokeWidth(2);
		canvas.drawArc(rectangle, (float) 180, (float)90, true, paintShadow);
		
		
		canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 39, paint);
		
	}

	
	private void drawButton(Canvas canvas, char direction, int xStart, int yStart, int xStop, int yStop) {
		if (direction == 'r') {
	
			paint.setColor(Color.DKGRAY);
			canvas.drawRect(xStart, yStart, xStop, yStop ,paint);


		    paint.setStrokeWidth(4);
		    paint.setColor(android.graphics.Color.RED);
		    paint.setStyle(Paint.Style.FILL_AND_STROKE);
		    paint.setAntiAlias(true);

		    Point a = new Point((int) (xStart + (xStop-xStart)*.25), yStart);
		    Point b = new Point((int) (xStart + (xStop-xStart)*.75), (yStart+yStop)/2);
		    Point c = new Point((int) (xStart + (xStop-xStart)*.25), yStop);

		    Path path = new Path();
		    path.setFillType(FillType.EVEN_ODD);
		    path.moveTo(a.x, a.y);
		    path.lineTo(b.x, b.y);
		    path.moveTo(b.x, b.y);
		    path.lineTo(c.x, c.y);
		    path.moveTo(c.x, c.y);
		    path.lineTo(a.x, a.y);
		    path.close();

		    canvas.drawPath(path, paint);
		
		
		}

		if (direction == 'l') {
			paint.setColor(Color.DKGRAY);
			canvas.drawRect(xStart, yStart, xStop, yStop ,paint);

		    paint.setStrokeWidth(4);
		    paint.setColor(android.graphics.Color.RED);
		    paint.setStyle(Paint.Style.FILL_AND_STROKE);
		    paint.setAntiAlias(true);

		    Point a = new Point((int) (xStart + (xStop-xStart)*.75), yStart);
		    Point b = new Point((int) (xStart + (xStop-xStart)*.25), (yStart+yStop)/2);
		    Point c = new Point((int) (xStart + (xStop-xStart)*.75), yStop);

		    Path path = new Path();
		    path.setFillType(FillType.EVEN_ODD);
		    path.moveTo(a.x, a.y);
		    path.lineTo(b.x, b.y);
		    path.moveTo(b.x, b.y);
		    path.lineTo(c.x, c.y);
		    path.moveTo(c.x, c.y);
		    path.lineTo(a.x, a.y);
		    path.close();

		    canvas.drawPath(path, paint);
		
		
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {

 
        switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				xValue = event.getX();
				yValue = event.getY();

				v.setWillNotDraw(false);

		        //Toast.makeText(this.getContext(), "onTouch", Toast.LENGTH_LONG).show(); 
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		return false;
		
	}
	
	public boolean onTouchEvent(MotionEvent event) {

		

        switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				xValue = event.getX();
				yValue = event.getY();

				//  for some reason it works if you first set it to true and then back to false
				// draw left button
				if(event.getX()>lButtonLeft && event.getX()<lButtonRight && event.getY()>lButtonTop && event.getY()<lButtonBottom && currentMatchIndex!=0) {
					currentMatchIndex--;
					setWillNotDraw(true);				
					setWillNotDraw(false);				
				}
				// draw right button
				if(event.getX()>rButtonLeft && event.getX()<rButtonRight && event.getY()>rButtonTop && event.getY()<rButtonBottom & currentMatchIndex!=matches.size()-1) {
					currentMatchIndex++;
					setWillNotDraw(true);				
					setWillNotDraw(false);				
				}
				
				// check for click on win array
				for (int i =0; i<7; i++) {
					if(event.getX()>xPartition + xPartition * i && event.getX()<xPartition + xPartition * i + 100 && event.getY()>1150 && event.getY()<1250) {						
						if(winningArray[i]=="0") {
							winningArray[i]="1";							
						} else {
							winningArray[i]="0";
						}
				
						setWillNotDraw(true);				
						setWillNotDraw(false);					
					}
					
				}
				
				// check for click on lose array
				for (int i =0; i<7; i++) {
					if(event.getX()>xPartition + xPartition * i && event.getX()<xPartition + xPartition * i + 100 && event.getY()>1300 && event.getY()<1400) {
						if(losingArray[i]=="0") {
							losingArray[i]="1";							
						} else {
							losingArray[i]="0";
						}						
						
						setWillNotDraw(true);				
						setWillNotDraw(false);					
					}
					
				}				
				
				
				
		        //Toast.makeText(this.getContext(), "onTouchEvent", Toast.LENGTH_LONG).show(); 
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		return false;		
		
		
	}
	
	
}
