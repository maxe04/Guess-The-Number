import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
	 private Clip clip;
	 
	 //Play Sound
	 public void playSound(String soundFilePath) {
        try {
            
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            //Lower the volume (could add menu button to let the user adjust the volume)
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f);
            
            
            clip.start();
           
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
	 }
	
	 //Stop Sound
	 public void stop() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
		}
	 }



}
