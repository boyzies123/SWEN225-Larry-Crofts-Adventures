package nz.ac.wgtn.swen225.lc.recorder;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import nz.ac.wgtn.swen225.lc.app.*;





/**
 * Recorder class for recording games. This class handles the recording 
 * feature of Larry Crofts Adventures. It is the concrete subject.
 *
 * @author Yi Chen 300618186
 */
public class Recorder {
  String fileName;
  private Main app;
  //contains a list of moves. Each list represents an single movement featuring all enemies. 
  List <Move>gameData = new ArrayList <Move>();
  //contains a list of moves, specifically movements of enemy.
  List <Move>enemyMoves = new ArrayList <Move>();
  //Speed for replaying purposes in milliseconds.
  private int speed = 1000;
  private int[] executedAmount = {0};
  private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  //For determining if data has been loaded
  //Note: Everytime a replay finishes, data must be loaded again.
  private boolean dataLoaded = false;
  private ScheduledExecutorService executorService;
  //Level of which recording was last saved in.
  private int lastSavedLevel = 1;
  //Has the replay finished
  private boolean finished = false;
  /**
   * Constructor initializes the Recorder object which takes in 
   * app as a parameter. The purpose is to know recording and replaying status.
   * @param app
   */
  public Recorder(Main app) {
    //Precondition: App is not null
    if (app == null) {
      throw new IllegalArgumentException("App should not be null");
    }
    this.app = app;
    
  }
  
  /**
   * Every second, move of player and enemies will be recorded
   * @param playerX The x coordinate of player
   * @param playerY The y coordinate of player
   * @param enemyCoordinates The coordinates of enemy
   */
  public void startRecording(int playerX, int playerY, List <Integer> enemyCoordinates) {
    app.setRecording(true);
    scheduler.scheduleAtFixedRate(()->{
      try {
        record(app.getPlayerX(), app.getPlayerY(), List.of(), app.getDirection());
      }catch (IOException e) {
        e.printStackTrace();
      }
      },0, 1000, TimeUnit.MILLISECONDS);
    
  }
  /**
   * The record method starts recording movements of both players and enemies
   * when the record button is pressed.
   * 
   * @param x The x coordinate of player
   * @param y The y coordinate of player
   * @param enemyCoordinates Coordinates of enemy
   * @param direction The direction of player
   * @throws JsonProcessingException 
   * @throws IOException 
   */
  public void record(int x, int y, List <Integer> enemyCoordinates, String direction) throws JsonProcessingException, IOException{
    //Precondition: enemyCoordinates must be even and must not be empty
//    if (enemyCoordinates.size()%2!=0 || enemyCoordinates.isEmpty()) {
//      throw new IllegalArgumentException("Size of enemyCoordinates is not correct");
//    }
    //Precondition: Coordinates are not negative
    for (int i = 0; i < enemyCoordinates.size(); i=i+2) {
      if (enemyCoordinates.get(i) < 0 && enemyCoordinates.get(i+1) < 0) {
        throw new IllegalArgumentException("Coordinates should not be negative");
      }
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Coordinates should not be negative");
    }
    if (app.isRecording()) {
      ObjectMapper mapper = new ObjectMapper();
      //add move of player
      Move move = new Move(x, y, "Player", direction);
      gameData.add(move);
      //Fake enemies. If real enemies end up being implemented then this will add them.
      gameData.add(new Move(5, 9, "Enemy", "Down"));
      gameData.add(new Move(5, 10, "Enemy", "Down"));

    }
    
    
  }
  
