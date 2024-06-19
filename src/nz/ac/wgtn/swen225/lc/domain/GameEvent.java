package nz.ac.wgtn.swen225.lc.domain;

/**
 * Class to handle events.
 *
 * @author Adam Venner 300612218
 * @version 05/10/2023
 */
public class GameEvent {
  private TileObject source;

  /**
   * A Game Event.
   *
   * @param source origin of the event.
   */
  public GameEvent(TileObject source) {
    this.source = source;
  }

  /**
   * Gets the source.
   *
   * @return source of the event
   */
  public TileObject getSource() {
    return source;
  }
}