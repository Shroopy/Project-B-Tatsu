package sprite;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import enums.BlockHeight;
import enums.ControlState;
import enums.HitboxState;
import other.DrawingSurface;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Character extends Sprite {

	// FIELDS
	protected boolean grounded = false, invincible = false, crouching, lastCrouching, knockedDown, attackHit;
	protected ControlState controlState = ControlState.CONTROLLABLE;
	protected ArrayList<Hitbox> hitboxes;
	protected ArrayList<Projectile> projectiles;
	// absolute position of character on the stage
	protected int hitstunLeft, recoveryLeft, invincibleLeft, invincibleStartupLeft, tatsuLeft, tatsuStartupLeft;
	private int hitboxOffsetX;
	protected BlockHeight blocking = BlockHeight.NOT;
	protected int meter;
	private double kbFromEdge;
	
	public BlockHeight getBlockHeight() {
		return blocking;
	}

	public void setBlocking(BlockHeight blocking) {
		this.blocking = blocking;
	}

	protected int facing; // 1 is right, -1 is left
	// protected String state;

	// CONSTRUCTORS
	/**
	 * Constructs an object of the Character class, using Sprite's super constructor
	 * 
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
		projectiles = new ArrayList<Projectile>();
		assert facing == 1 || facing == -1;
		this.facing = facing;
		absX = 800 + x;
		meter = 0;
	}

	public boolean isAttackHit() {
		return attackHit;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	/**
	 * Changes the height and y position of the character based on whether they were crouching or not.
	 */
	public void updateCrouching() {
		if (crouching != lastCrouching)
			y += height - getCharHeight();
		height = getCharHeight();
		lastCrouching = crouching;
	}

	/**
	 * This is the method that advances the game. Moves the characters based on the
	 * X and Y velocity and checks and sets state and Hitboxes.
	 */
	public void act() {

		updateCrouching();

		if(meter > 100)
			meter = 100;
		// FALL (and stop when a platform is hit)

		if (absX < 0) {
			moveByAmount(1, vY);
			absX = (int) (DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH + super.x);
		} else if (absX > DrawingSurface.STAGE_WIDTH - getCharWidth()) {
			moveByAmount(-1, vY);
			absX = (int) (DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH + super.x);
		} else if (grounded) {
			moveByAmount(vX, vY);
			absX = (int) (DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH + super.x);
			// System.out.println("absX: " + absX + " - screenX: " + super.x);
		} else {
			moveByAmount(vX * 0.6, vY);
			absX = (int) (DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH + super.x);
		}
		fall();

		if (this.getY() >= 415) {
			vY = 0;
			if (!grounded) {
				y = 335 + (float)getHeight();
				
				if (hitboxes.size() > 0) {
					controlState = ControlState.RECOVERY;
					recoveryLeft = hitboxes.get(hitboxes.size() - 1).getRecovery();
					hitboxes.clear();
				}
				if (recoveryLeft > 0)
					vX = 0;
				if (knockedDown) {
					knockedDown = false;
					vX = 0;
				}

				grounded = true;
			}
		} else {
			grounded = false;
			crouching = false;
		}

		updateHitboxes();
		if (hitboxes.size() > 0) {
			if (grounded)
				vX = 0;
			controlState = ControlState.ATTACKING;
		} else if (controlState == ControlState.ATTACKING) {
			controlState = ControlState.CONTROLLABLE;
		}

		if (recoveryLeft > 0)
			recoveryLeft--;
		else if (controlState == ControlState.RECOVERY) {
			controlState = ControlState.CONTROLLABLE;
			attackHit = false;
		}

		if (hitstunLeft > 0) {
			if (!knockedDown)
				hitstunLeft--;
		} else if (controlState == ControlState.HITSTUN) {
			invincible = false;
			controlState = ControlState.CONTROLLABLE;
			vX = 0;
		}

		if (invincibleStartupLeft > 0) {
			invincibleStartupLeft--;
		} else if (invincibleLeft > 0) {
			invincibleLeft--;
		} else {
			invincible = false;
		}
		
		if(tatsuStartupLeft > 0) {
			tatsuStartupLeft--;
			if(this.getY() < 375) {
				y = 295 + (float)getHeight();
			}
			else {
				vY = -6;
			}
		} else if(tatsuLeft > 0) {
			tatsuLeft--;
			if(this.getY() < 375) {
				y = 295 + (float)getHeight();
			}
			else {
				vY = -6;
			}
		}

	}

	/**
	 * Adds a hitbox to the ArrayList of hitboxes attributed to this Character.
	 * 
	 * @param xOffset: The x at which the hitbox should be generated relative to the
	 *        character
	 * @param yOffset: The y at which the hitbox should be generated relative to the
	 *        character
	 * @param width: The width of the hitbox
	 * @param height: The height of the hitbox
	 * @param startup: How long the hitbox takes to become active
	 * @param active: How long the hitbox remains active
	 * @param recovery: How long the Character cannot move after the hitbox has
	 *        stopped being active
	 * @param hitstun: How long the opposing Character cannot move after struck by
	 *        the hitbox
	 * @param xKB: Horizontal movement of the opposing Character after struck by the
	 *        hitbox
	 * @param yKB: Vertical movement of the opposing Character after struck by the
	 *        hitbox
	 * @param blockHeight: unused presently
	 */
	protected void addHitbox(int xOffset, int yOffset, int width, int height, int startup, int active, int recovery, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean ko) {
		assert facing == 1 || facing == -1;
		if (facing == 1)
			hitboxOffsetX = getCharWidth();
		else
			hitboxOffsetX = -width;
		updateCrouching();
		hitboxes.add(new Hitbox(xOffset, yOffset, (int) x + hitboxOffsetX, (int) y, width, height, startup, active, recovery, facing, hitstun, blockstun, xKB, yKB, blockHeight, ko));
	}
	
	protected void addProjectile(Color color, int xOffset, int yOffset, int width, int height, double vX, int startup, int maxDistance, int recovery, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean transcendent, boolean ko) {
		assert facing == 1 || facing == -1;
		if (facing == 1)
			hitboxOffsetX = getCharWidth();
		else
			hitboxOffsetX = -width;
		updateCrouching();
		projectiles.add(new Projectile(color, (int)x + hitboxOffsetX + xOffset, (int)y + yOffset, width, height, vX, startup, maxDistance, facing, hitstun, blockstun, xKB, yKB, blockHeight, transcendent, ko));
		attackHit = false;
		recoveryLeft = recovery;
		controlState = ControlState.RECOVERY;
	}

	/**
	 * Draws the next frame of the game. Calls Sprite's version of draw.
	 * 
	 * @param g: The PApplet on which the game is drawn.
	 */
	public void draw(PApplet g) {
		super.draw(g);
		for (Hitbox h : hitboxes) {
			h.draw(g);
		}
		for (Projectile p : projectiles) {
			p.draw(g);
		}
		if (hitstunLeft > 0 || recoveryLeft > 0) {
			g.noFill();
			g.ellipseMode(PConstants.CENTER);
			g.ellipse(width / 2 + x, height / 2 + y, width / 4, height / 4);
		}
		if (blocking != BlockHeight.NOT) {
			g.fill(0);
			g.triangle(x, y, x + width, y, x + width / 2, y + height / 2);
		}
		if (invincibleLeft > 0) {
			g.fill(0);
			g.triangle(x, y + height, x + width, y + height, x + width / 2, y + height / 2);
		}
	}

	/**
	 * Changes the state of the character between CONTROLLABLE or NOT.
	 * @param input: true if the character should not be controllable, false if they should be controllable
	 */
	public void changeState(boolean input) {
		if (input) {
			controlState = ControlState.NOT;
			vX = 0;
			vY = 0;
			aX = 0;
		} else
			controlState = ControlState.CONTROLLABLE;

	}

	protected abstract void fall();

	public boolean isInvincible() {
		return invincible;
	}

	public abstract int getCharHeight();

	public abstract int getCharWidth();

	public ControlState getControlState() {
		return controlState;
	}

	public int getFacing() {
		return facing;
	}

	public int getMeter() 
	{
		return meter;
	}
	public void adjustMeter(int m) 
	{
		meter += m;
	}
	public void setMeter(int m) {
		meter = m;
	}
	
	public ArrayList<Hitbox> getHitboxes() {
		return hitboxes;
	}

	/**
	 * Checks whether any of the hitboxes produced by this character touches the
	 * other character.
	 * 
	 * @param rect: The Rectangle2D.Double that represents the other character.
	 * @return A hitbox that is touching the other character.
	 */
	public Hitbox hitboxesIntersect(Rectangle2D.Float rect) {
		for (Hitbox h : hitboxes) {
			if (h.intersects(rect)) {
				System.out.println(h.toString());
				return h;
			}
		}
		return null;
	}
	
	/**
	 * Checks if the Projectiles produced by this character intersects with a Rectangle2D.Float
	 * @param rect: The Rectangle2D.Float checked for intersection
	 * @return The Projectile if it makes contact with the Rectangle2D.Float
	 */
	public Projectile projectilesIntersect(Rectangle2D.Float rect) {
		for (Projectile p : projectiles) {
			if (p.intersects(rect))
				return p;
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
		if (controlState == ControlState.CONTROLLABLE && grounded)
			vY = -12;
	}

	public void setCrouching(boolean crouching) {
		if(controlState == ControlState.CONTROLLABLE) {
			if (crouching)
				vX = 0;
			this.crouching = crouching;
		}
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public void setScreenX(float set) {
		super.x = set;
	}

	/**
	 * Called when a hitbox intersects this character, inflicts hitstun and
	 * knockback based on the attack the opponent has hit with.
	 * 
	 * @param hitstun: Hitstun inflicted by the opposing attack.
	 * @param xKB: Horizontal knockback inflicted by the opposing attack.
	 * @param yKB: Vertical knockback inflicted by the opposing attack.
	 */
	public boolean takeHit(int hitstun, double xKB, double yKB, boolean ko) {
		meter += xKB * 0.5;
		if(ko) 
		{
			if(facing == 1 && absX < 400) 
			{
				return true;
			}
			else if(facing == -1 && absX > 2000) 
			{
				return true;
			}
		}		
		if (hitstun == 1001) {
			hitstun = 30;
			hitstunLeft = 30;
			invincible = true;
			knockedDown = true;
		} else
			hitstunLeft = hitstun;
		
		double newXKB;
		if (absX - hitstun * xKB < 0) {
			newXKB = absX / hitstun;
			kbFromEdge = xKB - newXKB;
		} else if (absX + hitstun * xKB > DrawingSurface.STAGE_WIDTH) {
			newXKB = (DrawingSurface.STAGE_WIDTH - absX) / hitstun;
			kbFromEdge = xKB - newXKB;
		} else
			newXKB = xKB;
		vX = newXKB * -1 * facing;
		
		vY = -yKB;
		controlState = ControlState.HITSTUN;
		hitboxes.clear();
		recoveryLeft = 0;
		
		return false;
//		if (absX - hitstun * xKB < 0) {
//			moveByAmount(1, vY);
//		} else if (absX + hitstun * xKB > DrawingSurface.STAGE_WIDTH) {
//			moveByAmount(-1, vY);
//		}
	}

	/**
	 * Called when a hitbox intersects this character while it is blocking, inflicts
	 * hitstun and knockback based on the attack the opponent has hit with.
	 * 
	 * @param hitstun: Hitstun inflicted by the opposing attack on block.
	 * @param xKB: Horizontal knockback inflicted by the opposing attack normally.
	 */
	public void blockHit(int hitstun, double xKB) {
		meter += xKB * 0.25;
		hitstunLeft = hitstun;
		
		xKB = xKB * 3 / 4;
		double newXKB;
		if (absX - hitstun * xKB < 0) {
			newXKB = absX / hitstun;
			kbFromEdge = xKB - newXKB;
		} else if (absX + hitstun * xKB > DrawingSurface.STAGE_WIDTH) {
			newXKB = (DrawingSurface.STAGE_WIDTH - absX) / hitstun;
			kbFromEdge = xKB - newXKB;
		} else
			newXKB = xKB;
		vX = newXKB * -1 * facing;
		
		controlState = ControlState.HITSTUN;
		hitboxes.clear();
		recoveryLeft = 0;
	}

	// public abstract void testAttack();

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
			if (h.getState() == HitboxState.INACTIVE)
				hitboxes.remove(i);
			if (hitboxes.size() == 0) {
				controlState = ControlState.RECOVERY;
				recoveryLeft = h.getRecovery();
			}
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.adjustPosition();
			p.updateState();
			if (p.getState() == HitboxState.INACTIVE)
				projectiles.remove(i);
		}
	}

	/**
	 * If on the ground, causes the character to move left or right.
	 * 
	 * @param dir: Input value of -1 or 1, determines whether character walks left
	 *        or right.
	 */
	public void walk(int dir) {
		if (controlState == ControlState.CONTROLLABLE && grounded && !crouching)
			vX = dir * 4;
	}

	public void setRecoveryLeft(int recoveryLeft) {
		this.recoveryLeft = recoveryLeft;
	}
	
	public void addvX(double vX) {
		this.vX += vX;
	}
	
	// ATTACKS
	public abstract void fivea();

	public abstract void fiveb();

	public abstract void fivec();

	public abstract void sixc();

	public abstract void twoa();

	public abstract void twob();

	public abstract void twoc();

	public abstract void ja();

	public abstract void jb();

	public abstract void jc();

	public abstract void dpa();

	public abstract void dpb();
	
	public abstract void dpc();
	
	public abstract void qcfa();
	
	public abstract void qcfb();
	
	public abstract void qcfc();
	
	public abstract void qcba();
	
	public abstract void qcbb();
	
	public abstract void qcbc();

	public void setAttackHit(boolean attackHit) {
		this.attackHit = attackHit;
	}

	public double getKbFromEdge() {
		return kbFromEdge;
	}

}
