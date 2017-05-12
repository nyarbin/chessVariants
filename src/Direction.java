/** Defines direction of movement, and transformations for symmetries */
public class Direction {
  private int dX;
  private int dY;

  public Direction() {
    this.dX = 1;
    this.dY = 0;
  }

  public Direction(int dX, int dY) {
    if (dX == 0 && dY == 0)
      dX = 1;
    this.dX = dX;
    this.dY = dY;
  }

  public Direction(Direction other) {
    this.dX = other.dX;
    this.dY = other.dY;
  }

  public boolean isForward() {
    return dY > 0;
  }

  public boolean isRight() {
    return dX > 0;
  }

  public Direction forwSymm() {
    return new Direction(dX, -1 * dY);
  }

  public Direction sideSymm() {
    return new Direction(-1 * dX, dY);
  }

  public Direction diagSymm() {
    return new Direction(dY, dX);
  }

  public int xDist() {
    return dX;
  }

  public int yDist() {
    return dY;
  }

  @Override
  public String toString() {
    return "(" + Integer.toString(dX) + ", " + Integer.toString(dY) + ")";
  }
}
