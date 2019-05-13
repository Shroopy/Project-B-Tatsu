
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;


public abstract class Character extends Sprite {

	// FIELDS
	protected boolean grounded = false;
	protected String controlState = "controllable";
	protected boolean invincible = false;
	protected boolean crouching, lastCrouching;
	protected ArrayList<Hitbox> hitboxes;
	// absolute position of character on the stage
	protected int absX;
	protected int hitstunLeft, recoveryLeft;
	private int hitboxOffsetX;
	
	protected int facing; // 1 is right, -1 is left
	// protected String state;

	// CONSTRUCTORS
	/**
	 * Constructs an object of the Character class, using Sprite's super constructor
	 * @param color: The color of the drawn character
	 * @param x: The x value of the character
	 * @param y: The y value of the character
	 * @param w: The width of the character
	 * @param h: The height of the character
	 * @param facing: Which way the character is facing
	 */
	public Character(Color color, int x, int y, int w, int h, int facing) {
		super(color, x, y, w, h);
		hitboxes = new ArrayList<Hitbox>();
		assert facing == 1 || facing == -1;
		this.facing = facing;
	}
	
	/**
	 * This is the method that advances the game. Moves the characters based on the X and Y velocity and checks and sets state and Hitboxes.
	 */
	public void act() {
		
		if(crouching != lastCrouching)
			y += height - getCharHeight();
		height = getCharHeight();
		lastCrouching = crouching;
		
		// FALL (and stop when a platform is hit)
		if (grounded) {
			if(absX < 0) {
				moveByAmount(1,0);
				absX = (int) (DrawingSurface.midscreen - 400 + super.x);
			}
			else if(absX > 2400) {
				moveByAmount(-1,0);
				absX = (int) (DrawingSurface.midscreen - 400 + super.x);
			}
			else {
				moveByAmount(vX, vY);
				absX = (int) (DrawingSurface.midscreen - 400 + super.x);
				//System.out.println("absX: " + absX + " - screenX: " + super.x);
			}
		} else {
			moveByAmount(vX * 0.6, vY);
			absX = (int) (DrawingSurface.midscreen - 400 + super.x);
		}
		fall();

		if (this.getY() > 400) {
			vY = 0;
			grounded = true;

		} else {
			grounded = false;
			crouching = false;
		}

		updateHitboxes();
		if (hitboxes.size() > 0) {
			if (grounded)
				vX = 0;
			controlState = "attacking";
		} else if (controlState.equals("attacking"))
			controlState = "controllable";

		if (recoveryLeft > 0)
			recoveryLeft--;
		else if (controlState.equals("recovery")) {
			controlState = "controllable";
		}

		if (hitstunLeft > 0)
			hitstunLeft--;
		else if (controlState.equals("hitstun")) {
			controlState = "controllable";
			vX = 0;
		}
	}

	/**
	 * Adds a hitbox to the ArrayList of hitboxes attributed to this Character.
	 * @param xOffset: The x at which the hitbox should be generated relative to the character
	 * @param yOffset: The y at which the hitbox should be generated relative to the character
	 * @param width: The width of the hitbox
	 * @param height: The height of the hitbox
	 * @param startup: How long the hitbox takes to become active
	 * @param active: How long the hitbox remains active
	 * @param recovery: How long the Character cannot move after the hitbox has stopped being active
	 * @param hitstun: How long the opposing Character cannot move after struck by the hitbox
	 * @param xKB: Horizontal movement of the opposing Character after struck by the hitbox
	 * @param yKB: Vertical movement of the opposing Character after struck by the hitbox
	 * @param blockHeight: unused presently
	 */
	protected void addHitbox(int xOffset, int yOffset, int width, int height, int startup, int active, int recovery,  int hitstun, double xKB, double yKB, String blockHeight) {
		assert facing == 1 || facing == -1;
		if (facing == 1)
			hitboxOffsetX = getCharWidth();
		else
			hitboxOffsetX = -width;
		hitboxes.add(new Hitbox(xOffset, yOffset, (int) x + hitboxOffsetX, (int) y, width, height, startup, active,
				recovery, facing, hitstun, xKB, yKB, blockHeight));
	}

	/**
	 * Draws the next frame of the game. Calls Sprite's version of draw.
	 * @param g: The PApplet on which the game is drawn.
	 */
	public void draw(PApplet g) {
		super.draw(g);
		for (Hitbox h : hitboxes) {
			h.draw(g);
		}
	}

	/**
	 * 
	 */
	public void changeState(int input) 
	{
		assert input == 0 || input == 1;
		if(input == 0) {
			controlState = "";
			vX = 0;
			vY = 0;
			aX = 0;
		}	
		else 
			controlState = "controllable";
			
	}
	
	protected abstract void fall();

	public int getAbsX() {
		return absX;
	}
	
	public abstract int getCharHeight();

	public abstract int getCharWidth();

	public String getControlState() {
		return controlState;
	}

	public int getFacing() {
		return facing;
	}

	public ArrayList<Hitbox> getHitboxes() {
		return hitboxes;
	}

	/**
	 * 	Checks whether any of the hitboxes produced by this character touches the other character.
	 * @param rect: The Rectangle2D.Double that represents the other character.
	 * @return A hitbox that is touching the other character.
	 */
	public Hitbox hitboxesIntersect(Rectangle2D.Double rect) {
		for (Hitbox h : hitboxes) {
			if (h.intersects(rect))
				return h;
		}
		return null;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public boolean isGrounded() {
		return grounded;
	}

	/**
	 * Causes the character being controlled to jump.
	 */
	public void jump() {
		if (controlState.equals("controllable") && grounded)
			vY = -12;
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public void setScreenX(double set) 
	{
		super.x = set;
	}
	
	/**
	 * Called when a hitbox intersects this character, inflicts hitstun and knockback based on the attack the opponent has hit with. 
	 * @param hitstun: Hitstun inflicted by the opposing attack.
	 * @param xKB: Horizontal knockback inflicted by the opposing attack.
	 * @param yKB: Vertical knockback inflicted by the opposing attack.
	 */
	public void takeHit(int hitstun, double xKB, double yKB) {
		hitstunLeft = hitstun;
		vX = xKB * -1 * facing;
		vY = -yKB;
		controlState = "hitstun";
		hitboxes.clear();
		recoveryLeft = 0;
	}

	public abstract void testAttack();

	/**
	 * Updates what hitboxes are on screen and which phase they are in(startup, active, recovery)
	 */
	protected void updateHitboxes() {
		for (int i = 0; i < hitboxes.size(); i++) {
			Hitbox h = hitboxes.get(i);
			assert facing == 1 || facing == -1;
			if (facing == 1)
				hitboxOffsetX = getCharWidth();
			else
				hitboxOffsetX = -1 * (int) h.getHitboxWidth();
			h.adjustPosition((int) x + hitboxOffsetX, (int) y);
			h.updateState();
			if (h.getState().equals("inactive"))
				hitboxes.remove(i);
			if (hitboxes.size() == 0) {
				controlState = "recovery";
				recoveryLeft = h.getRecovery();
			}
		}
	}

	/**
	 * If on the ground, causes the character to move left or right.
	 * @param dir: Input value of -1 or 1, determines whether character walks left or right.
	 */
	public void walk(int dir) {
		if (controlState.equals("controllable") && grounded && !crouching)
			vX = dir * 4;
	}
	
	
	
	// ATTACKS
	public abstract void fivea();
	public abstract void fiveb();
	public abstract void fivec();
	public abstract void sixc();
	public abstract void twoa();
	public abstract void twob();
	public abstract void twoc();

}
