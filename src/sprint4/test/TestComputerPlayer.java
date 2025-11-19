package sprint4.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint4.product.Player;
import sprint4.product.SosGame.Cell;
import sprint4.product.SosGame.GameState;
import sprint4.product.SosGame.PlayerTurn;
import sprint4.product.SosGeneralGame;
import sprint4.product.SosSimpleGame;

public class TestComputerPlayer {

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

  // AC 8.1: Computer is Blue Player and makes first move
  @Test
  public void testSimpleGame_ComputerFirstMove_RandomMove() {
    simpleGame.setUpNewBoard(3);
    simpleGame.setUpPlayers('C', 'H');
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
    simpleGame.setUpPlayers('C', 'H');
    Player computer = simpleGame.getBluePlayer();

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
    simpleGame.setUpPlayers('C', 'H');
    Player computer = simpleGame.getBluePlayer();
    Player human = simpleGame.getRedPlayer();

    simpleGame.makeMove(1, 0);
    human.setMove('O');
    simpleGame.makeMove(0, 2);
    int[] move = computer.selectMove(simpleGame);
    simpleGame.performPlayerTurn(0, 0);

    // Doesn't make either of the two moves that sets up for SOS
    assertFalse(move[0] == 1 && move[1] == 1 && simpleGame.getCell(1, 1) == Cell.O);
    assertFalse(move[0] == 1 && move[1] == 2 && simpleGame.getCell(1, 1) == Cell.S);
    assertEquals(PlayerTurn.RED, simpleGame.getTurn());
  }

  // AC 8.4: A random move by a computer in a simple game
  @Test
  public void testSimpleGame_Computer_RandomMove() {
    simpleGame.setUpNewBoard(4);
    simpleGame.setUpPlayers('C', 'H');
    Player computer = simpleGame.getBluePlayer();

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

  // AC 9.1: Computer is Blue Player and makes first move general game
  @Test
  public void testGeneralGame_ComputerFirstMove_RandomMove() {
    generalGame.setUpNewBoard(3);
    generalGame.setUpPlayers('C', 'H');
    assertEquals(PlayerTurn.BLUE, generalGame.getTurn());

    generalGame.performPlayerTurn(0, 0);
    boolean hasMoved = false;
    for (int r = 0; r < generalGame.getTotalRows(); r++) {
      for (int c = 0; c < generalGame.getTotalColumns(); c++) {
        if (generalGame.getCell(r, c) != Cell.EMPTY) {
          hasMoved = true;
        }
      }
    }

    assertTrue(hasMoved);
    assertEquals(PlayerTurn.RED, generalGame.getTurn());
  }

  // AC 9.2: Sos move by computer in general game
  @Test
  public void testGeneralGame_Computer_SosMove() {
    generalGame.setUpNewBoard(3);
    generalGame.setUpPlayers('C', 'H');
    Player computer = generalGame.getBluePlayer();

    generalGame.makeMove(0, 0);
    generalGame.makeMove(2, 2);
    assertEquals(PlayerTurn.BLUE, generalGame.getTurn());
    int[] move = computer.selectMove(generalGame);
    generalGame.performPlayerTurn(0, 0);

    assertEquals(1, move[0]);
    assertEquals(1, move[1]);
    assertEquals(Cell.O, generalGame.getCell(1, 1));
    assertEquals(GameState.PLAYING, generalGame.getGameState());
    assertEquals(PlayerTurn.BLUE, generalGame.getTurn());
  }

  // AC 9.3: A safe move by a computer in a general game
  @Test
  public void testGeneralGame_Computer_SafeMove() {
    generalGame.setUpNewBoard(3);
    generalGame.setUpPlayers('C', 'H');
    Player computer = generalGame.getBluePlayer();
    Player human = generalGame.getRedPlayer();

    generalGame.makeMove(1, 0);
    human.setMove('O');
    generalGame.makeMove(0, 2);
    int[] move = computer.selectMove(generalGame);
    generalGame.performPlayerTurn(0, 0);

    // Doesn't make either of the two moves that sets up for SOS
    assertFalse(move[0] == 1 && move[1] == 1 && generalGame.getCell(1, 1) == Cell.O);
    assertFalse(move[0] == 1 && move[1] == 2 && generalGame.getCell(1, 1) == Cell.S);
    assertEquals(PlayerTurn.RED, generalGame.getTurn());
  }

  // AC 9.4: A random move by a computer in a general game
  @Test
  public void testGeneralGame_Computer_RandomMove() {
    generalGame.setUpNewBoard(4);
    generalGame.setUpPlayers('C', 'H');
    Player computer = generalGame.getBluePlayer();

    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 4; c++) {
        generalGame.makeMove(r, c);
      }
    }
    generalGame.makeMove(3, 0);
    generalGame.makeMove(3, 3);
    int[] move = computer.selectMove(generalGame);
    generalGame.performPlayerTurn(0, 0);

