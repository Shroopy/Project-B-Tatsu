
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

/*
 * 
 */
public abstract class Character extends Rectangle2D.Double {
	
	// FIELDS
	private Color color;
	protected double vX, vY, aX = 0;
	protected String state = "grounded";
	
	// CONSTRUCTORS
	public Character(Color color, int x, int y, int w, int h) {
		super(x,y,w,h);
		this.color = color;
	}
	
	
	// METHODS	
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	public void walk(int dir) {
		if(state.equals("grounded"))
			vX = dir*5;
	}
	
	public void jump(int dir) {
		if(state.equals("grounded"))
			vY -= 5;
	}
	
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	
	public void draw(PApplet g) {
		//g.image(image,(int)x,(int)y,(int)width,(int)height);
		g.stroke(0);
		g.fill(color.getRed(), color.getGreen(), color.getBlue());
		g.rect((float)x, (float)y, (float)width, (float)height);
	}
	
	
}










