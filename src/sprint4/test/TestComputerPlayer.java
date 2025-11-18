package sprint4.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint4.product.ComputerPlayer;
import sprint4.product.Player;
import sprint4.product.SosGame;
import sprint4.product.SosGame.Cell;
import sprint4.product.SosGame.GameState;
import sprint4.product.SosGame.PlayerTurn;
import sprint4.product.SosGeneralGame;
import sprint4.product.SosSimpleGame;

public class TestComputerPlayer {

  private SosSimpleGame simpleGame;
  private SosGeneralGame generalGame;
  private ComputerPlayer computer;
  private Player human;

  @Before
  public void setUp() throws Exception {
    simpleGame = new SosSimpleGame();
    generalGame = new SosGeneralGame();
    computer = new ComputerPlayer();
    human = new Player();
  }

  @After
  public void tearDown() throws Exception {
  }

  // AC 8.1: Computer is Blue Player and makes first move
  @Test
  public void testSimpleGame_ComputerFirstMove_RandomMove() {
    simpleGame.setUpNewBoard(3);
    simpleGame.setUpPlayers(computer, human);
    assertEquals(PlayerTurn.BLUE, simpleGame.getTurn());
    
    simpleGame.performPlayerTurn(0, 0);
    boolean hasMoved = false;
    for (int r = 0; r < simpleGame.getTotalRows(); r++) {
      for (int c = 0; c < simpleGame.getTotalColumns(); c++) {
        if (simpleGame.getCell(r, c) != Cell.EMPTY) {
          hasMoved = true;
        }
      }
    }

    assertTrue(hasMoved);
    assertEquals(PlayerTurn.RED, simpleGame.getTurn());
  }

  // AC 8.2: Winning move by computer in simple game
  @Test
  public void testSimpleGame_Computer_WinningMove() {
    simpleGame.setUpNewBoard(3);
    simpleGame.setUpPlayers(computer, human);

    simpleGame.makeMove(0, 0);
    simpleGame.makeMove(0, 2);
    assertEquals(PlayerTurn.BLUE, simpleGame.getTurn());
    int[] move = computer.selectMove(simpleGame);
    simpleGame.performPlayerTurn(0, 0);

    assertEquals(0, move[0]);
    assertEquals(1, move[1]);
    assertEquals(Cell.O, simpleGame.getCell(0, 1));
    assertEquals(GameState.BLUE_WON, simpleGame.getGameState());
  }

  // AC 8.3: A safe move by a computer in a simple game
  @Test
  public void testSimpleGame_Computer_SafeMove() {
    simpleGame.setUpNewBoard(3);
    simpleGame.setUpPlayers(computer, human);
    
    simpleGame.makeMove(0, 0);
    simpleGame.makeMove(0, 1);
    int[] move = computer.selectMove(simpleGame);
    simpleGame.performPlayerTurn(0, 0);
    
    // Doesn't make either of the two moves that sets up for SOS
    assertFalse(move[0] == 1 && (move[1] == 1 || move[1] == 0));
    assertNotEquals(Cell.O, simpleGame.getCell(1, 0));
    assertEquals(PlayerTurn.RED, simpleGame.getTurn());
  }
  
  // AC 8.4: A random move by a computer in a simple game
  @Test
  public void testSimpleGame_Computer_RandomMove() {
    simpleGame.setUpNewBoard(4);
    simpleGame.setUpPlayers(computer, human);
    
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 4; c++) {
        simpleGame.makeMove(r, c);
      }
    }
    simpleGame.makeMove(3, 0);
    simpleGame.makeMove(3, 3);
    int[] move = computer.selectMove(simpleGame);
    simpleGame.performPlayerTurn(0, 0);
    
    assertTrue(move[0] == 3 && (move[1] == 1 || move[1] == 2));
    assertEquals(PlayerTurn.RED, simpleGame.getTurn());
  }
  
  
  
}
