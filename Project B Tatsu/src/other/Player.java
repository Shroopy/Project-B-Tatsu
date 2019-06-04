package other;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enums.BlockHeight;
import enums.ControlState;
import sprite.Character;

public class Player {

	private Character character;
	public final Integer MVLEFT, MVRIGHT, JUMP, CROUCH, A, B, C, ALTJUMP, FRONT, BACK;
	private CheckDP checkDP;
	private CheckQCF checkQCF;
	private CheckQCB checkQCB;
	private CheckDD checkDD;
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
		checkDP = new CheckDP(this);
		checkQCF = new CheckQCF(this);
		checkQCB = new CheckQCB(this);
		checkDD = new CheckDD(this);
		if (playerNum == 1) {
			MVLEFT = KeyEvent.VK_A;
			MVRIGHT = KeyEvent.VK_D;
			JUMP = KeyEvent.VK_W;
			CROUCH = KeyEvent.VK_S;
			A = KeyEvent.VK_T;
			B = KeyEvent.VK_Y;
			C = KeyEvent.VK_U;
			ALTJUMP = KeyEvent.VK_G;
			FRONT = MVRIGHT;
			BACK = MVLEFT;
		} else {
			MVLEFT = KeyEvent.VK_COMMA;
			MVRIGHT = KeyEvent.VK_SLASH;
			JUMP = KeyEvent.VK_L;
			CROUCH = KeyEvent.VK_PERIOD;
			A = KeyEvent.VK_LEFT;
			B = KeyEvent.VK_DOWN;
			C = KeyEvent.VK_RIGHT;
			ALTJUMP = KeyEvent.VK_UP;
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
		checkDP.run(keys);
		checkQCF.run(keys);
		checkQCB.run(keys);
		checkDD.run(keys);
		
		if(commandKeys.size() < 60)
			commandKeys.add(keys);
		else {
			commandKeys.remove(0);
			commandKeys.add(keys);
		}
		
		if (keys.contains(CROUCH) && character.isGrounded()) {
			if (character.getControlState() == ControlState.CONTROLLABLE) {
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
		
		if(keys.contains(BACK) && (character.getControlState() == ControlState.CONTROLLABLE || character.getControlState() == ControlState.HITSTUN) && character.isGrounded()) {
			if(keys.contains(CROUCH))
				character.setBlocking(BlockHeight.LOW);
			else
				character.setBlocking(BlockHeight.HIGH);
		}
		else
			character.setBlocking(BlockHeight.NOT);

		if (keys.contains(JUMP) || keys.contains(ALTJUMP))
			character.jump();

		if (keys.contains(A)) {
			if(character.isGrounded()) {
				if(checkQCF.isReady() && character.getProjectiles().size() < 2 && character.getProjectiles().size() < 2)
					character.qcfa();
				else if(checkDP.isReady())
					character.dpa();
				else if(checkQCB.isReady())
					character.qcba();
				else if(checkDD.isReady()) {
					character.dda();
				}	
				else if (keys.contains(CROUCH))
					character.twoa();
				else
					character.fivea();
			}
			else
				character.ja();
		} else if (keys.contains(B)) {
			if(character.isGrounded()) {
				if(checkQCF.isReady() && character.getProjectiles().size() < 2)
					character.qcfb();
				else if(checkDP.isReady())
					character.dpb();
				else if(checkQCB.isReady())
					character.qcbb();
				else if(checkDD.isReady())
					character.ddb();
				else if (keys.contains(CROUCH))
					character.twob();
				else
					character.fiveb();
			}
			else
				character.jb();
			
		} else if (keys.contains(C)) {
			if(character.isGrounded()) {
				if(checkQCF.isReady() && character.getProjectiles().size() < 2) {
					if(character.getMeter() >= 33)
						character.qcfc();
					else
						character.qcfb();
				}
				else if(checkDP.isReady()) {
					if(character.getMeter() >= 33)
						character.dpc();
					else
						character.dpb();
				}
				else if(checkQCB.isReady()) {
					if(character.getMeter() >= 33)
						character.qcbc();
					else
						character.qcbb();
				}
				else if(checkDD.isReady()) {
					if(character.getMeter() >= 33)
						character.ddc();
					else
						character.ddb();
				}
				else if (keys.contains(CROUCH))
					character.twoc();
				else if (keys.contains(FRONT))
					character.sixc();
				else
					character.fivec();
			}	
			else
				character.jc();
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
	
	public boolean checkHmm() {
		return hmm > 60 * 10;
	}
}
