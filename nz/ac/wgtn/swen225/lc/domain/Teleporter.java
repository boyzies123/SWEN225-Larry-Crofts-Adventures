package nz.ac.wgtn.swen225.lc.domain;

/**
 * Teleport Object. The player can stand on a teleport to go to a partner teleporter
 *
 * @author Adam Venner 300612218
 * @version 13/10/2023
 * 
 */

public class Teleporter extends TileObject {

  //fields
  Teleporter partner;
  
  /**
   * A Teleporter.
   *
   * @param x xposition of the object.s
   * @param y yposition of the object.
   */
  public Teleporter(int x, int y) {
    super(x, y);
  }

  public boolean movementRequest(TileObject other) {
    return true;
  }
  
  /**
   * Sets the partner of the teleporter.
   *
   * @param teleporter the partner of this teleporter.
   */
  public void setPartner(Teleporter teleporter) {
    //precondition
    if (teleporter == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    partner = teleporter;
  }
  
  public Teleporter getPartner() {
    return partner;
  }

  public void playerInteraction(Player player) {
    //precondition
    if (player == null) {
      throw new IllegalArgumentException("invalid parameters");
    }
    int newX = partner.getX();
    int newY = partner.getY();
    /*
    player.setPosition(newX, newY);
    Map.getTile(player.getX(), player.getY()).removeObject(player);
    Map.getTile(newX, newY).addObject(player);
    */
  }

}
