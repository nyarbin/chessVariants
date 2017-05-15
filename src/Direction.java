import java.util.Random;
/** Defines single direction of movement, and ride length */
public class Direction {
  private int dX;          // x distance of single hop
  private int dY;          // y distance of single hop
  private int rideLength;  // maximum number of hops allowed if no collisions

  public Direction(int dX, int dY, int dist) {
    this.dX = dX;
    this.dY = dY;
    this.rideLength = dist;
  }
  /** Generate a random Direction based on board size */
  public Direction(Random random) {
    int maxMoveDist = Board.BOARD_SIZE - 1;
    do {       // dX and dY set in range [-(BOARD_SIZE-1), BOARD_SIZE-1]
      this.dX = random.nextInt(maxMoveDist*2 + 1) - maxMoveDist;
      this.dY = random.nextInt(maxMoveDist*2 + 1) - maxMoveDist;
    } while (this.dX == 0 && this.dY == 0);
    /** Generate a random rideLength based on baseDir vector */
    int dirMax = Math.max(Math.abs(this.xDist()), Math.abs(this.yDist()));
    this.rideLength = random.nextInt((Board.BOARD_SIZE-1)/dirMax) + 1;
  }

  public Direction(Direction copyTarget) {
    this.dX = copyTarget.dX;
    this.dY = copyTarget.dY;
    this.rideLength = copyTarget.rideLength;
  }

  public boolean isForward() {
    return this.dY > 0;
  }

  public boolean isRight() {
    return this.dX > 0;
  }

  public int xDist() {
    return this.dX;
  }

  public int yDist() {
    return this.dY;
  }

  public int length() {
    return this.rideLength;
  }

  public boolean equals(Direction other) {
    return (this.dX == other.dX) && (this.dY == other.dY)
           && (this.rideLength == other.rideLength);
  }

  @Override
  public String toString() {
    return "(" + Integer.toString(this.dX) + ", " + Integer.toString(this.dY)
           + ") Distance: " + Integer.toString(this.rideLength);
  }
}
