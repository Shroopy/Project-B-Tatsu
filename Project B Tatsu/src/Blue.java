
import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;
	public static final double grav = 0.2;

	public Blue(int x, int y) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT);
		//hitboxes.add(new Hitbox(25, 0, x, y, WIDTH, HEIGHT, 180, 180));
	}

	// METHODS

	public void act() {
		// FALL (and stop when a platform is hit)
		if (grounded)
			moveByAmount(vX, vY);
		else
			moveByAmount(vX * 0.6, vY);

		vY += grav;

		if (this.getY() > 400) {
			vY = 0;
			grounded = true;

		} else
			grounded = false;
		
		updateHitboxes();
	}
	

}
