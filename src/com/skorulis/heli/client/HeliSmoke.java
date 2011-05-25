package com.skorulis.heli.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.GWT;
import com.skorulis.heli.base.Entity;
import com.skorulis.heli.base.RenderComponent;
import com.skorulis.heli.base.UpdateComponent;
import com.skorulis.heli.math.Vec2f;

public class HeliSmoke implements Entity,RenderComponent,UpdateComponent{

	private static final float fadeRate = 0.4f;
	private float life;
	private Vec2f loc;
	public float landRate;
	CssColor color = CssColor.make("rgba(55,55,55,1)");
	
	public HeliSmoke(Vec2f loc){ 
		this.loc = new Vec2f(loc.x,loc.y);
		life = 0.7f;
	}
	
	public void update(float delta) {
		loc.x-=landRate*delta;
		life-=delta*fadeRate;
	}
	
	public boolean isAlive() {
		return life > 0;
	}

	@Override
	public void render(Context2d context) {
		context.setGlobalAlpha(Math.max(life,0));
		context.setFillStyle(color);
		context.fillRect(loc.x, loc.y, 20, 20);
		context.fill();
		context.setGlobalAlpha(1);
	}
	
}
