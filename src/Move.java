import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** A movement, defined by a direction vector and symmetries */
public class Move {
  private Direction baseDir;
  private Symmetry symmetry;
  /** Constuctor to randomly generate a move */
  public Move(Random random) {
    this.symmetry = new Symmetry(random);
    this.baseDir = new Direction(random);
  }
  /** Copy constructor */
  public Move(Move copyTarget) {
    this.symmetry = new Symmetry(copyTarget.symmetry);
    this.baseDir = new Direction(copyTarget.baseDir);
  }

  public List<Direction> getDirections() {
    return this.symmetry.getDirections(this.baseDir);
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

  public void mutate(Random random) {
    if (random.nextInt(4) == 0)
      this.baseDir = new Direction(random);
    else if (random.nextInt(2) == 0)
      this.symmetry = new Symmetry(random);
  }

  @Override
  public String toString() {
    String str = "Direction: " + this.baseDir.toString() + " Symmetries: ";
    str += this.symmetry.toString();
    return str;
  }
}
