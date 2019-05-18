package sprite;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import enums.BlockHeight;
import enums.HitboxState;
import other.DrawingSurface;
import processing.core.PApplet;

public class Projectile extends Hitbox {
	private int distanceTraveled, maxDistance;
	private boolean transcendent;

	public Projectile(Color color, int x, int y, int w, int h, double vX, int startup, int maxDistance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean transcendent) {
		super(0, 0, 0, y, w, h, startup, 0, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
		absX = x + DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH;
		this.color = color;
		this.vX = vX;
		distanceTraveled = 0;
		this.maxDistance = maxDistance;
		this.transcendent = transcendent;
	}

	public void adjustPosition() {
		if (state == HitboxState.ACTIVE) {
			moveBy(vX * getFacing(), vY);
			distanceTraveled += vX;
		}
	}
	
	private void moveBy(double x, double y) {
		absX += x;
		y += y;
	}
	
	@Override
	public boolean intersects(Rectangle2D r) {
		return new Rectangle2D.Float((float)absX + DrawingSurface.ENDZONE_WIDTH - DrawingSurface.midscreen, (float) y, (float)width, (float) height).intersects(r);
	}

	public void draw(PApplet g) {
		if (state == HitboxState.ACTIVE)
			g.stroke(0);
			g.fill(color.getRed(), color.getGreen(), color.getBlue());
			g.rect((float)absX + DrawingSurface.ENDZONE_WIDTH - DrawingSurface.midscreen, (float) y, (float)width, (float) height);
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
