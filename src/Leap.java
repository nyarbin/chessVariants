/** An immediate move, does not depend on other pieces (jumps over others) */
public class Leap extends Move {
  public Leap() {
    // just calls super()
  }

  public Leap(Leap other) {
    super(other);
  }

  @Override
  public int getCost() {
    return 0;
  }
}
