package com.skorulis.heli.client;

import java.util.Date;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.skorulis.heli.math.Vec2f;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Heli implements EntryPoint,KeyUpHandler,KeyDownHandler,MouseMoveHandler,MouseDownHandler,MouseUpHandler {

	private int MAX_KEYS = 256;
	
	private Context2d context;
	private Label frameLabel;
	private Label scoreLabel;
	private float score,bestScore;
	
	private final static int canvasWidth = 600;
	private final static int canvasHeight = 400;
	private Timer timer;
	
	private int lastUpdate;
	private int current;
	
	Duration duration;
	
	boolean keys[] = new boolean[MAX_KEYS];
	
	private Helicopter helicopter;
	private Landscape landscape;
	final CssColor redrawColor = CssColor.make("rgba(255,255,255,0.8)");
	
	private Vec2f mouseLoc; 
	private FrameRateCalc frameRate;
	
	private Button startButton;
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		frameRate = new FrameRateCalc();
		frameLabel = new Label("FrameRate:");
		RootPanel.get().add(frameLabel);
		
		scoreLabel = new Label();
		RootPanel.get().add(scoreLabel);
		
		
		final Canvas canvas = Canvas.createIfSupported();
		RootPanel.get().add(canvas);
		if(canvas==null) {
			return;
		}
		startButton = new Button("Start");
		RootPanel.get().add(startButton);
		
		startButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				canvas.setFocus(true);
				reset();
				timer.scheduleRepeating(20);
			}
		});
		
		
		
		canvas.setWidth(canvasWidth+"px");
		canvas.setCoordinateSpaceWidth(canvasWidth);
		
		canvas.setHeight(canvasHeight+"px");
		canvas.setCoordinateSpaceHeight(canvasHeight);
		canvas.addKeyDownHandler(this);
		canvas.addKeyUpHandler(this);
		//canvas.addMouseMoveHandler(this);
		//canvas.addMouseDownHandler(this);
		//canvas.addMouseUpHandler(this);
		
		context = canvas.getContext2d();

		mouseLoc = new Vec2f();
		
		landscape = new Landscape(canvasWidth,canvasHeight);
		helicopter = new Helicopter(canvasWidth,canvasHeight);
		duration = new Duration();
		lastUpdate = duration.elapsedMillis();
		String best = Cookies.getCookie("score");
		GWT.log("SCORE " + best);
		if(best!=null) {
			bestScore = Float.valueOf(best);
			landscape.setBestScore(bestScore);
		}
		
		timer = new Timer() {
			@Override
			public void run() {
				update();
			}
		};
	}
	
	public void reset() {
		score = 0;
		helicopter.reset();		
		landscape.reset();
		for(int i=0; i < MAX_KEYS; ++i) {
			keys[i] = false;
		}
	}
	
	public void update() {
		current = duration.elapsedMillis();
		float delta = (current-lastUpdate)/1000.0f;
		lastUpdate = current;
		if(delta > 0.5f) {
			return; //Don't update when the delta values are very high
		}
		frameRate.addFrame(delta);
		frameLabel.setText("" + frameRate.getTmpFrameRate() + " frames a second");
		score+=delta*landscape.slideRate;
		scoreLabel.setText("Score: " + (int)score);
		
		
		context.setFillStyle(redrawColor);
	    context.fillRect(0, 0, canvasWidth, canvasHeight);
		
	    landscape.update(delta);
	    landscape.render(context);
	    
		helicopter.update(delta);
		helicopter.render(context);
		
		if(keys['W']) {
			helicopter.vel.y+= -130*delta;
		}
		if(keys['D']) {
			helicopter.vel.x+= 80*delta;
		}
		if(keys['A']) {
			helicopter.vel.x+= -80*delta;
		}
		if(keys['S']) {
			//helicopter.vel.y+= 80*delta;
		}
		if(landscape.collides(helicopter.box)) {
			onDeath();
			timer.cancel();
		}
	}
	
	private void onDeath() {
		bestScore = Math.max(score, bestScore);
		Date now = new Date();
		Date expires = new Date(now.getTime() +1000*3600*24*1000);
		
		Cookies.setCookie("score", ""+bestScore, expires);
		landscape.setBestScore(bestScore);
	}	

	@Override
	public void onKeyDown(KeyDownEvent event) {
		int key = event.getNativeKeyCode();
		if(key < MAX_KEYS) {
			GWT.log("KEY " + key);
			keys[key] = true;
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		int key = event.getNativeKeyCode();
		if(key < MAX_KEYS) {
			keys[key] = false;
		}
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		mouseLoc.x = event.getX(); mouseLoc.y = event.getY();
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		helicopter.firing = true;
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		helicopter.firing = false;
	}
}
