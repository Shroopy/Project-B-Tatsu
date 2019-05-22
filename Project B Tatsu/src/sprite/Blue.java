package sprite;

import java.awt.*;
import java.util.*;

import enums.BlockHeight;
import enums.ControlState;
import other.DrawingSurface;
import other.Sounds;
import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 60;
	public static final int HEIGHT = 80;
	public static final double GRAV = 0.4;

	/**
	 * Calls Character's super constructor
	 * @param x: The x position of Blue
	 * @param y: The y position of Blue
	 * @param facing: The direction Blue is facing
	 */
	public Blue(int x, int y, int facing) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT, facing);
	}

	// METHODS

	/**
	 * Calls Character's act method.
	 */
	public void act() {
		super.act();
	}

	/**
	 * Increments Blue's vertical velocity by the static final field GRAV, representing its gravity.
	 */
	@Override
	protected void fall() {
		vY += GRAV;
	}

	@Override
	public int getCharHeight() {
		if (crouching) {
			return HEIGHT * 3 / 4;
		}
		return HEIGHT;
	}

	@Override
	public int getCharWidth() {
		return WIDTH;
	}

	// ATTACKS
	// int xOffset, int yOffset, int width, int height, int startup, int active, int recovery, int hitstun, int blockstun, double xKB, double yKB, String blockHeight

