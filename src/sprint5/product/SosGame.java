package sprint5.product;

import java.util.ArrayList;

public abstract class SosGame {

  public enum Cell {
    EMPTY, S, O
  }

  public enum GameState {
    SETUP, PLAYING, DRAW, BLUE_WON, RED_WON
  }

  public enum PlayerTurn {
    BLUE, RED
  }
  
  public enum GameMode {
    SIMPLE, GENERAL
  }
  
  private static final int[][] S_DIRECTIONS = { 
      { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 },
      { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }
  };
  
  private static final int[][] O_DIRECTIONS = {
      { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, -1 }
  };

  protected int totalRows;
  protected int totalColumns;
  protected Player bluePlayer;
  protected Player redPlayer;
  protected PlayerTurn turn;
  protected Cell[][] grid;
  protected GameState currentGameState;
  protected ArrayList<SosLine> sosLines;
  
  public SosGame() {
    currentGameState = GameState.SETUP;
    turn = PlayerTurn.BLUE;
    sosLines = new ArrayList<SosLine>();
  }

  /*
   * ===================================== 
   * ABSTRACT METHODS
   * =====================================
   */
  public abstract void makeMove(int row, int col);

  protected abstract void updateGameState(PlayerTurn turn, int row, int column);

  protected abstract boolean hasWon(PlayerTurn turn, int row, int column);

  protected abstract boolean isDraw();
  
  /*
   * ===================================== 
   * BOARD SETUP AND VALIDATION
   * =====================================
   */
  public void setUpNewBoard(int size) {
    if (size < 3 || size > 10) {
      throw new IllegalArgumentException("Board size must be between 3 and 10");
    }

    totalRows = size;
    totalColumns = size;
    grid = new Cell[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = Cell.EMPTY;
      }
    }

    currentGameState = GameState.PLAYING;
  }

  public void validateMove(int row, int col) {
    if (row < 0 || row >= totalRows || col < 0 || col >= totalColumns) {
      throw new IndexOutOfBoundsException("Move is outside the board's boundaries.");
    }
    if (grid[row][col] != Cell.EMPTY) {
      throw new IllegalStateException("Cell is already occupied.");
    }
  }

  /*
   * ===================================== 
   * SOS LOGIC
   * =====================================
   */
  public int madeSos(int row, int col) {
    Cell move = grid[row][col];
    if (move == null || move == Cell.EMPTY) {
      throw new IllegalStateException("Referenced cell is empty");
    }

    ArrayList<SosLine> formedSoses = (move == Cell.O) ? checkOFormed(grid, row, col) : checkSFormed(grid, row, col);

    sosLines.addAll(formedSoses);
    return formedSoses.size();
  }

  public ArrayList<SosLine> checkOFormed(Cell[][] grid, int row, int col) {
    ArrayList<SosLine> formedLines = new ArrayList<SosLine>();
    
    for (int[] direction : O_DIRECTIONS) {
      int r1 = row - direction[0];
      int c1 = col - direction[1];
      int r2 = row + direction[0];
      int c2 = col + direction[1];
      
      if (inBounds(r1, c1) && inBounds(r2, c2)
          && grid[r1][c1] == Cell.S
          && grid[r2][c2] == Cell.S) {
        formedLines.add(new SosLine(r1, c1, r2, c2, turn));
      }
    }
    return formedLines;
  }

  public ArrayList<SosLine> checkSFormed(Cell[][] grid, int row, int col) {
    ArrayList<SosLine> formedLines = new ArrayList<SosLine>();
    
    for (int[] direction : S_DIRECTIONS) {
      int r1 = row + direction[0];
      int c1 = col + direction[1];
      int r2 = row + (2 * direction[0]);
      int c2 = col + (2 * direction[1]);
      
      if (inBounds(r1, c1) && inBounds(r2, c2)
          && grid[r1][c1] == Cell.O
          && grid[r2][c2] == Cell.S) {
        formedLines.add(new SosLine(row, col, r2, c2, turn));
      }
    }
    return formedLines;
  }
  
  public boolean isBoardFull() {
    for (int row = 0; row < totalRows; ++row) {
      for (int col = 0; col < totalColumns; ++col) {
        if (grid[row][col] == Cell.EMPTY) {
          return false;
        }
      }
    }
    return true;
  }
  
  /*
   * ===================================== 
   * TURN AND PLAYER MANAGEMENT
   * =====================================
   */
  public void switchTurn() {
    turn = (turn == PlayerTurn.BLUE) ? PlayerTurn.RED : PlayerTurn.BLUE;
  }
  
  public void setBluePlayer(char playerType) {
    bluePlayer = (playerType == 'H') ? new Player() : new ComputerPlayer();
  }
  
  public void setRedPlayer(char playerType) {
    redPlayer = (playerType == 'H') ? new Player() : new ComputerPlayer();
  }

  public Player getCurrentPlayer() {
    return (turn == PlayerTurn.BLUE) ? bluePlayer : redPlayer;
  }
  
  /*
   * ===================================== 
   * GETTERS
   * =====================================
   */
  public Cell getCell(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalColumns) {
      return grid[row][col];
    } else {
      return null;
    }
  }
  
  public int getTotalRows() {
    return totalRows;
  }

  public int getTotalColumns() {
    return totalColumns;
  }
  
  public Player getBluePlayer() {
    return bluePlayer;
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public PlayerTurn getTurn() {
    return turn;
  }
  
  public GameState getGameState() {
    return currentGameState;
  }
  
  public ArrayList<SosLine> getSosLines() {
    return sosLines;
  }
  
  public Cell[][] getGridCopy() {
    Cell[][] copy = new Cell[totalRows][totalColumns];
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalColumns; col++) {
        copy[row][col] = grid[row][col];
      }
    }
    return copy;
  }
  
  /*
   * ===================================== 
   * HELPER METHODS
   * =====================================
   */
  private boolean inBounds(int r, int c) {
    return r >= 0 && r < totalRows && c >=0 && c < totalColumns;
  }
}