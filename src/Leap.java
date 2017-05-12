/** An immediate move, does not depend on other pieces (jumps over others) */
public class Leap implements Move {
  public enum LeapSymm {NONE, FULLSYMM, SIDESYMM}
  private int x;
  private int y;
  private LeapSymm symmetry;

  public Leap() {
    this.x = 0;
    this.y = 0;
    this.symmetry = LeapSymm.NONE;
  }
  /** Copy constructor */
  public Leap(Leap other) {
    this.x = other.x;
    this.y = other.y;
    this.symmetry = other.symmetry;
  }

  public int getCost() {
    return 0;
  }
}
