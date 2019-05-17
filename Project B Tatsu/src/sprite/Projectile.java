package sprite;

import java.awt.Color;

import enums.BlockHeight;
import enums.HitboxState;
import processing.core.PApplet;

public class Projectile extends Hitbox {
	private int distanceTraveled, maxDistance;
	private boolean transcendent;

	public Projectile(Color color, int x, int y, int w, int h, double vX, int startup, int maxDistance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean transcendent) {
		super(0, 0, x, y, w, h, startup, 0, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
		this.color = color;
		this.vX = vX;
		distanceTraveled = 0;
		this.maxDistance = maxDistance;
		this.transcendent = transcendent;
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
			if(state == HitboxState.STARTUP)
				state = HitboxState.ACTIVE;
			active--;
		} else
			state = HitboxState.INACTIVE;
	}
	
	public void deactivate() {
		state = HitboxState.INACTIVE;
	}
	
	public boolean isTranscendent() {
		return transcendent;
	}
}
