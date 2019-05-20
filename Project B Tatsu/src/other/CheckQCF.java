package other;
import java.util.ArrayList;

/**
 * State machine that checks to see if a down-down+forwards-forwards input has been inputted within a time limit
 */
public class CheckQCF {
	private int frames;

	/**
	 * Used for comparing current state
	 */
	enum State {
		INITIAL, AFTERDOWN, AFTERDOWNFORWARD, READY;
	}

	private State state;
	private Player player;

	/**
	 * Creates a new CheckQCF
	 * @param player the player that this checker belongs to
	 */
	public CheckQCF(Player player) {
		reset();
		this.player = player;
	}

	/**
	 * Resets the timer for the input time limit and resets the state to initial
	 */
	public void reset() {
		frames = 0;
		state = State.INITIAL;
	}

	public boolean isReady() {
		return state == State.READY;
	}

	/**
	 * Checks to see if the current state should be updated based on current state and inputs
	 * @param keys Keys being pressed during this frame
	 */
	public void run(ArrayList<Integer> keys) {
		frames++;
		if (frames > 60 || keys.contains(player.BACK))
			reset();

		switch (state) {
			case INITIAL:
				if (!keys.contains(player.FRONT) && keys.contains(player.CROUCH)) {
					state = State.AFTERDOWN;
				}
				break;
				
			case AFTERDOWN:
				if (keys.contains(player.FRONT) && keys.contains(player.CROUCH)) {
					state = State.AFTERDOWNFORWARD;
				}
				break;
				
			case AFTERDOWNFORWARD:
				if (keys.contains(player.FRONT) && !keys.contains(player.CROUCH)) {
					state = State.READY;
				}
				break;
			case READY:
				if(keys.contains(player.CROUCH))
					reset();
				
		}
	}
}
