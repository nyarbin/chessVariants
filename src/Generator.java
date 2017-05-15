import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
/** Main generator class for chess variants */
class Generator {
  private static final int NUM_ROUNDS = 1;  // # rounds for genetic algorithm
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
      Generator.generation();
      Generator.selection();
    }
    _candidates.get(0).printPieces();
  }
  /** Generates new pieces using genetic mutation and recombination */
  private static void generation() {
    for (int i = 0; i < CANDIDATES/2; i++)
      Generator.mutate(_candidates.get(_random.nextInt(CANDIDATES)));
    for (int j = 0; j < CANDIDATES/2; j++) {
      Board parent1 = _candidates.get(_random.nextInt(CANDIDATES));
      Board parent2 = _candidates.get(_random.nextInt(CANDIDATES));
      Generator.recombine(parent1, parent2);
    }
  }
  /** Makes random changes to a board */
  private static void mutate(Board board) {
    Board mutant = new Board(board);
    mutant.mutate(_random);
    _candidates.add(mutant);
  }
  /** Combines two boards to get a new board */
  private static void recombine(Board board1, Board board2) {
    _candidates.add(new Board(_random, board1, board2));
  }
  /** Selects survivors after evaluation using CadiaPlayer */
  private static void selection() {
    ProcessBuilder cadiaPb = new ProcessBuilder("/bin/bash", "test.sh", "3000");
    cadiaPb.directory(new File("/home/azhu8/Documents/DM425/chessVariants/hello"));
    File log = new File("log");
    cadiaPb.redirectErrorStream(true);
    cadiaPb.redirectOutput(Redirect.appendTo(log));
    try {
      Process p = cadiaPb.start();
      if (cadiaPb.redirectInput() != Redirect.PIPE)
        System.out.println("NOOO");
      if (cadiaPb.redirectOutput().file() != log)
        System.out.println("NO2");
      if (p.getInputStream().read() != -1)
        System.out.println("NO3");
    } catch (IOException ex) {
      System.out.println("EXCEPTION" + ex.getMessage());
    }
    //run cadiaplayer on each candidate
    //get fitness values
    //do tourney selection
    for (int i = 0; i < CANDIDATES; i++)   // for now, randomly pick survivors
      _candidates.remove(_random.nextInt(_candidates.size()));
  }
}
