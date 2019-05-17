package other;
import java.util.ArrayList;

public class CheckQCF {
	private int frames;

	enum State {
		INITIAL, AFTERDOWN, AFTERDOWNFORWARD, READY;
	}

	private State state;
	private Player player;

	public CheckQCF(Player player) {
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
		if (frames > 60 || keys.contains(player.BACK))
			reset();

		switch (state) {
			case INITIAL:
				if (keys.contains(player.CROUCH)) {
					state = State.AFTERDOWN;
				}
				break;
				
			case AFTERDOWN:
				if (keys.contains(player.FRONT) && keys.contains(player.CROUCH)) {
					state = State.AFTERDOWNFORWARD;
				}
				break;
				
			case AFTERDOWNFORWARD:
				if (keys.contains(player.FRONT)) {
					state = State.READY;
				}
				break;
			case READY:
		}
	}
}
