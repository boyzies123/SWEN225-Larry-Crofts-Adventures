package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Tile;

/**
 * BoardRenderer class for rendering a focused area of the game board. This
 * class handles rendering of a 2D array of Tile objects within a specified
 * focus area around the player on the canvas, and also invokes grid drawing
 * functionality.
 *
 * @author Dillon Sykes 300196292
 */
public class BoardRenderer {

  private static final int FOCUS_SIZE = 9; // Maximum grid size for focused view

  private final TileRenderer tileRenderer; // TileRenderer instance for rendering tiles
  private final GridRenderer gridRenderer; // GridDrawer instance for drawing grid lines

  /**
   * Constructor initializes the BoardRenderer with provided TileRenderer and
   * GridRenderer. TileRenderer is responsible for rendering individual tiles,
   * whereas GridRenderer handles the drawing of grid lines on the canvas.
   *
   * @param tileRenderer An instance of TileRenderer for rendering individual tiles.
   * @param gridRenderer An instance of GridRenderer for drawing grid lines.
   */
  public BoardRenderer(TileRenderer tileRenderer, GridRenderer gridRenderer) {
    this.tileRenderer = tileRenderer;
    this.gridRenderer = gridRenderer;
  }

  /**
   * Renders a focused area around the player's position on the canvas. The method
   * calculates the start and end coordinates for x and y axes to establish the
   * focus area, adjusts for edge cases, and then iteratively renders each tile
   * within this focus area. After rendering tiles, it invokes the GridRenderer to
   * draw grid lines.
   *
   * @param map      A 2D array of Tiles representing the game board.
   * @param playerX  The x-coordinate of the player's position.
   * @param playerY  The y-coordinate of the player's position.
   * @param tileSize The size of each tile to be rendered on the canvas.
   */
  public void renderBoard(Tile[][] map, int playerX, int playerY, double tileSize) {
    int halfFocusSize = FOCUS_SIZE / 2;

    int startX = Math.max(playerX - halfFocusSize, 0);
    int endX = Math.min(playerX + halfFocusSize, map[0].length - 1);

    int startY = Math.max(playerY - halfFocusSize, 0);
    int endY = Math.min(playerY + halfFocusSize, map.length - 1);

    // Adjustments for edge cases
    if (endX - startX < FOCUS_SIZE - 1) {
      if (startX == 0) {
        endX = startX + FOCUS_SIZE - 1;
      } else {
        startX = endX - FOCUS_SIZE + 1;
      }
    }
    if (endY - startY < FOCUS_SIZE - 1) {
      if (startY == 0) {
        endY = startY + FOCUS_SIZE - 1;
      } else {
        startY = endY - FOCUS_SIZE + 1;
      }
    }

    for (int i = startY; i <= endY; i++) {
      for (int j = startX; j <= endX; j++) {
        Tile currentTile = map[i][j];
        tileRenderer.renderTile(currentTile, (j - startX) * tileSize, (i - startY) * tileSize);
      }
    }

    // Render grid lines
    gridRenderer.drawGrid(FOCUS_SIZE * tileSize, FOCUS_SIZE * tileSize, tileSize);
  }
}