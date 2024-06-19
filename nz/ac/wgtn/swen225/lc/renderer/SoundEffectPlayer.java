package nz.ac.wgtn.swen225.lc.renderer;

/**
 * Handles playing sound effects corresponding to game events. This class makes
 * use of SoundLoader for efficient sound caching.
 *
 * @author Dillon Sykes 300196292
 */
public class SoundEffectPlayer {

  private SoundLoader soundLoader;

  /**
   * Initializes a SoundEffectPlayer and preloads sounds using SoundLoader.
   */
  public SoundEffectPlayer() {
    soundLoader = new SoundLoader();
  }

  /**
   * Adjusts the volume for all sound effects.
   *
   * @param gainValue the gain value in decibels.
   */
  public void setVolume(float gainValue) {
    soundLoader.setVolume(gainValue);
  }

  /**
   * Plays a sound effect associated with a particular game event type. The sound
   * effect files are managed by the SoundLoader class.
   *
   * @param soundType A string representing the type of game event.
   */
  public void playSound(String soundType) {
    soundLoader.playSound(soundType);
  }

  /**
   * Retrieves the SoundLoader instance associated with this object.
   *
   * @return the SoundLoader instance used for sound operations.
   */
  public SoundLoader getSoundLoader() {
    return soundLoader;
  }
}