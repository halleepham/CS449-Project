package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosGame;
import sprint1.product.SosGeneralGame;

public class TestGeneralMoves {
	
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
	public void testGeneralGame_BlueValidMoveFormsSos_Accepted() {
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
	public void testGeneralGame_RedValidMoveFormsSos_Accepted() {
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
	public void testGeneralGame_BlueValidMoveNoSos_Accepted() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
		
		generalGame.makeMove(0, 0);
		
		assertEquals(SosGame.Cell.S, generalGame.getCell(0, 0));
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
	}
	
	// AC 6.4: Red Player valid move that doesn't form an SOS
	@Test
	public void testGeneralGame_RedValidMoveNoSos_Accepted() {
		generalGame.makeMove(0, 0);
		assertEquals(SosGame.PlayerTurn.RED, generalGame.getTurn());
		
		generalGame.makeMove(0, 1);
		
		assertEquals(SosGame.Cell.O, generalGame.getCell(0, 1));
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}
	
	// AC 6.5: Illegal move on occupied cell (general game)
	@Test
	public void testGeneralGame_MoveOnOccupiedCell_Rejected() {
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
	public void testGeneralGame_MoveOutsideBoard_Rejected() {
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
		
		try {
	        generalGame.makeMove(-1, 0);
	        fail("Expected IndexOutOfBoundsException for move outside the board");
	    } catch (IndexOutOfBoundsException e) {
	        // Expected
	    }
		
		assertEquals(SosGame.PlayerTurn.BLUE, generalGame.getTurn());
	}

}
