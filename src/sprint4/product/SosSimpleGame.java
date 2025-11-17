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
  public void updateGameState(PlayerTurn turn, int row, int column) {
    if (hasWon(turn, row, column)) {
      currentGameState = (turn == PlayerTurn.BLUE) ? GameState.BLUE_WON : GameState.RED_WON;
    } else if (isDraw()) {
      currentGameState = GameState.DRAW;
    }
  }

  @Override
  public boolean hasWon(PlayerTurn turn, int row, int column) {
    return (madeSos(row, column) > 0);
  }

  @Override
  public boolean isDraw() {
    return isBoardFull();
  }
}
