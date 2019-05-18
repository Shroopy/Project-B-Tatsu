package sprite;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

public class Sprite extends Rectangle2D.Float {

	protected Color color;
	protected double vX, vY, aX = 0;
	protected int absX;

	/**
	 * Constructs a Sprite object
	 * @param color: color of the Rectangle drawn
	 * @param x: x position of the Rectangle
	 * @param y: y position of the Rectangle
	 * @param w: width of the Rectangle
	 * @param h: height of the Rectangle
	 */
	public Sprite(Color color, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.color = color;
	}
	
	public int getAbsX() {
		return absX;
	}

	/**
	 * Draws a rectangle on g based on the x, y, width, and height of this Sprite.
	 * @param g: The PApplet on which the rect is drawn
	 */
	public void draw(PApplet g) {
		// g.image(image,(int)x,(int)y,(int)width,(int)height);
		g.stroke(0);
		g.fill(color.getRed(), color.getGreen(), color.getBlue());
		g.rect((float) x, (float) y, (float) width, (float) height);
	}

	/**
	 * Adjusts the Sprite's location by the parameters
	 * @param x: How much the Sprite's x is adjusted by
	 * @param y: How much the Sprite's y is adjusted by
	 */
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}

	/**
	 * Changes the Sprite's location to the parameters
	 * @param x: Sprite's new x 
	 * @param y: Sprite's new y 
	 */
	public void moveToLocation(float x, float y) {
		super.x = x;
		super.y = y;
	}
	
	public void zeroVX() 
	{
		vX = 0;
	}

	public Color getColor() {
		return color;
	}

	public double getvX() {
		return vX;
	}

	public double getvY() {
		return vY;
	}

	public double getaX() {
		return aX;
	}

	public void setvX(double vX) {
		this.vX = vX;
	}

	public void setvY(double vY) {
		this.vY = vY;
	}

	public void setaX(double aX) {
		this.aX = aX;
	}

}
