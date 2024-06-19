package test.nz.ac.wgtn.swen225.lc.renderer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.domain.Exit;
import nz.ac.wgtn.swen225.lc.domain.ExitLock;
import nz.ac.wgtn.swen225.lc.domain.InfoField;
import nz.ac.wgtn.swen225.lc.domain.Key;
import nz.ac.wgtn.swen225.lc.domain.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile;
import nz.ac.wgtn.swen225.lc.domain.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Wall;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;

/**
 * A test class for visual rendering, primarily used to test the
 * {@link Renderer} functionality. Demonstrates the rendering of various game
 * tiles on a JavaFX canvas.
 *
 * @author Dillon Sykes 300196292
 */
public class TileRendererTest extends Application {

  /**
   * Entry point of the application.
   *
   * @param args command-line arguments; not used.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Starts the JavaFX application, initializes the renderer, and demonstrates the
   * rendering of various game tiles by initializing and rendering Tile objects.
   *
   * @param primaryStage the primary stage for this application.
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      Renderer renderer = new Renderer(700, 200);

      Tile wallTile = new Tile();
      wallTile.addObject(new Wall(20, 20));
      renderer.renderTile(wallTile, 20, 20);

      Tile freeTile = new Tile();
      renderer.renderTile(freeTile, 120, 20);

      Tile treasureTile = new Tile();
      treasureTile.addObject(new Treasure(220, 20));
      renderer.renderTile(treasureTile, 220, 20);

      Tile playerTile = new Tile();
      playerTile.addObject(new Player(320, 20));
      renderer.renderTile(playerTile, 320, 20);

      Tile keyRedTile = new Tile();
      keyRedTile.addObject(new Key(420, 20, Key.Color.RED));
      renderer.renderTile(keyRedTile, 420, 20);

      Tile keyBlueTile = new Tile();
      keyBlueTile.addObject(new Key(520, 20, Key.Color.BLUE));
      renderer.renderTile(keyBlueTile, 520, 20);

      Tile lockedDoorRedTile = new Tile();
      lockedDoorRedTile.addObject(new LockedDoor(620, 20, Key.Color.RED));
      renderer.renderTile(lockedDoorRedTile, 620, 20);

      Tile lockedDoorBlueTile = new Tile();
      lockedDoorBlueTile.addObject(new LockedDoor(20, 110, Key.Color.BLUE));
      renderer.renderTile(lockedDoorBlueTile, 20, 110);

      Tile infoFieldTile = new Tile();
      infoFieldTile.addObject(new InfoField(120, 110));
      renderer.renderTile(infoFieldTile, 120, 110);

      Tile exitLockTile = new Tile();
      exitLockTile.addObject(new ExitLock(220, 110));
      renderer.renderTile(exitLockTile, 220, 110);

      Tile exitTile = new Tile();
      exitTile.addObject(new Exit(320, 110));
      renderer.renderTile(exitTile, 320, 110);

      BorderPane root = new BorderPane();
      root.setCenter(renderer.getCanvas());

      Scene scene = new Scene(root, 700, 200);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Renderer Test");
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}