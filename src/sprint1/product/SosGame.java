package sprint1.product;

abstract class SosGame {
	
	public enum Cell{
		EMPTY, S, O
	}
	
	protected Cell[][] grid;
	protected String turn;
	
	public enum GameState {
		PLAYING, DRAW, BLUE_WON, RED_WON
	}
	
	protected GameState currentGameState;
	
	
	public SosGame() {

	}
}
