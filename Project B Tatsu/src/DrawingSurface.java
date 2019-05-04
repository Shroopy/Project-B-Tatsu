

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

	private Blue blue;
	//private ArrayList<Shape> obstacles;

	private ArrayList<Integer> keys;
	
	//private ArrayList<PImage> assets;

	public DrawingSurface() {
		super();
		//assets = new ArrayList<PImage>();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
	}


	public void spawnNewBlue() {
		blue = new Blue(/*assets.get(0), */DRAWING_WIDTH/2-Blue.WIDTH/2,50);
	}
	
	public void runMe() {
		runSketch();
	}

	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		//size(0,0,PApplet.P3D);
		/*assets.add(loadImage("mario.png"));*/
		
		spawnNewBlue();
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

		blue.draw(this);

		popMatrix();


		// modifying stuff

		if (isPressed(KeyEvent.VK_LEFT))
			blue.walk(-1);
		else if (isPressed(KeyEvent.VK_RIGHT))
			blue.walk(1);
		else
			blue.walk(0);
		if (isPressed(KeyEvent.VK_UP))
			blue.jump();

		blue.act();

		if (!screenRect.intersects(blue))
			spawnNewBlue();
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

