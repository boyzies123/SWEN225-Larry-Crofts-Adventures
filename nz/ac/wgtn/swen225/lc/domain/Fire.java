package nz.ac.wgtn.swen225.lc.domain;

/**
 * Fire Class. If chap walks on fire without boots he dies.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 * 
 */

public class Fire extends TileObject {

  /**
   * A Fire.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Fire(int x, int y) {
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
    if (!player.hasBoots()) {
      player.die();
    }
  }

}
