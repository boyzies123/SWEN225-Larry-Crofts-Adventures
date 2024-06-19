package nz.ac.wgtn.swen225.lc.renderer;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import nz.ac.wgtn.swen225.lc.domain.Key;

/**
 * Handles preloading and caching of images from /images/ directory to improve
 * rendering performance.
 *
 * @author Dillon Sykes 300196292
 */
public class ImageLoader {

  private final Map<String, Image> tileImages = new HashMap<>(); // Cache for tile images

  /**
   * Initializes ImageLoader and preloads images.
   */
  public ImageLoader() {
    preloadImages();
  }

  /**
   * Preloads and caches images associated with tile types and keys of different
   * colours.
   */
  private void preloadImages() {
    String[] tileTypes = { "wall", "free", "treasure", "teleporter", "boots", "fire", "enemy", 
      "player", "info-field", "exit-lock", "exit" };
    for (String type : tileTypes) {
      loadImage(type);
    }
    for (Key.Color colour : Key.Color.values()) {
      loadImage("key-" + colour.name().toLowerCase());
      loadImage("locked-door-" + colour.name().toLowerCase());
    }
  }

  /**
   * Loads and caches an image for a given tile type. Prints an error message if
   * the image cannot be loaded.
   *
   * @param type the type of tile for which to load the image.
   */
  private void loadImage(String type) {
    try {
      Image img = new Image(ImageLoader.class.getResourceAsStream("/images/" + type + ".png"));
      tileImages.put(type, img);
    } catch (NullPointerException e) {
      System.out.println("Error loading image for tile type: " + type);
    }
  }

  /**
   * Retrieves a cached image based on the tile type.
   *
   * @param type the type of tile for which to retrieve the image.
   * @return the cached Image object.
   */
  public Image getImage(String type) {
    return tileImages.get(type);
  }
}