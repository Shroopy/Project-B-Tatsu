package sprite;

import java.awt.Color;

import enums.BlockHeight;
import enums.HitboxState;
import processing.core.PApplet;

public class Projectile extends Hitbox {
	private int distanceTraveled, maxDistance;

	public Projectile(Color color, int x, int y, int w, int h, double vX, int startup, int maxDistance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight) {
		super(0, 0, x, y, w, h, startup, 0, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
		this.color = color;
		this.vX = vX;
		distanceTraveled = 0;
		this.maxDistance = maxDistance;
	}

	public void adjustPosition() {
		if (state == HitboxState.ACTIVE) {
			moveByAmount(vX * getFacing(), vY);
			distanceTraveled += vX;
		}
	}

	public void draw(PApplet g) {
		if (state == HitboxState.ACTIVE)
			super.draw(g);
	}

	public void updateState() {
		if (startup > 0) {
			startup--;
		} else if (distanceTraveled < maxDistance) {
			state = HitboxState.ACTIVE;
			active--;
		} else
			state = HitboxState.INACTIVE;
	}
}
