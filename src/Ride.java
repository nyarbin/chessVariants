/** A move that is blocked by intervening pieces */
public class Ride implements Move {
  public enum RideDir {NONE, FORWARD, HORIZONTAL, BACKWARD, DIAGFORW, DIAGBACK}
  private RideDir direction;
  private int distance;

  public Ride() {
    this.direction = RideDir.NONE;
    this.distance = 0;
  }
  /** Copy constructor */
  public Ride(Ride other) {
    this.direction = other.direction;
    this.distance = other.distance;
  }

  public int getCost() {
    return 0;
  }
}
