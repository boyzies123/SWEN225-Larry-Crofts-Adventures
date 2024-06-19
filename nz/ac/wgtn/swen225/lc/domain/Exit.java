package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.persistency.GameStateManager;
/**
 * Exit Class A Exit object. The goal chap must reach to end the level.
 *
 * @author Adam Venner 300612218
 * @version 27/09/2023
 * 
 */

public class Exit extends TileObject {

  /**
   * The Exit.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Exit(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }

  public void playerInteraction(Player player) {
    Map.setComplete(true);
    if (GameStateManager.getCurrentLevel() == 1) {
      GameStateManager.setCurrentLevel(2);
    }
    else {
      GameStateManager.setCurrentLevel(1);
    }
  }

}
