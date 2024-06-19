package nz.ac.wgtn.swen225.lc.domain;

/**
 * @author Adam Venner
 *
 */
public class DoorUnlockedEvent extends GameEvent {
  private LockedDoor lockedDoor;

  /**
   * @param source
   * @param lockedDoor
   */
  public DoorUnlockedEvent(TileObject source, LockedDoor lockedDoor) {
      super(source);
      this.lockedDoor = lockedDoor;
  }

  /**
   * @return 
   */
  public LockedDoor getLockedDoor() {
      return lockedDoor;
  }
}