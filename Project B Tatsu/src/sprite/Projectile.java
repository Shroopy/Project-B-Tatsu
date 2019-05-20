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

	/**
	 * Constructs a Projectile object.
	 * @param color: The color of the Projectile should be drawn in. 
	 * @param x: The projectile's initial horizontal position on the screen
	 * @param y: The projectile's initial vertical position on the screen
	 * @param w: The width of the Projectile
	 * @param h: The height of the Projectile
	 * @param vX: The horizontal velocity of the Projectile
	 * @param startup: The number of frames it takes for the Projectile to be able to damage an opponent 
	 * @param maxDistance: The maximum distance the projectile can travel
	 * @param facing: Which way the projectile moves, 1 for right, -1 for left
	 * @param hitstun: The amount of time the opponent cannot act for when they get hit by this Projectile.
	 * @param blockstun: The amount of time the opponent cannot act for when they block this Projectile
	 * @param xKB: The amount of horizontal knockback inflicted on the opponent by getting hit by this Projectile.
	 * @param yKB: The amount of vertical knockback inflicted on the opponent by getting hit by this Projectile.
	 * @param blockHeight: Which way this Projectile needs to be blocked
	 * @param transcendent: True if it should not interact with other projectiles, false if it should. 
	 */
	public Projectile(Color color, int x, int y, int w, int h, double vX, int startup, int maxDistance, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean transcendent) {
		super(0, 0, 0, y, w, h, startup, 0, 0, facing, hitstun, blockstun, xKB, yKB, blockHeight);
		absX = x + DrawingSurface.midscreen - DrawingSurface.ENDZONE_WIDTH;
		this.color = color;
		this.vX = vX;
		distanceTraveled = 0;
		this.maxDistance = maxDistance;
		this.transcendent = transcendent;
	}

	/**
	 * Moves the Projectile based on its velocity and where it is facing. 
	 */
	public void adjustPosition() {
		if (state == HitboxState.ACTIVE) {
			moveBy(vX * getFacing(), vY);
			distanceTraveled += vX;
		}
	}
	
	/**
	 * Moves the Projectile 
	 * @param x: The amount the Projectile should be moved horizontally
	 * @param y: The amount the Projectile should be moved horizontally
	 */
	private void moveBy(double x, double y) {
		absX += x;
		y += y;
	}
	
	@Override
	/**
	 * @param r: The Rectangle2D that is being checked for intersection
	 * @return True if the Projectile is intersecting the Rectangle2D, false if not
	 */
	public boolean intersects(Rectangle2D r) {
		return new Rectangle2D.Float((float)absX + DrawingSurface.ENDZONE_WIDTH - DrawingSurface.midscreen, (float) y, (float)width, (float) height).intersects(r);
	}

	/**
	 * Draws this Projectile onto a PApplet
	 * @param g: The PApplet on which the Projectile should be drawn on
	 */
	public void draw(PApplet g) {
		if (state == HitboxState.ACTIVE)
			g.stroke(0);
			g.fill(color.getRed(), color.getGreen(), color.getBlue());
			g.rect((float)absX + DrawingSurface.ENDZONE_WIDTH - DrawingSurface.midscreen, (float) y, (float)width, (float) height);
	}

	/**
	 * Updates the state of the Projectile depending on whether it should be able to hit the opponent.
	 */
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
