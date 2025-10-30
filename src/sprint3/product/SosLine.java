package sprint3.product;

public class SosLine {
  public int startRow;
  public int startColumn;
  public int endRow;
  public int endColumn;
  public SosGame.PlayerTurn player;
  
  public SosLine(int startRow, int startColumn, int endRow, int endColumn, SosGame.PlayerTurn player) {
    this.startRow = startRow;
    this.startColumn = startColumn;
    this.endRow = endRow;
    this.endColumn = endColumn;
    this.player = player;
  }
}
