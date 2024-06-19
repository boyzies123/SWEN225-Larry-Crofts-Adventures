package nz.ac.wgtn.swen225.lc.app;

import java.util.List;
import java.util.ArrayList;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.persistency.*;

/**
 * @author Harry Booth-Beach 300614975
 *
 */
public class Timer implements Runnable {
  
  private int time;
  private Map map;
  private Thread t;
  private boolean paused;
  
  /**
   * Base timer constructor
   * @param map Takes map from Main class and uses it to update enemy positions
   */
  public Timer(Map map) {
    this.map = map;
    t = new Thread(this);
    paused = false;
    time = GameLevelLoader.getTimeLimit(GameStateManager.getCurrentLevel());
    t.start();
  }
  
  /**
   * Constructor for timer if it has been interrupted and needs restarting
   * @param map Takes map from Main class and uses it to update enemy positions
   * @param time Sets time in case that the timer has been interrupted
   */
  public Timer(Map map, int time) {
    this.map = map;
    t = new Thread(this);
    paused = false;
    this.time = time;
    t.start();
  }
  
  /**
   * runs the timer and makes it tick down every second
   */
  @Override
  public void run() {
    
      try {
        for (int i = time; i >= 0; i--) {
          while (!Thread.interrupted() && !paused) {
            System.out.println(time);
            Thread.sleep(1000);
            time--;
            update();
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
  }

  /**
   * Update the position of the enemy every second
   */
  public void update() {
    List<Enemy> enemies = new ArrayList<>();
    // Find all enemies and update their positions
    for (int c=0; c<map.getCols(); c++) {
      for (int r=0; r<map.getRows(); r++) {
        for (TileObject t : map.getTile(c, r).getObjects()) {
          if (t instanceof Enemy) {
            enemies.add(((Enemy) t));
          }
        }
      }
    }
    for (Enemy e : enemies) {
      e.move();
    }
    Main.render();
  }
  
  /**
   * Stops the timer
   */
  public void interrupt() {
    t.interrupt();
  }
  
  /**
   * Reset the time on the timer
   */
  public void resetTimer() {
    time = GameLevelLoader.getTimeLimit(GameStateManager.getCurrentLevel());
  }
  
  /**
   * Pause the timer
   * @return time Returns time to be used to reinstate timer
   */
  public int pause() {
    paused = true;
    return time-1;
  }
  
  /**
   * Resume the timer
   */
  public void resume() {
    paused = false;
  }
  
  /**
   * @return paused If game is paused return true, else return false
   */
  public boolean isPaused() {
    return paused;
  }
}
