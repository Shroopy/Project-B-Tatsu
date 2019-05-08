

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
	
	private Rectangle screenRect;

	private Player player1, player2;
	// private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;
	
	//private ArrayList<PImage> assets;

	public DrawingSurface() {
		super();
		//assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(Blue.WIDTH,0,DRAWING_WIDTH-2*Blue.WIDTH,DRAWING_HEIGHT);
	}

	public void spawnPlayers() {
		player1 = new Player(1, new Blue(/* assets.get(0), */DRAWING_WIDTH / 4 - Blue.WIDTH / 2, 50, 1));
		player2 = new Player(2, new Blue(/* assets.get(0), */DRAWING_WIDTH / 4 * 3 + Blue.WIDTH / 2, 50, -1));
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

		background(0,255,255);   

		pushMatrix();

		float ratioX = (float)width/DRAWING_WIDTH;
		float ratioY = (float)height/DRAWING_HEIGHT;

		scale(ratioX, ratioY);

		fill(100);

		rect(0,395+Blue.HEIGHT,DRAWING_WIDTH, DRAWING_HEIGHT-395);
		fill(255,0,0);
		if(midscreen < 1200) 
		{
			rect(0, 395+Blue.HEIGHT, 1200-midscreen, DRAWING_HEIGHT-395);
		}
		else
		{
			rect(1600-midscreen, 395+Blue.HEIGHT, midscreen-1200, DRAWING_HEIGHT-395);
		}
		player1.draw(this);
		player2.draw(this);
		
		
		popMatrix();


		// modifying stuff

		player1.act(keys);
		player2.act(keys);
		
		Hitbox player1Hit = player2.getCharacter().hitboxesIntersect(player1.getCharacter());
		Hitbox player2Hit = player1.getCharacter().hitboxesIntersect(player2.getCharacter());
		
		if(player1Hit != null)
			player1.getCharacter().takeHit(player1Hit.getHitstun(), player1Hit.getxKB(), player1Hit.getyKB());
		if(player2Hit != null)
			player2.getCharacter().takeHit(player2Hit.getHitstun(), player2Hit.getxKB(), player2Hit.getyKB());

		slideWorldToImage(player1);
			
	}

	public void slideWorldToImage(Player img) {
	  	Point2D.Double center = new Point2D.Double(img.getCharacter().getCenterX(), img.getCharacter().getCenterY());
		if (!img.getCharacter().contains(center)) {
			double newX = screenRect.getX();
			double newY = screenRect.getY();
			
		  	if (center.getX() < img.getCharacter().getX()) {
		  		newX -= (img.getCharacter().getX() - center.getX());
		  	} else if (center.getX() > img.getCharacter().getX() + img.getCharacter().getWidth()) {
		  		newX += (center.getX() - (img.getCharacter().getX() + img.getCharacter().getWidth()));
		  	}
		  	
		  	if (center.getY() < img.getCharacter().getY()) {
		  		newY -= (img.getCharacter().getY() - center.getY());
		  	} else if (center.getY() > img.getCharacter().getY() + img.getCharacter().getHeight()) {
		  		newY += (center.getY() - img.getCharacter().getY() - img.getCharacter().getHeight());
		  	}
		  	newX = Math.max(newX,0);
		  	newY = Math.max(newY,0);
		  	newX = Math.min(newX,DRAWING_WIDTH-screenRect.getWidth());
		  	newY = Math.min(newY,DRAWING_HEIGHT-screenRect.getHeight());
		  	
		  	screenRect.setRect(newX,newY,screenRect.getWidth(),screenRect.getHeight());
		  	
		  	img.getCharacter().setRect(screenRect.getX()+screenRect.getWidth()/5,screenRect.getY()+screenRect.getHeight()/5,screenRect.getWidth()*3/5,screenRect.getHeight()*3/5);
		}
	  }

	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}


}

