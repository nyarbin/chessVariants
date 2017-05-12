import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Common interface for all moves */
public abstract class Move {
  public enum Symmetry {FORWARD, SIDE, DIAGONAL}

  protected Direction baseDir;
  protected List<Symmetry> symmetries;

  public abstract int getCost();

  public Move() {
    this.symmetries = new ArrayList<Symmetry>();
    this.baseDir = new Direction();
  }
  /** Constuctor to randomly generate a move */
  public Move(Random random) {
    this.symmetries = new ArrayList<Symmetry>();
    this.symmetries.add(Symmetry.SIDE);
    if (random.nextInt(2) == 0)
      this.symmetries.add(Symmetry.FORWARD);
    if (random.nextInt(2) == 0)
      this.symmetries.add(Symmetry.DIAGONAL);
    this.baseDir = new Direction(random.nextInt(Generator.BOARD_SIZE),
                                 random.nextInt(Generator.BOARD_SIZE));
  }
  /** Copy constructor */
  public Move(Move other) {
    this.symmetries = new ArrayList<Symmetry>();
    for (Symmetry symm : other.symmetries)
      this.symmetries.add(symm);
    this.baseDir = new Direction(other.baseDir);
  }
}
