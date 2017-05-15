import java.util.ArrayList;
import java.util.List;

/** Collection of functions to generate a game description. */
public class Description {

  /** Construct the GDL to be printed. */
  public String gdlOutput(Board board) {
    ArrayList<Piece> pieces = board.armies();
    String toReturn = headerInfo() + initialization() + baseRules() + base();
    toReturn += stateDynamics();
    toReturn += endgame(pieces.get(0));
    toReturn += placePhase(board.start(), board.len(), board.width(), pieces);
    for (Piece piece : pieces) {
      toReturn += pieceMovement(piece);
    }
    toReturn += constants(board.width(), board.len());
    toReturn += math();
    return toReturn;
  }

  /** Basic info for GDL */
  private String headerInfo() {
    return "(role white)\n(role black)\n(init (control white))\n" +
      "(init (phase placing))\n";
  }

  /** GDL sentences describing initial board state */
  private String initialization() {
    return "";
  }

  private String endgame(Piece king) {
    String toReturn = "\n; Ending the game.\n";
    // Endgame conditions.
    toReturn += "(<= (hasKing ?player)\n ((true ?cell ?x ?y ?player piece_" +
      Integer.toString(king.ID()) + ")))\n";
    //toReturn += "(<= terminal (role ?player)\n (not (hasKing ?player)))\n";
    toReturn += "(<= terminal (true (step 501)))\n";
    toReturn += "(<= terminal (true (control ?player))\n" +
     " (not (hasMove ?player)))\n";
    toReturn += "(<= (hasMove ?player)\n (okMove ?player ?move)\n" +
     " (hasKing ?player))\n";
    // Goals
    toReturn += "(<= (goal white 100)\n (hasMove white)\n" +
     " (not (hasMove black)))\n";
    toReturn += "(<= (goal black 100)\n (hasMove black)\n" +
     " (not (hasMove white)))\n";
    toReturn += "(<= (goal black 0)\n (hasMove white)\n" +
     " (not (hasMove black)))\n";
    toReturn += "(<= (goal white 0)\n (hasMove black)\n" +
     " (not (hasMove white)))\n";
    toReturn += "(<= (goal white 50)\n (hasMove white)\n (hasMove black))\n";
    toReturn += "(<= (goal black 50)\n (hasMove white)\n (hasMove black))\n";
    toReturn += "(<= (goal black 50)\n (not(hasMove white))\n" +
     " (not (hasMove black)))\n";
    toReturn += "(<= (goal white 50)\n (not(hasMove white))\n" +
     " (not (hasMove black)))\n";
    return toReturn;
  }

  /** The two basic move rules: noop off-turn; move on-turn. */
  private String baseRules() {
    String toReturn = "\n; The fundamental rules.\n";
    toReturn += "(<= (legal ?player ?noop)\n (role ?player)\n" +
      " (not (true (control ?player))) (true (phase playing)))\n";
    toReturn += "(<= (legal ?player ?move)\n (true (control ?player))\n" +
      " (okMove ?player ?move) (true (phase playing)))\n";
    toReturn += "(<= (legal ?player ?placement)\n (true (phase placing))\n" +
      " (okPlace ?player ?placement))\n";
    return toReturn;
  }

  /** Placement phase logic. */
  private string placePhase(int startRanks, int ranks, int files,
      ArrayList<Piece> armies) {
    String toReturn = "\n; How pieces get placed on the board.\n";
    toReturn += "(<= (okPlace ?player (place ?type ?x ?y))\n" +
      " (true (?player rank ?y))\n" +
      " (true (file ?x))\n" +
      " (available ?player ?type))\n";
    //TODO create "available" predicate, add ?player rank facts.
    return toReturn;
  }

  /** State change logic */
  private string stateDynamics() {
    String toReturn = "\n; State change logic.\n";
    toReturn += "(<= (next (control ?opponent))\n (true (control ?player))\n" +
      " (opponent ?player ?opponent) (true (phase playing)))\n";
    toReturn += "(<= (next (step ?np1))\n (true (step ?n))\n (succ ?n ?np1))\n";
    //TODO add logic to change phase from placing to playing.
    return toReturn;
  }

