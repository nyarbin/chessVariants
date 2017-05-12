/** A move that is blocked by intervening pieces */
public class Ride extends Move {
  private int distance;

  public Ride() {
    this.distance = 0;
  }

  public Ride(Ride other) {
    super(other);
    this.distance = other.distance;
  }

  @Override
  public int getCost() {
    return 0;
  }
}
