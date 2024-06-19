package nz.ac.wgtn.swen225.lc.renderer;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nz.ac.wgtn.swen225.lc.domain.Exit;
import nz.ac.wgtn.swen225.lc.domain.ExitLock;
import nz.ac.wgtn.swen225.lc.domain.InfoField;
import nz.ac.wgtn.swen225.lc.domain.Key;
import nz.ac.wgtn.swen225.lc.domain.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import nz.ac.wgtn.swen225.lc.domain.TileObject;

/**
 * TileRenderer class for rendering individual tiles. This class handles the
 * rendering of individual Tile objects onto the canvas.
 *
 * @author Dillon Sykes 300196292
 */
public class TileRenderer {

  private final GraphicsContext gc; // The graphics context associated with the canvas
  private final ImageLoader imageLoader; // The ImageLoader instance to retrieve images

  /**
   * Constructor initializes the TileRenderer with the provided GraphicsContext
   * and ImageLoader.
   *
   * @param gc          The GraphicsContext associated with the canvas.
   * @param imageLoader The ImageLoader for retrieving cached images.
   */
  public TileRenderer(GraphicsContext gc, ImageLoader imageLoader) {
    this.gc = gc;
    this.imageLoader = imageLoader;
  }

  /**
   * Renders a Tile at coordinates (x, y) on the canvas.
   *
   * @param tile Tile object to be rendered.
   * @param x    The x-coordinate of the tile.
   * @param y    The y-coordinate of the tile.
   */
  public void renderTile(Tile tile, double x, double y) {
    List<String> tileTypes = getTileTypes(tile);
    for (String tileType : tileTypes) {
      Image img = imageLoader.getImage(tileType);
      if (img != null) {
        gc.drawImage(img, x, y);
      } else {
        System.out.println("Tile type " + tileType + " not found!");
      }
    }
  }

  /**
   * Retrieves the list of TileObject types within the given Tile. Keys are
   * rendered on top of all other objects.
   *
   * @param tile Tile object to retrieve TileObject types from.
   * @return List of strings representing the types of TileObjects in the tile.
   */
  private List<String> getTileTypes(Tile tile) {
    List<String> tileTypes = new ArrayList<>();
    tileTypes.add("free"); // Always add "free" as the base tile

    for (TileObject tileObject : tile.getObjects()) {
      if (!(tileObject instanceof Key)) {
        tileTypes.add(getTileType(tileObject));
      }
    }

    for (TileObject tileObject : tile.getObjects()) {
      if (tileObject instanceof Key) {
        tileTypes.add(getTileType(tileObject));
      }
    }

    return tileTypes;
  }

  /**
   * Determines and returns the type of a specified TileObject. The type is used
   * to identify the image that represents the TileObject during rendering.
   *
   * @param tileObject The TileObject for which to determine the type.
   * @return a string representing the type of the TileObject.
   */
  private String getTileType(TileObject tileObject) {
    if (tileObject instanceof Key) {
      return "key-" + ((Key) tileObject).getColor().name().toLowerCase();
    } else if (tileObject instanceof LockedDoor) {
      return "locked-door-" + ((LockedDoor) tileObject).getColor().name().toLowerCase();
    } else if (tileObject instanceof InfoField) {
      return "info-field";
    } else if (tileObject instanceof ExitLock) {
      return "exit-lock";
    } else if (tileObject instanceof Exit) {
      return "exit";
    }
    return tileObject.getClass().getSimpleName().toLowerCase();
  }
}