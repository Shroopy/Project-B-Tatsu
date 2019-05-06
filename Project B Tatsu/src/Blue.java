
import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 60;
	public static final int HEIGHT = 80;
	public static final double GRAV = 0.4;

	public Blue(int x, int y) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT);
		//hitboxes.add(new Hitbox(25, 0, x, y, WIDTH, HEIGHT, 180, 180));
		//hitboxes.add(new Hitbox(25, 0, x, y, WIDTH, HEIGHT, 180, 180, facing));
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
			hitboxes.add(new Hitbox(25, 0, (int)x, (int)y, WIDTH/2, HEIGHT, 2, 1, 2, facing, 8, 7.5/2, 0));
	}
	

}
