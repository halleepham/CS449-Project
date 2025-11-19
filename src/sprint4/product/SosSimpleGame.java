package sprint4.product;

public class SosSimpleGame extends SosGame {

  @Override
  public void makeMove(int row, int col) {
    validateMove(row, col);
    grid[row][col] = (getCurrentPlayer().getMove() == 'S') ? Cell.S : Cell.O;

    updateGameState(turn, row, col);
    switchTurn();
  }

  @Override
  protected void updateGameState(PlayerTurn turn, int row, int column) {
    if (hasWon(turn, row, column)) {
      currentGameState = (turn == PlayerTurn.BLUE) ? GameState.BLUE_WON : GameState.RED_WON;
    } else if (isDraw()) {
      currentGameState = GameState.DRAW;
    }
  }

  @Override
  protected boolean hasWon(PlayerTurn turn, int row, int column) {
    return (madeSos(row, column) > 0);
  }

  @Override
  protected boolean isDraw() {
    return isBoardFull();
  }
}
