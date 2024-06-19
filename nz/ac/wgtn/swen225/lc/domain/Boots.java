package nz.ac.wgtn.swen225.lc.domain;

/**
 * Boot Class. Player can pick up to stand on fire.
 *
 * @author Adam Venner 300612218
 * @version 03/10/2023
 * 
 */

public class Boots extends TileObject {

  /**
   * A Boots.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Boots(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }

  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    player.giveBoots();
    Map.removeObject(this);
  }

}
