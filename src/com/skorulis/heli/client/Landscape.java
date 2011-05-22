package com.skorulis.heli.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.skorulis.heli.base.RectBoundBox;
import com.skorulis.heli.base.RenderComponent;
import com.skorulis.heli.base.UpdateComponent;

public class Landscape implements RenderComponent,UpdateComponent{

	public static final int SEGMENT_WIDTH = 20;
	public static final float SLIDE_RATE = 50;
	
	int width,height;
	
	int topHeight[];
	int bottomHeight[];
	
	CssColor color = CssColor.make("rgba(8,180,75,1)");
	
	float slideF;
	int slideI;
	
	private int shrink;
	private boolean moveUp;
	private int minGap;
	private static final int ABSOLUTE_MINGAP = 80;
	
	public Landscape(int width,int height) {
		this.width = width;
		this.height = height;
		int segs = (width/SEGMENT_WIDTH)+1;
		topHeight = new int[segs];
		bottomHeight = new int[segs];
		
		reset();
	}
	
	public void reset() {
		minGap = 200;
		slideF = 0; slideI = 0;
		shrink = -1;
		
		topHeight[0] = 60;
		bottomHeight[0] = 60;
		for(int i=1; i < topHeight.length; ++i) {
			generatePosition(i);
		}
		
	}
	
	public void generatePosition(int position) {
		int prev;
		if(position==0) {
			prev = topHeight.length-1;
		} else {
			prev = (position-1)%topHeight.length;
		}
		int prevTop = topHeight[prev];
		int prevBottom = bottomHeight[prev];
		if(Random.nextInt(100)>=95) {
			moveUp = !moveUp;
		}
		if(shrink==-1) {
			if(Random.nextInt(100)>=50) {
				shrink = 1;
			}
		} else {
			if(Random.nextInt(100)>=95) {
				shrink = -1;
			}
		}
		
		topHeight[position] = Math.min(Math.max(prevTop + Random.nextInt(20)*shrink,0),height-minGap);
		bottomHeight[position] = Math.min(Math.max(prevBottom + Random.nextInt(20)*shrink,0),height-minGap);
		int total = topHeight[position] + bottomHeight[position]; 
		if(total > height-minGap) {
			if(moveUp) {
				topHeight[position]-=(total- (height-minGap));
			} else {
				bottomHeight[position]-=(total- (height-minGap));
			}
		}
	}
	
	
	@Override
	public void render(Context2d context) {
		context.setFillStyle(color);
		int pos;
		for(int i=0; i < topHeight.length; ++i) {
			pos = (i+slideI)%topHeight.length;
			context.fillRect(i*SEGMENT_WIDTH - slideF, 0, SEGMENT_WIDTH, topHeight[pos]);
			context.fillRect(i*SEGMENT_WIDTH - slideF, height-bottomHeight[pos], SEGMENT_WIDTH, bottomHeight[pos]);
			
		}
		context.fill();
	}
	
	public int getPos(int x) {
		return (x/SEGMENT_WIDTH+slideI)%topHeight.length;
	}

	@Override
	public void update(float delta) {
		slideF+=delta*SLIDE_RATE;
		if(slideF > SEGMENT_WIDTH) {
			slideF-=SEGMENT_WIDTH;
			generatePosition(slideI%topHeight.length);
			slideI++;
			if(slideI%5==0 && minGap > ABSOLUTE_MINGAP) {
				minGap--;
			}
		}
	}
	
	public boolean collides(RectBoundBox box) {
		int x =(int) box.x;
		int pos;
		GWT.log(" box "+ box);
		while(x < box.x+box.width) {
			pos = getPos(x);
			int top = topHeight[pos];
			int bottom = bottomHeight[pos];
			if(box.y < top) {
				return true;
			}
			if(box.y+box.height > height-bottom) {
				return true;
			}
			x+=SEGMENT_WIDTH;
		}
		return false;
	}

}
