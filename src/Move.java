import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** A movement, defined by a direction vector, symmetries, and a distance */
public class Move {
  private Direction baseDir;
  private Symmetry symmetry;
  private int distance;
  /** Constuctor to randomly generate a move */
  public Move(Random random) {
    this.symmetry = new Symmetry(random);
    this.baseDir = new Direction(random);
    int dirMax = Math.max(Math.abs(this.baseDir.xDist()),
                          Math.abs(this.baseDir.yDist()));
    this.distance = random.nextInt((Board.BOARD_SIZE-1)/dirMax) + 1;
  }
  /** Copy constructor */
  public Move(Move copyTarget) {
    this.symmetry = new Symmetry(copyTarget.symmetry);
    this.baseDir = new Direction(copyTarget.baseDir);
    this.distance = copyTarget.distance;
  }

  public int getCost() {
    /* 2, 4 (dist 1) full symm -> 8 travel, 8 protect
       1, 2 (dist 2) full symm -> 16 travel, 8 protect
       cost = squares to travel + squares to protect/attack
       squares to travel double counted. squares to protect is fine
       maybe don't worry: the solver will figure out that this is worthless?

       Knight vs bishop -> take board size into account. Bishop has diag len 7,
       but only max 4 (BOARD_SIZE/2) is really effective at one time
       bishop has 16 move, 4 protect
       knight has 8 move, 8 protect. So make move worth 1/2 and protect worth 1?
    */
    return 0;
  }

  @Override
  public String toString() {
    String str = "Direction: " + this.baseDir.toString() + " Symmetries: ";
    str += this.symmetry.toString();
    str += " Distance: " + Integer.toString(this.distance);
    return str;
  }
}
