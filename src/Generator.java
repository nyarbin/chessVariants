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

  /** Basic info for GDL */
  private String headerInfo() {
    return "(role white)\n(role black)\n(init (control white)\n)";
  }

  /** GDL sentences describing initial board state */
  private String initialization() {
    return "";
  }

  /** The two basic move rules: noop off-turn; move on-turn. */
  private String baseRules() {
    String toReturn;
    toReturn = "(<= (legal ?player ?noop)\n (role ?player)\n" +
      " (not (true (control ?player))))\n";
    toReturn += "(<= (legal ?player ?move)\n (true (control ?player))\n" +
      " (legalMove ?player ?move))\n";
    return toReturn;
  }

  /** GDL sentences describing how a given piece moves */
  private String pieceMovement(Piece piece) {
    //TODO (needs addition)
    String toReturn = "";

    // for (Move move: piece.moves) {
    // }

    return toReturn;
  }

  /** Base/input clauses */
  private String base() {
    return "";
  }

  /** GDL definitions for fundamental constants and math */
  private String constants(int files, int ranks) {
    String toReturn;

    toReturn = "(opponent white black)\n(opponent black white)\n";
    for (int i = 0; i < files; i++) {
      toReturn += "(file " + Integer.toString(i) + ")\n";
    }
    for (int i = 0; i < ranks; i++) {
      toReturn += "(rank " + Integer.toString(i) + ")\n";
    }

    //Less-than relation
    toReturn += "(<= (lt ?x ?y) (succ ?x ?y))\n" +
      "(<= (lt ?x ?z) (succ ?x ?y) (lt ?y ?z))\n";

    //Addition
    toReturn += "(<= (add ?x 0 ?x)\n (number ?x)\n";
    toReturn += "(<= (add ?x ?y ?z)\n (succ ?x ?x1)\n (succ ?y1 ?y)\n" +
      " (add ?x1 ?y1 ?z))\n";
    toReturn += "(number 0)\n";
    toReturn += "(<= (number ?y) (succ ?x ?y))\n";

    //Define the first 500 numbers into existence
    for (int i = 0; i < 500; i++) {
      toReturn += "(succ " + Integer.toString(i) + " "
                  + Integer.toString(i + 1) + ")\n";
    }
    return toReturn;
  }
}
