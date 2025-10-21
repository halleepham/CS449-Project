package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosGeneralGame;
import sprint1.product.SosSimpleGame;
import sprint1.product.SosGame;
import sprint1.product.SosGame.Cell;
import sprint1.product.SosGame.GameState;
import sprint1.product.SosGame.PlayerTurn;


public class TestSosGameSetup {
	
	private SosGame game;

	@Before
	public void setUp() throws Exception {
		game = new SosSimpleGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	// AC 1.1: Valid board size input
	@Test
	public void testBoardSize_3_Accepted() {
		game.setUpNewBoard(3);
		assertEquals(3, game.getTotalRows());
		assertEquals(3, game.getTotalColumns());
	}
	
	@Test
	public void testBoardSize_10_Accepted() {
		game.setUpNewBoard(10);
		assertEquals(10, game.getTotalRows());
		assertEquals(10, game.getTotalColumns());
	}
	
	@Test
	public void testBoardSize_5_Accepted() {
		game.setUpNewBoard(5);
		assertEquals(5, game.getTotalRows());
		assertEquals(5, game.getTotalColumns());
	}
	
	// AC 1.2: Invalid board size input
	@Test(expected = IllegalArgumentException.class)
	public void testBoardSize_LessThan3_Rejected() {
		game.setUpNewBoard(2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardSize_GreaterThan10_Rejected() {
		game.setUpNewBoard(11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBoardSize_NegativeInt_Rejected() {
		game.setUpNewBoard(-1);
	}
	
	// AC 2.1: Game mode selection simple
	@Test
	public void testSimpleGameCreated_Succeeds() {
		game = new SosSimpleGame();
		assertTrue(game instanceof SosSimpleGame);
	}
	
	// AC 2.2: Game mode selection general
	@Test
	public void testGeneralGameCreated_Succeeds() {
		game = new SosGeneralGame();
		assertTrue(game instanceof SosGeneralGame);
	}
	
	// AC 3.1: Successful start of new game
	@Test
	public void testStartNewGame_Succeeds() {
		game = new SosSimpleGame();
		game.setUpNewBoard(4);
		assertEquals(GameState.PLAYING, game.getGameState());
		assertEquals(4, game.getTotalRows());
		assertEquals(4, game.getTotalColumns());
		for (int i = 0; i < game.getTotalRows(); i++) {
			for (int j = 0; j < game.getTotalColumns(); j++) {
				assertTrue(game.getCell(i, j) == Cell.EMPTY);
			}
		}
		assertEquals(PlayerTurn.BLUE, game.getTurn());
	}
	
	// AC 3.2: Unsuccessful start of new game
	@Test(expected = IllegalArgumentException.class)
	public void testStartNewGame_InvalidBoard_Fails() {
		game = new SosSimpleGame();
		game.setUpNewBoard(-1);
	}

}
