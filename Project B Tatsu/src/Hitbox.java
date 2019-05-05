import java.awt.Color;

public class Hitbox extends Sprite {
	
	private int xOffset, yOffset, startup, active, facing; //startup is frames before first active frame
	private String state;
	
	public Hitbox(int xOffset, int yOffset, int x, int y, int w, int h, int startup, int active, int facing) {
		super(Color.YELLOW, x + (xOffset * facing), y + yOffset, w, h);
		assert facing == -1 || facing == 1;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.startup = startup;
		this.active = active;
		this.facing = facing;
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
	
}
