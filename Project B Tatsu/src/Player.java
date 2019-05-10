import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

	private Character character;
	private final Integer MVLEFT, MVRIGHT, JUMP, CROUCH, A, B, C, ALTJUMP;

	/**
	 * Creates a Player object
	 * @param playerNum: 1 for player one, 2 for player 2
	 * @param character: The character the Player is playing
	 */
	public Player(int playerNum, Character character) {
		assert playerNum == 1 || playerNum == 2;
		if (playerNum == 1) {
			MVLEFT = KeyEvent.VK_A;
			MVRIGHT = KeyEvent.VK_D;
			JUMP = KeyEvent.VK_W;
			CROUCH = KeyEvent.VK_S;
			A = KeyEvent.VK_U;
			B = KeyEvent.VK_I;
			C = KeyEvent.VK_O;
			ALTJUMP = KeyEvent.VK_J;
		} else {
			MVLEFT = KeyEvent.VK_LEFT;
			MVRIGHT = KeyEvent.VK_RIGHT;
			JUMP = KeyEvent.VK_UP;
			CROUCH = KeyEvent.VK_DOWN;
			A = KeyEvent.VK_NUMPAD7;
			B = KeyEvent.VK_NUMPAD8;
			C = KeyEvent.VK_NUMPAD9;
			ALTJUMP = KeyEvent.VK_NUMPAD4;
		}
		this.character = character;
	}

	/**
	 * Calls methods to influence the character based on which keys are pressed 
	 * @param keys: An ArrayList of keys currently being pressed.
	 */
	public void act(ArrayList<Integer> keys) {
		if (keys.contains(MVLEFT))
			character.walk(-1);
		else if (keys.contains(MVRIGHT))
			character.walk(1);
		else
			character.walk(0);

		if (keys.contains(JUMP) || keys.contains(ALTJUMP))
			character.jump();

		if (keys.contains(A))
			character.testAttack();

		character.act();
	}

	/**
	 * Draws the Character on a PApplet
	 * @param drawingSurface: The PApplet on which the Player's Character is drawn.
	 */
	public void draw(DrawingSurface drawingSurface) {
		character.draw(drawingSurface);

	}

	public Character getCharacter() {
		return character;
	}
}
