package nz.ac.wgtn.swen225.lc.domain;

/**
 * Key Class A key that chap can collect. Opens locked doors.
 *
 * @author Adam Venner 300612218
 * @version 06/10/2023
 *
 */

public class Key extends TileObject {

  // fields
  private Color color;

  /**
   * The color of the key.
   */
  public enum Color {
    /**
     * The color red.
     */
    RED,
    /**
     * The color blue.
     */
    BLUE
  }

  /**
   * A Key the player can pick up.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   * @param color the color of the key.
   */
  public Key(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }

  /**
   * Gets the color of the key.
   *
   * @return color of the key.
   */
  public Color getColor() {
    return color;
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }

  /**
   * Adds the key to chap's inventory. removes the key from the board.
   */
  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    player.addKey(this);
    Map.removeObject(this);
    player.dispatchEvent(new KeyPickedUpEvent(player, this));
  }

}
