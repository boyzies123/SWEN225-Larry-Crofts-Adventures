package nz.ac.wgtn.swen225.lc.domain;

public class KeyPickedUpEvent extends GameEvent {
  private Key key;

  public KeyPickedUpEvent(TileObject source, Key key) {
      super(source);
      this.key = key;
  }

  public Key getKey() {
      return key;
  }
}