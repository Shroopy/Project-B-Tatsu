import processing.core.PApplet;
import processing.sound.SoundFile;


public class Sounds {
	private SoundFile hadouken;
	
	public Sounds(PApplet g) {
		hadouken = new SoundFile(g, "PL02_00013.wav");
	}
}
