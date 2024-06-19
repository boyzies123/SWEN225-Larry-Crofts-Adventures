package nz.ac.wgtn.swen225.lc.renderer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles loading and caching of sounds from /sounds/ directory to improve
 * in-game audio performance.
 *
 * @author Dillon Sykes 300196292
 */
public class SoundLoader {

  private final Map<String, Clip> soundClips = new HashMap<>(); // Cache for sound clips

  /**
   * Initializes SoundLoader and preloads sounds.
   */
  public SoundLoader() {
    preloadSounds();
  }

  /**
   * Preloads and caches sounds associated with certain game events.
   */
  private void preloadSounds() {
    String[] soundTypes = { "treasure-picked-up", "key-picked-up", "door-unlocked" };
    for (String type : soundTypes) {
      loadSound(type);
    }
  }

  /**
   * Loads and caches a sound for a given game event. Prints an error message if
   * the sound clip cannot be loaded.
   *
   * @param type the sound associated with game event.
   */
  private void loadSound(String type) {
    try {
      InputStream audioSrc = SoundLoader.class.getResourceAsStream("/sounds/" + type + ".wav");
      InputStream bufferedIn = new BufferedInputStream(audioSrc);
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      soundClips.put(type, clip);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("Error loading sound for type: " + type);
      e.printStackTrace();
    }
  }

  /**
   * Plays a sound clip associated with a game event.
   *
   * @param type the type of game event for which to play the sound.
   */
  public void playSound(String type) {
    Clip clip = getSound(type);
    if (clip != null) {
      clip.setFramePosition(0); // rewind to the beginning
      clip.start(); // Start playing
    }
  }

  /**
   * Adjusts the volume for all cached sound clips.
   *
   * @param gainValue the gain value in decibels. Positive values increase volume,
   *                  negative values decrease volume.
   */
  public void setVolume(float gainValue) {
    for (Clip clip : soundClips.values()) {
      if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float maxGain = gainControl.getMaximum();
        float minGain = gainControl.getMinimum();
        gainValue = Math.min(maxGain, Math.max(minGain, gainValue)); // Ensure within allowed range
        gainControl.setValue(gainValue);
      }
    }
  }

  /**
   * Retrieves a cached sound clip based on the game event.
   *
   * @param type the type of game event for which to retrieve the sound.
   * @return the cached Clip object.
   */
  public Clip getSound(String type) {
    return soundClips.get(type);
  }
}