
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
	public static final int CORNERL = 400;
	public static final int CORNERR = 2000;
	private int p1r, p2r;
	private PImage background;

	private Rectangle screenRect;

	private Player player1, player2;
	private Character player1char, player2char;
	// private ArrayList<Shape> obstacles;
	private PImage image;

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
		
		if(player2.checkHmm())
			image(image, 0, 0, width, 600);
		else
			background(0, 255, 255);

		pushMatrix();

		float ratioX = (float) width / DRAWING_WIDTH;
		float ratioY = (float) height / DRAWING_HEIGHT;

		// System.out.println(midscreen);
		scale(ratioX, ratioY);

		fill(0);
		textSize(100);
		text(60 - frameCount / 60, 325, 100);
		textSize(20);
		text("P1 Rounds: " + p1r, 50, 50);
		text("P2 Rounds: " + p2r, 600, 50);
		fill(100);
		rect(0, 395 + Blue.HEIGHT, DRAWING_WIDTH, DRAWING_HEIGHT - 395);	
		
		fill(255, 0, 0);
		if (midscreen < 800) {
			rect(0, 395 + Blue.HEIGHT, 800 - midscreen, DRAWING_HEIGHT - 395);
		} else {
			rect(2400 - midscreen, 395 + Blue.HEIGHT, midscreen - 1200, DRAWING_HEIGHT - 395);
		}
		
		textSize(50);
		float maxTextWidth = textWidth("100");
		fill(255,255,0);
		rect(20 + 10 + maxTextWidth, 500, player1char.getMeter()*2,50);
		text(player1char.getMeter(),20,545);
		rect(DRAWING_WIDTH - 20 - maxTextWidth - 10 - (player2char.getMeter()*2), 500, player2char.getMeter()*2,50);
		text(player2char.getMeter(),DRAWING_WIDTH - 20 - maxTextWidth,545);
		
		player1.draw(this);
		player2.draw(this);
		if (frameCount == 3600) {
			player1char.changeState(0);
			player2char.changeState(0);
		}
		if (frameCount > 3600 && frameCount < 3840) {
			textSize(60);
			fill(255, 0, 0);
			text("ROUND OVER", 220, 250);
			if ((2400 - player2char.getAbsX()) == player1char.getAbsX())
				text("TIE", 350, 400);
			else if ((2400 - player2char.getAbsX()) < player1char.getAbsX())
				text("Player One WINS", 180, 350);
			else
				text("Player Two WINS", 180, 350);
		}
		if (frameCount == 3840) {
			if ((2400 - player2char.getAbsX()) < player1char.getAbsX())
				p1r++;
			else if ((2400 - player2char.getAbsX()) > player1char.getAbsX())
				p2r++;

			frameCount = 0;
			spawnPlayers();
		}

		popMatrix();

		// modifying stuff

		player1.act(keys);
		player2.act(keys);

		Hitbox player1Hit = player2char.hitboxesIntersect(player1char);
		Hitbox player2Hit = player1char.hitboxesIntersect(player2char);

		checkPlayerHit(player1char, player2char, player1Hit);
		checkPlayerHit(player2char, player1char, player2Hit);

		checkPass();

		int prevX1 = (int) player1char.getX();
		int prevX2 = (int) player2char.getX();
		if (!player1char.intersects(screenRect)) {
			player1char.setScreenX(0);
			if (prevX2 - prevX1 <= DRAWING_WIDTH - player1char.getCharWidth()) {
				player2char.moveByAmount(-prevX1, 0);
				midscreen += prevX1;
				// System.out.println("X1: " + prevX1);
			}
		}
		if (!player2char.intersects(screenRect)) {
			player2char.setScreenX(DRAWING_WIDTH - player2char.getCharWidth());
			if (prevX2 - prevX1 <= DRAWING_WIDTH - player2char.getCharWidth()) {
				player1char.moveByAmount(-prevX2 - player2char.getCharWidth() + DRAWING_WIDTH, 0);
				midscreen += (prevX2 + player2char.getCharWidth() - DRAWING_WIDTH);
			}
		}

		
	}

	public void checkPass() {
		double p1x = player1char.getX();
		double p2x = player2char.getX();
		double v1 = player1char.getvX();
		double v2 = player2char.getvX();
		double diff = p2x - p1x;
		double diffv = v1 + v2;

		if (diff < player1char.getCharWidth()) {
			if (player1char.isGrounded() == player2char.isGrounded()) {
				player1char.moveByAmount((-Math.abs(diff - player1char.getCharWidth() - diffv)) / 2, 0);
				player2char.moveByAmount((Math.abs(diff - player1char.getCharWidth() + diffv)) / 2, 0);
				//System.out.println(-Math.abs(diff - player1char.getCharWidth() - diffv) / 2);
				//System.out.println(Math.abs(diff - player1char.getCharWidth() + diffv) / 2);
			} else if (player1char.isGrounded()) {
				player1char.moveByAmount((-Math.abs(diff - player1char.getCharWidth()) - diffv) / 2, 0);
				player2char.moveByAmount((Math.abs(diff - player1char.getCharWidth()) + 0.6 * diffv) / 2, 0);

			} else if (player2char.isGrounded()) {
				//System.out.println("diffv: " + diffv);
				player1char.moveByAmount((-Math.abs(diff - player1char.getCharWidth()) + 0.6 * diffv) / 2, 0);
				player2char.moveByAmount((Math.abs(diff - player1char.getCharWidth()) - diffv) / 2, 0);
				//System.out.println(player1char.getVX());
				//System.out.println(-Math.abs(diff - player1char.getCharWidth() - 0.6 * diffv) / 2);
				//System.out.println(Math.abs(diff - player1char.getCharWidth() + diffv) / 2);
			}
			player1char.zeroVX();
			player2char.zeroVX();
			// System.out.println(player1char.getVX());
		}
	}
	
	private void checkPlayerHit(Character character1, Character character2, Hitbox hitbox) {
		if (hitbox != null && hitbox.getState().equals("active") && !character1.isInvincible()) {
			if(hitbox.getBlockHeight() == character1.getBlockHeight() || (hitbox.getBlockHeight() == BlockHeight.MID && character1.getBlockHeight() != BlockHeight.NOT))
				character1.blockHit(hitbox.getBlockstun(), hitbox.getxKB());
			else	
				character1.takeHit(hitbox.getHitstun(), hitbox.getxKB(), hitbox.getyKB());
			
			character2.setvX(character2.getFacing() * -1 * character1.getKbFromEdge());
			character2.setAttackHit(true);
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

		image = loadImage("lib/hmm.png");

		spawnPlayers();
	}

	public void spawnPlayers() {
		player1char = new Blue(DRAWING_WIDTH / 4 - Blue.WIDTH / 2, 50, 1);
		player2char = new Blue(DRAWING_WIDTH * 3 / 4 - Blue.WIDTH / 2, 50, -1);
		player1 = new Player(1, player1char);
		player2 = new Player(2, player2char);
		midscreen = 1200;
	}

}
