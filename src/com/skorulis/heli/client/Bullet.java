package com.skorulis.heli.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.skorulis.heli.base.RenderComponent;
import com.skorulis.heli.base.UpdateComponent;
import com.skorulis.heli.components.Gen2fComponent;

public class Bullet  implements RenderComponent,UpdateComponent{

	ImageElement image;
	Gen2fComponent loc;
	
	public Bullet() {
		image = (ImageElement) new Image("bullet.png").getElement().cast();
	}
	
	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render(Context2d context) {
		
	}

}
