package sprint1.product;

abstract class SosGame {
	
	public enum Cell{
		EMPTY, S, O
	}
	public enum GameState {
		SETUP, PLAYING, DRAW, BLUE_WON, RED_WON
	}
	public enum PlayerTurn {
		BLUE, RED
	}
	
	protected int totalRows;
	protected int totalColumns;
	protected char blueMove;
	protected char redMove;
	protected PlayerTurn turn;
	protected Cell[][] grid;
	protected GameState currentGameState;
	
	public abstract void makeMove(int row, int col) throws Exception;
	
	public abstract void updateGameState(PlayerTurn turn, int row, int column);
	
	public abstract boolean isDraw();
	
	public SosGame() {
		currentGameState = GameState.SETUP;
		blueMove = 'S';
		redMove = 'S';
		turn = PlayerTurn.BLUE;
	}
	
	public void setUpNewBoard(int size) {
		if (size < 3 || size > 10) {
			throw new IllegalArgumentException("Board size must be between 3 and 10");
		}
		
		totalRows = size;
		totalColumns = size;
		grid = new Cell[size][size];
		
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				grid[i][j] = Cell.EMPTY;
			}
		}
		
		currentGameState = GameState.PLAYING;
	}
	
	public void validateMove(int row, int col) {
		if (row < 0 || row >= totalRows || col < 0 || col >= totalColumns) {
			throw new IndexOutOfBoundsException("Move is outside the board's boundaries.");
		}
		if (grid[row][col] != Cell.EMPTY) {
			throw new IllegalStateException("Cell is already occupied.");
		}
	}
	
	public Cell getCell(int row, int col) {
		if (row >= 0 && row < totalRows && col >= 0 && col < totalColumns) {
			return grid[row][col];
		} else {
			return null;
		}
	}
	
	public GameState getGameState() {
		return currentGameState;
	}
	
	public void setGameState(GameState gameState) {
		currentGameState = gameState;
	}
	
	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalColumns() {
		return totalColumns;
	}
	
	public PlayerTurn getTurn() {
		return turn;
	}
	
	public void setBlueMove(char move) {
		blueMove = move;
	}
	
	public void setRedMove(char move) {
		redMove = move;
	}
	
	public boolean madeSos(PlayerTurn turn, int row, int col) {
		// TODO: When general game is implemented change to return int for number of SOSes made
		Cell move = grid[row][col];
		if (move == null || move == Cell.EMPTY) {
			return false;
		}
		
		// Check if O move made an SOS
		if (move == Cell.O) {
			return checkOFormed(row, col);
		}
		
		// Check if S move made an SOS
		return checkSFormed(row, col);
	}
	
	public boolean checkOFormed(int row, int col) {
		// Horizontal
		if (col > 0 && col < totalColumns - 1 
				&& grid[row][col - 1] == Cell.S && grid[row][col + 1] == Cell.S) {
			return true;
		}
		// Vertical
		if (row > 0 && row < totalRows - 1 
				&& grid[row - 1][col] == Cell.S && grid[row + 1][col] == Cell.S) {
			return true;
		}
		// Diagonal (top left to bottom right)
		if (row - 1 >= 0 && col - 1 >= 0 && row + 1 < totalRows && col + 1 < totalColumns
				&& grid[row - 1][col - 1] == Cell.S && grid[row + 1][col + 1] == Cell.S) {
			return true;
		}
		// Diagonal (top right to bottom left)
		if (row - 1 >= 0 && col + 1 < totalColumns && row + 1 < totalRows && col - 1 >= 0
				&& grid[row - 1][col + 1] == Cell.S && grid[row + 1][col - 1] == Cell.S) {
			return true;
		}
		return false;
	}
	
	public boolean checkSFormed(int row, int col) {
		// Horizontal right
		if (col + 2 < totalColumns 
				&& grid[row][col + 1] == Cell.O && grid[row][col + 2] == Cell.S) {
			return true;
		}
		// Horizontal left
		if (col - 2 >= 0 
				&& grid[row][col - 1] == Cell.O && grid[row][col - 2] == Cell.S) {
			return true;
		}
		// Vertical down
		if (row + 2 < totalRows 
				&& grid[row + 1][col] == Cell.O && grid[row + 2][col] == Cell.S) {
			return true;
		}
		// Vertical up
		if (row - 2 >= 0 
				&& grid[row - 1][col] == Cell.O && grid[row - 2][col] == Cell.S) {
			return true;
		}
		// Diagonal (top left to bottom right)
		if (row + 2 < totalRows && col + 2 < totalColumns 
				&& grid[row + 1][col + 1] == Cell.O && grid[row + 2][col + 2] == Cell.S) {
			return true;
		}
		// Diagonal (top right to bottom left)
		if (row + 2 < totalRows && col - 2 >= 0 
				&& grid[row + 1][col - 1] == Cell.O && grid[row + 2][col - 2] == Cell.S) {
			return true;
		}
		// Diagonal (bottom left to top right)
		if (row - 2 >= 0 && col + 2 < totalColumns 
				&& grid[row - 1][col + 1] == Cell.O && grid[row - 2][col + 2] == Cell.S) {
			return true;
		}
		// Diagonal (bottom right to top left)
		if (row - 2 >= 0 && col - 2 >= 0 
				&& grid[row - 1][col - 1] == Cell.O && grid[row - 2][col - 2] == Cell.S) {
			return true;
		}
		return false;
	}
}