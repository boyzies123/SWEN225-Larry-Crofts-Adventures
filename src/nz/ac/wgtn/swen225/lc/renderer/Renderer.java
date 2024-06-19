package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import nz.ac.wgtn.swen225.lc.domain.Tile;

/**
 * The Renderer class is responsible for managing and coordinating the rendering
 * of game elements on a Canvas using JavaFX. It serves as the central rendering
 * component, utilizing various specialized renderers (TileRenderer,
 * GridRenderer, BoardRenderer) for different rendering tasks. The class
 * initializes and holds the Canvas, GraphicsContext, and associated renderers.
 * It offers methods to render individual tiles and focused game board sections.
 *
 * @author Dillon Sykes 300196292
 */
public class Renderer {

  private static final int TILE_SIZE = 64; // Size of each tile in pixels

  private Canvas canvas; // The canvas on which game elements are rendered
  private GraphicsContext gc; // The graphics context for drawing on the canvas

  private final ImageLoader imageLoader; // Handles image loading and caching
  private final TileRenderer tileRenderer; // Responsible for rendering individual tiles
  private final GridRenderer gridRenderer; // Draws grid lines on the canvas
  private final BoardRenderer boardRenderer; // Renders focused area of the game board

  /**
   * Constructor initializes the Renderer with specified canvas width and height.
   * It creates a new Canvas and GraphicsContext, and initializes the ImageLoader,
   * TileRenderer, GridRenderer, and BoardRenderer with appropriate parameters.
   * Each specialized renderer is assigned tasks relevant to their functionality.
   *
   * @param width  The width of the canvas in pixels.
   * @param height The height of the canvas in pixels.
   */
  public Renderer(double width, double height) {
    this.canvas = new Canvas(width, height);
    this.gc = canvas.getGraphicsContext2D();

    this.imageLoader = new ImageLoader();
    this.tileRenderer = new TileRenderer(gc, imageLoader);
    this.gridRenderer = new GridRenderer(gc);
    this.boardRenderer = new BoardRenderer(tileRenderer, gridRenderer);
  }

  /**
   * Invokes the TileRenderer to render a specified Tile at given (x, y)
   * coordinates on the canvas. Coordinates represent the pixel position on the
   * canvas where the top-left corner of the tile will be placed.
   *
   * @param tile The Tile object to be rendered.
   * @param x    The x-coordinate (in pixels) on the canvas.
   * @param y    The y-coordinate (in pixels) on the canvas.
   */
  public void renderTile(Tile tile, double x, double y) {
    tileRenderer.renderTile(tile, x, y);
  }

  /**
   * Invokes the BoardRenderer to render a focused area of the game board around
   * the player. The focused area is defined based on player’s coordinates and
   * rendered on the canvas. This method is useful for rendering sections of the
   * game board where the player is active, providing a zoomed or focused view.
   *
   * @param map     A 2D array of Tiles representing the game board.
   * @param playerX The x-coordinate of the player’s position on the board.
   * @param playerY The y-coordinate of the player’s position on the board.
   */
  public void renderBoard(Tile[][] map, int playerX, int playerY) {
    boardRenderer.renderBoard(map, playerX, playerY, TILE_SIZE);
  }

  /**
   * Retrieves the Canvas object used by this Renderer. The Canvas is the drawing
   * area where game elements are rendered.
   *
   * @return The Canvas object where game elements are rendered.
   */
  public Canvas getCanvas() {
    return canvas;
  }
}