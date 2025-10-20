package sprint1.product;

public class SosSimpleGame extends SosGame {

	@Override
	public void makeMove(int row, int col) throws Exception {
		if (isValidMove(row, col)) {
			if (turn == "BLUE") {
				if (blueMove == 'S') {
					grid[row][col] = Cell.S;
				}
				else if (blueMove == 'O'){
					grid[row][col] = Cell.O;
				}
			} else if (turn == "RED") {
				if (redMove == 'S') {
					grid[row][col] = Cell.S;
				}
				else if (redMove == 'O') {
					grid[row][col] = Cell.O;
				}
			}
		}
		
		updateGameState(turn, row, col);
		turn = (turn == "BLUE") ? "RED" : "BLUE";
		
		
	}

	@Override
	public void updateGameState(String turn, int row, int column) {
		if (madeSos(turn, row, column)) {
			currentGameState = (turn == "BLUE") ? GameState.BLUE_WON : GameState.RED_WON;
		} else if (isDraw()) {
			currentGameState = GameState.DRAW;
		}

	}

	@Override
	public boolean isDraw() {
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
