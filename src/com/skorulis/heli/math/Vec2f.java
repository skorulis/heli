package com.skorulis.heli.math;

public class Vec2f {

	public float x;
	public float y;
	
	public Vec2f() {
		x = 0;
		y = 0;
	}
	
	public Vec2f(float x,float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2f minus(Vec2f v) {
		return new Vec2f(x-v.x,y-v.y);
	}
	
	public Vec2f minusE(Vec2f v) {
		x-=v.x; y-=v.y;
		return this;
	}
	
	public Vec2f plusE(Vec2f v) {
		this.x+=v.x; this.y+=v.y;
		return this;
	}
	
	public Vec2f mult(float f) {
		return new Vec2f(x*f,y*f);
	}
	
	public Vec2f multE(float f) {
		this.x*=f; this.y*=f;
		return this;
	}
	
	public float dist(Vec2f v) {
		return (float)Math.sqrt((x-v.x)*(x-v.x) + (y-v.y)*(y-v.y));
	}
	
	public float dist(float x,float y) {
		return (float)Math.sqrt((x-this.x)*(x-this.x) + (y-this.y)*(y-this.y));
	}
	
	public float length() {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public Vec2f normaliseE() {
		float len = length();
		if(len==0) {
			return this;
		}
		this.x/=len;
		this.y/=len;
		return this;
	}
	
	public Vec2f normalise() {
		float len = length();
		if(len==0) {
			return new Vec2f();
		}
		return new Vec2f(x/len,y/len);
	}
	
	public Vec2f rotateE(float angle) {
		float cosA =(float) Math.cos(angle);
		float sinA =(float) Math.sin(angle);
		float xTmp = cosA*x - sinA*y;
		float yTmp = sinA*x + cosA*y;
		
		this.x = xTmp; this.y = yTmp;
		
		return this;
	}
	
	public Vec2f rotate(float angle) {
		float cosA =(float) Math.cos(angle);
		float sinA =(float) Math.sin(angle);
		float xTmp = cosA*x - sinA*y;
		float yTmp = sinA*x + cosA*y;
		
		return new Vec2f(xTmp,yTmp);
	}

	public Vec2f clamp(float length) {
		//TODO
		return this;
	}
	
	public String toString() {
		return "Vec2:("+x+","+y+")";
	}
	
	
}
