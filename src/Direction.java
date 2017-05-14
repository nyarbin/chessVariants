import java.util.Random;
/** Defines single direction of movement */
public class Direction {
  private int dX;
  private int dY;

  public Direction(int dX, int dY) {
    this.dX = dX;
    this.dY = dY;
  }

  public Direction(Random random) {
    do {
      this.dX = random.nextInt(Generator.BOARD_SIZE);
      this.dY = random.nextInt(Generator.BOARD_SIZE);
    } while (this.dX == 0 && this.dY == 0);
  }

  public Direction(Direction copyTarget) {
    this.dX = copyTarget.dX;
    this.dY = copyTarget.dY;
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

  @Override
  public String toString() {
    return "(" + Integer.toString(this.dX) + ", "
           + Integer.toString(this.dY) + ")";
  }
}
