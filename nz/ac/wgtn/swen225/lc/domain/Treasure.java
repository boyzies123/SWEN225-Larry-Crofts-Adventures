package nz.ac.wgtn.swen225.lc.domain;

/**
 * Treasure Class A treasure item that chap can pick up. Chap must collect all
 * treasure on the map to open the exit lock.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 * 
 */

public class Treasure extends TileObject {

  /**
   * Treasure the player can pick up.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Treasure(int x, int y) {
    super(x, y);
  }

  /**
   * Checks if object can move onto tile.
   */
  public boolean movementRequest(TileObject other) {
    return true;
  }

  /**
   * Adds to chap's treasure. Deletes the object from the map
   */
  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    player.incremeantTreasure();
    Map.removeObject(this);
    player.dispatchEvent(new TreasurePickedUpEvent(player, this));
  }

}
