package sprint5.product;

import java.util.ArrayList;
import java.util.Random;

import sprint5.product.SosGame.Cell;

public class ComputerPlayer extends Player {

  @Override
  public int[] selectMove(SosGame game) {
    int[] move = pickSosMove(game);
    if (move == null) {
      move = pickSafeMove(game);
      if (move == null) {
        move = pickRandomMove(game);
      }
    }
    return move;
  }

  private int[] pickSosMove(SosGame game) {
    Cell[][] gridCopy = game.getGridCopy();
    int maxSosCount = 0;
    int[] bestMove = null;
    char bestLetter = 'S';
    
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == Cell.EMPTY) {
          
          ArrayList<SosLine> oFormed = game.checkOFormed(gridCopy, row, col);
          if (!oFormed.isEmpty() && oFormed.size() > maxSosCount) {
            maxSosCount = oFormed.size();
            bestMove = new int[] { row, col };
            bestLetter = 'O';
          }
          
          ArrayList<SosLine> sFormed = game.checkSFormed(gridCopy, row, col);
          if (!sFormed.isEmpty() && sFormed.size() > maxSosCount) {
            maxSosCount = sFormed.size();
            bestMove = new int[] { row, col };
            bestLetter = 'S';
          }
        }
      }
    }
    if (bestMove != null) {
      this.move = bestLetter;
    }
    return bestMove;
  }

  private int[] pickSafeMove(SosGame game) {
    Cell[][] gridCopy = game.getGridCopy();
    ArrayList<int[]> safeMoves = new ArrayList<int[]>();

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (gridCopy[row][col] == Cell.EMPTY) {
          boolean safeS = isMoveSafe(gridCopy, row, col, Cell.S, game);
          boolean safeO = isMoveSafe(gridCopy, row, col, Cell.O, game);

          if (safeS) {
            safeMoves.add(new int[] { row, col, 'S' });
          }
          if (safeO) {
            safeMoves.add(new int[] { row, col, 'O' });
          }
        }
      }
    }

    if (!safeMoves.isEmpty()) {
      int[] move = safeMoves.get(new Random().nextInt(safeMoves.size()));
      this.move = (char) move[2];
      return new int[] { move[0], move[1] };
    }
    return null;
  }

  private int[] pickRandomMove(SosGame game) {
    ArrayList<int[]> emptyCells = new ArrayList<int[]>();

    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == Cell.EMPTY) {
          emptyCells.add(new int[] { row, col });
        }
      }
    }

    if (emptyCells.isEmpty()) {
      return null;
    }

    this.move = new Random().nextBoolean() ? 'S' : 'O';
    int[] cell = emptyCells.get(new Random().nextInt(emptyCells.size()));
    return new int[] { cell[0], cell[1] };
  }

  private boolean isMoveSafe(Cell[][] grid, int row, int col, Cell cellType, SosGame game) {
    grid[row][col] = cellType;
    boolean opponentCanForm = false;

    outer: for (int r = 0; r < game.getTotalRows(); r++) {
      for (int c = 0; c < game.getTotalColumns(); c++) {
        if (grid[r][c] == Cell.EMPTY) {
          if (!game.checkSFormed(grid, r, c).isEmpty()) {
            opponentCanForm = true;
            break outer;
          }
          if (!game.checkOFormed(grid, r, c).isEmpty()) {
            opponentCanForm = true;
            break outer;
          }

        }
      }
    }
    grid[row][col] = Cell.EMPTY;
    return !opponentCanForm;
  }
}