import java.util.List;
/** DTO for a game summary created by GGP server */
public class GameSummary {
  public int startClock;
  public List<Integer> goalValues;
  public String randomToken;
  public List<String> playerNamesFromHost;
  public List<String> states;
  public String tournamentNameFromHost;
  public int playClock;
  public List<List<String>> moves;
  public long startTime;
  public List<Long> stateTimes;
  public boolean scrambled;
  public String matchId;
  public List<List<String>> errors;
  public boolean isCompleted;
  public boolean isAborted;
  public int previewClock;
}
