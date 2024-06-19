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
 * GameStateManager
 * Consists of a series of static methods which update the state of the current level's
 * JSON file - which method you use depends on what has been changed.
 * @author James McKenzie 300619135
 *
 */
public class GameStateManager {
  
    private static ArrayList<Key> keysInPlayerInventory = new ArrayList<Key>();
    private static Player player;
    
    /**
     * For getting the number of the current level.
     * 
     * 
     * @return the current level number
     */
    public static int getCurrentLevel() {
      String fileName = "levels/currentLevel.json";
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        int levelNum = o.getInt("currentLevel");
        return levelNum;
      } catch (IOException e) {
        e.printStackTrace();
        return 1;
      }
      
    }
    
    /**
     * For updating what the current level is.
     * 
     * @param levelNumber
     */
    public static void setCurrentLevel(int levelNumber) {
      String filename = "levels/currentLevel.json";
      
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        o.put("currentLevel", levelNumber);
        
        // Reset the file
        try (FileWriter file = new FileWriter(filename)) 
        {
            file.write(o.toString());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  
    
    /**
     * For updating the player's position.
     * 
     * @param p
     * @param levelNumber
     */
    public static void updatePlayerPos(Player p, int levelNumber) {
      String fileName = "levels/level" + levelNumber + ".json";
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        JSONObject chap = o.getJSONObject("chap");
        
        int playerCurrentX = p.getX();
        int playerCurrentY = p.getY();
        
        // Check if they are different
        if (playerCurrentX != chap.getInt("x") || playerCurrentY != chap.getInt("y")) {
          chap.put("x", playerCurrentX);
          chap.put("y", playerCurrentY);
          
          // Update the file
          try (FileWriter file = new FileWriter(fileName)) 
          {
              file.write(o.toString());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      
    }
    
    /**
     * For updating an enemy's position.
     * 
     * @param en
     * @param levelNumber
     */
    public static void updateEnemyPos(Enemy en, int levelNumber) {
      String fileName = "levels/level" + levelNumber + ".json";
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        JSONObject enemy = o.getJSONObject("enemy");
        
        int enemyCurrentX = en.getX();
        int enemyCurrentY = en.getY();
        
        
        // Check if they are different
        if (enemyCurrentX != enemy.getInt("x") || enemyCurrentY != enemy.getInt("y")) {
          enemy.put("x", enemyCurrentX);
          enemy.put("y", enemyCurrentY);
          
          // Update the file
          try (FileWriter file = new FileWriter(fileName)) 
          {
              file.write(o.toString());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      
    }
    
    /**
     * For updating the player's inventory.
     * 
     * @param p
     * @param levelNumber
     */
    public static void updatePlayerInventory(Player p, int levelNumber) {
      String fileName = "levels/level" + levelNumber + ".json";
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        int numTreasures = o.getInt("treasures_in_inventory");
        int keys = o.getInt("keys_in_inventory");
        boolean hasBoots = o.getBoolean("player_has_boots");
        
        int treasures = p.getTreasures();
        keysInPlayerInventory = p.getKeys();
        int numKeys = keysInPlayerInventory.size();
        boolean currentBootStatus = p.hasBoots();
        
        // Check if they are different
        if (treasures != numTreasures || numKeys != keys || currentBootStatus != hasBoots) {
          o.put("treasures_in_inventory", treasures);
          o.put("keys_in_inventory", numKeys);
          o.put("player_has_boots", currentBootStatus);
          
          // Update the file
          try (FileWriter file = new FileWriter(fileName)) 
          {
              file.write(o.toString());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      
    }
    
    
    /**
     * For updating the status of removable objects (treasures, keys, locked doors and boots)
     * if one has been removed.
     * @param t
     * @param levelNumber
     */
    public static void updateRemovedObject(TileObject t, int levelNumber) {
      String fileName = "levels/level" + levelNumber + ".json";
      String key = "keys"; // key by default
      
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        if (t instanceof Treasure) {key = "treasures";}
        if (t instanceof Key) {key = "keys";}
        if (t instanceof LockedDoor) {key = "locked_doors";}
        if (t instanceof Boots) {key = "boots";}
        JSONArray objects = o.getJSONArray(key);
        for (int i = 0; i < objects.length(); i++) {
            JSONObject objectPos = objects.getJSONObject(i);
            if (!objectPos.isNull("x") && !objectPos.isNull("y") && objectPos.getInt("x") == t.getX()
                    && objectPos.getInt("y") == t.getY()) {
                objects.getJSONObject(i).put("x", JSONObject.NULL);
                objects.getJSONObject(i).put("y", JSONObject.NULL);
            }
        }

        // Update the file
        try (FileWriter file = new FileWriter(fileName)) 
        {
            file.write(o.toString());
        }
      } catch(IOException e) {
        e.printStackTrace();
      }
      
    }
    
    /**
     * Set the player's starting position
     * 
     * @param p
     */
    public static void setPlayer(Player p) {
      player = p;
    }
    
    /**
     * Get the player's starting position
     * 
     * @return player
     */
    public static Player getPlayer() {
      return player;
    }
    
    
    /**
     * @param t
     * @param levelNumber
     */
    public static void updateTreasure(Treasure t, int levelNumber) {
      
      
    }
    
    /**
     * For updating the status of the Exit Lock to note its removal.
     * Not sure about the utility of this one.
     * 
     * @param el 
     * @param levelNumber
     */
    public static void updateExitLock(ExitLock el, int levelNumber) {
      String fileName = "levels/level" + levelNumber + ".json";
      try {
        String jsonContents = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(jsonContents);
        JSONObject exitLock = o.getJSONObject("exit_lock");
        
        if (!exitLock.isNull("x") && !exitLock.isNull("y") && exitLock.getInt("x") == el.getX()
            && exitLock.getInt("y") == el.getY()) {
          exitLock.put("x", JSONObject.NULL);
          exitLock.put("y", JSONObject.NULL);
          
          // Update the file
          try (FileWriter file = new FileWriter(fileName)) 
          {
              file.write(o.toString());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      
    }
    
    /**
     * Resets the game back to its starting state
     */
    public static void resetGame() {
      setCurrentLevel(1);
      GameLevelLoader.resetLevel(1);
      GameLevelLoader.resetLevel(2);
    }
    
    /**
     * For getting the current list of keys in the player's inventory
     * 
     * @return keysInPlayerInventory
     */
    public static ArrayList<Key> getKeysInPlayerInventory() {
      return keysInPlayerInventory;
    }
    
}






