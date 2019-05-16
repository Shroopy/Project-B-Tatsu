import java.util.ArrayList;

public class CheckDP {
	private int frames;

	enum State {
		INITIAL, AFTERFORWARD1, AFTERNEUTRAL, READY;
	}

	private State state;
	private Player player;

	public CheckDP(Player player) {
		reset();
		this.player = player;
	}

	public void reset() {
		frames = 0;
		state = State.INITIAL;
	}

	public boolean isReady() {
		return state == State.READY;
	}

	public void run(ArrayList<Integer> keys) {
		frames++;
		if (frames > 20 || keys.contains(player.BACK))
			reset();

		switch (state) {
			case INITIAL:
				if (keys.contains(player.FRONT)) {
					state = State.AFTERFORWARD1;
				}
				break;
				
			case AFTERFORWARD1:
				if (keys.contains(player.FRONT)) {
					state = State.INITIAL;
				} else {
					state = State.AFTERNEUTRAL;
				}
				break;
				
			case AFTERNEUTRAL:
				if (keys.contains(player.FRONT)) {
					state = State.READY;
				}
				break;
			case READY:
		}
	}
}
