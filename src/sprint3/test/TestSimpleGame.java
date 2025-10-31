package sprint3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3.product.SosGame;
import sprint3.product.SosSimpleGame;

public class TestSimpleGame {
	
	private SosSimpleGame simpleGame;

	@Before
	public void setUp() throws Exception {
		simpleGame = new SosSimpleGame();
		simpleGame.setUpNewBoard(3);
		simpleGame.setBlueMove('S');
		simpleGame.setRedMove('O');
	}

	@After
	public void tearDown() throws Exception {
	}

	// [GPT] AC 4.1: Blue Player valid move on empty cell (simple game)
	@Test
	public void testSimpleGame_BlueValidMove_Accepted() {
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
		assertEquals(SosGame.Cell.EMPTY, simpleGame.getCell(0, 0));
		
		simpleGame.makeMove(0, 0);
		
		assertEquals(SosGame.Cell.S, simpleGame.getCell(0, 0));
		assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());
	}
	
	// AC 4.2: Red Player valid move on empty cell (simple game)
	@Test
	public void testSimpleGame_RedValidMove_Accepted() {
		simpleGame.makeMove(0, 0);
		assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());
		
		simpleGame.makeMove(1, 1);
		
		assertEquals(SosGame.Cell.O, simpleGame.getCell(1, 1));
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
	}

	// [GPT] AC 4.3: Illegal move on occupied cell (simple game)
	@Test
	public void testSimpleGame_MoveOnOccupiedCell_Rejected() {
		// Blue makes valid move first
		simpleGame.makeMove(0, 0);
		assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());

		try {
			simpleGame.makeMove(0, 0);
			fail("Expected an IllegalStateException for occupied cell");
		} catch (IllegalStateException e) {
			// Expected
		}

		assertEquals(SosGame.Cell.S, simpleGame.getCell(0, 0));
		assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());
	}
	
	// AC 4.4: Illegal move outside the board (simple game)
	@Test
	public void testSimpleGame_MoveOutsideBoardRow_Rejected() {
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
		
		try {
	        simpleGame.makeMove(-1, 0);
	        fail("Expected IndexOutOfBoundsException for move outside the board");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
		
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
	}
	
	@Test
	public void testSimpleGame_MoveOutsideBoardColumn_Rejected() {
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
		
		try {
	        simpleGame.makeMove(0, 3);
	        fail("Expected IndexOutOfBoundsException for move outside the board");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
		
		assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
	}
	
	// AC 5.1: A simple SOS game win by Blue Player
	@Test
	public void testSimpleGame_BlueFormsSOS_Wins() {
	  simpleGame.makeMove(0, 0);
	  simpleGame.makeMove(0, 1);
	  assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
	  
	  // Blue forms SOS
	  simpleGame.makeMove(0, 2);
	  
	  assertEquals(SosGame.GameState.BLUE_WON, simpleGame.getGameState());
	}
	
	// AC 5.2: A simple SOS game win by Red Player
	public void testSimpleGame_RedFormsSOS_Wins() {
    simpleGame.makeMove(0, 0);
    simpleGame.makeMove(1, 0);
    simpleGame.setRedMove('S');
    simpleGame.makeMove(0, 2);
    assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
    
    // Red forms SOS
    simpleGame.makeMove(0,  1);
    
    assertEquals(SosGame.GameState.RED_WON, simpleGame.getGameState());
  }
	
	// AC 5.3: A draw game (simple game)
	@Test
	public void testSimpleGame_NoSOSLastMove_Draw() {
	  simpleGame.setRedMove('S');
	  simpleGame.makeMove(0, 0);
	  simpleGame.makeMove(0, 1);
	  simpleGame.makeMove(0, 2);
	  simpleGame.makeMove(1, 0);
	  simpleGame.makeMove(1, 1);
	  simpleGame.makeMove(1, 2);
	  simpleGame.makeMove(2, 0);
	  simpleGame.makeMove(2, 1);
	  assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
	  assertEquals(SosGame.Cell.EMPTY, simpleGame.getCell(2, 2));
	  
	  simpleGame.makeMove(2, 2);
	  
	  assertEquals(SosGame.GameState.DRAW, simpleGame.getGameState());
	}
	
	// AC 5.4: A continuing simple game after a Blue Player move
	@Test
	public void testSimpleGame_BlueMove_NonSOS_GameContinues() {
	  assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
	  assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
	  
	  simpleGame.makeMove(1, 1);
	  
	  assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
	  assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());
	}
	
	
	// AC 5.5: A continuing simple game after a Red Player move
	@Test
	public void testSimpleGame_RedMove_NonSOS_GameContinues() {
	  simpleGame.makeMove(0, 0);
	  assertEquals(SosGame.PlayerTurn.RED, simpleGame.getTurn());
	  
	  simpleGame.makeMove(1, 1);
	  
	  assertEquals(SosGame.GameState.PLAYING, simpleGame.getGameState());
	  assertEquals(SosGame.PlayerTurn.BLUE, simpleGame.getTurn());
	}
}