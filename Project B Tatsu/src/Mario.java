

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Mario extends Sprite {

	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	public static final double grav = 0.1;
	private double vX, vY, aX = 0;
	private boolean grounded = false;

	public Mario(PImage img, int x, int y) {
		super(img, x, y, MARIO_WIDTH, MARIO_HEIGHT);
	}

	// METHODS
	public void walk(int dir) {
		vX = dir*5;
	}

	public void jump() {
		if(grounded)
			vY -= 5;
	}

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
		super.moveByAmount(vX, vY);
		vY += grav;
		for(Shape s : obstacles) 
		{
			if(this.intersects(s.getBounds2D())) 
			{
				vY = 0;
				grounded = true;
				break;
			}
			else
				grounded = false;
		}
	}


}
