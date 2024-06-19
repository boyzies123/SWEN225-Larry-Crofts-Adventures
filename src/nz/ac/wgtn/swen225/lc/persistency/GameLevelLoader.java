package nz.ac.wgtn.swen225.lc.persistency;
import org.json.*;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * GameLevelLoader class for loading levels from JSON files.
 * Handles the loading of levels by converting JSON objects into
 * a Map of tiles and the objects on those tiles.
 * Also has the ability to reset levels back to their staring state
 * i.e. when the player wants to start a new game.
 * 
 * @author James McKenzie 300619135
 *
 */
public class GameLevelLoader {
  
  // GameLevelLoader fields
  
  /**
   * Loads a level given a level number.
   * Reads JSON data corresponding to the level number,
   * and parses it into the corresponding Domain objects.
   * Returns a Map object that contains this data.
   * Currently just returns a Map with the specified dimensions;
   * will complete this later.
   * @param levelNumber
   * @return Map
   * @throws IOException
   */
  public static Map loadLevel(int levelNumber) throws IOException {
    String fileName = "levels/level" + levelNumber + ".json";
    try {
      String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
      JSONObject o = new JSONObject(jsonContents);
      JSONObject dimensions = o.getJSONObject("dimensions");
      int rows = dimensions.getInt("rows");
      int columns = dimensions.getInt("columns");
      Map level = new Map(rows, columns);
      Tile[][] board = new Tile[rows][columns];
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          board[i][j] = new Tile();
        }
      }
      level.setMap(board);
      
      // Add the walls - this was definitely the most tedious bit
      JSONArray walls = o.getJSONArray("walls");
      for (int i = 0; i < walls.length(); i++) {
        JSONObject wallSet = walls.getJSONObject(i);
        int x1 = wallSet.getInt("x1");
        int x2 = wallSet.getInt("x2");
        int y1 = wallSet.getInt("y1");
        int y2 = wallSet.getInt("y2");
        
        for (int x = x1; x <= x2; x++) {
          for (int y = y1; y <= y2; y++) {
            level.getTile(x, y).addObject(new Wall(x,y));
          }
        }
        
      }
      
      // Add the treasures
      JSONArray treasures = o.getJSONArray("treasures");
      for (int i = 0; i < treasures.length(); i++) {
        JSONObject treasurePos = treasures.getJSONObject(i);
        if (!treasurePos.isNull("x") && !treasurePos.isNull("y")) {
          int x = treasurePos.getInt("x");
          int y = treasurePos.getInt("y");
          level.getTile(x, y).addObject(new Treasure(x,y));
        }
        
      }
      
      // Add the keys and their associated locks
      JSONArray lockedDoors = o.getJSONArray("locked_doors");
      for (int i = 0; i < lockedDoors.length(); i++) {
        JSONObject doorPos = lockedDoors.getJSONObject(i);
        if (!doorPos.isNull("x") && !doorPos.isNull("y")) {
          int x = doorPos.getInt("x");
          int y = doorPos.getInt("y");
          String colour = doorPos.getString("colour");
          if (colour.equals("red")) {level.getTile(x, y).addObject(new LockedDoor(x,y, Key.Color.RED));}
          if (colour.equals("blue")) {level.getTile(x, y).addObject(new LockedDoor(x,y, Key.Color.BLUE));}
        }
        
      }
      
      JSONArray keys = o.getJSONArray("keys");
      for (int i = 0; i < keys.length(); i++) {
        JSONObject keyPos = keys.getJSONObject(i);
        if (!keyPos.isNull("x") && !keyPos.isNull("y")) {
          int x = keyPos.getInt("x");
          int y = keyPos.getInt("y");
          String colour = keyPos.getString("colour");
          if (colour.equals("red")) {level.getTile(x, y).addObject(new Key(x,y, Key.Color.RED));}
          if (colour.equals("blue")) {level.getTile(x, y).addObject(new Key(x,y, Key.Color.BLUE));}
        }
        
      }
      
      // Add the player, info field, exit lock and exit
      JSONObject chap = o.getJSONObject("chap");
      int chapx = chap.getInt("x");
      int chapy = chap.getInt("y");
      Player player = new Player(chapx, chapy);
      int treasuresInInventory = o.getInt("treasures_in_inventory");
      if (treasuresInInventory > 0){
        for (int i = 0; i <= treasuresInInventory; i++) {
          player.incremeantTreasure();
        }
      }
      ArrayList<Key> keysInInventory = GameStateManager.getKeysInPlayerInventory();
      int numKeys = o.getInt("keys_in_inventory");
      if (!(keysInInventory.isEmpty()) && numKeys > 0) {
        for (Key key : keysInInventory) {
          player.addKey(key);
        }
      }
      
