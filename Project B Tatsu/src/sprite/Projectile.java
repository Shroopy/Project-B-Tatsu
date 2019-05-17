package sprite;
import java.awt.Color;

import enums.BlockHeight;
import processing.core.PApplet;

public class Projectile extends Hitbox {
	private int distanceTraveled;
	
	public Projectile(Color color, int x, int y, int w, int h, int distance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight) {
		super(0, 0, x, y, w, h, 0, distance, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
		this.color = color;
		distanceTraveled = 0;
	}
	
	public void adjustPosition() {
		moveByAmount(vX * getFacing(), vY);
		distanceTraveled += vX;
	}

	public void updateState() {
		// TODO
	}
}
