package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

/**
 * TileObject Abstract Class. Objects that can be placed on tiles Gives a basic
 * structure for all map objects
 *
 * @author Adam Venner 300612218
 * @version 07/10/2023
 *
 */
public abstract class TileObject {

  // fields
  protected int posX;
  protected int posY;
  private final ArrayList<GameEventListener> listeners = new ArrayList<>();

  /**
   * The directions Chap can move.
   */
  public enum Direction {
    /**
     * Left of the object.
     */
    LEFT,
    /**
     * Right of the object.
     */
    RIGHT,
    /**
     * Upwards of the object.
     */
    UP,
    /**
     * Downwards of the object.
     */
    DOWN
  }

  /**
   * Creates a Tile for the map.
   *
   * @param x The x position of the object.
   * @param y The y position of the object.
   */
  public TileObject(int x, int y) {
    //precondition
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("invalid parameters");
    }
    this.posX = x;
    this.posY = y;
  }

  /**
   * Gets the x position.
   *
   * @return the x position
   */
  public int getX() {
    return posX;
  }

  /**
   * Gets the y position.
   *
   * @return the y positon.
   */
  public int getY() {
    return posY;
  }


  /**
   * Checks if a movement request is valid.
   *
   * @param other the object trying to move onto this space.
   * @return returns if the movementRequest is valid.
   */
  public abstract boolean movementRequest(TileObject other);

  /**
   * Does something when chap interacts with the object.
   *
   * @param player Chap/ the player.
   */
  public abstract void playerInteraction(Player player);
  
  /**
   * Adds a listener.
   *
   * @param listener the game event to be added.
   */
  public void addEventListener(GameEventListener listener) {
    //precondition
    if (listener == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    listeners.add(listener);
  }

  public void dispatchEvent(GameEvent event) {
    //precondition
    if (event == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    for (GameEventListener listener : listeners) {
      listener.onGameEvent(event);
    }
  }
}
