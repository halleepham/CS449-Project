package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosGeneralGame;

public class TestGeneralMoves {
	
	private SosGeneralGame generalGame;

	@Before
	public void setUp() throws Exception {
		generalGame = new SosGeneralGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGeneralGame_BleuValidMoveFormsSos_Accepted() {
		
	}
	
	@Test
	public void testGeneralGame_RedValidMoveFormsSos_Accepted() {
		
	}
	
	@Test
	public void testGeneralGame_BlueValidMoveNoSos_Accepted() {
		
	}
	
	@Test
	public void testGeneralGame_RedValidMoveNoSos_Accepted() {
		
	}
	
	@Test
	public void testGeneralGame_MoveOnOccupiedCell_Rejected() {
		
	}
	
	@Test
	public void testGeneralGame_MoveOutsideBoard_Rejected() {
		
	}

}
