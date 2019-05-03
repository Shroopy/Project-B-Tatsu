

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;
	public static final double grav = 0.2;
	private double vX, vY, aX = 0;
	private boolean grounded = false;

	public Blue(int x, int y) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT);
	}

	// METHODS
	public void walk(int dir) {
		if(grounded)
			vX = dir*5;
	}

	public void jump() {
		if(grounded)
			vY -= 8;
	}

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
		if(grounded)
			super.moveByAmount(vX, vY);
		else
			super.moveByAmount(vX*0.6, vY);
		
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
