import java.util.Random;
/** A move that is blocked by intervening pieces */
public class Ride extends Move {
  private int distance;

  public Ride() {
    this.distance = 0;
  }

  public Ride(Random random) {
    super(random);
    int dirMax = Math.max(this.baseDir.xDist(), this.baseDir.yDist());
    this.distance = random.nextInt((Generator.BOARD_SIZE-1)/dirMax) + 1;
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
