package com.mti.connectfour;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BoardView  extends SurfaceView implements OnTouchListener {

	Paint paint;
	Paint paintShadow;
	
	float xValue;
	float yValue;	

	ArrayList<Match> matches;
	int currentMatchIndex;
	
	private SharedPreferences sharedPreferences;
	
	public BoardView(Context context) {
		super(context);
		
		paint = new Paint();
		paintShadow = new Paint();
		

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
		
		
		int numColumns=7;
		int numRows=6;
		int xPartition;
		int yPartition;
		int yBase;
		int xSemiPartition;
		int ySemiPartition;
		
		canvas.drawColor(Color.GRAY);
		
		
		
		paint.setTextSize(50);
		paint.setColor(Color.RED);
		

		paint.setColor(Color.BLACK);
		canvas.drawRect(800, 1200, 900, 1300 ,paint);
		
		paint.setColor(Color.BLACK);
		canvas.drawRect(1000, 1200, 1100, 1300 ,paint);
	

		
		
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
					paint.setColor(Color.RED);
					canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 50, paint);					
	
					RectF rectangle = new RectF((column+1) * xPartition - 40, yBase - (row) * yPartition -40,(column+1) * xPartition + 40, yBase - (row) * yPartition + 40);
					RectF innerRect = new RectF((column+1) * xPartition - 28, yBase - (row) * yPartition -28,(column+1) * xPartition + 28, yBase - (row) * yPartition + 28);


					
					paintShadow.setStyle(Paint.Style.STROKE);
					paintShadow.setColor(Color.WHITE);
					canvas.drawArc(rectangle, (float) 135, (float)180, true, paintShadow);
					paintShadow.setColor(Color.BLACK);	
					canvas.drawArc(rectangle, (float) 315, (float)180, true, paintShadow);
					paintShadow.setColor(Color.RED);
					paintShadow.setStrokeWidth(1);
					canvas.drawLine((column+1) * xPartition - 35, yBase - (row) * yPartition + 35, (column+1) * xPartition + 35, yBase - (row) * yPartition - 35, paintShadow);
					canvas.drawRect(innerRect, paint);
				}
				if(matches.get(currentMatchIndex).getValueAtPosition(row, column)==2) {
					paint.setColor(Color.BLACK);
					canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 50, paint);					

					RectF rectangle = new RectF((column+1) * xPartition - 40, yBase - (row) * yPartition -40,(column+1) * xPartition + 40, yBase - (row) * yPartition + 40);

					paintShadow.setStyle(Paint.Style.STROKE);
					paintShadow.setColor(Color.WHITE);
					canvas.drawArc(rectangle, (float) 135, (float)180, true, paintShadow);
					paintShadow.setColor(Color.BLACK);	
					canvas.drawArc(rectangle, (float) 315, (float)180, true, paintShadow);
					paintShadow.setColor(Color.BLACK);
					paintShadow.setStrokeWidth(1);
					canvas.drawLine((column+1) * xPartition - 35, yBase - (row) * yPartition + 35, (column+1) * xPartition + 35, yBase - (row) * yPartition - 35, paintShadow);

				
				
				}
				if(matches.get(currentMatchIndex).getValueAtPosition(row, column)==0) {
					paint.setColor(Color.WHITE);
					canvas.drawCircle((column+1) * xPartition, yBase - (row) * yPartition, 50, paint);					
				}
			
			}
			
		}


		RectF rectangle = new RectF(100,100,150,150);
		//paint.setColor(Color.BLUE);
		//canvas.drawRect(rectangle, paint);

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

		//  This is my javascript code
		//ctx.arc(xpos,ypos,20,0,Math.PI*2,true);
		//canvas.drawText("" + matches.get(matches.size()-1).getValueAtPosition(0, 0), 200, 200, paint);

		paint.setColor(Color.BLACK);
		canvas.drawText("X and Y Values " + xValue + " " + yValue, 200, 1200, paint);

	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {

 

        switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				xValue = event.getX();
				yValue = event.getY();

				v.setWillNotDraw(false);

		        Toast.makeText(this.getContext(), "onTouch", Toast.LENGTH_LONG).show(); 
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

				if(event.getX()>800 && event.getX()<900 && event.getY()>1200 && event.getY()<1300 && currentMatchIndex!=0) {
					currentMatchIndex--;
					setWillNotDraw(true);				
					setWillNotDraw(false);				
				}
				
				if(event.getX()>1000 && event.getX()<1100 && event.getY()>1200 && event.getY()<1300 & currentMatchIndex!=matches.size()-1) {
					currentMatchIndex++;
					setWillNotDraw(true);				
					setWillNotDraw(false);				
				}
				
				
		        Toast.makeText(this.getContext(), "onTouchEvent", Toast.LENGTH_LONG).show(); 
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		return false;		
		
		
	}
	
	
}
