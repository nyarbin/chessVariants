import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Defines transformations for symmetries */
public class Symmetry {
  private boolean diagonal;
  private boolean side;
  private boolean forward;

  public Symmetry(Random random) {
    this.side = true;
    if (random.nextInt(2) == 0)
      this.forward = true;
    if (random.nextInt(2) == 0)
      this.diagonal = true;
  }

  public Symmetry(Symmetry copyTarget) {
    this.diagonal = copyTarget.diagonal;
    this.side = copyTarget.side;
    this.forward = copyTarget.forward;
  }

  public Direction forwSymm(Direction origDir) {
    return new Direction(origDir.xDist(), -1 * origDir.yDist());
  }

  public Direction sideSymm(Direction origDir) {
    return new Direction(-1 * origDir.xDist(), origDir.yDist());
  }

  public Direction diagSymm(Direction origDir) {
    return new Direction(origDir.yDist(), origDir.xDist());
  }

  public List<Direction> getDirections(Direction dir) {
    List<Direction> dirList = new ArrayList<Direction>();
    dirList.add(new Direction(dir));
    if (this.diagonal)      // must do diagonal first to maintain side symmetry
      for (int i = dirList.size()-1; i >= 0; i--)
        dirList.add(this.diagSymm(dirList.get(i)));
    if (this.side)
      for (int i = dirList.size()-1; i >= 0; i--)
        dirList.add(this.sideSymm(dirList.get(i)));
    if (this.forward)
      for (int i = dirList.size()-1; i >= 0; i--)
        dirList.add(this.forwSymm(dirList.get(i)));
    return dirList;
  }

  @Override
  public String toString() {
    String str = (diagonal ? "DIAGONAL, " : "")
                 + (side ? "SIDE, " : "")
                 + (forward ? "FORWARD, " : "");
    return "[" + str.substring(0, Math.max(str.length()-2, 1)) + "]";
  }
}
