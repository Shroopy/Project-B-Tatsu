
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
	protected ArrayList<Hitbox> hitboxes;
	protected boolean facingRight = true;
	
	//absolute position of character on the stage
	protected boolean absX;
	//protected String state;
	
	// CONSTRUCTORS
	public Character(Color color, int x, int y, int w, int h) {
		super(color,x,y,w,h);
		hitboxes = new ArrayList<Hitbox>();
	}
	
	
	// METHODS	
	public void walk(int dir) {
		if(controllable && grounded)
			vX = dir*4;
	}
	
	public void jump() {
		if(controllable && grounded)
			vY -= 12;
	}
	
	protected void updateHitboxes() {
		for(int i = 0; i < hitboxes.size(); i++) {
			hitboxes.get(i).adjustPosition((int)x, (int)y);
			hitboxes.get(i).updateState();
			if(hitboxes.get(i).getState().equals("inactive"))
				hitboxes.remove(i);
		}
	}
	
	public void draw(PApplet g) {
		super.draw(g);
		for(Hitbox h : hitboxes) {
			h.draw(g);
		}
	}
	
	
	
//	public void applyWindowLimits(int windowWidth, int windowHeight) {
//		x = Math.min(x,windowWidth-width);
//		y = Math.min(y,windowHeight-height);
//		x = Math.max(0,x);
//		y = Math.max(0,y);
//	}
	
	
	
	
	
}










