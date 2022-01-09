package pasapalabra;

// CLASE SOUNDPLAYER OBTENIDA DE LA WEB: https://www.delftstack.com/es/howto/java/play-sound-in-java/
// MODIFICADA: VICTOR GARCIA AGUILAR

import java.io.File; 
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException; 

public class SoundPlayer_FINAL 
{ 

	//define storage for start position
	Long nowFrame; 
	public Clip clip; 
	
	// get the clip status 
	String thestatus; 
	
	AudioInputStream audioStream; 
	static String thePath; 

	// initialize both the clip and streams 
	public SoundPlayer_FINAL(String path,int i) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		
		// the input stream object 
		audioStream = AudioSystem.getAudioInputStream(new File(path)); 
		// the reference to the clip 
		clip = AudioSystem.getClip(); 
 
		clip.open(audioStream);
		
		long reproduccion_pista = (clip.getMicrosecondLength())/5;
		long a=(reproduccion_pista*i);
		if (a > 0 && a < clip.getMicrosecondLength()) {
			clip.setMicrosecondPosition(reproduccion_pista*i); 
		}
		
		clip.start();
		clip.loop(0);
	} 

	public long getMiliSegundosAudio() {
		return ((clip.getMicrosecondLength()/1000)/5);
	}
	
	// play 
	public void play() 
	{ 
		//start the clip 
		clip.start(); 
		
		thestatus = "play"; 
	} 
	
	
	// stop audio 
	public void stop() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException 
	{ 
		nowFrame = 0L; 
		clip.stop(); 
		clip.close(); 
	} 
	
	// jump to a selected point 
	public void jump(long a) throws UnsupportedAudioFileException, IOException, 
														LineUnavailableException 
	{ 
		if (a > 0 && a < clip.getMicrosecondLength()) 
		{ 
			clip.stop(); 
			clip.close(); 
			resetAudioStream(); 
			nowFrame = a; 
			clip.setMicrosecondPosition(a); 
			this.play(); 
		} 
	} 
	
	// reset the audio stream 
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, 
											LineUnavailableException 
	{ 
		audioStream = AudioSystem.getAudioInputStream( 
		new File(thePath).getAbsoluteFile()); 
		clip.open(audioStream); 
		clip.loop(Clip.LOOP_CONTINUOUSLY); 
	} 

} 
