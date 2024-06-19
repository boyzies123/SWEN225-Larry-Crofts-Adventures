package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Key.Color;

/**
 * LockedDoor Class A Door object. An object that blocks chap unless he has the
 * correct key.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 * 
 */

public class LockedDoor extends TileObject {

  // fields
  Key.Color color;

  /**
   * A Locked Door.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   * @param color the color of the door.
   */
  public LockedDoor(int x, int y, Key.Color color) {
    super(x, y);
    this.color = color;
  }

  /**
   * Checks if a other object can move onto this space. is only true if the object
   * is chap and he has the correct key.
   */
  public boolean movementRequest(TileObject other) {
    //precondition
    if (other == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    if (other instanceof Player) {
      Player player = (Player) other;
      return player.getKeys().stream().anyMatch(key -> key.getColor() == color);
    }
    return false;
  }

  /**
   * Gets the color of the door.
   *
   * @return the color of the door.
   */
  public Color getColor() {
    return color;
  }

  /**
   * Removes a key from chap's inventory based on color. Removes this object from
   * the map.
   * 
   */
  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    player.removeKey(color);
    Map.removeObject(this);
    player.dispatchEvent(new DoorUnlockedEvent(player, this));
  }

}
