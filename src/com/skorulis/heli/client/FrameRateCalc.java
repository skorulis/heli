package com.skorulis.heli.client;


public class FrameRateCalc {

	private int frames;
	private float time;
	private int tmpFrames;
	private float tmpTime;
	
	private int tmpReset;
	private float tmpFrameRate;
	
	public FrameRateCalc() {
		frames=0; time = 0;
		tmpReset = 10;
		tmpFrames = 0; tmpTime = 0;
	}
	
	public void addFrame(float time) {
		frames++; tmpFrames++;
		this.time+=time;
		tmpTime+=time;
		if(tmpFrames>tmpReset) {
			tmpFrameRate = tmpFrames/tmpTime;
			tmpTime = 0; tmpFrames = 0;
		}
	}
	
	public float getFrameRate() {
		return frames/time;
	}
	
	public float getTmpFrameRate() {
		return tmpFrameRate;
	}
	
}
