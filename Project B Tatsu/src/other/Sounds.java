package other;
import processing.core.PApplet;
import processing.sound.*;


public class Sounds {
	private static SoundFile hadouken;
	
	public static void init(PApplet g) {
		hadouken = new SoundFile(g, "PL02_00013.wav");
	}
	
	public static void hadouken() {
		hadouken.play();
	}
}
