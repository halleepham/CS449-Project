package sprint1.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint1.product.SosGeneralGame;
import sprint1.product.SosSimpleGame;


public class TestSosGameSetup {
	
	private SosSimpleGame simpleGame;
	private SosGeneralGame generalGame;

	@Before
	public void setUp() throws Exception {
		simpleGame = new SosSimpleGame();
		generalGame = new SosGeneralGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	// AC 1.1: Valid board size input
	@Test
	public void testBoardSize_3_Accepted() {
		
	}
	
	@Test
	public void testBoardSize_10_Accepted() {
		
	}
	
	@Test
	public void testBoardSize_5_Accepted() {
		
	}
	
	// AC 1.2: Invalid board size input
	@Test
	public void testBoardSize_LessThan3_Rejected() {
		
	}
	
	@Test
	public void testBoardSize_GreaterThan10_Rejected() {
		
	}
	
	@Test
	public void testBoardSize_NonNumeric_Rejected() {
		
	}
	
	// AC 2.1: Game mode selection simple
	@Test
	public void testSimpleGameCreated_Succeeds() {
		
	}
	
	// AC 2.2: Game mode selection general
	@Test
	public void testGeneralGameCreated_Succeeds() {
		
	}
	
	// AC 3.1: Successful start of new game
	@Test
	public void testStartNewGame_Succeeds() {
		
	}
	
	// AC 3.2: Unsuccessful start of new game
	public void testStartNewGame_InvalidBoard_Fails() {
		
	}

}
