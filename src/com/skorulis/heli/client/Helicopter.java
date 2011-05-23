package com.skorulis.heli.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import com.skorulis.heli.base.RectBoundBox;
import com.skorulis.heli.components.ClipLoc2f;
import com.skorulis.heli.components.Gen2fComponent;
import com.skorulis.heli.math.Vec2f;

public class Helicopter {

	ClipLoc2f loc;
	ClipLoc2f vel;
	Gen2fComponent acc;
	ImageElement image;
	
	Vec2f target;
	boolean firing;
	
	RectBoundBox box;
	
	public Helicopter(float width,float height) {
		loc = new ClipLoc2f();
		loc.maxX = width-50; loc.maxY = height-30;
		vel = new ClipLoc2f(loc);
		vel.setBounds(-80, -80, 80, 80);
		acc = new Gen2fComponent(vel,0,30);
		image = (ImageElement) new Image("helicopter.png").getElement().cast();
		box = new RectBoundBox();
		box.width = image.getWidth();
		box.height = image.getHeight();
	}
	
	public void reset() {
		vel.x =0; vel.y = 0;
		loc.y = loc.maxY/2;
		loc.x = 0;
	}

	public void render(Context2d context) {
		context.drawImage(image, loc.x, loc.y);
	}

	public void update(float delta) {
		acc.update(delta);
		vel.update(delta);
		box.width = image.getWidth()-8;
		box.height = image.getHeight()-8;
		box.x = loc.x+4;
		box.y = loc.y+4;
	}
	
}
