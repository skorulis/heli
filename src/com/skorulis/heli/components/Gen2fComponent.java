package com.skorulis.heli.components;

import com.skorulis.heli.base.UpdateComponent;
import com.skorulis.heli.math.Vec2f;

public class Gen2fComponent extends Vec2f implements UpdateComponent{

	private Gen2fComponent child;
	
	public Gen2fComponent() {
		
	}
	
	public Gen2fComponent(Gen2fComponent child) {
		this.child = child;
	}
	
	public Gen2fComponent(Gen2fComponent child,float x,float y) {
		this.child = child;
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public void update(float delta) {
		child.setX(child.x + x*delta);
		child.setY(child.y + y*delta);
	}

	
	
}
