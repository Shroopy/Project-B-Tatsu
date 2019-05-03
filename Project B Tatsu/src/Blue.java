

import java.awt.*;
import java.util.*;

import processing.core.PImage;

public class Blue extends Character {

	public static final int WIDTH = 40;
	public static final int HEIGHT = 60;
	public static final double grav = 0.2;

	public Blue(int x, int y) {
		super(Color.BLUE, x, y, WIDTH, HEIGHT);
	}

	// METHODS

	public void act(ArrayList<Shape> obstacles) {
		// FALL (and stop when a platform is hit)
		if(state.equals("grounded"))
			moveByAmount(vX, vY);
		else
			moveByAmount(vX*0.6, vY);
		
		vY += grav;
		for(Shape s : obstacles) 
		{
			if(this.intersects(s.getBounds2D())) 
			{
				vY = 0;
				state = "grounded";
				break;
			}
			else
				state = "air";
		}
	}
	


}
