

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
		return HEIGHT;
	}

	@Override
	public int getCharWidth() {
		return WIDTH;
	}

	/**
	 * Produces a hitbox 30 pixels wide and 80 hitboxes tall
	 * Has 2 startup, 1 active, 2 recovery, and hits mid
	 */
	public void testAttack() {
		if (controlState.equals("controllable"))
			addHitbox(0, 0, 30, 80, 2, 1, 2, 8, 30 / 8, 0, "mid");
	}

}
