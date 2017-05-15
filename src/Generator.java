import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import com.google.gson.Gson;
/** Main generator class for chess variants */
class Generator {
  private static final int NUM_ROUNDS = 1;    // # rounds for genetic algorithm
  private static final int CANDIDATES = 4;    // number of candidate boards
  private static final int NUM_TRIALS = 1;    // number of games per round
  private static final int NUM_SERVERS = 1;   // number of running servers
  private static final int BASE_PORT = 4000;  // base port number
  private static List<Board> _candidates;     // list of candidate boards
  private static Random _random;
  private static Gson _gson;
  /** Runs genetic algorithm to pick chess variants */
  public static void main(String args[]) {
    _random = new Random();
    _gson = new Gson();
    /* Initialize candidates */
    _candidates = new ArrayList<Board>();
    for (int i = 0; i < CANDIDATES; i++)
      _candidates.add(new Board(_random));
    /* Do genetic algorithm rounds */
    for (int j = 0; j < 2*NUM_SERVERS; j++)   // startup 2 players per server
      Generator.startupCadiaPlayer(4000 + j);
    for (int r = 0; r < NUM_ROUNDS; r++) {
      Generator.generation();
      Generator.selection();
    }
    /* Cleanup & output results */
    killCadiaPlayers();
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
  /** Creates process that runs GGP server, given ports and game name */
  private static Process startupGgpServer(String gameName, int serverNum) {
    int port1 = BASE_PORT + 2 * serverNum;
    int port2 = port1 + 1;
    ProcessBuilder ggpPb;
    ggpPb = new ProcessBuilder("/bin/bash", "gameServerRunner.sh",
                               gameName, gameName, "60", "15",
                               "127.0.0.1", Integer.toString(port1), "cadia1",
                               "127.0.0.1", Integer.toString(port2), "cadia2");
    ggpPb.directory(new File("/home/azhu8/Documents/DM425/ggp-base"));
    File log = new File("ggp_" + gameName + "_" + Integer.toString(serverNum) + ".log");
    ggpPb.redirectErrorStream(true);
    ggpPb.redirectOutput(Redirect.appendTo(log));
    try {
      Process p = ggpPb.start();
      return p;
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
    return null;
  }
  /** Creates a process that runs a CadiaPlayer */
  private static void startupCadiaPlayer(int port) {
    ProcessBuilder cadiaPb = new ProcessBuilder("./ggpserver",
                                                "./cadiaplayer",
                                                Integer.toString(port));
    cadiaPb.directory(new File("/home/azhu8/Documents/DM425/cadiaplayer-3.0/bin"));
    cadiaPb.environment().put("LD_LIBRARY_PATH", "/usr/local/lib");
    File log = new File("cadia" + Integer.toString(port) + ".log");
    cadiaPb.redirectErrorStream(true);
    cadiaPb.redirectOutput(Redirect.appendTo(log));
    try {
      cadiaPb.start();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
  /** Kill all instances of CadiaPlayer (left behind after .destroy()) */
  private static void killCadiaPlayers() {
    ProcessBuilder pb = new ProcessBuilder("/bin/bash", "killcadia.sh");
    try {
      pb.start();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }
  /** Selects survivors after evaluation using CadiaPlayer */
  private static void selection() {
    List<Process> servers = new ArrayList<Process>();
    for (int b = 0; b < 1; b++) {   //TODO: change back to b < candidates.size()
      /* Run the game simulation NUM_TRIALS times */
      //Generate gdl, put into file named chess<number>.kif in games/games/
      String gameName = "ticTacToe224";
      int trial = 0;
      while (trial < NUM_TRIALS) {
        for (int s = 0; s < NUM_SERVERS; s++) {
          servers.add(Generator.startupGgpServer(gameName, s));
          trial++;
        }
        for (Process p : servers) {     // wait for all games to complete
          try {
            p.waitFor();
          } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
          }
        }
        servers.clear();
      }
      /* Read json data created by the server */
      File resultDir = new File("/home/azhu8/Documents/DM425/ggp-base/" + gameName);
      File[] fileList = resultDir.listFiles();
      for (File file : fileList) {
        if (file.getName().contains("json")) {
          try {
            FileReader reader = new FileReader(file);
            GameSummary summary = _gson.fromJson(reader, GameSummary.class);
            System.out.println(summary.matchId);
            System.out.println(summary.playClock);
            System.out.println(summary.moves.get(0).get(0));
          } catch (FileNotFoundException ex) {
            System.out.println("File not found?!");   // shouldn't get here...
          }
        }
      }
    }

    //do tourney selection
    for (int i = 0; i < CANDIDATES; i++)   // for now, randomly pick survivors
      _candidates.remove(_random.nextInt(_candidates.size()));
  }
}
