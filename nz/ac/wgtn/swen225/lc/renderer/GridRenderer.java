package nz.ac.wgtn.swen225.lc.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * GridRenderer class for rendering grid lines on a canvas. This class handles
 * the drawing of grid lines for the game board, providing a visual structure
 * for placing game elements.
 *
 * @author Dillon Sykes 300196292
 */
public class GridRenderer {

  private final GraphicsContext gc; // The graphics context associated with the canvas

  /**
   * Constructor initializes the TileRenderer with the provided GraphicsContext.
   *
   * @param gc The GraphicsContext associated with the canvas.
   */
  public GridRenderer(GraphicsContext gc) {
    this.gc = gc;
  }

  /**
   * Draws the grid lines on the canvas for the game board, covering the specified
   * width and height of the canvas.
   *
   * @param width    The width of the area on the canvas where grid lines will be drawn.
   * @param height   The height of the area on the canvas where grid lines will be drawn.
   * @param tileSize Size of each tile in the grid, determining the spacing between lines.
   */
  public void drawGrid(double width, double height, double tileSize) {
    gc.setStroke(Color.BLACK);
    for (int i = 0; i <= width; i += tileSize) {
      gc.strokeLine(i, 0, i, height);
    }
    for (int i = 0; i <= height; i += tileSize) {
      gc.strokeLine(0, i, width, i);
    }
  }
}