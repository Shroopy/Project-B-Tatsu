
import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 60;
	public static final int HEIGHT = 80;
	public static final double GRAV = 0.4;

	public Blue(int x, int y, int facing) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT, facing);
	}

	// METHODS

	public void act() {
		super.act();
	}

	@Override
	protected void fall() {
		vY += GRAV;
	}
	
	public void testAttack() {
		if(controlState.equals("controllable"))
			addHitbox(0, 0, 30, 80, 2, 1, 2, 8, 30/8, 0, "mid");
	}

	@Override
	public int getCharWidth() {
		return WIDTH;
	}

	@Override
	public int getCharHeight() {
		return HEIGHT;
	}
	

}
