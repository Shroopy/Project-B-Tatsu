package other;

import java.io.*;

import javax.sound.sampled.*;

import processing.core.PApplet;
import processing.sound.*;

public class Sounds {
	private static Clip hadouken;
	private static Clip tatsu;
	private static Clip srk;

	public static void init(PApplet g) {
//		hadouken = new SoundFile(g, "PL02_00013.wav");
//		tatsu = new SoundFile(g, "tatsu.wav");
//		srk = new SoundFile(g, "srk2.wav");

		try {
			InputStream stream = Sounds.class.getResourceAsStream("resources/sounds/PL02_00013.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(stream);
			hadouken = AudioSystem.getClip();
			hadouken.open(ais);
			
			stream = Sounds.class.getResourceAsStream("resources/sounds/tatsu.wav");
			ais = AudioSystem.getAudioInputStream(stream);
			tatsu = AudioSystem.getClip();
			tatsu.open(ais);
			
			stream = Sounds.class.getResourceAsStream("resources/sounds/srk2.wav");
			ais = AudioSystem.getAudioInputStream(stream);
			srk = AudioSystem.getClip();
			srk.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void hadouken() {
		hadouken.setFramePosition(0);
		hadouken.start();
	}

	public static void tatsu() {
		tatsu.setFramePosition(0);
		tatsu.start();
	}

	public static void srk() {
		srk.setFramePosition(0);
		srk.start();
	}
}
