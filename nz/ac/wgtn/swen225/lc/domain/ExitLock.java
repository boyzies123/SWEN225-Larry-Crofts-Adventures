package nz.ac.wgtn.swen225.lc.domain;

/**
 * Exit Lock Class A lock for the exit. Can be opened if chap has all the
 * treasure on the map.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 * 
 */

public class ExitLock extends TileObject {
  
  /**
   * A lock that blocks the exit.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public ExitLock(int x, int y) {
    super(x, y);
  }

  /**
   * Checks if chap can move onto the lock. is only true if chap has all
   * treasures.
   *
   * @return if chap can move onto the lock or not.
   */
  public boolean movementRequest(TileObject other) {
    //precondition
    if (other == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    if (other instanceof Player) {
      Player chap = (Player) other;
      return !Map.treasuresLeft();
    }
    return false;
  }

  public void playerInteraction(Player player) {
    Map.removeObject(this);
  }

}
