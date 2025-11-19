package sprint4.product;

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

  protected Player bluePlayer;
  protected Player redPlayer;
  protected int totalRows;
  protected int totalColumns;
  protected PlayerTurn turn;
  protected Cell[][] grid;
  protected GameState currentGameState;
  protected ArrayList<SosLine> sosLines;

  public abstract void makeMove(int row, int col);

  protected abstract void updateGameState(PlayerTurn turn, int row, int column);

  protected abstract boolean hasWon(PlayerTurn turn, int row, int column);

  protected abstract boolean isDraw();

  public SosGame() {
    currentGameState = GameState.SETUP;
    turn = PlayerTurn.BLUE;
    sosLines = new ArrayList<SosLine>();
  }

  public void setUpPlayers(char blue, char red) {
    bluePlayer = (blue == 'H') ? new Player() : new ComputerPlayer();
    redPlayer = (red == 'H') ? new Player() : new ComputerPlayer(); 
  }

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

  public void performPlayerTurn(int row, int col) {
    Player currentPlayer = getCurrentPlayer();
    int[] move;

    if (!(currentPlayer instanceof ComputerPlayer)) {
      move = new int[] { row, col };
    } else {
      move = currentPlayer.selectMove(this);
      if (move == null) {
        throw new IllegalStateException("Computer player failed to pick a move");
      }
    }
    makeMove(move[0], move[1]);
  }

  public void validateMove(int row, int col) {
    if (row < 0 || row >= totalRows || col < 0 || col >= totalColumns) {
      throw new IndexOutOfBoundsException("Move is outside the board's boundaries.");
    }
    if (grid[row][col] != Cell.EMPTY) {
      throw new IllegalStateException("Cell is already occupied.");
    }
  }

  public Cell getCell(int row, int col) {
    if (row >= 0 && row < totalRows && col >= 0 && col < totalColumns) {
      return grid[row][col];
    } else {
      return null;
    }
  }

  public int madeSos(int row, int col) {
    Cell move = grid[row][col];
    if (move == null || move == Cell.EMPTY) {
      throw new IllegalStateException("Referenced cell is empty");
    }

    ArrayList<SosLine> formedSoses;
    formedSoses = (move == Cell.O) ? checkOFormed(grid, row, col) : checkSFormed(grid, row, col);

    for (SosLine line : formedSoses) {
      sosLines.add(line);
    }
    return formedSoses.size();
  }

  public ArrayList<SosLine> checkOFormed(Cell[][] grid, int row, int col) {
    ArrayList<SosLine> formedLines = new ArrayList<SosLine>();

    // Horizontal
    if (col > 0 && col < totalColumns - 1 && grid[row][col - 1] == Cell.S && grid[row][col + 1] == Cell.S) {
      formedLines.add(new SosLine(row, col - 1, row, col + 1, turn));
    }
    // Vertical
    if (row > 0 && row < totalRows - 1 && grid[row - 1][col] == Cell.S && grid[row + 1][col] == Cell.S) {
      formedLines.add(new SosLine(row - 1, col, row + 1, col, turn));
    }
    // Diagonal (top left to bottom right)
    if (row - 1 >= 0 && col - 1 >= 0 && row + 1 < totalRows && col + 1 < totalColumns
        && grid[row - 1][col - 1] == Cell.S && grid[row + 1][col + 1] == Cell.S) {
      formedLines.add(new SosLine(row - 1, col - 1, row + 1, col + 1, turn));
    }
    // Diagonal (top right to bottom left)
    if (row - 1 >= 0 && col + 1 < totalColumns && row + 1 < totalRows && col - 1 >= 0
        && grid[row - 1][col + 1] == Cell.S && grid[row + 1][col - 1] == Cell.S) {
      formedLines.add(new SosLine(row - 1, col + 1, row + 1, col - 1, turn));
    }
    return formedLines;
  }

  public ArrayList<SosLine> checkSFormed(Cell[][] grid, int row, int col) {
    ArrayList<SosLine> formedLines = new ArrayList<SosLine>();

    // Horizontal right
    if (col + 2 < totalColumns && grid[row][col + 1] == Cell.O && grid[row][col + 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row, col + 2, turn));
    }
    // Horizontal left
    if (col - 2 >= 0 && grid[row][col - 1] == Cell.O && grid[row][col - 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row, col - 2, turn));
    }
    // Vertical down
    if (row + 2 < totalRows && grid[row + 1][col] == Cell.O && grid[row + 2][col] == Cell.S) {
      formedLines.add(new SosLine(row, col, row + 2, col, turn));
    }
    // Vertical up
    if (row - 2 >= 0 && grid[row - 1][col] == Cell.O && grid[row - 2][col] == Cell.S) {
      formedLines.add(new SosLine(row, col, row - 2, col, turn));
    }
    // Diagonal (top left to bottom right)
    if (row + 2 < totalRows && col + 2 < totalColumns && grid[row + 1][col + 1] == Cell.O
        && grid[row + 2][col + 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row + 2, col + 2, turn));
    }
    // Diagonal (top right to bottom left)
    if (row + 2 < totalRows && col - 2 >= 0 && grid[row + 1][col - 1] == Cell.O && grid[row + 2][col - 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row + 2, col - 2, turn));
    }
    // Diagonal (bottom left to top right)
    if (row - 2 >= 0 && col + 2 < totalColumns && grid[row - 1][col + 1] == Cell.O
        && grid[row - 2][col + 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row - 2, col + 2, turn));
    }
    // Diagonal (bottom right to top left)
    if (row - 2 >= 0 && col - 2 >= 0 && grid[row - 1][col - 1] == Cell.O && grid[row - 2][col - 2] == Cell.S) {
      formedLines.add(new SosLine(row, col, row - 2, col - 2, turn));
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

  public Player getCurrentPlayer() {
    return (turn == PlayerTurn.BLUE) ? bluePlayer : redPlayer;
  }

  public void switchTurn() {
    turn = (turn == PlayerTurn.BLUE) ? PlayerTurn.RED : PlayerTurn.BLUE;
  }

  public Player getBluePlayer() {
    return bluePlayer;
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public GameState getGameState() {
    return currentGameState;
  }

  public int getTotalRows() {
    return totalRows;
  }

  public int getTotalColumns() {
    return totalColumns;
  }

  public PlayerTurn getTurn() {
    return turn;
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

  public ArrayList<SosLine> getSosLines() {
    return sosLines;
  }
}