    assertTrue(move[0] == 3 && (move[1] == 1 || move[1] == 2));
    assertEquals(PlayerTurn.RED, generalGame.getTurn());
  }

  // Tests not directly related to AC
  @Test
  public void testCompleteSimpleGame_HumanBlue_ComputerRed() {
    simpleGame.setUpNewBoard(3);
    simpleGame.setUpPlayers('H', 'C');

    while (simpleGame.getGameState() == GameState.PLAYING) {
      if (simpleGame.getTurn() == PlayerTurn.BLUE) {
        boolean moved = false;
        for (int r = 0; r < simpleGame.getTotalRows() && !moved; r++) {
          for (int c = 0; c < simpleGame.getTotalColumns() && !moved; c++) {
            if (simpleGame.getCell(r, c) == Cell.EMPTY) {
              simpleGame.performPlayerTurn(r, c);
              moved = true;
            }
          }
        }
      } else {
        int[] move = simpleGame.getCurrentPlayer().selectMove(simpleGame);
        simpleGame.performPlayerTurn(move[0], move[1]);
      }
    }

    assertTrue(simpleGame.getGameState() == GameState.BLUE_WON || simpleGame.getGameState() == GameState.RED_WON
        || simpleGame.getGameState() == GameState.DRAW);
  }

  @Test
  public void testCompleteGeneralGame_ComputerBlue_HumanRed() {
    generalGame.setUpPlayers('C', 'R');
    generalGame.setUpNewBoard(3);

    generalGame.getRedPlayer().setMove('O');

    while (generalGame.getGameState() == GameState.PLAYING) {
      if (generalGame.getTurn() == PlayerTurn.BLUE) {
        int[] move = generalGame.getBluePlayer().selectMove(generalGame);
        generalGame.performPlayerTurn(move[0], move[1]);
      } else {
        boolean moved = false;
        for (int r = 0; r < generalGame.getTotalRows() && !moved; r++) {
          for (int c = 0; c < generalGame.getTotalColumns() && !moved; c++) {
            if (generalGame.getCell(r, c) == Cell.EMPTY) {
              generalGame.performPlayerTurn(r, c);
              moved = true;
            }
          }
        }
      }
    }

    assertTrue(generalGame.getGameState() == GameState.BLUE_WON || generalGame.getGameState() == GameState.RED_WON
        || generalGame.getGameState() == GameState.DRAW);
  }

  @Test
  public void testCompleteSimpleGame_ComputerVsComputer() {
    simpleGame.setUpPlayers('C', 'C');
    simpleGame.setUpNewBoard(3);

    while (simpleGame.getGameState() == GameState.PLAYING) {
      int[] move = simpleGame.getCurrentPlayer().selectMove(simpleGame);
      simpleGame.performPlayerTurn(move[0], move[1]);
    }

    assertTrue(simpleGame.getGameState() == GameState.BLUE_WON || simpleGame.getGameState() == GameState.RED_WON
        || simpleGame.getGameState() == GameState.DRAW);
  }

  
  @Test
  public void testCompleteGeneralGame_ComputerVsComputer() {
    generalGame.setUpPlayers('C', 'C');
   generalGame.setUpNewBoard(3);

      while (generalGame.getGameState() == GameState.PLAYING) {
          int[] move = generalGame.getCurrentPlayer().selectMove(generalGame);
          generalGame.performPlayerTurn(move[0], move[1]);
      }

      assertTrue(generalGame.getGameState() == GameState.BLUE_WON || generalGame.getGameState() == GameState.RED_WON
          || generalGame.getGameState() == GameState.DRAW);
  }

}