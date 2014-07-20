package com.example.fling;

import android.R.interpolator;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity {
	int win_width;
	int win_height;
	int user_img_width;
	int user_img_height;
	ImageView bubbleHead;
	LayoutParams params;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	    Point size = new Point();
	    getWindowManager().getDefaultDisplay().getSize(size);
	    win_width = size.x;
	    win_height = size.y;
	    
		bubbleHead = (ImageView)findViewById(R.id.user_bubble);
		bubbleHead.setScaleType(ScaleType.CENTER);
	    params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    params.leftMargin = 0;
    	params.topMargin = 0;
	    bubbleHead.setLayoutParams(params);
	    
	    bubbleHead.setOnTouchListener(new View.OnTouchListener() {
		  float current_x, current_y;
		  float dx = 0, dy = 0;
    	  
		  @Override
		  public boolean onTouch(View v, final MotionEvent event) {
			  switch (event.getAction()) {
	    	  	case MotionEvent.ACTION_DOWN:
	    	  		dx = current_x - params.leftMargin;
	    	  		dy = current_y - params.topMargin;
	    	  		
	    	  		current_x = event.getX();// - win_width/2 - dx;
	    	    	current_y = event.getY();// - win_height/2 - dy;
	    	    	//Toast.makeText(getApplicationContext(), "X: "+current_x+"; Y: "+current_y, Toast.LENGTH_LONG).show();
	    	    	
	    	    return true;
	    	    
	    	    case MotionEvent.ACTION_MOVE:
	    	    	current_x = event.getX();// - win_width/2 - dx;
	    	    	current_y = event.getY();// - win_height/2 - dy;
	    	    	//Toast.makeText(getApplicationContext(), "X: "+current_x+"; Y: "+current_y, Toast.LENGTH_LONG).show();
	    	    	
	    	    	if(current_x + user_img_width> win_width)
	    	    		params.leftMargin = (int) win_width - user_img_width;
	    	    	else
	    	    		params.leftMargin = (int) (current_x);
	    	    	if(current_y + user_img_height > win_height)
	    	    		params.topMargin = (int) win_height - user_img_height;
	    	    	else
	    	    		params.topMargin = (int) (current_y);
	    	    	
	    	    	bubbleHead.setLayoutParams(params);
	    	    return true;
	    	    
	    	    case MotionEvent.ACTION_UP:
	    	    	current_x = event.getX();// - win_width/2 - dx;
	    	    	current_y = event.getY();// - win_height/2 - dy;
	    	    	//Toast.makeText(getApplicationContext(), "X: "+current_x+"; Y: "+current_y, Toast.LENGTH_LONG).show();
	    	    	
		    	    TranslateAnimation trans = 
		    	    	new TranslateAnimation(0, -(current_x/2), 0, -(current_y/2));
		    	    trans.setStartOffset(0);
		    	    trans.setDuration(500);
		    	    trans.setInterpolator(getApplicationContext(), interpolator.bounce);
		    	    trans.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation arg0) {}
						
						@Override
						public void onAnimationRepeat(Animation arg0) {}
						
						@Override
						public void onAnimationEnd(Animation arg0)
						{
							// Stop glitch
							bubbleHead.clearAnimation();
							
							// Keep imageView at animation end
							params.leftMargin = (int) (0);
							params.topMargin = (int) (0);
							bubbleHead.setLayoutParams(params);
						}
					});
		    	    bubbleHead.startAnimation(trans);
	    	    return true;
	    	  }
	    	return false;
	      }
	    }
	    );
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) {
	  // TODO Auto-generated method stub
	  super.onWindowFocusChanged(hasFocus);
	  user_img_width = bubbleHead.getWidth();
	  user_img_height = bubbleHead.getHeight();
	 }
}
