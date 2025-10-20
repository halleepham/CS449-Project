package sprint1.product;

import sprint1.product.SosGame.Cell;

public class SosGeneralGame extends SosGame {

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
		
		
	}

	@Override
	public void updateGameState(String turn, int row, int column) {
		// TODO Auto-generated method stub
		
	}

}
