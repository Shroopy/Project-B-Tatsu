package other;
import processing.core.PApplet;
import processing.sound.*;


public class Sounds {
	private static SoundFile hadouken;
	private static SoundFile tatsu;
	private static SoundFile srk;
	
	public static void init(PApplet g) {
		hadouken = new SoundFile(g, "PL02_00013.wav");
		tatsu = new SoundFile(g, "tatsu.wav");
		srk = new SoundFile(g, "srk2.wav");
	}
	
	public static void hadouken() {
		hadouken.play();
	}
	
	public static void tatsu() {
		tatsu.play();
	}
	
	public static void srk() {
		srk.play();
	}
}