  /** GDL sentences describing how a given piece moves */
  private String pieceMovement(Piece piece) {
    // Top matter for a piece's moves.
    String type = "piece_" + Integer.toString(piece.ID());
    String mtype = "move_" + type;
    String ctype = "capture_" + type;

    //TODO check against speedchess.kif c 150-250
    String toReturn = "\n; " + type + " move definitions.\n";
    toReturn += "(<= (okMove ?player (move " + type + " ?x1 ?y1 ?x2 ?y2))\n" +
      " (true (cell ?x1 ?y1 ?player " + type + "))\n" +
      " (true (file ?x2))\n (true (rank ?y2))\n" + //necessary?
      " (" + mtype + " ?x1 ?y1 ?x2 ?y2)\n" +
      " (clearBtwn ?x1 ?y1 ?x2 ?y2)\n" +
      " (not (occupied ?x2 ?y2)))\n";
    toReturn += "(<= (okMove ?player (move " + type + " ?x1 ?y1 ?x2 ?y2))\n" +
      " (true (cell ?x1 ?y1 ?player " + type + "))\n" +
      " (" + ctype + " ?x1 ?y1 ?x2 ?y2)\n" +
      " (clearBtwn ?x1 ?y1 ?x2 ?y2)\n" +
      " (true (cell ?x2 ?y2 ?oplayer ?piece))\n" +
      " (true (opponent ?player ?oplayer)))\n";

    //Generate the moves.
    String dx;
    String dy;
    for (Direction dir : piece.getMoveDirections()) {
      dx = Integer.toString(dir.xDist());
      dy = Integer.toString(dir.yDist());
      toReturn += "(<= (" + mtype + " ?x1 ?y1 ?x2 ?y2)\n";
      if (dx >= 0 && dy >= 0) {
        toReturn += " (dir ?x1 ?y1 ?x2 ?y2 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else if (dx < 0 && dy >= 0) {
        toReturn += " (dir ?x2 ?y1 ?x1 ?y2 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else if (dx >= 0 && dy < 0) {
        toReturn += " (dir ?x1 ?y2 ?x2 ?y1 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else { //if (dx < 0 &&dy < 0)
        toReturn += " (dir ?x2 ?y2 ?x1 ?y1 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      }
    }

    //Generate captures.
    for (Direction dir : piece.getCaptureDirections()) {
      dx = Integer.toString(dir.xDist());
      dy = Integer.toString(dir.yDist());
      toReturn += "(<= (" + ctype + " ?x1 ?y1 ?x2 ?y2)\n";
      if (dx >= 0 && dy >= 0) {
        toReturn += " (dir ?x1 ?y1 ?x2 ?y2 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else if (dx < 0 && dy >= 0) {
        toReturn += " (dir ?x2 ?y1 ?x1 ?y2 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else if (dx >= 0 && dy < 0) {
        toReturn += " (dir ?x1 ?y2 ?x2 ?y1 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      } else { //if (dx < 0 &&dy < 0)
        toReturn += " (dir ?x2 ?y2 ?x1 ?y1 " + dx + " " + dy + " ?d)\n";
        toReturn += " (leq ?d " + Integer.toString(dir.length()) + "))\n";
      }
    }

    return toReturn;
  }

  /** Base/input clauses */
  private String base() {
    return "";
  }

  /** GDL definitions for fundamental game constants. */
  private String constants(int files, int ranks) {
    String toReturn = "\n; Simple constants.\n";

    toReturn += "(opponent white black)\n(opponent black white)\n";
    toReturn += "(<= (occupied ?x ?y)\n (true (cell ?x ?y ?player ?piece)))\n";
    for (int i = 0; i < files; i++) {
      toReturn += "(file " + Integer.toString(i) + ")\n";
    }
    for (int i = 0; i < ranks; i++) {
      toReturn += "(rank " + Integer.toString(i) + ")\n";
    }
    return toReturn;
  }
  
  /** GDL for arithmetic and board geometry */
  private String math() {
    String toReturn = "\n; Fundamental chess math.\n";
    //Define the first 500 numbers into existence
    toReturn += "\n; Simple successor function.\n";
    for (int i = 0; i < 500; i++) {
      toReturn += "(succ " + Integer.toString(i) + " "
                  + Integer.toString(i + 1) + ")\n";
    }

    //Less-than relation
    toReturn += "\n; Less-than-or-equal relation.\n";
    toReturn += "(<= (leq ?x ?x) (number ?x))\n" +
      "(<= (leq ?x ?y) (succ ?x ?y))\n" +
      "(<= (leq ?x ?z) (succ ?x ?y) (leq ?y ?z))\n";

    //Addition
    toReturn += "\n; Addition.\n";
    toReturn += "(<= (add ?x 0 ?x)\n (number ?x))\n";
    toReturn += "(<= (add ?x ?y ?z)\n (succ ?x ?x1)\n (succ ?y1 ?y)\n" +
      " (add ?x1 ?y1 ?z))\n";
    toReturn += "(number 0)\n";
    toReturn += "(<= (number ?y) (succ ?x ?y))\n";

    //Multiplication
    toReturn += "\n; Multiplication.\n";
    toReturn += "(<= (mul ?x 0 0)\n (number ?x))\n";
    toReturn += "(<= (mul ?x ?y ?z)\n" +
      " (succ ?ym1 ?y)\n" +
      " (mul ?x ?ym1 ?zmx)\n" +
      " (add ?zmx ?x ?z))\n";

    //Direction vectors
    toReturn += "\n; Directions\n";
    toReturn += "(<= (dir ?x ?y) (number ?x) (number ?y))\n";

    //Define a function to test whether a ride is clear
    // toReturn += "\n; Test whether c squares from 1 in x,y are clear.\n" +
    //   "; x1,y1 may be occupied.\n";
    // toReturn += "(<= (openUntil ?x1 ?y1 ?x ?y 1)\n" +
    //   " (add ?x1 ?x ?x2)\n" +
    //   " (add ?y1 ?y ?y2)\n" +
    //   " (not (occupied ?x2 ?y2)))\n";
    // toReturn += "(<= (openUntil ?x1 ?y1 ?x ?y ?c)\n" +
    //   " (succ ?c ?c2)\n" +
    //   " (openUntil ?x2 ?y2 ?x ?y ?c2))\n";
    toReturn += "(<= (clearBtwn ?x1 ?y1 ?x2 ?y2)\n" +
      " (dir ?x1 ?y1 ?x2 ?y2 ?x ?y ?d)\n" +
      " (add ?x1 ?x ?x2)\n" +
      " (add ?y1 ?y ?y2))\n";
    toReturn += "(<= (clearBtwn ?x1 ?y1 ?x2 ?y2)\n" +
      " (dir ?x1 ?y1 ?x2 ?y2 ?x ?y ?d)\n" +
      " (add ?x1 ?x ?x3)\n" +
      " (add ?y1 ?y ?y3)\n" +
      " (clearBtwn (?x3 ?y3 ?x2 ?y2))\n";

    //Define function to calc direction vector from x1,y1 to x2,y2
    toReturn += "\n; Determine whether x2,y2 lies d steps on x,y from x1,y1.\n";
    toReturn += "(<= (dir ?x1 ?y1 ?x2 ?y2 ?x ?y ?d)\n" +
      " (add ?x1 ?xtd ?x2)\n" +
      " (add ?y1 ?ytd ?y2)\n" +
      " (mul ?x ?d ?xtd)\n" +
      " (mul ?y ?d ?ytd)\n";


    return toReturn;
  }
}
