package nz.ac.wgtn.swen225.lc.domain;

/**
 * Game Event.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 */
public interface GameEventListener {
  
    /**
     * @param event
     */
    void onGameEvent(GameEvent event);
}
