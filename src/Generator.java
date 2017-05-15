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
    PrintWriter gdl;
    for (candidate : candidates) {
      gdl = new PrintWriter(candidate.id + ".kif");
      gdl.println(Description.gdlOutput(candidate));
      gld.close();
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
  /** Creates a process that runs the GGP server */
  private static void startupGgpServer() {
    ProcessBuilder ggpPb = new ProcessBuilder("/bin/bash",
                                              "gameServerRunner.sh",
                                              "results", "ticTacToe223",
                                              "30", "15",
                                              "127.0.0.1", "4047", "cadia1",
                                              "127.0.0.1", "4048", "cadia2");
    ggpPb.directory(new File("/home/azhu8/Documents/DM425/ggp-base"));
    File log = new File("ggp.log");
    ggpPb.redirectErrorStream(true);
    ggpPb.redirectOutput(Redirect.appendTo(log));
    try {
      Process p = ggpPb.start();
      p.waitFor();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    } catch (InterruptedException ex) {
      System.out.println(ex.getMessage());
    }
  }
  /** Creates a process that runs a CadiaPlayer */
  private static Process startupCadiaPlayer(int port) {
    ProcessBuilder cadiaPb = new ProcessBuilder("./ggpserver",
                                                "./cadiaplayer",
                                                Integer.toString(port));
    cadiaPb.directory(new File("/home/azhu8/Documents/DM425/cadiaplayer-3.0/bin"));
    cadiaPb.environment().put("LD_LIBRARY_PATH", "/usr/local/lib");
    File log = new File("cadia" + Integer.toString(port) + ".log");
    cadiaPb.redirectErrorStream(true);
    cadiaPb.redirectOutput(Redirect.appendTo(log));
    try {
      return cadiaPb.start();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    return null;
  }
  /** Kill all instances of CadiaPlayer (left behind after .destroy()) */
  private static void killCadiaPlayer() {
    ProcessBuilder pb = new ProcessBuilder("/bin/bash", "killcadia.sh");
    try {
      pb.start();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
  /** Selects survivors after evaluation using CadiaPlayer */
  private static void selection() {
    Process p1 = Generator.startupCadiaPlayer(4047);
    Process p2 = Generator.startupCadiaPlayer(4048);
    Generator.startupGgpServer();
    p1.destroy();
    p2.destroy();
    killCadiaPlayer();
    //get fitness values
    //do tourney selection
    for (int i = 0; i < CANDIDATES; i++)   // for now, randomly pick survivors
      _candidates.remove(_random.nextInt(_candidates.size()));
  }
}
