

import java.awt.*;
import java.util.*;

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
		if(crouching) {
			return HEIGHT * 3 / 4;
		}
		return HEIGHT;
	}

	@Override
	public int getCharWidth() {
		return WIDTH;
	}

	//ATTACKS
	//int xOffset, int yOffset, int width, int height, int startup, int active, int recovery,  int hitstun, double xKB, double yKB, String blockHeight
	
	/**
	 * Produces a hitbox 30 pixels wide and 80 hitboxes tall
	 * Has 2 startup, 1 active, 2 recovery, and hits mid
	 */
	public void testAttack() {
		if (controlState.equals("controllable"))
			addHitbox(0, 0, 30, 80, 2, 1, 2, 8, 30 / 8, 0, "mid");
	}

	@Override
	public void fivea() {
		if (controlState.equals("controllable"))
			addHitbox(0, 0, 60, 10, 2, 1, 2, 8, 60 / 8, 0, "mid");
	}

	@Override
	public void fiveb() {
		if (controlState.equals("controllable")) {
			moveByAmount(20, 0);
			addHitbox(0, 0, 50, 40, 4, 4, 10, 18, 70 / 18, 0, "mid");
		}
	}

	@Override
	public void fivec() {
		if (controlState.equals("controllable"))
			addHitbox(-30, -40, 90, 60, 9, 5, 16, 18, 120 / 18, 0, "mid");
	}

	@Override
	public void sixc() {
		if (controlState.equals("controllable"))
			addHitbox(0, 20, 60, 40, 20, 8, 6, 17, 90 / 17, 0, "high");
		
	}

	@Override
	public void twoa() {
		if (controlState.equals("controllable"))
			addHitbox(0, (int)((getHeight() - 30)), 60, 30, 3, 2, 4, 10, 40 / 10, 0, "low");
		
	}

	@Override
	public void twob() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void twoc() {
		// TODO Auto-generated method stub
		
	}
	
	

}
