package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosGame;
import sprint1.product.SosSimpleGame;

public class TestSimpleMoves {
	
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
}
