package com.research.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GraphView extends SurfaceView implements SurfaceHolder.Callback{
	
	private static final String TAG = "GraphView";
	private GraphThread m_graphThread;

	public GraphView(Context context) {
		super(context);
		init();
	}
	
	public GraphView(Context context, AttributeSet attr){
		super(context, attr);
		init();
	}
	public GraphView(Context context, AttributeSet attr, int defStyle){
		super(context, attr, defStyle);
		init();
	}
	
	public void init(){
		getHolder().addCallback(this);
		m_graphThread = new GraphThread(getHolder(), this);
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(m_graphThread.getState() == Thread.State.TERMINATED){
			m_graphThread = new GraphThread(getHolder(), this);
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}else{
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// Do Nothing
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		m_graphThread.setRunning(false);
		
		while(retry){
			try{
				m_graphThread.join();
				retry = false;
			}catch(InterruptedException e){
				// Try again...
			}
		}
	}
	
	public void onDraw(Canvas canvas){
		Log.v(TAG, "Entering draw sequence");
	}
}

















