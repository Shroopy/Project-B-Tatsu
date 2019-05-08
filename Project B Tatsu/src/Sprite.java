import java.awt.Color;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

public class Sprite extends Rectangle2D.Double {

	protected Color color;
	protected double vX, vY, aX = 0;

	public Sprite(Color color, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.color = color;
	}

	public void draw(PApplet g) {
		// g.image(image,(int)x,(int)y,(int)width,(int)height);
		g.stroke(0);
		g.fill(color.getRed(), color.getGreen(), color.getBlue());
		g.rect((float) x, (float) y, (float) width, (float) height);
	}

	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}

	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}

}
