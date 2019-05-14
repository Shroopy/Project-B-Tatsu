
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;

public class DrawingSurface extends PApplet {

	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;
	public static int midscreen = 1200;
	public static final int cornerL = 400;
	public static final int cornerR = 2000;
	private int p1r, p2r;
	private PImage background;

	private Rectangle screenRect;

	private Player player1, player2;
	// private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;

	// private ArrayList<PImage> assets;

	/**
	 * Calls PApplet's super constructor, initializes the keys ArrayList and the Rectangle representing the screen
	 */
	public DrawingSurface() {
		super();
		// assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(Blue.WIDTH, 0, DRAWING_WIDTH - 2 * Blue.WIDTH, DRAWING_HEIGHT);
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	/**
	 * Draws the game window and both players. Calls both players' acts based on which keys were inputted. Scrolls the screen based on both players' positions.
	 */
	public void draw() {

		// drawing stuff

		background(0, 255, 255);

		pushMatrix();

		float ratioX = (float) width / DRAWING_WIDTH;
		float ratioY = (float) height / DRAWING_HEIGHT;

		// System.out.println(midscreen);
		scale(ratioX, ratioY);

		fill(0);
		textSize(100);
		text("" + (60 - frameCount / 60), 325, 100);
		textSize(20);
		text("P1 Rounds: " + p1r, 50, 50);
		text("P2 Rounds: " + p2r, 600, 50);
		// fill(0,255,0);
		// rect(Blue.WIDTH, 0, DRAWING_WIDTH - 2 * Blue.WIDTH, DRAWING_HEIGHT);
		fill(100);
		rect(0, 395 + Blue.HEIGHT, DRAWING_WIDTH, DRAWING_HEIGHT - 395);
		// image(background.get(midscreen-800, 0, DRAWING_WIDTH+200, DRAWING_HEIGHT -
		// 395), 0, 395 + Blue.HEIGHT, DRAWING_WIDTH+200, DRAWING_HEIGHT - 395);
		fill(255, 0, 0);
		if (midscreen < 800) {
			rect(0, 395 + Blue.HEIGHT, 800 - midscreen, DRAWING_HEIGHT - 395);
		} else {
			rect(2400 - midscreen, 395 + Blue.HEIGHT, midscreen - 1200, DRAWING_HEIGHT - 395);
		}
		player1.draw(this);
		player2.draw(this);
		if (frameCount == 3600) {
			player1.getCharacter().changeState(0);
			player2.getCharacter().changeState(0);
		}
		if (frameCount > 3600 && frameCount < 3840) {
			textSize(60);
			fill(255, 0, 0);
			text("ROUND OVER", 220, 250);
			if ((2400 - player2.getCharacter().getAbsX()) == player1.getCharacter().getAbsX())
				text("TIE", 350, 400);
			else if ((2400 - player2.getCharacter().getAbsX()) < player1.getCharacter().getAbsX())
				text("Player One WINS", 180, 350);
			else
				text("Player Two WINS", 180, 350);
		}
		if (frameCount == 3840) {
			if ((2400 - player2.getCharacter().getAbsX()) < player1.getCharacter().getAbsX())
				p1r++;
			else if ((2400 - player2.getCharacter().getAbsX()) > player1.getCharacter().getAbsX())
				p2r++;

			frameCount = 0;
			spawnPlayers();
		}

		popMatrix();

		// modifying stuff

		player1.act(keys);
		player2.act(keys);

		Hitbox player1Hit = player2.getCharacter().hitboxesIntersect(player1.getCharacter());
		Hitbox player2Hit = player1.getCharacter().hitboxesIntersect(player2.getCharacter());

		if (player1Hit != null && player1Hit.getState().equals("active") && !player1.getCharacter().isInvincible())
			player1.getCharacter().takeHit(player1Hit.getHitstun(), player1Hit.getxKB(), player1Hit.getyKB());
		if (player2Hit != null && player2Hit.getState().equals("active") && !player2.getCharacter().isInvincible())
			player2.getCharacter().takeHit(player2Hit.getHitstun(), player2Hit.getxKB(), player2Hit.getyKB());

		checkPass();

		int prevX1 = (int) player1.getCharacter().getX();
		int prevX2 = (int) player2.getCharacter().getX();
		if (!player1.getCharacter().intersects(screenRect)) {
			player1.getCharacter().setScreenX(0);
			if (prevX2 - prevX1 <= DRAWING_WIDTH - player1.getCharacter().getCharWidth()) {
				player2.getCharacter().moveByAmount(-prevX1, 0);
				midscreen += prevX1;
				// System.out.println("X1: " + prevX1);
			}
		}
		if (!player2.getCharacter().intersects(screenRect)) {
			player2.getCharacter().setScreenX(DRAWING_WIDTH - player2.getCharacter().getCharWidth());
			if (prevX2 - prevX1 <= DRAWING_WIDTH - player2.getCharacter().getCharWidth()) {
				player1.getCharacter().moveByAmount(-prevX2 - player2.getCharacter().getCharWidth() + DRAWING_WIDTH, 0);
				midscreen += (prevX2 + player2.getCharacter().getCharWidth() - DRAWING_WIDTH);
			}
		}

	}

	public void checkPass() {
		double p1x = player1.getCharacter().getX();
		double p2x = player2.getCharacter().getX();
		double v1 = player1.getCharacter().getVX();
		double v2 = player2.getCharacter().getVX();
		double diff = p2x - p1x;
		double diffv = v1 + v2;

		if (diff < player1.getCharacter().getCharWidth()) {
			if (player1.getCharacter().isGrounded() == player2.getCharacter().isGrounded()) {
				player1.getCharacter().moveByAmount((-Math.abs(diff - player1.getCharacter().getCharWidth() - diffv)) / 2, 0);
				player2.getCharacter().moveByAmount((Math.abs(diff - player1.getCharacter().getCharWidth() + diffv)) / 2, 0);
				System.out.println(-Math.abs(diff - player1.getCharacter().getCharWidth() - diffv) / 2);
				System.out.println(Math.abs(diff - player1.getCharacter().getCharWidth() + diffv) / 2);
			} else if (player1.getCharacter().isGrounded()) {
				player1.getCharacter().moveByAmount((-Math.abs(diff - player1.getCharacter().getCharWidth()) - diffv) / 2, 0);
				player2.getCharacter().moveByAmount((Math.abs(diff - player1.getCharacter().getCharWidth()) + 0.6 * diffv) / 2, 0);

			} else if (player2.getCharacter().isGrounded()) {
				System.out.println("diffv: " + diffv);
				player1.getCharacter().moveByAmount((-Math.abs(diff - player1.getCharacter().getCharWidth()) + 0.6 * diffv) / 2, 0);
				player2.getCharacter().moveByAmount((Math.abs(diff - player1.getCharacter().getCharWidth()) - diffv) / 2, 0);
				System.out.println(player1.getCharacter().getVX());
				System.out.println(-Math.abs(diff - player1.getCharacter().getCharWidth() - 0.6 * diffv) / 2);
				System.out.println(Math.abs(diff - player1.getCharacter().getCharWidth() + diffv) / 2);
			}
			player1.getCharacter().zeroVX();
			player2.getCharacter().zeroVX();
			// System.out.println(player1.getCharacter().getVX());
		}
	}

	/**
	 * @param code: Code of a key to check if it was pressed
	 * @return true if the key was pressed, false if not.
	 */
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}

	/**
	 * Adds a pressed key to an ArrayList of keys currently being pressed.
	 */
	public void keyPressed() {
		keys.add(keyCode);
	}

	/**
	 * Removes a released key from an ArrayList of keys currently being pressed.
	 */
	public void keyReleased() {
		while (keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
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

		// background =
		// loadImage("C:\\Users\\Kids\\Downloads\\close-up-concrete-dark-908286.jpg");

		spawnPlayers();
	}

	public void spawnPlayers() {
		player1 = new Player(1, new Blue(/* assets.get(0), */DRAWING_WIDTH / 4 - Blue.WIDTH / 2, 50, 1));
		player2 = new Player(2, new Blue(/* assets.get(0), */DRAWING_WIDTH / 4 * 3 + Blue.WIDTH / 2, 50, -1));
	}

}
