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
  }
  /** Constuctor to randomly generate a piece */
  public Piece(Random random) {
    this();           // run Piece() constructor
    if (random.nextInt(2) == 0) {
      Ride ride = new Ride(random);
      this.movements.add(ride);
      this.captures.add(new Ride(ride));
    } else {
      Leap leap = new Leap(random);
      this.movements.add(leap);
      this.captures.add(new Leap(leap));
    }
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
}
