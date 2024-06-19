package nz.ac.wgtn.swen225.lc.domain;

import java.util.Random;

/**
 * An Enemy object. Moves randomly, if it hits the player they die.
 *
 * @author Adam Venner 300612218
 * @version 07/10/2023
 * 
 */

public class Enemy extends TileObject {

  /**
   * An Enemy.
   *
   * @param x xposition of the object.
   * @param y yposition of the object.
   */
  public Enemy(int x, int y) {
    super(x, y);
  }
  
  /**
   * Enemy moves a random direction, if it there is nothing in that space other than the player.
   */
  public void move() {
    int newX = posX;
    int newY = posY;
    int randomNumber = new Random().nextInt(4);
    if (randomNumber == 0) {
      newX--;
    } else if (randomNumber == 1) {
      newX++;
    } else if (randomNumber == 2) {
      newY--;
    } else if (randomNumber == 3) {
      newY++;
    }
    if (newX < 0 || newY < 0) {
      throw new IllegalStateException("Method should not return null");
    }
    
    if (!Map.getTile(newX, newY).hasObject()) { 
      Map.getTile(posX, posY).removeObject(this);
      posX = newX;
      posY = newY;
      Map.getTile(newX, newY).addObject(this);
    }
    
    if (Map.getTile(newX, newY).hasObject()) {
      Map.getTile(newX, newY).getObjects().stream()
      .filter(x -> x instanceof Player)
      .findAny()
      .ifPresent(player -> ((Player) player).die());

    }
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }

  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    player.die();
  }

}
