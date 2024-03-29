package sprite;
import java.awt.Color;

import enums.BlockHeight;
import enums.HitboxState;

public class Hitbox extends Sprite {

	protected int xOffset, yOffset, startup, active, facing, hitstun, recovery, width, blockstun; // startup is frames before first active frame
	private double xKB, yKB;
	protected boolean knockout;
	protected HitboxState state;
	private BlockHeight blockHeight;

	/**
	 * Constructs a Hitbox object.
	 * @param xOffset: The x at which the hitbox should be generated relative to the character
	 * @param yOffset: The y at which the hitbox should be generated relative to the character
	 * @param x: The x at which the hitbox is generated
	 * @param y: The y at which the hitbox is generated
	 * @param w: The width of the hitbox
	 * @param h: The height of the hitbox
	 * @param startup: How long the hitbox takes to become active
	 * @param active: How long the hitbox remains active
	 * @param recovery: How long the Character cannot move after the hitbox has stopped being active
	 * @param facing: Which direction the character this hitbox belongs to is facing
	 * @param hitstun: How long the opposing Character cannot move after struck by the hitbox
	 * @param blockstun: How long the opposing Character cannot move after being struck by the hitbox while blocking
	 * @param xKB: Horizontal movement of the opposing Character after struck by the hitbox
	 * @param yKB: Vertical movement of the opposing Character after struck by the hitbox
	 * @param blockHeight: What height the opposing Character must block at to block this attack
	 * @param ko: Whether the move knocks out in the corner
	 */
	public Hitbox(int xOffset, int yOffset, int x, int y, int w, int h, int startup, int active, int recovery, int facing, int hitstun, int blockstun, double xKB, double yKB, BlockHeight blockHeight, boolean ko) {
		super(Color.YELLOW, x + (xOffset * facing), y + yOffset, w, h);
		assert facing == -1 || facing == 1;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.width = w;
		this.startup = startup;
		this.active = active;
		this.facing = facing;
		this.hitstun = hitstun;
		this.recovery = recovery;
		this.xKB = xKB;
		this.blockstun = blockstun;
		this.yKB = yKB;
		this.blockHeight = blockHeight;
		state = HitboxState.STARTUP;
		knockout = ko;
	}

	public int getBlockstun() {
		return blockstun;
	}

	/**
	 * Moves the hitbox's location using Sprite's moveToLocation method
	 * @param x: The x the hitbox should be moved
	 * @param y: The y the hitbox should be moved
	 */
	public void adjustPosition(int x, int y) {
		moveToLocation(x + (xOffset * facing), y + yOffset);
	}

	public int getActive() {
		return active;
	}

	public BlockHeight getBlockHeight() {
		return blockHeight;
	}

	public int getFacing() {
		return facing;
	}

	public int getHitboxWidth() {
		return width;
	}

	public int getHitstun() {
		return hitstun;
	}

	public int getRecovery() {
		return recovery;
	}

	public int getStartup() {
		return startup;
	}

	public HitboxState getState() {
		return state;
	}

	public double getxKB() {
		return xKB;
	}

	public int getxOffset() {
		return xOffset;
	}

	public double getyKB() {
		return yKB;
	}

	public int getyOffset() {
		return yOffset;
	}
	
	public boolean getKO() 
	{
		return knockout;
	}

	/**
	 * Decrements the frames a hitbox is in a certain state, updates state if necessary.
	 */
	public void updateState() {
		if (startup > 0) {
			startup--;
		} else if (active > 0) {
			if(state == HitboxState.STARTUP)
				state = HitboxState.ACTIVE;
			color = Color.PINK;
			active--;
		} else
			state = HitboxState.INACTIVE;
	}
	
	public void deactivate() {
		state = HitboxState.INACTIVE;
	}

}
