import java.awt.Color;

import processing.core.PApplet;

public class Projectile extends Sprite {
	private Hitbox hitbox;
	//private Player player;
	
	public Projectile(Color color, int x, int y, int w, int h, int distance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight) {
		super(color, x, y, w, h);
		hitbox = new Hitbox(0, 0, x, y, w, h, 0, distance, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
	}
	
	public void draw(PApplet g) {
		// g.image(image,(int)x,(int)y,(int)width,(int)height);
		g.stroke(0);
		g.fill(color.getRed(), color.getGreen(), color.getBlue());
		g.rect((float) x, (float) y, (float) width, (float) height);
	}
	
	public void act() {
		moveByAmount(vX * hitbox.getFacing(), vY);
	}

	
	
}
