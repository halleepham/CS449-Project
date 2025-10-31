package sprint3.product;

public class SosLine {
  private int startRow;
  private int startColumn;
  private int endRow;
  private int endColumn;
  private SosGame.PlayerTurn player;
  
  public SosLine(int startRow, int startColumn, int endRow, int endColumn, SosGame.PlayerTurn player) {
    this.startRow = startRow;
    this.startColumn = startColumn;
    this.endRow = endRow;
    this.endColumn = endColumn;
    this.player = player;
  }

  public int getStartRow() {
    return startRow;
  }

  public int getStartColumn() {
    return startColumn;
  }

  public int getEndRow() {
    return endRow;
  }

  public int getEndColumn() {
    return endColumn;
  }

  public SosGame.PlayerTurn getPlayer() {
    return player;
  }
}