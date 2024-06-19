package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;

/**
 * Tile Class. A representation of a tile on the map Builds the map and contains
 * objects
 *
 * @author Adam Venner 300612218
 * @version 10/10/2023
 *
 */
public class Tile {

  // fields
  private ArrayList<TileObject> objects = new ArrayList<>();

  /**
   * Checks if the tile has an object.
   *
   * @return if the tile has an object.
   */
  public boolean hasObject() {
    return !objects.isEmpty();
  }

  /**
   * Gets all objects from a tile.
   *
   * @return objects of the tile.
   */
  public ArrayList<TileObject> getObjects() {
    return objects;
  }
  
  /**
   * Adds an object to the tile.
   *
   * @param obj the object to be added.
   */
  public void addObject(TileObject obj) {
    //precondition
    if (obj == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    objects.add(obj);
  }

  /**
   * Removes an object from a tile.
   *
   * @param obj the object to be removed.
   */
  public void removeObject(TileObject obj) {
    //precondition
    if (obj == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    objects.remove(obj);
  }
  
  /**
   * Returns the first object in the objects list.
   * Convenient when there is only 1 object on the tile.
   *
   * @return the first object in the objects list.
   * 
   */
  public TileObject getFirstObject() {
    //precondition
    if (!hasObject()) {
      throw new IllegalStateException("Method should not return null");
    }
    return objects.get(0);
  }

}
