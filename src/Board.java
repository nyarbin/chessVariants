import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
  public static final int BOARD_SIZE = 5;     // size of board (n by n grid)
  public static final int PLACEMENT_ROWS = 1; // # ranks player places pieces in
  public static final int PLAYER_PIECES = 5;  // max pieces per side (w/o king)
  public static final int MAX_SCORE = 50;     // max score of pieces (w/ king)

  private static final int INIT_PIECES = 4;   // number of pieces initially
  private static final int MAX_PIECES = 8;    // max piece choices per candidate

  private List<Piece> pieces;

  public Board(Random random) {
    this.pieces = new ArrayList<Piece>();
    for (int j = 0; j < INIT_PIECES; j++)
      this.pieces.add(new Piece(random));
  }
  /** Print details about all pieces */
  public void printPieces() {
    for (Piece piece : this.pieces)
      System.out.println(piece);
  }
}
