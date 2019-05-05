
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class DrawingSurface extends PApplet {

	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private Player player1, player2;
	// private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;

	// private ArrayList<PImage> assets;

	public DrawingSurface() {
		super();
		// assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT);
	}

	public void spawnPlayers() {
		player1 = new Player(1, new Blue(/* assets.get(0), */DRAWING_WIDTH / 2 - Blue.WIDTH / 2, 50));
	}

	public void runMe() {
		runSketch();
	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
		frameRate(60);
		// size(0,0,PApplet.P3D);
		/* assets.add(loadImage("mario.png")); */

		spawnPlayers();
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {

		// drawing stuff

		background(0, 255, 255);

		pushMatrix();

		float ratioX = (float) width / DRAWING_WIDTH;
		float ratioY = (float) height / DRAWING_HEIGHT;

		scale(ratioX, ratioY);

		fill(100);

		rect(0, 395 + Blue.HEIGHT, DRAWING_WIDTH, DRAWING_HEIGHT - 395);
		player1.draw(this);

		popMatrix();

		// modifying stuff

		player1.act(keys);

		if (!screenRect.intersects(player1.getCharacter()))
			spawnPlayers();
	}

	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

}
