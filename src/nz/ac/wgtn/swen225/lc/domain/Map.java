package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Map Class. The map/board that the game is played on A structure for tiles
 *
 * @author Adam Venner 300612218
 * @version 10/10/2023
 *
 */
public class Map {

  /**
   * A Map structure to contains tiles.
   *
   * @param rows number of rows.
   * @param cols number of columns.
   */
  public Map(int rows, int cols) {
    //precondition
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("invalid parameters");
    }
    Map.rows = rows;
    Map.cols = cols;
    tileMap = new Tile[rows][cols];
  }

  // fields
  private static int rows;
  private static int cols;
  private static Tile[][] tileMap;
  public static boolean complete = false;

  /**
   * Returns a tile on the map based on coords.
   *
   * @param x the x position on the map.
   * @param y the y position on the map.
   * @return a tile from the map.
   */
  public static Tile getTile(int x, int y) {
    //precondition
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("invalid parameters");
    }
    return tileMap[x][y];
  }

  /**
   * Returns the amount of treasures on the map.
   *
   * @return treasures.
   */
  public static boolean treasuresLeft() {
    return Arrays.stream(tileMap)
    .flatMap(Arrays::stream)
    .anyMatch(x -> x.getObjects().stream()
        .anyMatch(obj -> obj instanceof Treasure));
  }

  /**
   * Removes an object from the map.
   *
   * @param obj the object to be removed.
   */
  public static void removeObject(TileObject obj) {
    //precondition
    if (obj == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    tileMap[obj.getX()][obj.getY()].removeObject(obj);
  }

  /**
   * Checks if object can make a certain move.
   *
   * @param newX x position the object is trying to move to.
   * @param newY y position the object is trying to move to.
   * @param toMove the object trying to move.
   *
   * @return if the movement is legal.
   */
  public static boolean isMoveLegal(int newX, int newY, TileObject toMove) {
    //precondition
    if (newX < 0 || newY < 0 || toMove == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    Tile tile = tileMap[newX][newY];
    if (!tile.hasObject()) {
      return true;
    }
    ArrayList<TileObject> interactions = tile.getObjects();
    return interactions.stream().allMatch(obj -> obj.movementRequest(toMove));
  }

  /**
   * Returns a 2d array of tiles representing the map.
   *
   * @return the map.
   */
  public static Tile[][] getMap() {
    return tileMap;
  }

  /**
   * Sets the map.
   *
   * @param sample a representation of the map.
   */
  public static void setMap(Tile[][] sample) {
    //precondition
    if (sample == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    tileMap = sample;
  }
  
  /**
   * Gets the columns.
   *
   * @return columns of the map
   */
  public int getCols() {
    return cols;
  }
  
  /**
   * Gets the rows.
   *
   * @return rows of the map
   */
  public int getRows() {
    return rows;
  }
  
  public static void setComplete(boolean value) {
    complete = value;
  }
  
  public static boolean isComplete() {
    return complete;
  }
  
  
  
  
}
