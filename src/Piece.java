import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Defines a piece and its movements */
public class Piece {
  private List<Move> movements;
  private List<Move> captures;
  private int cost;
  private int id;
  private static int next_id;

  /** Constuctor to randomly generate a piece */
  public Piece(Random random) {
    this.id = next_id++;
    this.movements = new ArrayList<Move>();
    this.captures = new ArrayList<Move>();
    this.movements.add(new Move(random));           // single random move for
    this.captures.add(new Move(movements.get(0)));  //  movement & capture
    this.calcCost();
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

  ///Fetchers for available moves.
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
