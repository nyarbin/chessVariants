import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Piece {
  private List<Move> movements;
  private List<Move> captures;
  private int cost;

  public Piece() {
    this.movements = new ArrayList<Move>();
    this.captures = new ArrayList<Move>();
    this.cost = 0;
  }
  /** Returns a randomly generated piece */
  public static Piece generatePiece(Random generator) {
    Piece piece = new Piece();
    /* Start with single movement and single capture rule */
    int rand1 = generator.nextInt(2);
    if (rand1 == 0) {
      piece.movements.add(new Ride());
      piece.captures.add(new Ride((Ride)piece.movements.get(0)));
    } else {
      piece.movements.add(new Leap());
      piece.captures.add(new Leap((Leap)piece.movements.get(0)));
    }
    piece.calcCost();
    return piece;
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
}