  /**
   * Stops recording when the stop record button is pressed
   * @throws JsonProcessingException If an issue arises from processing the json data
   * @throws IOException 
   * 
   */
  public void stopRecording() throws JsonProcessingException, IOException{
    //only allow recording to stop when recording is actually happening
    //in the first place
    if (app.isRecording()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(new File("Recorded data.json"), gameData);
      gameData.clear();
      //Shut down scheduler so movements are no longer recorded.
      //But create a new one so recording can continue the next time
      //record button is pressed.
      scheduler.shutdown();
      scheduler = Executors.newScheduledThreadPool(1);
      app.setRecording(false);
    }
    else {
      System.out.println("No game is being recorded!");
    }
    
  }
  /**
   * Load the json data file and displays the first saved position.
   * @param file The file obtained from file chooser
   * @return moves Moves loaded from the file
   */
  public List <Move> load(File file) {
    ObjectMapper mapper = new ObjectMapper();
    Scanner sc;
    String data = "";
    try {
      sc = new Scanner(file);
      while (sc.hasNextLine()) {
        data = sc.nextLine();
      }

      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    try {
      gameData = mapper.readValue(data, new TypeReference<List<Move>>() {});
      //PostCondition: gamedata should not return null
      if (gameData == null) {
        throw new IllegalStateException("Gamedata is null");
      }

      
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    dataLoaded = true;
    return gameData;
  }
  /**
   * Goes through the replay step by step
   * @param moves Moves of players and enemies
   */
  public void stepByStep(List <Move> moves) {
    //Precondition: Moves should not be null
    if (moves == null) {
      throw new IllegalArgumentException("Moves should not be null");
    }
    if (enemyMoves.isEmpty()) {
      findEnemyMoves(moves);
    }
    app.setAutoReplay(false);
    app.setStepByStepReplay(true);
    if (!enemyMoves.isEmpty()) {
      int enemyMovesSize = enemyMoves.size();

      for (int a = enemyMoves.size()-1; a > enemyMovesSize-1-2; a--) {
        enemyMoves.remove(a);
        
      }
    }
    if (!moves.isEmpty()) {
      Move m = moves.get(0);
      moves.remove(0);
      app.move(m.getX(), m.getY());
      executedAmount[0]++;

       
    }
    if (moves.isEmpty()) {
      gameData.clear();
      app.setAutoReplay(false);
      app.setStepByStepReplay(false);
      dataLoaded = false;
      enemyMoves.clear();
      System.out.println("Finished replay");
    }
  }
  /**
   * Replay the game
   * @param moves Moves of players and enemies
   *
   */
  public void replay(List <Move> moves) {
    app.setAutoReplay(true);
    //Invariant : Auto replay status and step by step replay status should never both be true
    if (app.isStepByStepReplay() && app.isAutoReplay()) {
      throw new IllegalStateException("Step by Step replay and Auto Replay should not be both true at the same time");
    }
    //Precondition: moves must not be null
    //Precondition: moves must not be empty
    if (moves.isEmpty()) {
      throw new IllegalArgumentException("Moves must not be empty");
    }
    //Loop over the list of moves and get all the enemy moves in one list
    if (enemyMoves.isEmpty()) {
      findEnemyMoves(moves);
    }
    Collections.reverse(enemyMoves);
    //temp variable enemySize
    int enemySize = 2;
    //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService = Executors.newSingleThreadScheduledExecutor();
    executedAmount[0] = 0;
    final int movesSize = moves.size();
    for (int i = 0; i < moves.size(); i++) {
        Move m = moves.get(i);
        int delay = i * speed; // 1000 milliseconds between each move
        //every 1000 milliseconds a task will be executed
        executorService.schedule(() -> {
            Platform.runLater(() -> {
              if (!stopAutoReplay(executorService, moves)) {
                app.move(m.getX(), m.getY());
                executedAmount[0]++;
                moves.remove(m);
                //Move enemies here
                int enemyMovesSize = enemyMoves.size();
                if (!enemyMoves.isEmpty()) {
                  for (int a = enemyMoves.size()-1; a > enemyMovesSize-1-enemySize; a--) {
                    enemyMoves.remove(a);
                  }
                }
                //Replay is finished
                if (executedAmount[0] == movesSize) {
                  gameData.clear();
                  app.setAutoReplay(false);
                  app.setStepByStepReplay(false);
                  executedAmount[0] = 0;
                  dataLoaded = false;
                  enemyMoves.clear();
                  finished = true;
                }
              }
                
                
             
            });
        }, delay, TimeUnit.MILLISECONDS);
        
        
    }


  }
  
  /**
   * Update the current game state so that recording can replay from this state
   * @param currentLevel The current level when record key is pressed
   * @param cols Number of columns for the map
   * @param rows Number of rows for the map
   * @param itemCoordinates Map of items that are to be removed
   * @return coordinates
   */
  public List <Integer>updateGameState(int currentLevel, int cols, int rows, java.util.Map <String, List<Integer>> itemCoordinates) {
    lastSavedLevel = currentLevel;
    List <Integer> coordinates = new ArrayList <Integer>();
    return coordinates;
  }
  /**
   * Shutsdown current executorService and create a new one,
   * so that the new speed can be applied.
   * @param moves Moves of players and enemies
   */
  public void changeSpeed(List<Move> moves) {
    if (executorService!=null) {
      executorService.shutdownNow();
      executorService = Executors.newSingleThreadScheduledExecutor();
      replay(moves);
    }
    
    
  }
  /**
   * If the step by step button is pressed, then auto replay should stop.
   * @param executorService To stop the replay
   * @param moves Moves of player and enemies
   * @return status Whether or not auto replay should stop or continue
   */
  public boolean stopAutoReplay(ScheduledExecutorService executorService, List<Move>moves) {
    if (app.isStepByStepReplay()) {
      executorService.shutdownNow();
      Platform.runLater(() -> {}); //stop the execution of tasks since auto replay is not what we want
      System.out.println("Auto replay stopping");
      return true;
    }
    return false;
  }
  
  /**
   * Set the replay speed. The maximum allowed will be 5 times.
   * @param speed Speed of replay that should be set
   */
  public void setSpeed(int speed) {
    //Precondition: Speed must be positive
    if (speed < 0) {
      throw new IllegalArgumentException("Speed should not be negative");
    }
    //Precondition: Speed should be between 1 and 5
    if (speed > 5) {
      throw new IllegalArgumentException("Speed is too large");
    }
    this.speed = 1000/speed;
  }
  /**
   * Get the replay speed
   * @return speed Speed of current recording
   */
  public Integer getSpeed() {
    return speed;
  }
  /**
   * Get the list of moves
   * @return gameData All moves of players and enemies
   */
  public List <Move> getGameData(){
    return gameData;
  }
  /**
   * From the list of moves, add all moves done by the enemy
   * and add them to a list
   * @param moves Moves of both player and enemies
   */
  public void findEnemyMoves(List<Move> moves) {
    for (int i = moves.size()-1; i >= 0; i--) {
      if (moves.get(i).getCharacter() != null) {
        if (moves.get(i).getCharacter().equals("Enemy")) {
          enemyMoves.add(moves.get(i));
          moves.remove(i);
        }
      }
    }
  }
  /**
   * Get the list of moves done by enemies
   * @param moves Moves of both player and enemy
   * @return enemyMoves Moves of enemy
   */
 public List <Move> getEnemyMoves(List <Move> moves){
   return enemyMoves;
 }
 /**
 * Returns whether data has been loaded or not.
 * @return dataLoaded The boolean status for whether data has been loaded
 */
 public boolean getDataLoaded() {
   return dataLoaded;
 }
 /**
  * Returns the level of recording
  * @return lastSavedlevel The level of the recording
  */
 public int getLastSavedLevel() {
   return lastSavedLevel;
 }
 /**
  * Returns whether replay has finished
  * @return finished Status of replay
  */
 public boolean getReplayFinished() {
   return finished;
 }
 /**
  * Set replay finished status
  * @param status Status to be set
  */
 public void setReplayFinished(boolean status) {
   finished = status;
 }
}
