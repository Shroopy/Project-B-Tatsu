package other;

import java.io.*;

import javax.sound.sampled.*;

import processing.core.PApplet;
import processing.sound.*;

public class Sounds {
	private static AudioInputStream hadouken;
	private static Clip tatsu;
	private static Clip srk;

	public static void init(PApplet g) {
//		hadouken = new SoundFile(g, "PL02_00013.wav");
//		tatsu = new SoundFile(g, "tatsu.wav");
//		srk = new SoundFile(g, "srk2.wav");

		try {

//			tatsu = AudioSystem.getClip();
//			stream = Sounds.class.getResourceAsStream("/resources/sounds/tatsu.wav");
//			System.out.println(stream);
//			inputStream = AudioSystem.getAudioInputStream(stream);
//			tatsu.open(inputStream);
//			
//			srk = AudioSystem.getClip();
//			inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("data/srk2.wav"));
//			srk.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void hadouken() {
		try {
			InputStream stream = Sounds.class.getResourceAsStream("/resources/sounds/PL02_00013.wav");
			System.out.println(stream);
			hadouken = AudioSystem.getAudioInputStream(stream);
			Clip clip = AudioSystem.getClip();
			hadouken.reset();
			clip.open(hadouken);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void tatsu() {
//		tatsu.start();
//	}

//	public static void srk() {
//		srk.start();
//	}
}
