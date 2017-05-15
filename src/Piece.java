import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Defines a piece and its movements */
public class Piece {
  private static final int MAX_MOVES = 4;
  private List<Move> movements;
  private List<Move> captures;
  private int cost;
  private int id;
  private static int next_id;
  /** Basic constructor */
  public Piece() {
    this.id = next_id++;
    this.movements = new ArrayList<Move>();
    this.captures = new ArrayList<Move>();
  }
  /** Constuctor to randomly generate a piece */
  public Piece(Random random) {
    this();                                         // calls Piece() constructor
    this.movements.add(new Move(random));           // single random move for
    this.captures.add(new Move(movements.get(0)));  //  movement & capture
    this.calcCost();
  }
  /** Copies another piece */
  public Piece(Piece copyTarget) {
    this();
    for (Move move : copyTarget.movements)
      this.movements.add(new Move(move));
    for (Move cap : copyTarget.captures)
      this.captures.add(new Move(cap));
    this.calcCost();
  }
  /* Get set of all movements given a list of moves */
  private static List<Direction> getDirections(List<Move> moveList) {
    List<Direction> dirList = new ArrayList<Direction>();
    for (Move cap : moveList)
      dirList.addAll(cap.getDirections());
    /* Remove duplicate movements */
    List<Integer> dupList = new ArrayList<Integer>();   // duplicate indices
    for (int i = 0; i < dirList.size(); i++) {
      Direction firstDirCopy = dirList.get(i);
      for (int j = i+1; j < dirList.size(); j++) {
        if (dirList.get(j).equals(firstDirCopy))
          dupList.add(new Integer(j));
      }
      for (int k = dupList.size() - 1; k >= 0; k--)     // remove duplicates
        dirList.remove(dupList.get(k).intValue());
      dupList.clear();
    }
    return dirList;
  }
  /** Get full list of this piece's movement Directions */
  public List<Direction> getMoveDirections() {
    return Piece.getDirections(this.movements);
  }
  /** Get full list of this piece's capture Directions */
  public List<Direction> getCaptureDirections() {
    return Piece.getDirections(this.captures);
  }
  /** Calculates the cost of a piece by summing costs of individual moves */
  public int calcCost() {
    this.cost = 0;
    for (Move move : this.movements)
      this.cost += move.getCost();
    for (Move cap : this.captures)
      this.cost += cap.getCost();
    return this.cost;
  }
  /** Change a piece's movements randomly */
  public void mutate(Random random) {
    /* Add new random move */
    if (this.movements.size() < MAX_MOVES && random.nextInt(4) == 0)
      this.movements.add(new Move(random));
    else if (this.captures.size() < MAX_MOVES && random.nextInt(4) == 0)
      this.captures.add(new Move(random));
    /* Remove random move */
    if (this.movements.size() > 1 && random.nextInt(4) == 0)
      this.movements.remove(random.nextInt(this.movements.size()));
    else if (this.captures.size() > 1 && random.nextInt(4) == 0)
      this.captures.remove(random.nextInt(this.captures.size()));
    /* Mutate random moves */
    for (Move move : this.movements)
      if (random.nextInt(4) == 0)
        move.mutate(random);
    for (Move cap : this.captures)
      if (random.nextInt(4) == 0)
        cap.mutate(random);
  }

  // Fetchers for available moves.
  public List<Move> move() {
    return this.movements;
  }

  public List<Move> capture() {
    return this.captures;
  }

  public int ID() {
    return this.id;
  }

  @Override
  public String toString() {
    String str = "Piece_" + Integer.toString(this.id) + "\n";
    str += "Movements: ";
    for (Move move : this.movements)
      str += move.toString() + "\n";
    str += "Captures: ";
    for (Move cap : this.captures)
      str += cap.toString() + "\n";
    return str;
  }
}
