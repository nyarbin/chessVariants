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
  private int id;
  private static int next_id;
  /** Randomly generates a board's pieces */
  public Board(Random random) {
    this.id = next_id++;
    this.pieces = new ArrayList<Piece>();
    for (int i = 0; i < INIT_PIECES; i++)
      this.pieces.add(new Piece(random));
  }
  /** Copies all pieces from another board */
  public Board(Board copyTarget) {
    this.id = next_id++;
    this.pieces = new ArrayList<Piece>();
    for (Piece piece : copyTarget.pieces)
      this.pieces.add(new Piece(piece));
  }
  /** Constructor for recombination of two boards */
  public Board(Random random, Board parent1, Board parent2) {
    this.id = next_id++;
    this.pieces = new ArrayList<Piece>();
    /* Combine piece lists of both parents */
    for (Piece p1Piece : parent1.pieces)
      this.pieces.add(new Piece(p1Piece));
    for (Piece p2Piece : parent2.pieces)
      this.pieces.add(new Piece(p2Piece));
    /* Randomly remove pieces from board until # = average of parent boards */
    int numPieces = (parent1.pieces.size() + parent2.pieces.size()) / 2;
    while (this.pieces.size() > numPieces)
      this.pieces.remove(random.nextInt(this.pieces.size()));
  }
  /** Change a board's pieces randomly */
  public void mutate(Random random) {
    /* Add new random piece */
    if (this.pieces.size() < MAX_PIECES && random.nextInt(10) == 0)
      this.pieces.add(new Piece(random));
    /* Remove random piece */
    if (this.pieces.size() > INIT_PIECES && random.nextInt(10) == 0)
      this.pieces.remove(random.nextInt(this.pieces.size()));
    /* Mutate random pieces */
    for (Piece piece : this.pieces) {
      if (random.nextInt(2) == 0)
        piece.mutate(random);
    }
  }

  public int ID() {
    return this.id;
  }
  /** Print details about all pieces */
  public void printPieces() {
    for (Piece piece : this.pieces)
      System.out.println(piece);
  }
}
