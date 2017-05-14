import java.util.ArrayList;
import java.util.List;

/** Collection of functions to generate a game description. */
public class Description {

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
    String toReturn = "\n; The fundamental rules.\n";
    toReturn += "(<= (legal ?player ?noop)\n (role ?player)\n" +
      " (not (true (control ?player))))\n";
    toReturn += "(<= (legal ?player ?move)\n (true (control ?player))\n" +
      " (okMove ?player ?move))\n";
    return toReturn;
  }

  /** GDL sentences describing how a given piece moves */
  private String pieceMovement(Piece piece) {
    // Top matter for a piece's moves.
    String type = "piece_" + Integer.toString(piece.ID());
    String mtype = "move_" + type;
    String ctype = "capture_" + type;
    String toReturn = "\n; " + type + " move definitions.\n";
    //TODO edit move headers to account for whether there are intervening pieces
    //TODO check against speedchess.kif c 150-250
    toReturn += "(<= (okMove ?player (move " + type + " ?x1 ?y1 ?x2 ?y2))\n" +
      " (true (cell ?x1 ?y1 ?player " + type + "))\n" +
      " (" + mtype + " ?x1 ?y1 ?x2 ?y2)\n" +
      " (not (occupied ?x2 ?y2)))\n";
    toReturn += "(<= (okMove ?player (move " + type + " ?x1 ?y1 ?x2 ?y2))\n" +
      " (true (cell ?x1 ?y1 ?player " + type + "))\n" +
      " (" + ctype + " ?x1 ?y1 ?x2 ?y2)\n" +
      " (true (cell ?x2 ?y2 ?oplayer ?piece))\n" +
      " (true (opponent ?player ?oplayer)))\n";

    //Generate the moves.
    String dx;
    String dy;
    for (Move move: piece.moves) {
      for (Direction dir : move.getDirections()) {
        dx = Integer.toString(dir.xDist());
        dy = Integer.toString(dir.yDist());
        toReturn += "(<= (" + mtype + " ?x1 ?y1 ?x2 ?y2)\n";
        if (dx >= 0 && dy >= 0) {
          toReturn += " (dir ?x1 ?y1 ?x2 ?y2 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x1 ?y1 " + dx + " " + dy + "?x2 ?y2 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else if (dx < 0 && dy >= 0) {
          toReturn += " (dir ?x2 ?y1 ?x1 ?y2 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x2 ?y1 " + dx + " " + dy + "?x1 ?y2 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else if (dx >= 0 && dy < 0) {
          toReturn += " (dir ?x1 ?y2 ?x2 ?y1 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x1 ?y2 " + dx + " " + dy + "?x2 ?y1 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else { //if (dx < 0 &&dy < 0)
          toReturn += " (dir ?x2 ?y2 ?x1 ?y1 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x2 ?y2 " + dx + " " + dy + "?x1 ?y1 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        }
      }
    }


    //Generate captures.
    for (Move move: piece.captures) {
      for (Direction dir : move.getDirections()) {
        dx = Integer.toString(dir.xDist());
        dy = Integer.toString(dir.yDist());
        toReturn += "(<= (" + ctype + " ?x1 ?y1 ?x2 ?y2)\n";
        if (dx >= 0 && dy >= 0) {
          toReturn += " (dir ?x1 ?y1 ?x2 ?y2 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x1 ?y1 " + dx + " " + dy + "?x2 ?y2 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else if (dx < 0 && dy >= 0) {
          toReturn += " (dir ?x2 ?y1 ?x1 ?y2 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x2 ?y1 " + dx + " " + dy + "?x1 ?y2 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else if (dx >= 0 && dy < 0) {
          toReturn += " (dir ?x1 ?y2 ?x2 ?y1 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x1 ?y2 " + dx + " " + dy + "?x2 ?y1 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        } else { //if (dx < 0 &&dy < 0)
          toReturn += " (dir ?x2 ?y2 ?x1 ?y1 " + dx + " " + dy + ")\n";
          toReturn += " (dist ?x2 ?y2 " + dx + " " + dy + "?x1 ?y1 ?d)\n";
          toReturn += " (lt ?d " + Integer.toString(move.length()) + "))\n";
        }
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
    toReturn += "\n; Less-than relation.\n";
    toReturn += "(<= (lt ?x ?y) (succ ?x ?y))\n" +
      "(<= (lt ?x ?z) (succ ?x ?y) (lt ?y ?z))\n";

    //Addition
    toReturn += "\n; Addition.\n";
    toReturn += "(<= (add ?x 0 ?x)\n (number ?x))\n";
    toReturn += "(<= (add ?x ?y ?z)\n (succ ?x ?x1)\n (succ ?y1 ?y)\n" +
      " (add ?x1 ?y1 ?z))\n";
    toReturn += "(number 0)\n";
    toReturn += "(<= (number ?y) (succ ?x ?y))\n";

    //Multiplication
    // toReturn += "\n; Multiplication.\n";
    // toReturn += "(<= (mul ?x 0 0)\n (number ?x))\n";
    // toReturn += "(<= (mul ?x ?y ?z)\n" +
    //   " (succ ?ym1 ?y)\n" +
    //   " (mul ?x ?ym1 ?zmx)\n" +
    //   " (add ?zmx ?x ?z))\n";

    //Direction vectors
    toReturn += "\n; Directions\n";
    toReturn += "(<= (dir ?x ?y) (number ?x) (number ?y))\n";

    //Define a function to test whether a ride is clear
    toReturn += "\n; Test whether c squares from 1 in x,y are clear.\n" +
      "; x1,y1 may be occupied.\n";
    toReturn += "(<= (openUntil ?x1 ?y1 ?x ?y 1)\n" +
      " (add ?x1 ?x ?x2)\n" +
      " (add ?y1 ?y ?y2)\n" +
      " (not (occupied ?x2 ?y2)))\n";
    toReturn += "(<= (openUntil ?x1 ?y1 ?x ?y ?c)\n" +
      " (succ ?c ?c2)\n" +
      " (openUntil ?x2 ?y2 ?x ?y ?c2))\n";

    //TODO define function to calc direction vector from x1,y1 to x2,y2

    //TODO define function to calc distance from x1,y1 along x,y

    return toReturn;
  }
}
