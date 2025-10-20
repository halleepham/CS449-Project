package sprint1.product;

public class SosGeneralGame extends SosGame {

	@Override
	public void makeMove(int row, int col) {
		validateMove(row, col);
		char moveLetter = (turn == PlayerTurn.BLUE) ? blueMove : redMove;
		Cell moveCell = (moveLetter == 'S') ? Cell.S : Cell.O;
		grid[row][col] = moveCell;
		
		// updateGameState(turn, row, col);
		if (!madeSos(turn, row, col)) {
			turn = (turn == PlayerTurn.BLUE) ? PlayerTurn.RED : PlayerTurn.BLUE;
		}
	}

	@Override
	public void updateGameState(PlayerTurn turn, int row, int column) {
		// TODO: Implement game state update logic
	}
	
	@Override
	public boolean isDraw() {
		// TODO: Implement draw check
		return false;
	}
	
	public boolean boardFull() {
		for (int row = 0; row < totalRows; ++row) {
			for (int col = 0; col < totalColumns; ++col) {
				if (grid[row][col] == Cell.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

}
