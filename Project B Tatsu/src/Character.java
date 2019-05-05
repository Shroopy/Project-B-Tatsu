
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
	protected int facing = 1; //1 is right, -1 is left
	//protected String state;
	
	// CONSTRUCTORS
	public Character(Color color, int x, int y, int w, int h) {
		super(color,x,y,w,h);
		hitboxes = new ArrayList<Hitbox>();
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
	
	public void act() {
		// FALL (and stop when a platform is hit)
		if (grounded)
			moveByAmount(vX, vY);
		else
			moveByAmount(vX * 0.6, vY);
		
		fall();

		if (this.getY() > 400) {
			vY = 0;
			grounded = true;

		} else
			grounded = false;
		
		if(hitboxes.size() > 0)
			controllable = false;
		else
			controllable = true;
		
		updateHitboxes();
	}
	
	public boolean isGrounded() {
		return grounded;
	}


	public boolean isControllable() {
		return controllable;
	}


	public ArrayList<Hitbox> getHitboxes() {
		return hitboxes;
	}


	public int getFacing() {
		return facing;
	}
	
	public abstract void testAttack();


	protected abstract void fall();
	
	
	
	
//	public void applyWindowLimits(int windowWidth, int windowHeight) {
//		x = Math.min(x,windowWidth-width);
//		y = Math.min(y,windowHeight-height);
//		x = Math.max(0,x);
//		y = Math.max(0,y);
//	}
		
}
