package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.DoorUnlockedEvent;
import nz.ac.wgtn.swen225.lc.domain.GameEvent;
import nz.ac.wgtn.swen225.lc.domain.GameEventListener;
import nz.ac.wgtn.swen225.lc.domain.KeyPickedUpEvent;
import nz.ac.wgtn.swen225.lc.domain.TreasurePickedUpEvent;

/**
 * Listens for game events and plays associated sounds.
 *
 * @author Dillon Sykes 300196292
 */
public class SoundEventListener implements GameEventListener {

  private SoundLoader soundLoader;

  /**
   * Initializes SoundEventListener and the SoundLoader.
   *
   * @param soundLoader The SoundLoader instance to be used for sound operations.
   */
  public SoundEventListener(SoundLoader soundLoader) {
    this.soundLoader = soundLoader;
  }

  /**
   * Plays a sound when a game event occurs.
   *
   * @param event This is an event related to the game, and is expected to be a
   *              subtype of GameEvent.
   */
  @Override
  public void onGameEvent(GameEvent event) {
    // System.out.println("onGameEvent called with: " + event);

    // Check the event type and play the corresponding sound
    if (event instanceof KeyPickedUpEvent) {
      soundLoader.playSound("key-picked-up");
    } else if (event instanceof TreasurePickedUpEvent) {
      soundLoader.playSound("treasure-picked-up");
    } else if (event instanceof DoorUnlockedEvent) {
      soundLoader.playSound("door-unlocked");
    }
  }
}