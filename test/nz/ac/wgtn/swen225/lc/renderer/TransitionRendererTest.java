package test.nz.ac.wgtn.swen225.lc.renderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import nz.ac.wgtn.swen225.lc.domain.Wall;
import nz.ac.wgtn.swen225.lc.renderer.ImageLoader;
import nz.ac.wgtn.swen225.lc.renderer.TileRenderer;
import nz.ac.wgtn.swen225.lc.renderer.TransitionRenderer;

/**
 * TransitionRendererTest class for testing transition rendering in the game.
 * This class initializes a game board with a Canvas, sets up a Scene to display
 * the canvas, and adds buttons for testing fade-in and fade-out transitions.
 *
 * @author Dillon Sykes 300196292
 */
public class TransitionRendererTest extends Application {

  private int playerX = 4; // x-position of the player
  private int playerY = 4; // y-position of the player
  private TransitionRenderer transitionRenderer; // TransitionRenderer for rendering transitions
  private TileRenderer tileRenderer; // TileRenderer for rendering tiles
  private Tile[][] sampleBoard; // Board for testing

  /**
   * Main method for launching the application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Initializes a test stage with a Canvas and buttons for testing fade-in and
   * fade-out transitions. This method is overridden from the Application class.
   *
   * @param primaryStage The primary Stage for this Application.
   */
  @Override
  public void start(Stage primaryStage) {
    initializeBoard();
    Canvas canvas = new Canvas(576, 576);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Initialize tile rendering
    ImageLoader imageLoader = new ImageLoader();
    tileRenderer = new TileRenderer(gc, imageLoader);

    // Initialize TransitionRenderer with the canvas
    transitionRenderer = new TransitionRenderer(canvas, gc, 64);


    // Button for fade in transition
    Button fadeInBtn = new Button("Start Fade In");
    fadeInBtn.setOnAction(e -> {
      renderInitialState();
      transitionRenderer.fadeInTransition(0.75);
    });

    // Button for fade out transition
    Button fadeOutBtn = new Button("Start Fade Out");
    fadeOutBtn.setOnAction(e -> {
      renderInitialState();
      transitionRenderer.fadeOutTransition(0.75);
    });

    // Button for pixel transition
    Button pixelTransitionBtn = new Button("Start Pixel Fade Out");
    pixelTransitionBtn.setOnAction(e -> {
      renderInitialState();
      transitionRenderer.fadeInTransition(0.01); // Display the board
      transitionRenderer.fadeOutPixelTransition(sampleBoard);
    });

    StackPane root = new StackPane();
    root.getChildren().add(canvas);
    root.getChildren().add(transitionRenderer.getTransitionPane());

    Scene scene = new Scene(root, 576, 576);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Transition Renderer Test");
    primaryStage.show();

    // Add buttons to VBox
    VBox buttonBox = new VBox(); // Create a VBox to hold the buttons
    buttonBox.getChildren().addAll(fadeInBtn, fadeOutBtn, pixelTransitionBtn);
    root.getChildren().add(buttonBox); // Add the VBox to the root StackPane

    renderInitialState();
  }

  /**
   * Initializes the game board for testing with a set of predetermined tiles and
   * objects.
   */
  private void initializeBoard() {
    sampleBoard = new Tile[13][13];
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 13; j++) {
        sampleBoard[i][j] = new Tile();
        if (i == playerX && j == playerY) {
          sampleBoard[i][j].addObject(new Player(i, j));
        } else if (i % 2 == 0 && j % 2 == 0) {
          sampleBoard[i][j].addObject(new Wall(i, j));
        }
      }
    }
  }

  /**
   * Renders the initial state of the game board for testing.
   */
  private void renderInitialState() {
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 13; j++) {
        tileRenderer.renderTile(sampleBoard[i][j], i * 64, j * 64);
      }
    }
  }
}