      boolean hasBoots = o.getBoolean("player_has_boots");
      if (hasBoots) {player.giveBoots();}
      GameStateManager.setPlayer(player);
      level.getTile(chapx, chapy).addObject(player);
      
      
      
      JSONObject infoField = o.getJSONObject("info_field");
      int infox = infoField.getInt("x");
      int infoy = infoField.getInt("y");
      level.getTile(infox, infoy).addObject(new InfoField(infox, infoy));
      
      JSONObject exitLock = o.getJSONObject("exit_lock");
      int lockx = exitLock.getInt("x");
      int locky = exitLock.getInt("y");
      level.getTile(lockx, locky).addObject(new ExitLock(lockx, locky));
      
      JSONObject exit = o.getJSONObject("exit");
      int exitx = exit.getInt("x");
      int exity = exit.getInt("y");
      level.getTile(exitx, exity).addObject(new Exit(exitx, exity));
      
      // Add any boots, fire, enemies and teleporters (if present)
      
      if (!o.isNull("boots")) {
        JSONObject boots = o.getJSONObject("boots");
        int bootsx = boots.getInt("x");
        int bootsy = boots.getInt("y");
        level.getTile(bootsx, bootsy).addObject(new Boots(bootsx, bootsy));
      }
      
      if (!o.isNull("enemy")) {
        JSONObject enemies = o.getJSONObject("enemy");
        int enemyx = enemies.getInt("x");
        int enemyy = enemies.getInt("y");
        level.getTile(enemyx, enemyy).addObject(new Enemy(enemyx, enemyy));
      }
      
      if (!o.isNull("fire")) {
        JSONArray fire = o.getJSONArray("fire");
        for (int i = 0; i < fire.length(); i++) {
          JSONObject firePos = fire.getJSONObject(i);
          int x = firePos.getInt("x");
          int y = firePos.getInt("y");
          level.getTile(x, y).addObject(new Fire(x,y));
          
        }
      }
      
      if (!o.isNull("teleporter")) {
        JSONArray teleporters = o.getJSONArray("teleporter");
        // Make sure the number of teleporters is even
        if ((teleporters.length() % 2) == 0) {
          for (int i = 0; i < teleporters.length(); i+=2) {
            JSONObject telepPos1 = teleporters.getJSONObject(i);
            JSONObject telepPos2 = teleporters.getJSONObject(i+1);
            int x1 = telepPos1.getInt("x");
            int y1 = telepPos1.getInt("y");
            int x2 = telepPos2.getInt("x");
            int y2 = telepPos2.getInt("y");
            Teleporter telep1 = new Teleporter(x1,y1);
            Teleporter telep2 = new Teleporter(x2,y2);
            telep1.setPartner(telep2);
            telep2.setPartner(telep1);
            level.getTile(x1, y1).addObject(telep1);
            level.getTile(x2, y2).addObject(telep2);
            
          }
        }
        
        
        
      }
      
      return level;
    } catch(IOException e) {
      e.printStackTrace();
    }
    // Return empty map in case of IO failure
    return new Map(13, 13);
  }
  
  /**
   * For getting the time limit (in seconds) of the current level.
   * @param levelNumber 
   * 
   * 
   * @return the current level's time limit (in seconds)
   */
  public static int getTimeLimit(int levelNumber) {
    String fileName = "levels/level" + levelNumber + ".json";
    try {
      String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
      JSONObject o = new JSONObject(jsonContents);
      int time = o.getInt("time");
      return time;
    } catch (IOException e) {
      e.printStackTrace();
      return 60;
    }
    
  }
  
  /**
   * For when the player wants to reset the level.
   * Takes the current state of level's json file and resets it
   * to its starting state (as defined in the level's corresponding
   * "pure" json file).
   * 
   * @param levelNumber
   */
  public static void resetLevel(int levelNumber) {
    String fromFile = "levels/level" + levelNumber + "pure.json";
    String toFile = "levels/level" + levelNumber + ".json";
    
    try {
      String levelContents = new String(Files.readAllBytes(Paths.get(toFile)), StandardCharsets.UTF_8);
      String pureContents = new String(Files.readAllBytes(Paths.get(fromFile)), StandardCharsets.UTF_8);
      JSONObject toReset = new JSONObject(levelContents);
      JSONObject pure = new JSONObject(pureContents);
      
      // Reset the file
      try (FileWriter file = new FileWriter(toFile)) 
      {
          file.write(pure.toString());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
