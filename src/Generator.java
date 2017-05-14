import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/** Main generator class for chess variants */
class Generator {
  public static final int BOARD_SIZE = 5;     // size of board (n by n grid)
  public static final int PLACEMENT_ROWS = 1; // # ranks player places pieces in
  public static final int MAX_PIECES = 5;     // max pieces per side (w/o king)
  public static final int MAX_SCORE = 50;     // max score of pieces (w/ king)

  private static final int NUM_ROUNDS = 100;
  private static final int INIT_PIECES = 4;   // number of pieces initially
  private static List<Piece> _pieces;         // piece list for current round
  private static Random _random;

  /** Runs genetic algorithm to pick chess variants */
  public static void main(String args[]) {
    _random = new Random();
    _pieces = new ArrayList<Piece>();
    /* Initialize pieces */
    for (int i = 0; i < INIT_PIECES; i++)
      _pieces.add(new Piece(_random));
    /* do genetic algorithm rounds */
    for (int r = 0; r < NUM_ROUNDS; r++) {
      selection();
      generation();
    }

    printPieces();
  }
  /** Selects survivors after evaluation using CadiaPlayer */
  private static void selection() {

  }
  /** Generates new pieces using genetic mutation and recombination */
  private static void generation() {

  }
  /** Print details about all pieces */
  private static void printPieces() {
    for (Piece piece : _pieces)
      System.out.println(piece);
  }
}
