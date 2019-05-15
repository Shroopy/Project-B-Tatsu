import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

	private Character character;
	private final Integer MVLEFT, MVRIGHT, JUMP, CROUCH, A, B, C, ALTJUMP, FRONT, BACK;
	private int hmm;
	private ArrayList<ArrayList<Integer>> commandKeys;

	/**
	 * Creates a Player object
	 * @param playerNum: 1 for player one, 2 for player 2
	 * @param character: The character the Player is playing
	 */
	public Player(int playerNum, Character character) {
		assert playerNum == 1 || playerNum == 2;
		commandKeys = new ArrayList<ArrayList<Integer>>();
		if (playerNum == 1) {
			MVLEFT = KeyEvent.VK_A;
			MVRIGHT = KeyEvent.VK_D;
			JUMP = KeyEvent.VK_W;
			CROUCH = KeyEvent.VK_S;
			A = KeyEvent.VK_U;
			B = KeyEvent.VK_I;
			C = KeyEvent.VK_O;
			ALTJUMP = KeyEvent.VK_J;
			FRONT = MVRIGHT;
			BACK = MVLEFT;
		} else {
			MVLEFT = KeyEvent.VK_LEFT;
			MVRIGHT = KeyEvent.VK_RIGHT;
			JUMP = KeyEvent.VK_UP;
			CROUCH = KeyEvent.VK_DOWN;
			A = KeyEvent.VK_NUMPAD7;
			B = KeyEvent.VK_NUMPAD8;
			C = KeyEvent.VK_NUMPAD9;
			ALTJUMP = KeyEvent.VK_NUMPAD4;
			FRONT = MVLEFT;
			BACK = MVRIGHT;
		}
		this.character = character;
	}

	/**
	 * Calls methods to influence the character based on which keys are pressed
	 * @param keys: An ArrayList of keys currently being pressed.
	 */
	public void act(ArrayList<Integer> keys) {
		if(commandKeys.size() < 60)
			commandKeys.add(keys);
		else {
			commandKeys.remove(0);
			commandKeys.add(keys);
		}
		
		if (keys.contains(CROUCH) && character.isGrounded()) {
			if (character.getControlState().equals("controllable")) {
				character.setCrouching(true);
				hmm++;
			}
		} else {
			character.setCrouching(false);
			hmm = 0;
		}

		if (keys.contains(MVLEFT))
			character.walk(-1);
		else if (keys.contains(MVRIGHT))
			character.walk(1);
		else
			character.walk(0);
		
		if(keys.contains(BACK) && character.controlState.equals("controllable") && character.isGrounded()) {
			if(keys.contains(CROUCH))
				character.setBlocking("low");
			else
				character.setBlocking("high");
		}
		else
			character.setBlocking("not");

		if (keys.contains(JUMP) || keys.contains(ALTJUMP))
			character.jump();

		if (keys.contains(A)) {
			if(character.isGrounded() && checkDP())
				character.dpa();
			else if (!character.isGrounded())
				character.ja();
			else if (keys.contains(CROUCH))
				character.twoa();
			else
				character.fivea();
		} else if (keys.contains(B)) {
			if (!character.isGrounded())
				character.jb();
			else if (keys.contains(CROUCH))
				character.twob();
			else
				character.fiveb();
		} else if (keys.contains(C)) {
			if (!character.isGrounded())
				character.jc();
			else if (keys.contains(CROUCH))
				character.twoc();
			else if (keys.contains(FRONT))
				character.sixc();
			else
				character.fivec();
		}

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
	
	private boolean checkDP() {
		int i = commandKeys.indexOf(FRONT);
		if(i > -1) {
			for(;i > -1; i--)
				commandKeys.remove(0);
			i = commandKeys.indexOf(FRONT);
			if(i > -1)
				return true;
		}
		return false;
	}
	
	public boolean checkHmm() {
		return hmm > 60 * 10;
	}
}
