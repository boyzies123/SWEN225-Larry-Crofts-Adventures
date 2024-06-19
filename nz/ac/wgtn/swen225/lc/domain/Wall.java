package nz.ac.wgtn.swen225.lc.domain;

/**
 * Wall Class A Wall object. An object that blocks chap.
 *
 * @author Adam Venner 300612218
 * @version 27/09/2023
 * 
 */

public class Wall extends TileObject {

  /**
   * A Wall.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Wall(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return false;
  }

  public void playerInteraction(Player player) {
    throw new IllegalArgumentException("Player cannot be moved into a wall");
  }

}
