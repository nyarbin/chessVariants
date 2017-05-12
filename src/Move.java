import java.util.ArrayList;
import java.util.List;

/** Common interface for all moves */
public abstract class Move {
  public enum Symmetry {FORWARD, SIDE, DIAGONAL}

  private Direction baseDir;
  private List<Symmetry> symmetries;

  public abstract int getCost();

  public Move() {
    this.symmetries = new ArrayList<Symmetry>();
    this.baseDir = new Direction();
  }
  /** Copy constructor */
  public Move(Move other) {
    this.symmetries = new ArrayList<Symmetry>();
    for (Symmetry symm : other.symmetries)
      this.symmetries.add(symm);
    this.baseDir = new Direction(other.baseDir);
  }
}
