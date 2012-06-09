package com.research.test;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GraphThread extends Thread{
	SurfaceHolder m_surfaceHolder;
	GraphView m_graphView;
	boolean isRunning = false;
	
	public GraphThread(SurfaceHolder holder, GraphView game){
		m_surfaceHolder = holder;
		m_graphView = game;
	}
	
	public void setRunning(boolean r){
		isRunning = r;
	}
	
	public SurfaceHolder getSurfaceHolder(){
		return m_surfaceHolder;
	}
	
	public void run(){
		Canvas c;
		while(isRunning){
			c = null;
			try{
				c = m_surfaceHolder.lockCanvas();
				synchronized (m_surfaceHolder){
					m_graphView.onDraw(c);
				}
			}finally{
				if(c != null)
					m_surfaceHolder.unlockCanvasAndPost(c);
			}
		}
	}
}
