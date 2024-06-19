package nz.ac.wgtn.swen225.lc.recorder;



/**
 * 
 */

public class Move {
  

  private int x;
  private int y;
  private String character;
  private String direction;
  
  /**Default constructor for the purpose of serializing
   *
   */
  public Move() {
    
  }
  /**Constructor that initializes move with everything important
   * about that move. This includes the position, what character, and the direction.
   * @param x
   * @param y
   * @param character
   * @param direction
   */
  public Move(int x, int y, String character, String direction) {
    //Precondition x and y should be valid coordinates
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    this.x = x;
    this.y = y;
    this.character = character;
    this.direction = direction;
  }
  
  /**Get x position of character
   * @return x
   */
  public int getX() {
    return x;
  }
  /**Get y position of character
   * @return y
   */
  public int getY() {
    return y;
  }
  /**Get type of character
   * @return character
   */
  public String getCharacter() {
    return character;
  }
  /**Get direction of character
   * @return direction
   */
  public String getDirection() {
    return direction;
  }
}
