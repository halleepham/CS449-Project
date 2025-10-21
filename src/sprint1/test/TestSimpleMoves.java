package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosSimpleGame;

public class TestSimpleMoves {
	
	private SosSimpleGame simpleGame;

	@Before
	public void setUp() throws Exception {
		simpleGame = new SosSimpleGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimpleGame_BlueValidMove_Accepted() {
		
	}
	
	@Test
	public void testSimpleGame_RedValidMove_Accepted() {
		
	}

	@Test
	public void testSimpleGame_MoveOnOccupiedCell_Rejected(){
		
	}
	
	@Test
	public void testSimpleGame_MoveOutsideBoard_Rejected() {
		
	}
}
