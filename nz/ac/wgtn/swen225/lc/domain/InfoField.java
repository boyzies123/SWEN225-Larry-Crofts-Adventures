package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.app.Information;

/**
 * Info Field Class A Field for information. Displays text on screen when chap
 * is touching it.
 *
 * @author Adam Venner 300612218
 * @version 07/10/2023
 * 
 */

public class InfoField extends TileObject {

  /**
   * A Infofield that informs the player about stuff.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public InfoField(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }

  public void playerInteraction(Player player) {
    Information.display("Info", "Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
        + " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
        + " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute"
        + " irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
        + " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim"
        + " id est laborum");
  }

}
