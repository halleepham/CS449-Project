package sprint3.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint3.product.SosGame;
import sprint3.product.SosSimpleGame;
import sprint3.product.SosGame.Cell;

public class TestEmptyBoard {
	
	SosGame game;

	@Before
	public void setUp() throws Exception {
		game = new SosSimpleGame();
		game.setUpNewBoard(4);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEmptyBoard() {
		for (int row = 0; row <3; row++) {
			for (int col = 0; col < 3; col++) {
				assertEquals(Cell.EMPTY, game.getCell(row, col)); 
			}
		}
	}
	
	// AC 3.3: Invalid row index referenced
	@Test
	public void testInvalidRow_Fails() {
		assertEquals(null, game.getCell(-1, 0));
		assertEquals(null, game.getCell(4, 0));
	}
	
	// AC 3.4: Invalid column index referenced
	@Test
	public void testInvalidColumn_Fails() {
		assertEquals(null, game.getCell(0, -1));
		assertEquals(null, game.getCell(0, 4));
	}
}