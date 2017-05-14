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

    int dx;
    int dy;

    for (Move move: piece.moves) {
      dx = move.direction.xDist;
      dy = move.direction.yDist;
      //TODO actually generate the move

    }

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
