package sprint4.product;

import java.util.ArrayList;
import java.util.Random;

import sprint4.product.SosGame.Cell;

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
    //TODO: Change to pick the move that makes the most soses
    Cell[][] gridCopy = game.getGridCopy();
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == Cell.EMPTY) {
          if (game.checkOFormed(gridCopy, row, col).size() > 0) {
            this.move = 'O';
            return new int[] {row, col};
          } else if (game.checkSFormed(gridCopy, row, col).size() > 0) {
            this.move = 'S';
            return new int[] {row, col};
          }
        }
      }
    }
    return null;
  }
  
  private int[] pickSafeMove(SosGame game) {
    Cell[][] gridCopy = game.getGridCopy();
    
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (gridCopy[row][col] == Cell.EMPTY) {
          if (isMoveSafe(gridCopy, row, col, Cell.S, game)) {
            this.move = 'S';
            return new int[] {row, col};
          }
          if (isMoveSafe(gridCopy, row, col, Cell.O, game)) {
            this.move = 'O';
            return new int[] {row, col};
          }
        }
      }
    }
    return null;
  }
  
  // temporary logic to pick first empty cell to make sure computer plays works
  private int[] pickRandomMove(SosGame game) {
    ArrayList<int[]> emptyCells = new ArrayList<int[]>();
    
    for (int row = 0; row < game.getTotalRows(); row ++) {
      for (int col = 0; col < game.getTotalColumns(); col ++) {
        if (game.getCell(row, col) == Cell.EMPTY) {
          emptyCells.add(new int[] {row, col});
        }
      }
    }
    
    if (emptyCells.size() == 0) {
      return null;
    }
    
    this.move = new Random().nextBoolean() ? 'S' : 'O';
    int[] cell = emptyCells.get(new Random().nextInt(emptyCells.size()));
    return new int[] {cell[0], cell[1]};
  }
  
  private boolean isMoveSafe(Cell[][] grid, int row, int col, Cell cellType, SosGame game) {
    grid[row][col] = cellType;
    boolean opponentCanForm = false;
    
    outer:
    for (int r = 0; r < game.getTotalRows(); r++) {
      for (int c = 0; c < game.getTotalColumns(); c++) {
        if (grid[r][c] == Cell.EMPTY) {
          if (game.checkSFormed(grid, r, c).size() > 0) {
            opponentCanForm = true;
            break outer;
          }
          if (game.checkOFormed(grid, r, c).size() > 0) {
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
