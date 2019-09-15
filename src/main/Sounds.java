package main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sounds {
	
	public AudioClip arrow;
	
	public AudioClip loadSound(String file){
		return Applet.newAudioClip(Sounds.class.getResource(file));
	}
	
	public void init(){
		arrow = loadSound("/arrow2.wav");
	}
	
	
	public void play(AudioClip sound){
		Thread t = new Thread(){
			public void run(){
				sound.play();
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