//	/**
//	 * Produces a hitbox 30 pixels wide and 80 hitboxes tall Has 2 startup, 1 active, 2 recovery, and hits mid
//	 */
//	public void testAttack() {
//		if (controlState == ControlState.CONTROLLABLE)
//			addHitbox(0, 0, 30, 80, 2, 1, 2, 8, 4, 30 / 8, 0, BlockHeight.MID);
//	}

	@Override
	public void fivea() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, 20, 60, 10, 2, 1, 2, 8, 4, 60 / 8, 0, BlockHeight.MID, false);
	}

	@Override
	public void fiveb() {
		if (controlState == ControlState.CONTROLLABLE) {
			moveByAmount(20 * facing, 0);
			addHitbox(0, 20, 50, 40, 4, 4, 10, 20, 15, 70 / 20, 0, BlockHeight.MID, false);
		}
	}

	@Override
	public void fivec() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(-30, -40, 90, 60, 9, 5, 16, 18, 14, 120 / 18, 0, BlockHeight.MID, false);
	}

	@Override
	public void sixc() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, 20, 60, 40, 20, 8, 6, 17, 14, 90 / 17, 0, BlockHeight.HIGH, false);

	}

	@Override
	public void twoa() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, (int) ((getHeight() - 30)), 60, 30, 3, 2, 4, 10, 6, 40 / 10, 0, BlockHeight.LOW, false);

	}

	@Override
	public void twob() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, (int) ((getHeight() - 30)), 80, 30, 5, 4, 10, 18, 14, 60 / 18, 0, BlockHeight.LOW, false);

	}

	@Override
	public void twoc() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, (int) ((getHeight() - 30)), 70, 30, 7, 3, 25, 1001, 19, 30 / 14, 3, BlockHeight.LOW, false);
	}

	@Override
	public void ja() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, (int)(getHeight()), 30, 30, 3, 1001, 2, 9, 7, 30 / 9, 0, BlockHeight.HIGH, false);
		
	}

	@Override
	public void jb() {
		if (controlState == ControlState.CONTROLLABLE) {
			addHitbox(0, -40, 30, 60, 9, 3, 10, 9, 7, 30 / 9, 0, BlockHeight.HIGH, false); //second
			addHitbox(0, 10, 60, 40, 5, 3, 0, 11, 9, 80 / 11, 0, BlockHeight.HIGH, false); //first
		}
		
	}

	@Override
	public void jc() {
		if (controlState == ControlState.CONTROLLABLE)
			addHitbox(0, (int) ((getHeight() - 30)), 80, 40, 9, 8, 18, 16, 14, 30 / 16, 0, BlockHeight.HIGH, false);
		
	}
	
	@Override
	public void dpa() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vX = 0;
			vY -= 6;
			addHitbox(0, -20, 60, 75, 3, 10, 26, 1001, 10, 90 / 30, 8, BlockHeight.MID, false);
			this.adjustMeter(10);
			Sounds.srk();
			invincibleStartupLeft = 4;
			invincibleLeft = 11;
		}
	}
	
	@Override
	public void dpb() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vY -= 10;
			vX = 0.5 * facing;
			addHitbox(0, -20, 60, 90, 1, 15, 33, 1001, 10, 90 / 30, 10, BlockHeight.MID, false);
			this.adjustMeter(10);
			Sounds.srk();
			invincibleStartupLeft = 0;
			invincibleLeft = 15;
		}
	}
	
	@Override
	public void dpc() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vY -= 12;
			vX = 1 * facing;
			addHitbox(0, -40, 60, 110, 1, 15, 33, 1001, 10, 120 / 30, 12, BlockHeight.MID, true);
			adjustMeter(-33);
			Sounds.srk();
			invincibleStartupLeft = 0;
			invincibleLeft = 19;
		}
	}
	
	@Override
	public void qcfa() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vX = 0;
			addProjectile(Color.CYAN.darker(), 0, 20, 50, 50, 4, 14, DrawingSurface.DRAWING_WIDTH, 40, facing, 25, 25, 60 / 25, 0, BlockHeight.MID, false, false);
			this.adjustMeter(5);
			Sounds.hadouken();
		}
	}
	
	@Override
	public void qcfb() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vX = 0;
			addProjectile(Color.CYAN.darker(), 0, 20, 50, 50, 6, 11, DrawingSurface.DRAWING_WIDTH, 42, facing, 25, 25, 80 / 25, 0, BlockHeight.MID, false, false);
			this.adjustMeter(5);
			Sounds.hadouken();
		}
	}
	
	@Override
	public void qcfc() {
		if (controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			vX = 0;
			addProjectile(Color.CYAN.brighter(), 0, 20, 50, 50, 8, 5, DrawingSurface.DRAWING_WIDTH, 30, facing, 1001, 15, 100 / 15, 12, BlockHeight.MID, true, true);
			this.adjustMeter(-33);
			Sounds.hadouken();
		}
	}
	
	public void qcba() 
	{
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			grounded = false;
			tatsuV = facing * 5;
			addHitbox(-20, (int)(getHeight())-20, 80, 40, 9, 3, 16, 16, 14, 5, 0, BlockHeight.MID, false);
			//vY = 0;
			this.adjustMeter(10);
			Sounds.tatsu();
			tatsuStartupLeft = 9;
			tatsuLeft = 13;
			y--;
		}
	}
	
	public void qcbb() 
	{
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			grounded = false;
			tatsuV = facing * 5;
			//vY = 0;
			addHitbox(-20, (int)(getHeight())-20, 80, 40, 9, 3, 0, 6, 6, 10, 0, BlockHeight.MID, false); //first
			addHitbox(-20, 0, 50, 80, 9 + 3 + 6, 5, 0, 4, 4, 10, 0, BlockHeight.MID, false); //second
			addHitbox(-20, (int)(getHeight()), 60, 30, 9 + 3 + 6 + 5 + 4, 4, 18, 18, 14, 5, 0, BlockHeight.HIGH, false); //third
			
			this.adjustMeter(5);
			Sounds.tatsu();
			tatsuStartupLeft = 9;
			tatsuLeft = 13;
			y--;
		}
	}
	
	public void qcbc() 
	{
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			grounded = false;
			tatsuV = facing * 10;
			addHitbox(-20, (int)(getHeight())-20, 80, 40, 5, 1, 0, 6, 6, 10, 0, BlockHeight.MID, true); //first
			addHitbox(-20, 0, 50, 80, 5 + 2 + 1, 1, 0, 4, 4, 10, 0, BlockHeight.MID, true); //second
			addHitbox(-20, (int)(getHeight()), 60, 30, 5 + 2 + 1 + 2 + 1, 1, 18, 1001, 14, 5, 2, BlockHeight.HIGH, true); 
			//vY = 0;
			this.adjustMeter(-33);
			Sounds.tatsu();
			tatsuStartupLeft = 5;
			tatsuLeft = 13;
			y--;
		}
	}

	public void dda() {
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			moveByAmount(15 * facing, 0);
			addHitbox(0, 0, 80, 50, 12, 5, 22, 30, 12, 12, 5, BlockHeight.MID, true); //first
			this.adjustMeter(15);
		}
	}

	public void ddb() {
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			moveByAmount(30 * facing, 0);
			addHitbox(0, 0, 80, 50, 15, 5, 27, 30, 12, 15, 5, BlockHeight.MID, true); //first
			this.adjustMeter(15);
		}
	}

	public void ddc() {
		if(controlState == ControlState.CONTROLLABLE || (controlState == ControlState.RECOVERY && attackHit)) {
			crouching = false;
			moveByAmount(60 * facing, 0);
			addHitbox(0, 0, 80, 50, 8, 5, 18, 30, 12, 18, 5, BlockHeight.MID, true); //first
			this.adjustMeter(-33);
		}
	}
}
