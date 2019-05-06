import java.awt.Color;

public class Hitbox extends Sprite {
	
	private int xOffset, yOffset, startup, active, facing, hitstun, recovery; //startup is frames before first active frame
	double xKB, yKB;
	private String state;
	
	public Hitbox(int xOffset, int yOffset, int x, int y, int w, int h, int startup, int active, int recovery, int facing, int hitstun, double xKB, double yKB) {
		super(Color.YELLOW, x + (xOffset * facing), y + yOffset, w, h);
		assert facing == -1 || facing == 1;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.startup = startup;
		this.active = active;
		this.facing = facing;
		this.hitstun = hitstun;
		this.recovery = recovery;
		this.xKB = xKB;
		this.yKB = yKB;
		state = "startup";
	}
	
	public void adjustPosition(int x, int y) {
		moveToLocation(x + (xOffset * facing), y + yOffset);
	}
	
	public String getState() {
		return state;
	}
	
	public void updateState() {
		if(startup > 0) {
			startup--;
		}
		else if(active > 0){
			state = "active";
			color = Color.PINK;
			active--;
		}
		else
			state = "inactive";
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public int getStartup() {
		return startup;
	}

	public int getActive() {
		return active;
	}

	public int getFacing() {
		return facing;
	}

	public int getHitstun() {
		return hitstun;
	}

	public double getxKB() {
		return xKB;
	}

	public double getyKB() {
		return yKB;
	}

	public int getRecovery() {
		return recovery;
	}
	
}
