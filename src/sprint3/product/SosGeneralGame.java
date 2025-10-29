package sprint3.product;

public class SosGeneralGame extends SosGame {
  
  private int bluePoints;
  private int redPoints;

	@Override
	public void makeMove(int row, int col) {
		validateMove(row, col);
		char moveLetter = (turn == PlayerTurn.BLUE) ? blueMove : redMove;
		Cell moveCell = (moveLetter == 'S') ? Cell.S : Cell.O;
		grid[row][col] = moveCell;
		
		updateGameState(turn, row, col);
		if (madeSos(row, col) > 0) {
			if (turn == PlayerTurn.BLUE) {
			  bluePoints += madeSos(row, col);
			} else {
			  redPoints += madeSos(row, col);
			}
		} else {
		  turn = (turn == PlayerTurn.BLUE) ? PlayerTurn.RED : PlayerTurn.BLUE;
		}
	}

	@Override
	public void updateGameState(PlayerTurn turn, int row, int column) {
	  if (isBoardFull()) {
	    if (isDraw()) {
	      currentGameState = GameState.DRAW;
	    } else if (hasWon(turn, row, column)) {
	      currentGameState = (turn == PlayerTurn.BLUE) ? GameState.BLUE_WON : GameState.RED_WON;
	    }
	  }
	}
	
	@Override
  public boolean hasWon(PlayerTurn turn, int row, int column) {
    if (turn == PlayerTurn.BLUE && bluePoints > redPoints
        || turn == PlayerTurn.RED && redPoints > bluePoints) {
      return true;
    }
    return false;
  }
	
	@Override
	public boolean isDraw() {
		return (bluePoints == redPoints);
	}

  
}