package com.skorulis.heli.components;

public class ClipLoc2f extends Gen2fComponent {

	public float minX,minY,maxX,maxY;
	
	public ClipLoc2f() {
		
	}
	
	public ClipLoc2f(Gen2fComponent child) {
		super(child);
	}
	
	public void setBounds(float minX,float minY, float maxX,float maxY) {
		this.minX = minX; this.minY = minY;
		this.maxX = maxX; this.maxY = maxY;
	}
	
	public void setX(float x) {
		if(x < minX) {
			this.x=minX;
		} else if(x > maxX) {
			this.x = maxX;
		} else {
			this.x = x;
		}
	}
	
	public void setY(float y) {
		if(y < minY) {
			this.y=minY;
		} else if(y > maxY) {
			this.y = maxY;
		} else {
			this.y = y;
		}
	}
}
