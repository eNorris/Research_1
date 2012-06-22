package com.research.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpectrumView extends SurfaceView implements SurfaceHolder.Callback{
	
	private static final String TAG = "GraphView";
	private SpectrumThread m_graphThread;
	private int[] spectrumData = new int[]{
			0, 6, 24, -106, 58, -609, -576, -838, -412, -145, 393, 958, 1404, 2322, 2963, 4053, 3761, 4393, 4632, 4892,
			5135, 5087, 5144, 5213, 5364, 5302, 5135, 4984, 4945, 4618, 4502, 4287, 3979, 3919, 3592, 3358, 3065, 2819, 2621, 2373,
			2134, 2121, 1897, 1837, 1641, 1594, 1433, 1611, 1496, 1551, 1545, 1479, 1536, 1527, 1660, 1713, 1731, 1793, 1904, 2017,
			2022, 2101, 2197, 2189, 2394, 2438, 2351, 2486, 2479, 2651, 2608, 2651, 2648, 2746, 2841, 2729, 2921, 2968, 2990, 3063,
			3168, 3165, 3291, 3479, 3586, 3786, 3770, 3898, 4038, 3998, 4149, 4057, 4210, 4253, 4216, 4289, 4069, 4198, 4175, 4041,
			3842, 3725, 3610, 3490, 3337, 3060, 2955, 2775, 2502, 2299, 2117, 1918, 1784, 1632, 1561, 1400, 1312, 1120, 1029, 943,
			896, 858, 769, 685, 641, 606, 544, 585, 548, 532, 540, 498, 481, 496, 466, 486, 498, 433, 456, 497,
			463, 410, 465, 447, 460, 477, 441, 428, 436, 458, 402, 461, 422, 388, 418, 422, 444, 398, 424, 427,
			435, 425, 393, 405, 397, 452, 405, 451, 432, 519, 485, 478, 464, 461, 435, 520, 541, 523, 516, 587,
			576, 564, 591, 562, 631, 655, 687, 620, 696, 689, 731, 772, 749, 658, 705, 724, 745, 786, 753, 770,
			757, 749, 702, 673, 718, 718, 695, 647, 712, 688, 617, 606, 580, 592, 528, 526, 497, 516, 521, 464,
			456, 430, 417, 432, 397, 414, 350, 378, 383, 344, 376, 321, 347, 364, 360, 364, 346, 303, 320, 341,
			323, 360, 342, 339, 331, 385, 425, 363, 432, 420, 389, 428, 421, 431, 453, 478, 508, 523, 547, 543,
			547, 524, 643, 578, 639, 654, 719, 734, 772, 738, 764, 769, 791, 793, 779, 838, 775, 779, 873, 785,
			872, 831, 863, 866, 812, 836, 790, 841, 789, 770, 754, 740, 704, 658, 631, 714, 656, 619, 584, 597,
			589, 546, 530, 525, 453, 473, 459, 442, 431, 391, 418, 394, 409, 365, 396, 320, 335, 349, 305, 266,
			313, 296, 309, 285, 284, 243, 280, 267, 287, 263, 297, 301, 283, 295, 268, 279, 240, 286, 277, 278,
			262, 266, 279, 264, 264, 293, 258, 273, 242, 277, 300, 259, 261, 279, 249, 272, 296, 280, 265, 269,
			245, 280, 271, 273, 253, 237, 261, 250, 276, 261, 284, 273, 235, 252, 223, 228, 276, 214, 246, 241,
			243, 228, 222, 239, 250, 196, 265, 183, 230, 226, 193, 217, 208, 240, 239, 217, 208, 221, 210, 218,
			211, 203, 215, 216, 219, 200, 211, 189, 183, 220, 213, 186, 198, 215, 192, 192, 200, 182, 178, 196,
			195, 183, 185, 186, 197, 186, 182, 188, 186, 170, 193, 198, 195, 170, 181, 192, 171, 189, 205, 193,
			199, 189, 189, 215, 206, 198, 168, 193, 171, 189, 194, 202, 190, 201, 182, 201, 203, 188, 194, 202,
			191, 197, 177, 172, 195, 248, 200, 225, 180, 219, 237, 186, 216, 200, 196, 237, 229, 196, 208, 195,
			206, 211, 208, 205, 239, 238, 200, 200, 215, 197, 218, 215, 194, 182, 193, 187, 202, 211, 198, 205,
			193, 184, 178, 170, 174, 186, 173, 171, 188, 197, 192, 156, 146, 163, 182, 202, 170, 172, 166, 185,
			171, 176, 189, 200, 192, 166, 168, 166, 193, 184, 213, 157, 136, 144, 191, 162, 187, 178, 182, 175,
			175, 171, 192, 186, 156, 170, 195, 183, 217, 194, 212, 162, 200, 183, 199, 190, 180, 216, 222, 206,
			213, 216, 244, 199, 215, 234, 215, 228, 264, 261, 284, 246, 234, 236, 225, 256, 267, 253, 250, 257,
			261, 268, 268, 263, 249, 269, 260, 255, 256, 287, 290, 269, 273, 305, 270, 263, 259, 305, 276, 281,
			278, 271, 260, 250, 250, 265, 279, 275, 285, 259, 240, 275, 241, 253, 274, 272, 229, 265, 269, 269,
			278, 234, 258, 264, 273, 232, 235, 252, 246, 267, 253, 226, 256, 269, 237, 204, 232, 229, 204, 232,
			237, 227, 194, 237, 225, 202, 191, 213, 198, 191, 222, 190, 207, 228, 201, 202, 176, 179, 168, 186,
			168, 166, 157, 160, 154, 158, 168, 172, 157, 175, 175, 165, 138, 160, 175, 128, 166, 147, 167, 127,
			174, 140, 164, 162, 160, 144, 150, 174, 171, 159, 139, 164, 160, 176, 163, 158, 142, 141, 162, 138,
			150, 168, 163, 150, 160, 166, 166, 152, 178, 168, 145, 158, 188, 170, 160, 184, 137, 131, 155, 171,
			157, 169, 151, 128, 135, 171, 149, 172, 142, 153, 126, 144, 152, 138, 146, 147, 139, 153, 130, 144,
			139, 146, 144, 132, 154, 138, 148, 160, 156, 96, 133, 149, 138, 139, 138, 156, 158, 151, 137, 138,
			161, 160, 135, 177, 169, 155, 190, 145, 187, 192, 175, 178, 161, 169, 174, 166, 172, 191, 173, 136,
			191, 178, 203, 192, 172, 184, 183, 179, 202, 212, 174, 193, 194, 171, 178, 209, 186, 182, 216, 210,
			184, 186, 182, 181, 180, 179, 212, 210, 218, 205, 173, 216, 206, 177, 190, 185, 166, 212, 196, 191,
			168, 194, 176, 195, 160, 191, 190, 169, 161, 155, 165, 149, 147, 179, 158, 145, 143, 162, 147, 166,
			156, 131, 148, 151, 137, 143, 118, 140, 118, 125, 140, 122, 126, 130, 143, 146, 137, 113, 122, 127,
			124, 128, 129, 133, 126, 136, 105, 116, 129, 127, 105, 124, 110, 118, 122, 131, 123, 147, 119, 126,
			119, 140, 134, 146, 151, 126, 137, 143, 123, 144, 128, 119, 132, 117, 129, 117, 117, 142, 100, 115,
			125, 119, 117, 124, 119, 108, 118, 119, 102, 119, 101, 94, 102, 109, 111, 108, 97, 102, 91, 86,
			112, 114, 89, 103, 105, 89, 76, 89, 89, 85, 103, 72, 70, 74, 73, 73, 61, 84, 86, 73,
			58, 66, 74, 71, 70, 48, 65, 52, 68, 50, 51, 58, 67, 46, 47, 66, 54, 53, 67, 51,
			46, 59, 47, 52, 29, 44, 47, 47, 63, 55, 57, 61, 39, 54, 59, 37, 39, 44, 57, 46,
			58, 51, 47, 54, 56, 48, 65, 54, 51, 65, 51, 64, 54, 54, 70, 60, 65, 77, 65, 72,
			69, 79, 74, 71, 65, 58, 69, 70, 79, 84, 71, 79, 74, 78, 84, 74, 77, 82, 80, 69,
			78, 84, 86, 96, 73, 90, 97, 79, 79, 87, 90, 94, 85, 85, 67, 90, 68, 81, 71, 73,
			92, 93, 96, 102, 85, 68, 73, 83, 94, 64, 90, 69, 78, 71, 60, 85, 90, 69, 86, 67,
			58, 63, 64, 64, 75, 77, 72, 56, 54, 67, 47, 57, 57, 52, 57, 49, 51, 51, 49, 42,
			48, 40, 56, 52, 63, 49, 34, 37, 41, 44, 45, 47, 44, 40, 46, 48, 45, 39, 47, 47,
			52, 37, 43, 45, 38, 48, 51, 48, 49, 38, 50, 41, 43, 37, 33, 47, 48, 53, 60, 44,
			49, 45, 44, 57, 43, 55, 60, 55, 55, 61, 52, 55, 63, 70, 40, 52, 60, 49, 48, 66,
			52, 42, 48, 48, 54, 62, 49, 49, 49, 48, 49, 55, 56, 48, 46, 49, 64, 46, 41, 49,
			39, 49, 49, 51, 48, 38, 46, 48, 35, 34, 34, 39, 42, 33, 29, 32, 45, 40, 24, 28,
			25, 30, 41, 35, 34, 24, 29, 33, 28, 33, 15, 26, 15, 28, 21, 23, 22, 20, 17, 17,
			24, 21, 25, 24, 19, 20, 18, 11, 13, 15, 14, 6, 5, 13, 14, 4, 9, 6, 20, 1,
			10, 12, 10, 11, 6, 7, 2, 5, 12, 11, 4, 15, 8, 5, 9, 4, 12, 11, 6, 3,
			6, 3, 10, 7, -1, 8, 12, 5, 11, 4, 5, 8, 13, 7, 10, 10, 6, 8, 3, 4,
			5, 8, 6, 9, 9, -3, 8, 7, 9, 7, 5, 4, 8, 1, 8, 4, 3, 1, 6, 3,
			9, 1, 2, 6, 2, 6, 6, 2, 5, 9, 10, 8, 4, 6, 3, 3, 2, 3, 6, 3,
			2, 8, 5, 3, 4, 4, 7, 1, 3, 4, 4, 4, 3, 5, 2, 2, 4, 6, 5, 1,
			5, 5, 0, 4, 2, 4, 1, 4, 2, 5, 4, 2, 2, 3, 2, 2, 3, 2, 3, 6,
			0, 3, 1, 3, -1, 3, 1, 2, 2, -3, 1, -2, 2, 2, 2, 2, 3, 1, 1, 3,
			2, 3, 0, 1, 1, -3, 2, 2, 0, 0, 2, 0, -1, 0, 0, 2, 1, -2, 0, 6,
			2, 1, -4, 0, 0, 1, 2, 1, -2, 0, -1, 1, 3, 2, 0, -3, 4, 1, 3, 2,
			2, 4, -1, 2, 0, -2, 0, 0, 1, 3, 0, 1, 0, 1, -1, 2, 1, 3, 0, 0,
			0, 0, 0, 1, -1, 0, 2, 0, 2, 2, -2, 1, 2, 5, -3, 2, 1, 0, -4, 0,
			3, 1, -1, -1, 0, -1, -1, 3, -2, -1, -1, 0, -1, 2, -1, 3, 3, -2, -2, 1,
			0, 2, 2, -2, -3, 0, 2, -1, 1, -3, 3, 0, 3, 1, 2, -1, 1, 2, -2, -1,
			1, 0, 0, 2, 0, 0, -1, 2, 0, 0, -1, 1, 2, 0, 1, 0, -1, 0, -2, 0,
			1, 2, -1, 1, 2, -1, 0, -1, 0, 1, -1, 1, 0, -1, -1, 0, -1, 1, 2, 0,
			0, 0, 0, 1, 0, -1, 3, -1, -1, 0, 3, -1, -1, 1, -1, 2, 0, 1, -3, 0,
			0, 0, -1, 0, 1, -1, 0, -2, -1, -1, 0, 0, 0, 0, 0, 1, 1, 0, -1, 0,
			2, 1, -1, 0, 0, 0, 4, 0, 1, 0, 0, 0, 0, 2, 1, 2, 0, 2, 2, 0,
			-2, 0, 1, -2, -1, -1, 2, -1, 1, -1, -1, 0, -1, 0, 2, -1, 0, 0, -1, 0,
			1, 0, 0, 0, 1, -1, 1, 0, 1, 1, 0, 0, -1, 2, 0, 1, 0, 0, -1, 1,
			-1, 2, -1, -1, 0, 0, -2, 1, 0, 1, 1, 0, 1, -1, 0, 0, 0, 0, 1, 0,
			0, 1, 0, 1, 0, -1, 1, -1, 0, 0, 0, 1, 0, 0, -3, 1, 1, 0, -1, -1,
			-1, 0, 0, 0, -2, 0, -1, -1, -1, 1, 2, -1, 0, -1, 0, 1, -2, 0, 2, -1,
			0, 0, 0, 1, -3, 0, 1, -1, -1, 1, 1, -1, 2, 0, 0, 0, 1, 0, 0, -1,
			0, 2, -2, 1, 1, 0, 5, 0, 0, 0, 2, -1, 1, 1, -1, -2, -1, 0, 0, -1,
			1, 0, 0, -1, -1, -2, 0, -2, 1, 1, -1, 0, 0, -1, 0, -1, 0, 0, 1, -1,
			1, 1, 0, 1, 0, -2, 0, 0, -1, -1, 0, -1, 0, -1, -1, 0, 0, 1, 0, 0,
			-1, 0, 1, 0, 0, 0, 0, 0, 1, 2, 3, -4, 0, -1, 0, -1, -1, 0, 0, 0,
			-2, 1, 0, 0, 0, 0, -1, 1, 2, 0, 0, -1, 0, 1, 1, -1, -1, 0, -2, 0,
			0, -2, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, -1, -2, -1, -1, -1, 1, 1, 1,
			-1, -1, 1, 0, -1, 0, -1, -1, 0, 0, 0, 3, -1, -2, 0, 0, -1, 0, 0, 3,
			0, 0, 0, -1, -1, 0, 1, 0, 0, 2, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0,
			1, 0, 1, 0, 0, -1, 1, 0, -2, 2, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0,
			-1, 0, 0, 0, -1, 1, 0, -1, -1, 0, 1, 0, 0, 0, 0, 0, 0, -1, -1, 0,
			0, 0, 0, 1, 0, 0, 0, 0, -2, 0, 0, -4, 0, 0, 0, 0, -1, 1, 0, 0,
			1, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 0,
			1, 1, -1, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,
			0, 0, 0, -1, 0, 1, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 1, 0, 1, 0,
			0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0,
			0, 1, -1, 0, 0, 0, 1, -1, 1, -1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0,
			0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -1, 0, 0, 1, 0, -1, 0, -1, 0, 0,
			0, 0, -1, -1, 1, -1, 0, 0
	};
	private Paint paint = new Paint();
	
	public int xMax, xMin, yMin, yMax;
	
	public SpectrumView(Context context) {
		super(context);
		init();
	}
	
	public SpectrumView(Context context, AttributeSet attr){
		super(context, attr);
		init();
	}
	public SpectrumView(Context context, AttributeSet attr, int defStyle){
		super(context, attr, defStyle);
		init();
	}
	
	public void init(){
		getHolder().addCallback(this);
		m_graphThread = new SpectrumThread(getHolder(), this);
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if(m_graphThread.getState() == Thread.State.TERMINATED){
			m_graphThread = new SpectrumThread(getHolder(), this);
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}else{
			m_graphThread.setRunning(true);
			m_graphThread.start();
		}
		Log.v(TAG, "Created surface");
		
		
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		EchelonBundle.screenBundle.xMin = 0;
//		EchelonBundle.screenBundle.xMax = getWidth();
		EchelonBundle.screenBundle.yMin = 0;
//		EchelonBundle.screenBundle.yMax = getHeight();
		EchelonBundle.screenBundle.height = (float) (EchelonBundle.screenBundle.yMax = getHeight());
		EchelonBundle.screenBundle.width = (float) (EchelonBundle.screenBundle.xMax = getWidth());
		
		// TODO - make sure these are correct and not zero
		Log.d(TAG, "Caught height = " + EchelonBundle.screenBundle.height);
		Log.d(TAG, "Caught width = " + EchelonBundle.screenBundle.width);
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
	
	// TODO get rid of spectrumData - this will eventually be gotten from
	// EchelonBundle instead
	public void onDraw(Canvas canvas){
		if(canvas != null){
			drawSpectrum(canvas, spectrumData);
			drawAxisSystem(canvas);
		}
	}
	
	// TODO data -> EchelonData
	public void drawSpectrum(Canvas canvas, int[] data){
		
		// Calculate the width of each bar in the graph
		float width = ((float) canvas.getWidth()) / data.length;
		float max = 0;
		for(int i = 0; i < data.length; i++)
			if(data[i] > max)
				max = data[i];
		float heightmod = canvas.getHeight() / max;
		
		// Set the spectrum color
		paint.setColor(Color.BLUE);
		
		// Draw the bar graph
		for(int i = 0; i < data.length; i++){
			canvas.drawRect(i*width, canvas.getHeight() - data[i]*heightmod, (i+1)*width, canvas.getHeight(), paint);
		}
	}
	
	public void drawAxisSystem(Canvas canvas){
		// TODO - remove the below line later, it should be a user config
		EchelonBundle.configBundle.axisPaint.setColor(Color.GREEN);
		
		if(EchelonBundle.configBundle.xAxisOn){
			// Draw the main x-axis
			canvas.drawLine(0, absY(EchelonBundle.screenBundle.oriY), 
					canvas.getWidth(), absY(EchelonBundle.screenBundle.oriY), 
					EchelonBundle.configBundle.axisPaint);
	
			// Draw tick marks
			float startPoint = EchelonBundle.screenBundle.oriX;
			float moveBy = EchelonBundle.screenBundle.scaleX * EchelonBundle.configBundle.tickHeight;
			float goal = EchelonBundle.screenBundle.width;
			
			// Set the printhead
			while(absX(startPoint) > 0){
				startPoint -= moveBy;
				Log.d(TAG, "backtracking along x...");
			}
			
			// TODO - Draw ticks.
			
			while(absX(startPoint) < goal){
				
				Log.d(TAG, "will draw x tick: [spectrum: " + EchelonBundle.screenBundle.oriY + " abs: " + 
				absY(EchelonBundle.screenBundle.oriY) + "]");
				
				canvas.drawLine(
						absX(startPoint),						// start x
						absY(EchelonBundle.screenBundle.oriY), 	// start y
						absX(startPoint), 						// stop x
						absY(EchelonBundle.screenBundle.oriY) - EchelonBundle.configBundle.tickHeight, // stop y
						EchelonBundle.configBundle.axisPaint);	// paint
				startPoint += moveBy;
				Log.d(TAG, "advancing in x...(" + startPoint + ")");
			}
			
//			for(float i = EchelonBundle.screenBundle.xMin; i < EchelonBundle.screenBundle.xMax; i += 
//					EchelonBundle.configBundle.tickX){
//				canvas.drawLine(i, absY(EchelonBundle.screenBundle.oriY), i, 
//						absY(EchelonBundle.screenBundle.oriY) - EchelonBundle.configBundle.tickHeight,
//						EchelonBundle.configBundle.axisPaint);
//			}
		}
		if(EchelonBundle.configBundle.yAxisOn){
			// Draw the main y-axis
			canvas.drawLine(absX(EchelonBundle.screenBundle.oriX), 0, 
					absX(EchelonBundle.screenBundle.oriX), EchelonBundle.screenBundle.height,
					EchelonBundle.configBundle.axisPaint);
			// Draw tick marks
			
			float startPoint = EchelonBundle.screenBundle.oriY;
			float moveBy = EchelonBundle.configBundle.tickHeight * EchelonBundle.screenBundle.scaleY;
			float goal = EchelonBundle.screenBundle.height;
			
			while(absY(startPoint) > 0){
				startPoint += moveBy;
				Log.d(TAG, "backtracking... (" + startPoint + ")");
			}
			
			while(absY(startPoint) < goal){
				canvas.drawLine(
						absX(EchelonBundle.screenBundle.oriX),		// start x
						absY(startPoint), 						// start y
						absX(EchelonBundle.screenBundle.oriX) + EchelonBundle.configBundle.tickHeight, 	// stop x
						absY(startPoint), 						// stop y
						EchelonBundle.configBundle.axisPaint);	// paint
				startPoint -= moveBy;
				Log.d(TAG, "advancing...(" + startPoint + ")");
			}
			
		/*	for(float i = EchelonBundle.screenBundle.yMin; i < EchelonBundle.screenBundle.yMax; i += 
					EchelonBundle.configBundle.tickY){
				canvas.drawLine(absX(EchelonBundle.screenBundle.oriX), i, absX(EchelonBundle.screenBundle.oriX), 
						absY(EchelonBundle.screenBundle.oriY) - EchelonBundle.configBundle.tickHeight,
						EchelonBundle.configBundle.axisPaint);
			}
		*/
		}
	}
	
	public float absX(float x){
		return EchelonBundle.screenBundle.oriX + EchelonBundle.screenBundle.scaleX * x;
	}
	
	public float absY(float y){
		return EchelonBundle.screenBundle.height - EchelonBundle.screenBundle.oriY - EchelonBundle.screenBundle.scaleY * y;
	}
}


















