package sprint1.product;

abstract class SosGame {
	
	public enum GameMode{
		SIMPLE, GENERAL;
	}
	public enum Cell{
		EMPTY, S, O
	}
	public enum GameState {
		SETUP, PLAYING, DRAW, BLUE_WON, RED_WON
	}
	
	private int totalRows;
	private int totalColumns;
	
	protected Cell[][] grid;
	protected String turn;

	protected GameState currentGameState;
	protected GameMode currentGameMode;
	
	
	public SosGame() {
		
	}
	
	public void setupNewGame(int size, GameMode mode) {
		this.totalRows = size;
		this.totalColumns = size;
		this.currentGameMode = mode;
		this.grid = new Cell[size][size];
		
		for (int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				grid[i][j] = Cell.EMPTY;
			}
		}
		
		this.currentGameState = GameState.PLAYING;
		this.turn = "BLUE";
		
	}
	
	public GameState getGameState() {
		return currentGameState;
	}
	
	public void setGameState(GameState gameState) {
		currentGameState = gameState;
	}
	
	public GameMode getGameMode() {
		return currentGameMode;
	}
	
	public void setGameMode(GameMode gameMode) {
		currentGameMode = gameMode;
	}
	
	public void setSize(int size) {
		totalRows = size;
		totalColumns = size;
	}
}
