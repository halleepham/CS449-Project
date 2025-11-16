package sprint4.product;

import sprint4.product.SosGame.Cell;

public class ComputerPlayer extends Player {

  @Override
  public int[] selectMove(SosGame game) {
    int[] move = pickSosMove(game);
    if (move == null) {
      move = pickSafeMove(game);
      if (move == null) {
        move = pickFirstEmptyMove(game);
      }
    }
    return move;
  }

  private int[] pickSosMove(SosGame game) {
    //TODO: Change to pick the move that makes the most soses
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == Cell.EMPTY) {
          if (game.checkOFormed(row, col).size() > 0) {
            this.setMove('O');
            return new int[] {row, col};
          } else if (game.checkSFormed(row, col).size() > 0) {
            this.setMove('S');
            return new int[] {row, col};
          }
        }
      }
    }
    return null;
  }
  
  private int[] pickSafeMove(SosGame game) {
    // TODO: implement logic after getting player logic working
    return null;
  }
  
  // temporary logic to pick first empty cell to make sure computer plays works
  private int[] pickFirstEmptyMove(SosGame game) {
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == SosGame.Cell.EMPTY) {
          return new int[] {row, col};
        }
      }
    }
    return null;
  }
}
