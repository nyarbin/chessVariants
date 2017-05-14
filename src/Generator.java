import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Main generator class for chess variants */
class Generator {
  private static final int NUM_ROUNDS = 100;  // # rounds for genetic algorithm
  private static final int CANDIDATES = 4;    // number of candidate boards
  private static List<Board> _candidates;     // list of candidate boards
  private static Random _random;
  /** Runs genetic algorithm to pick chess variants */
  public static void main(String args[]) {
    _random = new Random();
    /* Initialize candidates */
    _candidates = new ArrayList<Board>();
    for (int i = 0; i < CANDIDATES; i++)
      _candidates.add(new Board(_random));
    /* Do genetic algorithm rounds */
    for (int r = 0; r < NUM_ROUNDS; r++) {
      selection();
      generation();
    }
    _candidates.get(0).printPieces();
  }
  /** Selects survivors after evaluation using CadiaPlayer */
  private static void selection() {

  }
  /** Generates new pieces using genetic mutation and recombination */
  private static void generation() {

  }
}
