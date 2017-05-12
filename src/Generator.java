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
}
