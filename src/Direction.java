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
    int maxMoveDist = Board.BOARD_SIZE - 1;
    do {       // dX and dY set in range [-(BOARD_SIZE-1), BOARD_SIZE-1]
      this.dX = random.nextInt(maxMoveDist*2 + 1) - maxMoveDist;
      this.dY = random.nextInt(maxMoveDist*2 + 1) - maxMoveDist;
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
