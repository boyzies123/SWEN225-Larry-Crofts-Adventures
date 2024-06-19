package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.TileObject.Direction;

/**
 * Chap Class The character that the player controls. Chap is moved around to
 * solve puzzles and interact with the objects.
 *
 * @author Adam Venner 300612218
 * @version 10/10/2023
 * 
 */

public class Player extends TileObject {

  // fields
  private Direction lastDirection = Direction.DOWN; // the last direction Chap moved in.
  private ArrayList<Key> keys = new ArrayList<>(); // keys that chap currently has.
  private int treasures = 0; // the amount of treasures chap has picked up
  private boolean boots = false; // boots that allow chap to walk on fire
  private boolean dead = false;

  /**
   * The Player.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Player(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return false;
  }

  /**
   * Gets the keys from the player.
   *
   * @return the keys the player has in their inventory.
   */
  public ArrayList<Key> getKeys() {
    return keys;
  }

  
  /**
   * Adds a key to chap's inventory.
   *
   * @param key the key to be added.
   */
  public void addKey(Key key) {
    //precondition
    if (key == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    keys.add(key);
  }

  /**
   * Removes a key of a specific color from chap's inventory.
   *
   * @param col color of the key.
   */
  public void removeKey(Key.Color col) {
    //precondition
    if (col == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    for (Key key : keys) {
      if (key.getColor() == col) {
        keys.remove(key);
        break;
      }
    }
  }

  /**
   * Gets the number of treasures chap has.
   *
   * @return treasures chap has
   */
  public int getTreasures() {
    return treasures;
  }
  
  /**
   * Increases the amount of treasure's chap has by one.
   */
  public void incremeantTreasure() {
    treasures++;
  }
  
  /**
   * Gets the direction of chap.
   *
   * @return the direction the player is facing.
   */
  public Direction getDirection() {
    return lastDirection;
  }
  
  /**
   * Used to find out if the player is wearing boots.
   *
   * @return boots
   */
  public boolean hasBoots() {
    return boots;
  }
  
  /**
   * Gives the player boots to walk on fire.
   */
  public void giveBoots() {
    boots = true;
  }
  
  /**
   * Destroy's the player and restarts the level.
   */
  public void die() {
    Map.removeObject(this);
    dead = true;
  }
  
  public void setAlive(boolean state) {
    dead = !state;
  }
  
  public boolean isDead() {
    return dead;
  }
  
  /**
   * Sets the position of the player.
   *
   * @param x the new x position
   * @param y the new y position
   */
  public void setPosition(int x, int y) {
    //precondition
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("invalid parameters");
    }
    posX = x;
    posY = y;
  }

  public void playerInteraction(Player player) {
    throw new IllegalArgumentException("Player cannot be moved into itself");
  }

}
