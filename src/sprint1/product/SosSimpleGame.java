package sprint1.product;

public class SosSimpleGame extends SosGame {

	@Override
	public void makeMove(int row, int col) throws Exception {
		if (isValidMove(row, col)) {
			if (turn == "BLUE") {
				System.out.println("Blue turn putting down " + blueMove + " on cell " + row + ", " + col);
				if (blueMove == 'S') {
					grid[row][col] = Cell.S;
				}
				else if (blueMove == 'O'){
					grid[row][col] = Cell.O;
				}
			} else if (turn == "RED") {
				System.out.println("Red turn putting down " + redMove + " on cell " + row + ", " + col);
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
		System.out.println();
		
	}
	
	
	
}
