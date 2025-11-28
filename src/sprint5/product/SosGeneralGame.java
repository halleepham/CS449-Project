package sprint5.product;

public class SosGeneralGame extends SosGame {

  @Override
  public void makeMove(int row, int col) {
    validateMove(row, col);
    grid[row][col] = (getCurrentPlayer().getMove() == 'S') ? Cell.S : Cell.O;

    int sosMade = madeSos(row, col);
    if (sosMade > 0) {
      getCurrentPlayer().addPoints(sosMade);
    } else {
      switchTurn();
    }
    updateGameState(turn, row, col);
  }

  @Override
  protected void updateGameState(PlayerTurn turn, int row, int column) {
    if (isBoardFull()) {
      if (isDraw()) {
        currentGameState = GameState.DRAW;
      } else if (hasWon(turn, row, column)) {
        currentGameState = (turn == PlayerTurn.BLUE) ? GameState.BLUE_WON : GameState.RED_WON;
      } else if (!hasWon(turn, row, column)) {
        currentGameState = (turn == PlayerTurn.BLUE) ? GameState.RED_WON : GameState.BLUE_WON;
      }
    }
  }

  @Override
  protected boolean hasWon(PlayerTurn turn, int row, int column) {
    return (turn == PlayerTurn.BLUE && bluePlayer.getPoints() > redPlayer.getPoints()
        || turn == PlayerTurn.RED && redPlayer.getPoints() > bluePlayer.getPoints());
  }

  @Override
  protected boolean isDraw() {
    return (bluePlayer.getPoints() == redPlayer.getPoints());
  }
}