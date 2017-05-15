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
