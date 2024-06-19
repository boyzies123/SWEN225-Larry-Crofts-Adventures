package nz.ac.wgtn.swen225.lc.renderer;

import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.wgtn.swen225.lc.domain.Tile;

/**
 * The TransitionRenderer class provides methods for rendering fade-in and
 * fade-out transitions. It creates a rectangle for transitions and a pane to
 * hold the rectangle. Populating fade transitions on this rectangle creates the
 * transition effect.
 *
 * @author Dillon Sykes 300196292
 */
public class TransitionRenderer {
  
  private final Pane transitionPane; // Holds the transition effect
  private final Rectangle fadeRectangle; // Rectangle for fade-in and fade-out effects
  private final GraphicsContext gc; // Graphics context to render transitions on the canvas
  private final double tileSize; // Size of each tile in pixels

  /**
   * Constructor initializes the TransitionRenderer with the provided canvas's
   * dimensions. It uses those dimensions to create a pane and a rectangle for the
   * transition effects.
   *
   * @param canvas   The canvas on which game is rendered.
   * @param gc       Graphics context associated with the canvas, used to draw
   *                 graphics onto the canvas.
   * @param tileSize The size of each tile on the canvas in pixels.
   */
  public TransitionRenderer(Canvas canvas, GraphicsContext gc, double tileSize) {
    this.gc = gc;
    this.tileSize = tileSize;

    // Pane to hold transition effects
    transitionPane = new Pane();
    transitionPane.setPrefSize(canvas.getWidth(), canvas.getHeight());

    // Rectangle for fade effects
    fadeRectangle = new Rectangle(canvas.getWidth(), canvas.getHeight());
    fadeRectangle.setFill(Color.BLACK);
    transitionPane.getChildren().add(fadeRectangle);
  }

  /**
   * Renders a fade-in transition on the rectangle from black.
   *
   * @param durationInSeconds The duration in seconds for which the fade-in
   *                          transition takes place.
   */
  public void fadeInTransition(double durationInSeconds) {
    FadeTransition fadeTransition = new FadeTransition(
        Duration.seconds(durationInSeconds), fadeRectangle
    );
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
  }

  /**
   * Renders a fade-out transition on the rectangle to black.
   *
   * @param durationInSeconds The duration in seconds for which the fade-out
   *                          transition takes place.
   */
  public void fadeOutTransition(double durationInSeconds) {
    FadeTransition fadeTransition = new FadeTransition(
        Duration.seconds(durationInSeconds), fadeRectangle
    );
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  /**
   * Returns the transition pane containing the rectangle for transition effects.
   *
   * @return The transition pane.
   */
  public Pane getTransitionPane() {
    return transitionPane;
  }

  /**
   * Executes a pixel transition effect where individual tiles fade out to black,
   * creating a pixelated fade out effect.
   *
   * @param map The 2D array of tiles representing the game map.
   */
  public void fadeOutPixelTransition(Tile[][] map) {
    int rowCount = map.length;
    int colCount = map[0].length;

    Timeline transitionTimeline = new Timeline();
    Random random = new Random();

    for (int row = 0; row < rowCount; row++) {
      for (int col = 0; col < colCount; col++) {
        final int finalRow = row;
        final int finalCol = col;

        double delaySeconds = 1 * random.nextDouble();

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(delaySeconds), e ->
            drawBlackTile(finalCol, finalRow)
        );
        transitionTimeline.getKeyFrames().add(keyFrame);
      }
    }
    transitionTimeline.play();
  }

  /**
   * Helper method for pixel transition. Draws a black tile on the canvas at the
   * specified (x, y) tile coordinates.
   *
   * @param x The x-coordinate of the tile.
   * @param y The y-coordinate of the tile.
   */
  private void drawBlackTile(int x, int y) {
    gc.setFill(Color.BLACK);
    gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
  }
}