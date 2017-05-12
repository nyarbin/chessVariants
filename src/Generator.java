import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Main generator class for chess variants */
class Generator {
  private static int _numPieceTypes = 4; // number of pieces available to use
  private static int _boardSize = 5;     // size of board (n by n grid)
  private static int _placementRows = 1; // # of ranks pieces can be placed in
  private static int _maxPieces = 5;     // max pieces per side (including king)
  private static int _maxScore = 50;     // max score of pieces (excluding king)

  private static List<Piece> _pieces;    // available pieces in current round
  private static Random _generator;

  /** Runs genetic algorithm to pick chess variants */
  public static void main(String args[]) {
    _generator = new Random();
    _pieces = new ArrayList<Piece>();
    /* Initialize pieces */
    for (int i = 0; i < _numPieceTypes; i++)
      _pieces.add(Piece.generatePiece(_generator));

    /* do genetic algorithm rounds */
  }

  /** Basic info for GDL */
  private String headerInfo() {
     return "(role white)\n(role black)\n(init (control white)\n)";
  }

  /** GDL sentences describing initial board state */
  private String initialization() {
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
     String toReturn;

     for (Move : piece.moves) {
     }

     return toReturn;
  }

  /** Base/input clauses */
  private String base() {
  }

  /** GDL definitions for fundamental constants and math */
  private String constants(int files, int ranks) {
     String toReturn;

     toReturn = "(opponent white black)\n(opponent black white)\n";
     for (int i = 0; i < files; i++) {
        toReturn += "(file " + toString(i) + ")\n";
     }
     for (int i = 0; i < ranks; i++) {
        toReturn += "(rank " + toString(i) + ")\n";
     }

     //Less-than relation
     toReturn += "(<= (lt ?x ?y) (succ ?x ?y))\n" +
        "(<= (lt ?x ?z) (succ ?x ?y) (lt ?y ?z))\n";

     //Addition
     toReturn += "(<= (add ?x 0 ?x)\n (number ?x)\n";
     toReturn += "(<= (add ?x ?y ?z)\n (succ ?x ?x1)\n (succ ?y1 ?y)\n" +
        " (add ?x1 ?y1 ?z))\n";
     toReturn += "(number 0)\n";
     toReturn += "(<= (number ?y) (succ ?x ?y))\n"

     //Define the first 500 numbers into existence
     for (int i = 0; i < 500; i++) {
        toReturn += "(succ " + toString(i) + " " toString(i + 1) + ")\n";
     return toReturn;
  }

}
