package nz.ac.wgtn.swen225.lc.app;
import nz.ac.wgtn.swen225.lc.renderer.SoundLoader;
import nz.ac.wgtn.swen225.lc.renderer.SoundEventListener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nz.ac.wgtn.swen225.lc.renderer.Renderer;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.recorder.Recorder;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.recorder.Move;
import nz.ac.wgtn.swen225.lc.renderer.TransitionRenderer;


/**
 * @author Harry Booth-Beach 300614975
 */
public class Main extends Application {

  private static int playerX = 4;
  private static int playerY = 4;
  private static Renderer renderer;
  private Tile[][] sampleBoard;
  private Recorder recorder;
  private boolean recording = false;
  private static Map map;
  //Items that can be picked up
  private List <Tile> pickupableObjects = new ArrayList <Tile>();
  //Items that have been picked up
  private List <TileObject> pickedUpObjects = new ArrayList <TileObject>();
  //check if game has been reset
  private boolean reset = false;
  private Player p;
  int currentLevel = 1;
  //Variables for sound
  private SoundLoader soundLoader;
  private GameEventListener soundEventListener;
  // Timer class instance
  private Timer timer;
  private int pausedTime = 0;
  // Transitions
  private TransitionRenderer transitionRenderer;
  
  /**
   * Sets up the start of the game
   */
  @Override
  public void start(Stage primaryStage) {
    try {
      GameLevelLoader.resetLevel(1);
      GameLevelLoader.resetLevel(2);
      renderer = new Renderer(576, 576);
      recorder = new Recorder(this);
      intializeBoard();
      BorderPane root = new BorderPane();
      root.setCenter(renderer.getCanvas());
      root.getChildren().add(transitionRenderer.getTransitionPane());

      Scene scene = new Scene(root, 576, 576);
      scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

      primaryStage.setOnCloseRequest(event -> {
        try {
          recorder.stopRecording();
          timer.interrupt();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      
      primaryStage.setScene(scene);
      primaryStage.setTitle("Larry Croft's Adventures");
      primaryStage.show();
      timer = new Timer(map);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  /**
   * Initializes the board
   */
  public void intializeBoard() {
    
    try {
      map = GameLevelLoader.loadLevel(currentLevel);
      int cols = map.getCols();
      int rows = map.getRows();
      
      findPlayer();
      GraphicsContext graphicsContext = renderer.getCanvas().getGraphicsContext2D();
      transitionRenderer = new TransitionRenderer(renderer.getCanvas(), graphicsContext, 64);

      transitionRenderer.fadeInTransition(1.0);
  
      renderer.renderBoard(map.getMap(), playerX, playerY);
      List <Tile> keys = Arrays.stream(map.getMap())
      .flatMap(Arrays::stream)
      .filter(x -> x.getObjects().stream()
      .anyMatch(obj -> obj instanceof Key))
      .collect(Collectors.toList());
      List <Tile> treasures = Arrays.stream(map.getMap())
          .flatMap(Arrays::stream)
          .filter(x -> x.getObjects().stream()
          .anyMatch(obj -> obj instanceof Treasure))
          .collect(Collectors.toList());
      List <Tile> lockedDoors = Arrays.stream(map.getMap())
          .flatMap(Arrays::stream)
          .filter(x -> x.getObjects().stream()
          .anyMatch(obj -> obj instanceof LockedDoor))
          .collect(Collectors.toList());
      pickupableObjects.addAll(treasures);
      pickupableObjects.addAll(lockedDoors);
      pickupableObjects.addAll(keys);
    }catch (Exception e) {
      e.printStackTrace();
    };
    
  }
  
  /**
   * Find where the player is on the map
   */
  public void findPlayer() {
    // iterate through map and try find player
    for (int c=0; c<map.getCols(); c++) {
      for (int r=0; r<map.getRows(); r++) {
        if (map.getTile(c, r).hasObject()) {
          for (TileObject t : map.getTile(c, r).getObjects()) {
            if (t instanceof Player) {
              playerX = c;
              playerY = r;
              soundLoader = new SoundLoader();
              soundLoader.setVolume(-20.0f); // set volume
              soundEventListener = new SoundEventListener(soundLoader);
              ((Player) t).addEventListener(soundEventListener);

              if (currentLevel == 2) {
                playerX = 1;
                playerY = 5;
              }
              p = (Player)t;
              p.setPosition(p.getY(), p.getX());
            }
          }
        }
      }
    }
  }
  
  private boolean autoReplay = false;
  private boolean stepByStepReplay = false;
  private List<Move> moves;
  private String direction = "Right";
  /**Handles key presses.
   * @param code
   */
  public void handleKeyPress(KeyCode code) {
    int newX = playerX;
    int newY = playerY;
    if (autoReplay) {
      switch (code) {
      case DIGIT1:
        recorder.setSpeed(1);
        recorder.changeSpeed(moves);
        break;
      case DIGIT2:
        recorder.setSpeed(2);
        recorder.changeSpeed(moves);
        break;
      case DIGIT3:
        recorder.setSpeed(3);
        recorder.changeSpeed(moves);
        break;
      case DIGIT4:
        recorder.setSpeed(4);
        recorder.changeSpeed(moves);
        break;
      case DIGIT5:
        recorder.setSpeed(5);
        recorder.changeSpeed(moves);
        break;
      }
    }
    switch (code) {
    
    case UP:
      if (!timer.isPaused()) {
        newY = playerY - 1;
        direction = "Up";
      }
      break;
    case DOWN:
      if (!timer.isPaused()) {
        newY = playerY + 1;
        direction = "Down";
      }
      break;
    case LEFT:
      if (!timer.isPaused()) {
        newX = playerX - 1;
        direction = "Left";
      }
      break;
    case RIGHT:
      if (!timer.isPaused()) {
        newX = playerX + 1;
        direction = "Right";
      }
      break;
    case SPACE:
      pausedTime = timer.pause();
      Information.display("Game is paused", "Close this and then press 'Esc' to resume game.");
      break;
    case ESCAPE:
      timer.resume();
      timer = new Timer(map, pausedTime);
      break;
    case R:
      
      if (recording) {
        System.out.println("Recording button has already been pressed");
      }
      if (autoReplay || stepByStepReplay) {
        System.out.println("Currently replaying, you cannot record while replaying");
      }
      else {
        recorder.setReplayFinished(false);
        java.util.Map <String, List<Integer>> itemCoordinates = new HashMap<String, List<Integer>>();
        recorder.updateGameState(currentLevel, map.getCols(), map.getRows(), itemCoordinates);
        if (recorder.getLastSavedLevel() == 2 ) {
          //pickedUpObjects.clear();
        }
      //save the current state
        for (int c=0; c<map.getCols(); c++) {
          for (int r=0; r<map.getRows(); r++) {
            if (map.getTile(c, r).hasObject()) {
              for (TileObject t : map.getTile(c, r).getObjects()) {
                if (t instanceof Player) {
                  ((Player) t).setPosition(c, r);
                  GameStateManager.updatePlayerPos((Player)t, recorder.getLastSavedLevel());
                }
              }
            }
          }
        }
        for (TileObject t: pickedUpObjects) {
          map.removeObject(t);
          GameStateManager.updateRemovedObject(t, recorder.getLastSavedLevel());
        }
        recorder.startRecording(playerX, playerY, List.of());
      }
      break;
    case S:
    try {
      
      recorder.stopRecording();
    } catch (IOException e) {
      e.printStackTrace();
    }
    break;
    case L:
      if (recording || autoReplay || stepByStepReplay) {
        System.out.println("Please wait for recording or replay to finish to load data");
      
      }
      else {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        moves = recorder.load(file);
        try {
          //load the gamestate when record button was first pressed
          currentLevel = recorder.getLastSavedLevel();
          map = GameLevelLoader.loadLevel(recorder.getLastSavedLevel());
          for (int c=0; c<map.getCols(); c++) {
            for (int r=0; r<map.getRows(); r++) {
              if (map.getTile(c, r).hasObject()) {
                for (TileObject t : map.getTile(c, r).getObjects()) {
                  if (t instanceof Player) {
                    for (TileObject item: pickedUpObjects) {
                      if (item instanceof Key) {
                
                        ((Player) t).addKey((Key)item);
                        p = ((Player) t);
                      }
                    }
                    playerX = r;
                    playerY = c;
                  }
                }
              }
            }
          }
          for (Tile t: pickupableObjects) {
            List <TileObject> tileObject =t.getObjects();
            for (TileObject item: tileObject) {
              if (item.getY() == newX && item.getX() == newY) {
                if (item instanceof LockedDoor) {
                  for (int i = pickedUpObjects.size()-1; i>=0; i--) {
                    if (pickedUpObjects.get(i) instanceof Key) {
                      if (((Key)pickedUpObjects.get(i)).getColor().equals(((LockedDoor)item).getColor())) {
                        pickedUpObjects.add(item);
                        
                      }
                    }
                  }
                  
                }
                else {
                  pickedUpObjects.add(item);
                }
                
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
        renderer.renderBoard(map.getMap(), playerX, playerY);
      }
      break;
    //AutoReplay
    case A:
      if (!recorder.getDataLoaded()) {
        System.out.println("Data is not loaded, please press Load button to load data if replay has finished");
        break;
      }
      stepByStepReplay = false;
      if (!autoReplay) {
        //autoReplay = true;
        recorder.replay(moves);
      }
      else {
        System.out.println("already replaying");
      }
      break;
    //Step by Step replay
    case B:
     recorder.stepByStep(moves);

      break;
    default:
      return;
    }
    for (Tile t: pickupableObjects) {
      List <TileObject> tileObject =t.getObjects();
      for (TileObject item: tileObject) {
        if (item.getY() == newX && item.getX() == newY) {
          if (item instanceof LockedDoor) {
            for (int i = pickedUpObjects.size()-1; i>=0; i--) {
              if (pickedUpObjects.get(i) instanceof Key) {
                if (((Key)pickedUpObjects.get(i)).getColor().equals(((LockedDoor)item).getColor())) {
                  pickedUpObjects.add(item);
                  
                }
              }
            }
            
          }
          else {
            System.out.println(item.getClass() + "this is type of waht object was picked" + currentLevel);
            pickedUpObjects.add(item);
          }
          
        }
      }
    }
    if (code == KeyCode.UP || code == KeyCode.DOWN || code == KeyCode.LEFT || code == KeyCode.RIGHT) {
      move(newX, newY);
    }
    
    

    
//    if (recorder.getReplayFinished()) {
//      p.getKeys().clear();
//      recorder.setReplayFinished(false);
//    }
  }  
  
  /**
   * Method for moving the player
   * @param newX 
   * @param newY 
   */
  public void move(int newX, int newY) {
   
    TileObject player = map.getTile(playerY, playerX).getObjects().stream().filter(obj -> obj instanceof Player)
        .findFirst().orElse(null);
    if (map.isMoveLegal(newY, newX, player)) {
      
      // Check if player is Dead
      if (((Player) player).isDead()) {
        GameStateManager.setCurrentLevel(GameStateManager.getCurrentLevel());
        GameLevelLoader.resetLevel(GameStateManager.getCurrentLevel());
        timer.resetTimer();
        reset = true;
        pickedUpObjects.clear();
        intializeBoard();
        Information.display("You Died!", "Get better");
        ((Player) player).setAlive(true);
      }
      
      if (map.getTile(newY, newX).hasObject()) {
        map.getTile(newY, newX).getFirstObject().playerInteraction((Player) player);
        
        // Check for Exit interaction
        if(map.getTile(newY, newX).getObjects().stream().anyMatch(obj -> obj instanceof Exit)) {
            if(!Map.treasuresLeft()) {
                // No more treasures left. Player can exit.
                // Load level 2
                this.transitionRenderer.fadeInTransition(1.0);
                try {
                    reset = true;
                    currentLevel++;
                    pickedUpObjects.clear();
                    //remove keys
                    for (int c=0; c<map.getCols(); c++) {
                      for (int r=0; r<map.getRows(); r++) {
                        if (map.getTile(c, r).hasObject()) {
                          for (TileObject t : map.getTile(c, r).getObjects()) {
                            if (t instanceof Player) {
                              List <Key> keys = ((Player) t).getKeys();
                              keys.clear();
                            }
                          }
                        }
                      }
                    }
                    recorder.stopRecording();
         
                    GameLevelLoader.resetLevel(2); // Resetting level 2
                    //map = GameLevelLoader.loadLevel(2); // Loading level 2
                    intializeBoard(); // Reinitialize the board with the new level
                    
                } catch(Exception e) {
                    e.printStackTrace();
                }
            } else {
                // There are still treasures left. Notify the player.
                // play a sound effect the player cannot exit yet etc
            }
        }
        
      }

      map.getTile(playerY, playerX).removeObject(player);

      if (!map.getTile(newY,  newX).hasObject() || !(map.getTile(newY, newX).getFirstObject() instanceof Teleporter)) {
        playerX = newX;
        playerY = newY;
      } else {
        Teleporter teleporter = (Teleporter) map.getTile(newY, newX).getFirstObject();
        playerX = teleporter.getPartner().getY();
        playerY = teleporter.getPartner().getX();
      }


      map.getTile(playerY, playerX).addObject(player);
      //if (!reset) {
        try {
          recorder.record(playerX, playerY, new ArrayList<Integer>(), direction);
        } catch (IOException e) {
          e.printStackTrace();
        }
      //}
    }
    renderer.renderBoard(map.getMap(), playerX, playerY);
  }
  
  /**
   * Static method which can be called by other classes to render game
   */
  public static void render() {
    try {
      renderer.renderBoard(map.getMap(), playerX, playerY);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** Gets recording status
   * @return recording
   */
  public boolean isRecording() {
    return recording;
  }
  
  /** Gets stepbystep replay status
   * @return recording
   */
  public boolean isStepByStepReplay() {
    return stepByStepReplay;
  }
  
  /** Sets auto replay status
   * @param status
   */
  public void setAutoReplay(boolean status) {
    autoReplay = status;
  }
  
  /** Sets stepbystep replay status
   * @param status
   */
  public void setStepByStepReplay(boolean status) {
    stepByStepReplay = status;
  }
  
  /** Sets recording status
   * @param status
   */
  public void setRecording(boolean status) {
    recording = status;
  }
  
  /** Gets auto replay status
   * @return recording
   */
  public boolean isAutoReplay() {
    return stepByStepReplay;
  }
  
  /**
   * @return playerX
   * 
   */
  public int getPlayerX() {
    return playerX;
  }
  
  /**
   * @return playerY
   */
  public int getPlayerY() {
    return playerY;
  }
  /**
   * @param x
   */
  public void setPlayerX(int x) {
    playerX = x;
  }
  /**
   * @param y
   */
  public void setPlayerY(int y) {
    playerY = y;
  }
  /**
   * @return direction
   */
  public String getDirection() {
    return direction;
  }
  /**
   * @return recorder
   */
  public Recorder getRecorder() {
    return recorder;
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}