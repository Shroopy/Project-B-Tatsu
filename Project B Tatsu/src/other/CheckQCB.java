package other;
import java.util.ArrayList;

public class CheckQCB {
	private int frames;

	enum State {
		INITIAL, AFTERDOWN, AFTERDOWNBACK, READY;
	}

	private State state;
	private Player player;

	public CheckQCB(Player player) {
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
		if (frames > 60 || keys.contains(player.FRONT))
			reset();

		switch (state) {
			case INITIAL:
				if (!keys.contains(player.BACK) && keys.contains(player.CROUCH)) {
					state = State.AFTERDOWN;
				}
				break;
				
			case AFTERDOWN:
				if (keys.contains(player.BACK) && keys.contains(player.CROUCH)) {
					state = State.AFTERDOWNBACK;
				}
				break;
				
			case AFTERDOWNBACK:
				if (keys.contains(player.BACK) && !keys.contains(player.CROUCH)) {
					state = State.READY;
				}
				break;
			case READY:
				if(keys.contains(player.CROUCH))
					reset();
				
		}
	}
}
