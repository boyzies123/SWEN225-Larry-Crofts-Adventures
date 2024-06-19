package test.nz.ac.wgtn.swen225.lc.persistency;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

import org.junit.jupiter.api.Test;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Key.Color;


/**
 * Unit tests for the persistency module.
 * Since the methods being tested involve reading from and writing to JSON files,
 * I made two dummy files in the levels folder (level99.json and level99pure.json)
 * for the purposes of these tests.
 * 
 * @author James McKenzie 300619135
 *
 */
class PersistencyTests {
  
  @Test
  void LoadLevelTest() {
    try {
      Map map = GameLevelLoader.loadLevel(99);
      assert map.getTile(1, 1).hasObject();
      assert map.getTile(1, 1).getFirstObject() instanceof Wall;
      assert map.getTile(2, 2).hasObject();
      assert map.getTile(2, 2).getFirstObject() instanceof Treasure;
      assert map.getTile(3, 3).hasObject();
      assert map.getTile(3, 3).getFirstObject() instanceof LockedDoor;
      assert map.getTile(4, 4).hasObject();
      assert map.getTile(4, 4).getFirstObject() instanceof Key;
      assert map.getTile(5, 5).hasObject();
      assert map.getTile(5, 5).getFirstObject() instanceof InfoField;
      assert map.getTile(6, 6).hasObject();
      assert map.getTile(6, 6).getFirstObject() instanceof Player;
      Player p = (Player) map.getTile(6, 6).getFirstObject();
      assert p.hasBoots() == false;
      assert map.getTile(7, 7).hasObject();
      assert map.getTile(7, 7).getFirstObject() instanceof ExitLock;
      assert map.getTile(8, 8).hasObject();
      assert map.getTile(8, 8).getFirstObject() instanceof Exit;
      assert map.getTile(9, 9).hasObject();
      assert map.getTile(9, 9).getFirstObject() instanceof Enemy;
      assert map.getTile(10, 10).hasObject();
      assert map.getTile(10, 10).getFirstObject() instanceof Boots;
      assert map.getTile(11, 11).hasObject();
      assert map.getTile(11, 11).getFirstObject() instanceof Fire;
      assert map.getTile(7, 5).hasObject();
      assert map.getTile(7, 5).getFirstObject() instanceof Teleporter;
      assert map.getTile(3, 11).hasObject();
      assert map.getTile(3, 11).getFirstObject() instanceof Teleporter;
      
      
      int time = GameLevelLoader.getTimeLimit(99);
      assert time == 60;
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  void SaveLevelTest() {
    
    GameStateManager.setCurrentLevel(1);
    assert GameStateManager.getCurrentLevel() == 1;
    
    int newPlayerY = 7;
    Player p = new Player(6,7);
    
    GameStateManager.updatePlayerPos(p, 99);
    try {
      String contents = new String(Files.readAllBytes(Paths.get("levels/level99.json")), StandardCharsets.UTF_8);
      JSONObject o = new JSONObject(contents);
      JSONObject playerPos = o.getJSONObject("chap");
      int y = playerPos.getInt("y");
      assert y == newPlayerY;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try {
      Map m = GameLevelLoader.loadLevel(99);
      Treasure t = (Treasure) m.getTile(2, 2).getFirstObject();
      GameStateManager.updateRemovedObject(t, 99);
      try {
        String contents = new String(Files.readAllBytes(Paths.get("levels/level99.json")), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(contents);
        JSONArray treasures = o.getJSONArray("treasures");
        for (int i = 0; i < treasures.length(); i++) {
          JSONObject treasurePos = treasures.getJSONObject(i);
          assert treasurePos.isNull("x");
          assert treasurePos.isNull("y");
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try {
      Map m = GameLevelLoader.loadLevel(99);
      ExitLock t = (ExitLock) m.getTile(7, 7).getFirstObject();
      GameStateManager.updateExitLock(t, 99);
      try {
        String contents = new String(Files.readAllBytes(Paths.get("levels/level99.json")), StandardCharsets.UTF_8);
        JSONObject o = new JSONObject(contents);
        JSONObject exitLock = o.getJSONObject("exit_lock");
        assert exitLock.isNull("x");
        assert exitLock.isNull("y");
        
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    int newEnemyY = 7;
    Enemy en = new Enemy(9,7);
    
    GameStateManager.updateEnemyPos(en, 99);
    try {
      String contents = new String(Files.readAllBytes(Paths.get("levels/level99.json")), StandardCharsets.UTF_8);
      JSONObject o = new JSONObject(contents);
      JSONObject enemyPos = o.getJSONObject("enemy");
      int y = enemyPos.getInt("y");
      assert y == newEnemyY;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    p.addKey(new Key(10, 10, Color.RED));
    p.incremeantTreasure();
    
    GameStateManager.updatePlayerInventory(p, 99);
    try {
      String contents = new String(Files.readAllBytes(Paths.get("levels/level99.json")), StandardCharsets.UTF_8);
      JSONObject o = new JSONObject(contents);
      int keys = o.getInt("keys_in_inventory");
      int treasures = o.getInt("treasures_in_inventory");
      assert keys == 1;
      assert treasures == 1;
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    // Make sure the tests work every time by resetting the level every time
    GameLevelLoader.resetLevel(99);
  }
}
