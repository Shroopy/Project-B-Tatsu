
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * 
 */
public abstract class Character extends Sprite {
	
	// FIELDS
	protected boolean grounded = false;
	protected boolean controllable = true;
	protected boolean facingRight = true;
	//protected String state;
	
	// CONSTRUCTORS
	public Character(Color color, int x, int y, int w, int h) {
		super(color,x,y,w,h);
	}
	
	
	// METHODS	
	public void walk(int dir) {
		if(controllable && grounded)
			vX = dir*5;
	}
	
	public void jump() {
		if(controllable && grounded)
			vY -= 10;
	}
	
	
	
//	public void applyWindowLimits(int windowWidth, int windowHeight) {
//		x = Math.min(x,windowWidth-width);
//		y = Math.min(y,windowHeight-height);
//		x = Math.max(0,x);
//		y = Math.max(0,y);
//	}
	
	
	
	
	
}










