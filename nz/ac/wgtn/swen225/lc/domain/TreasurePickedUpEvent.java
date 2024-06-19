package nz.ac.wgtn.swen225.lc.domain;

public class TreasurePickedUpEvent extends GameEvent {
  private Treasure treasure;

  public TreasurePickedUpEvent(TileObject source, Treasure treasure) {
      super(source);
      this.treasure = treasure;
  }

  public Treasure getTreasure() {
      return treasure;
  }
}