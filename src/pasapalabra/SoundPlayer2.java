package pasapalabra;

import java.io.File; 
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.io.IOException; 

import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;  
import javax.sound.sampled.UnsupportedAudioFileException; 

public class SoundPlayer2 
{ 

	//define storage for start position
	Long nowFrame; 
	public Clip clip; 
	
	// get the clip status 
	String thestatus; 
	
	AudioInputStream audioStream; 
	static String thePath; 

	// initialize both the clip and streams 
	public SoundPlayer2(String path) 
		throws UnsupportedAudioFileException, 
		IOException, LineUnavailableException 
	{ 
		
		// the input stream object 
		audioStream = 
				AudioSystem.getAudioInputStream(
				    new File(path)
				    ); 
		
		// the reference to the clip 
		clip = AudioSystem.getClip(); 
 
		clip.open(audioStream);
		
		clip.start();
		
		
		clip.loop(0);
		
	} 

	/*public static void main(String[] args) 
	{ 
		try
		{ 
		 
			SoundPlayer2 simpleSoundPlayer = new SoundPlayer2("1.snd"); 		
			System.out.println(simpleSoundPlayer.getMiliSegundosAudio());
		} 
		
		catch (Exception e) 
		{ 
			System.out.println("Experienced an error while playing sound."); 
			e.printStackTrace(); 
		
		} 
	} */
	
	// operation is now as per the user's choice 
	public long getMiliSegundosAudio() {
		return (clip.getMicrosecondLength()/1000);
	}
	
	// play 
	public void play() 
	{ 
		//start the clip 
		clip.start(); 
		
		thestatus = "play"; 
	} 
	
	// Pause audio 
	public void pause() 
	{ 
		if (thestatus.equals("paused")) 
		{ 
			System.out.println("audio is already paused"); 
			return; 
		} 
		this.nowFrame = 
		this.clip.getMicrosecondPosition(); 
		clip.stop(); 
		thestatus = "paused"; 
	} 
	
	// resume audio
	public void resumeAudio() throws UnsupportedAudioFileException, 
							IOException, LineUnavailableException 
	{ 
		if (thestatus.equals("play")) 
		{ 
			System.out.println("The audio is"+ 
			"being played"); 
			return; 
		} 
		clip.close(); 
		resetAudioStream(); 
		clip.setMicrosecondPosition(nowFrame); 
		this.play(); 
	} 
	
	// restart audio 
	public void restart() throws IOException, LineUnavailableException, 
											UnsupportedAudioFileException 
	{ 
		clip.stop(); 
		clip.close(); 
		resetAudioStream(); 
		nowFrame = 0L; 
		clip.setMicrosecondPosition(0); 
		this.play(); 
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
