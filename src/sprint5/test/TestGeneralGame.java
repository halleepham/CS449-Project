package sprint5.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3.product.SosGame;
import sprint3.product.SosGeneralGame;

public class TestGeneralGame {
	
	private SosGeneralGame generalGame;

	@Before
	public void setUp() throws Exception {
		generalGame = new SosGeneralGame();
		generalGame.setUpNewBoard(3);
		generalGame.setBlueMove('S');
		generalGame.setRedMove('O');
	}

	@After
	public void tearDown() throws Exception {
	}

	// AC 6.1: Blue Player valid move that forms an SOS
	@Test
	public void testGeneralMove_BlueValidMoveFormsSos_Accepted() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
		// Blue places S
		generalGame.makeMove(0, 0);
		// Red places O
		generalGame.makeMove(1, 0);

		// Blue placed S to form SOS
		generalGame.makeMove(2, 0);

		assertEquals(SosGame.Cell.S, generalGame.getCell(2, 0));
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}
	
	// AC 6.2: Red Player valid move that forms an SOS
	@Test
	public void testGeneralMove_RedValidMoveFormsSos_Accepted() {
		generalGame.makeMove(1, 0);
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
		generalGame.makeMove(0, 0);
		generalGame.makeMove(1, 2);
		
		// Red placed O to form SOS
		generalGame.makeMove(1, 1);

    assertEquals(SosGame.Cell.O, generalGame.getCell(1, 1));
    assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
	}
	
	// AC 6.3: Blue Player valid move that doesn't form an SOS
	@Test
	public void testGeneralMove_BlueValidMoveNoSos_Accepted() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
		
		generalGame.makeMove(0, 0);
		
		assertEquals(SosGame.Cell.S, generalGame.getCell(0, 0));
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
	}
	
	// AC 6.4: Red Player valid move that doesn't form an SOS
	@Test
	public void testGeneralMove_RedValidMoveNoSos_Accepted() {
		generalGame.makeMove(0, 0);
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
		
		generalGame.makeMove(0, 1);
		
		assertEquals(SosGame.Cell.O, generalGame.getCell(0, 1));
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}
	
	// AC 6.5: Illegal move on occupied cell (general game)
	@Test
	public void testGeneralMove_MoveOnOccupiedCell_Rejected() {
		generalGame.makeMove(0, 0);
		
		try {
		  generalGame.makeMove(0, 0);
		  fail("Expected IllegalStateException for occupied cell");
		  } catch (IllegalStateException e) {
		    // Expected
		  }
		
		assertEquals(SosGame.Cell.S, generalGame.getCell(0, 0));
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
	}
	
	// AC 6.6: Illegal move outside the board (general game)
	@Test
	public void testGeneralMove_MoveOutsideBoardRow_Rejected() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
		
		try {
	        generalGame.makeMove(-1, 0);
	        fail("Expected IndexOutOfBoundsException for move outside the board");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
		
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}
	
	@Test
	public void testGeneralMove_MoveOutsideBoardColumn_Rejected() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());

		try {
			generalGame.makeMove(0, 3);
			fail("Expected IndexOutOfBoundsException for move outside the board");
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}
	
	// AC 7.1: A general SOS game win by Blue Player
	@Test
	public void testGeneralGame_GameEndsWithMoreBluePoints_BlueWins() {
	  generalGame.makeMove(0, 0);
	  generalGame.makeMove(1, 0);
	  //Blue forms SOS
	  generalGame.makeMove(2, 0);
	  // Blue turn again
	  generalGame.makeMove(0, 1);
	  generalGame.makeMove(1, 1);
	  // Blue forms SOS
	  generalGame.makeMove(2, 1);
	  generalGame.makeMove(0, 2);
	  generalGame.makeMove(1, 2);
	  
	  assertEquals(SosGame.GameState.PLAYING, generalGame.getGameState());
	  generalGame.makeMove(2, 2);
	  
	  assertTrue(generalGame.getBluePoints() > generalGame.getRedPoints());
	  assertEquals(SosGame.GameState.BLUE_WON, generalGame.getGameState());
	}
	
	// AC 7.2: A general SOS game win by Red Player
	@Test
	public void testGeneralGame_GameEndsWithMoreRedPoints_RedWins() {
	  generalGame.setBlueMove('O');
	  generalGame.setRedMove('S');
	  generalGame.makeMove(1, 0);
    generalGame.makeMove(0, 0);
    generalGame.makeMove(0, 1);
    // Red makes SOS
    generalGame.makeMove(2, 0);
    // Red makes SOS
    generalGame.makeMove(0, 2);
    generalGame.makeMove(1, 1);
    generalGame.makeMove(1, 2);
    generalGame.makeMove(2, 1);
    
    assertEquals(SosGame.GameState.PLAYING, generalGame.getGameState());
    generalGame.makeMove(2, 2);
    
    assertTrue(generalGame.getRedPoints() > generalGame.getBluePoints());
    assertEquals(SosGame.GameState.RED_WON, generalGame.getGameState());
	}
	
	// AC 7.5: A draw game (general game)
	@Test
	public void testGeneralGame_GameEndsWithEqualPoints_Draw() {
	  generalGame.setRedMove('S');
	  generalGame.makeMove(0, 0);
	  generalGame.makeMove(2, 2);
	  generalGame.makeMove(1, 0);
	  generalGame.setRedMove('O');
	  generalGame.makeMove(1, 2);
	  // Blue makes SOS
	  generalGame.makeMove(0, 2);
	  generalGame.makeMove(2, 1);
	  // Red makes SOS
	  generalGame.makeMove(0, 1);
	  generalGame.makeMove(2, 0);
	  
	  assertEquals(SosGame.GameState.PLAYING, generalGame.getGameState());
	  generalGame.makeMove(1, 1);
	  
	  assertTrue(generalGame.getBluePoints() == generalGame.getRedPoints());
	  assertEquals(SosGame.GameState.DRAW, generalGame.getGameState());
	}
}