
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
	protected String controlState = "controllable";
	protected boolean invincible = false;
	protected ArrayList<Hitbox> hitboxes;
	//absolute position of character on the stage
	protected int absX;
	protected int hitstunLeft, recoveryLeft;
	

	protected int facing; //1 is right, -1 is left
	//protected String state;
	
	// CONSTRUCTORS
	public Character(Color color, int x, int y, int w, int h, int facing) {
		super(color,x,y,w,h);
		hitboxes = new ArrayList<Hitbox>();
		assert facing == 1 || facing == -1;
		this.facing = facing;
	}
	
	
	// METHODS	
	public void walk(int dir) {
		if(controlState.equals("controllable") && grounded)
			vX = dir*4;
	}
	
	public void jump() {
		if(controlState.equals("controllable") && grounded)
			vY = -12;
	}
	
	public Hitbox hitboxesIntersect(Rectangle2D.Double rect) {
		for(Hitbox h : hitboxes) {
			if(h.intersects(rect))
				return h;
		}
		return null;
	}
	
	public abstract int getCharWidth();
	//public abstract int getCharHeight();
	
	protected void updateHitboxes() {
		for(int i = 0; i < hitboxes.size(); i++) {
			Hitbox h = hitboxes.get(i);
			h.adjustPosition((int)x, (int)y);
			h.updateState();
			if(h.getState().equals("inactive"))
				hitboxes.remove(i);
			if(hitboxes.size() == 0) {
				controlState = "recovery";
				recoveryLeft = h.getRecovery();
			}
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
		if (grounded) {
			moveByAmount(vX, vY);
			absX = (int) (DrawingSurface.midscreen - 400 + super.x);
		}
		else {
			moveByAmount(vX * 0.6, vY);
			absX = (int) (DrawingSurface.midscreen - 400 + super.x);
		}
		fall();

		if (this.getY() > 400) {
			vY = 0;
			grounded = true;

		} else
			grounded = false;
		
		updateHitboxes();
		if(hitboxes.size() > 0) {
			controlState = "attacking";
			vX = 0;
		}
		else if(controlState.equals("attacking"))
			controlState = "controllable";
		
		if(recoveryLeft > 0)
			recoveryLeft--;
		else if(controlState.equals("recovery")){
			controlState = "controllable";
		}
		
		if(hitstunLeft > 0)
			hitstunLeft--;
		else if(controlState.equals("hitstun")){
			controlState = "controllable";
			vX = 0;
		}
	}
	
	public boolean isGrounded() {
		return grounded;
	}


	public String getControlState() {
		return controlState;
	}


	public ArrayList<Hitbox> getHitboxes() {
		return hitboxes;
	}
	
	public int getAbsX() 
	{
		return absX;
	}

	public int getFacing() {
		return facing;
	}
	
	public abstract void testAttack();
	
	public void takeHit(int hitstun, double xKB, double yKB) {
		hitstunLeft = hitstun;
		vX = xKB * -1;
		vY = -yKB;
		controlState = "hitstun";
		hitboxes.clear();
		recoveryLeft = 0;
	}
	
	protected void addHitbox(int xOffset, int yOffset, int width, int height, int startup, int active, int recovery, int hitstun, double xKB, double yKB) {
		int hitboxOffsetX;
		
		assert facing == 1 || facing == -1;
		if(facing == 1)
			hitboxOffsetX = getCharWidth();
		else
			hitboxOffsetX = -width;
		hitboxes.add(new Hitbox(xOffset, yOffset, (int)x + hitboxOffsetX, (int)y, width, height, startup, active, recovery, facing, hitstun, xKB, yKB));
	}


	protected abstract void fall();
	
	
	
	
//	public void applyWindowLimits(int windowWidth, int windowHeight) {
//		x = Math.min(x,windowWidth-width);
//		y = Math.min(y,windowHeight-height);
//		x = Math.max(0,x);
//		y = Math.max(0,y);
//	}
		
}
