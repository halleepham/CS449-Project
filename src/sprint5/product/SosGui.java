package sprint5.product;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprint5.product.GameRecorder.Move;
import sprint5.product.SosGame.Cell;

public class SosGui extends Application {

  public enum GameMode { SIMPLE, GENERAL }
  private static final int BOARD_PIXEL_SIZE = 450;
  
  private GameRecorder gameRecorder;
  private SosGame game;

  private Square[][] squares;
  private BorderPane layout;
  private Pane lineOverlayPane;
  private VBox bluePlayerPane;
  private VBox redPlayerPane;
  private ToggleGroup rbGroupBlueMoves;
  private ToggleGroup rbGroupRedMoves;
  private RadioButton rbSimpleGame;
  private RadioButton rbGeneralGame;
  private RadioButton rbBlueHuman;
  private RadioButton rbBlueComputer;
  private RadioButton rbRedHuman;
  private RadioButton rbRedComputer;
  private RadioButton rbBlueS;
  private RadioButton rbBlueO;
  private RadioButton rbRedS;
  private RadioButton rbRedO;
  private TextField txtBoardSize;
  private Label lblCurrentTurn;
  private Label lblBluePoints;
  private Label lblRedPoints;
  private Button btnStartGame;
  private Button btnNewGame;
  private Button btnReplay;
  private CheckBox chkRecordGame;

  @Override
  public void start(Stage primaryStage) {
    layout = new BorderPane();
    Scene scene = new Scene(layout);

    resetGame();

    primaryStage.setTitle("SOS Game");
    primaryStage.setMaximized(true);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(1280);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  private void handleStartGame() {
    try {
      int size = getBoardSize();
      GameMode mode = getGameMode();
      setUpGameMode(mode);
      setBluePlayer(getSelectedBluePlayer());
      setRedPlayer(getSelectedRedPlayer());
      disablePlayerSelection();
      game.setUpNewBoard(size);
      setUpBoard(size);

      disableSettings();
      buildPointDisplays();
      
      gameRecorder = new GameRecorder();
      gameRecorder.startRecording(chkRecordGame.isSelected(), size, mode);

      if (game.getCurrentPlayer() instanceof ComputerPlayer) {
        handleComputerMove();
      }

    } catch (NumberFormatException e) {
      showError("Please enter a valid integer for the board size.");
    } catch (IllegalArgumentException e) {
      showError(e.getMessage());
    } catch (Exception e) {
      showError("Unexpected error: " + e.getMessage());
    }
  }

  private void handleMove(Square square) {
    if (game == null) {
      return;
    }
    try {
      gameRecorder.recordMove(game.getTurn(), game.getCurrentPlayer().getMove(), square.getRow(), square.getColumn());
      game.makeMove(square.getRow(), square.getColumn());
      refreshUI();
      displayGameStatus();

      if (game != null && game.getGameState() == SosGame.GameState.PLAYING
          && game.getCurrentPlayer() instanceof ComputerPlayer) {
        handleComputerMove();
      }

    } catch (IndexOutOfBoundsException e) {
      showError("That move is outside the board.");
    } catch (IllegalStateException e) {
      showError(e.getMessage());
    } catch (Exception e) {
      showError("Unexpected error: " + e.getMessage());
    }
  }

  private void handleComputerMove() {
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(event -> {
      int[] move = game.getCurrentPlayer().selectMove(game);
      if (move != null) {
        gameRecorder.recordMove(game.getTurn(), game.getCurrentPlayer().getMove(), move[0], move[1]);
        game.makeMove(move[0], move[1]);
        refreshUI();

        if (game.getGameState() != SosGame.GameState.PLAYING) {
          Platform.runLater(() -> displayGameStatus());
          return;
        }
        if (game.getCurrentPlayer() instanceof ComputerPlayer) {
          handleComputerMove();
        }
      }
    });
    pause.play();
  }
  
  private void handleReplay() {
    try {
      btnReplay.setDisable(true);
      int recordedSize = gameRecorder.loadBoardSizeFromFile();
      GameMode recordedMode = gameRecorder.loadModeFromFile();
      ArrayList<Move> recordedMoves = gameRecorder.loadMovesFromFile();
      
      setUpGameMode(recordedMode);
      setBluePlayer('H');
      setRedPlayer('H');
      game.setUpNewBoard(recordedSize);
      setUpBoard(recordedSize);
      refreshUI();
      replayMove(recordedMoves, 0);
     
    } catch (FileNotFoundException e) {
      showError(e.getMessage());
    }
  }
  
  private void replayMove(ArrayList<Move> moves, int index) {
    if (index >= moves.size()) {
      Platform.runLater(() -> displayGameStatus());
      return;
    }
    Move move = moves.get(index);
    PauseTransition pause = new PauseTransition(Duration.seconds(1));
    pause.setOnFinished(event -> {
      game.getCurrentPlayer().setMove(move.getLetter());
      game.makeMove(move.getRow(), move.getColumn());
      refreshUI();
      replayMove(moves, index + 1);
    });
    pause.play();
  }
  
  private void setUpActions() {
    btnStartGame.setOnAction(event -> handleStartGame());
    btnNewGame.setOnAction(event -> resetGame());
    btnReplay.setOnAction(event -> handleReplay());

    rbGroupBlueMoves.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        if (newValue != null && game != null) {
          game.getBluePlayer().setMove((char) newValue.getUserData());
        }
      }
    });

    rbGroupRedMoves.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
      @Override
      public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        if (newValue != null && game != null) {
          game.getRedPlayer().setMove((char) newValue.getUserData());
        }
      }
    });
  }
  
  private GameMode getGameMode() {
    return rbSimpleGame.isSelected() ? GameMode.SIMPLE : GameMode.GENERAL;
  }
  
  private void setUpGameMode(GameMode gameMode) {
    if (gameMode == GameMode.SIMPLE) {
      game = new SosSimpleGame();
    } else {
      game = new SosGeneralGame();
    }
  }
  
  private int getBoardSize() {
    String input = txtBoardSize.getText().trim();
    int size = Integer.parseInt(input);

    if (size < 3 || size > 10) {
      throw new IllegalArgumentException("Board size must be between 3 and 10");
    }
    return size;
  }
  
  private void setUpBoard(int size) {
    int width = BOARD_PIXEL_SIZE / size;
    GridPane boardPane = new GridPane();
    boardPane.setStyle("-fx-border-color: black");
    boardPane.setPadding(new Insets(30));
    boardPane.setAlignment(Pos.CENTER);

    squares = new Square[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        squares[row][col] = new Square(width, row, col);
        boardPane.add(squares[row][col].getSquare(), col, row);
      }
    }

    lineOverlayPane = new Pane();
    lineOverlayPane.setPadding(new Insets(30));
    lineOverlayPane.setPickOnBounds(false);
    lineOverlayPane.setMinSize(boardPane.getMinWidth(), boardPane.getMinHeight());
    lineOverlayPane.setMaxSize(boardPane.getMaxWidth(), boardPane.getMaxHeight());

    StackPane boardStackPane = new StackPane(boardPane, lineOverlayPane);
    layout.setCenter(boardStackPane);
  }
  
  private char getSelectedBluePlayer() {
    return rbBlueHuman.isSelected() ? 'H' : 'C';
  }
  
  private void setBluePlayer(char blue) {
    if (blue == 'H') {
      setDisableBlueMoveSelection(false);
    }
    game.setBluePlayer(blue);
  }
  
  private char getSelectedRedPlayer() {
    return rbRedHuman.isSelected() ? 'H' : 'C';
  }
  
  private void setRedPlayer(char red) {
    if (red == 'H') {
      setDisableRedMoveSelection(false);
    }
    game.setRedPlayer(red);
  }
  
  private void resetGame() {
    game = null;
    buildSettingsPane();
    buildBluePlayerPane();
    buildRedPlayerPane();
    buildInfoPane();
    layout.setCenter(new GridPane());
    setUpActions();
  }
  
  private void drawBoard() {
    String value;
    Cell cell;
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        cell = game.getCell(row, col);
        value = (cell == Cell.EMPTY) ? "" : cell.name();
        squares[row][col].setValue(value);
      }
    }
  }

  private void drawLines() {
    lineOverlayPane.getChildren().clear();
    int width = squares[0][0].getWidth();
    for (SosLine sos : game.getSosLines()) {
      Line line = new Line(squares[sos.getStartRow()][sos.getStartColumn()].getSquare().getLayoutX() + (width / 2),
          squares[sos.getStartRow()][sos.getStartColumn()].getSquare().getLayoutY() + (width / 2),
          squares[sos.getEndRow()][sos.getEndColumn()].getSquare().getLayoutX() + (width / 2),
          squares[sos.getEndRow()][sos.getEndColumn()].getSquare().getLayoutY() + (width / 2));

      line.setStroke(sos.getPlayer() == SosGame.PlayerTurn.BLUE ? Color.BLUE : Color.RED);
      line.setStrokeWidth(3);
      lineOverlayPane.getChildren().add(line);
    }
  }

  private void updateScore() {
    if (rbGeneralGame.isSelected()) {
      lblBluePoints.setText(String.valueOf(game.getBluePlayer().getPoints()));
      lblRedPoints.setText(String.valueOf(game.getRedPlayer().getPoints()));
    }
  }

  private void updateTurn() {
    lblCurrentTurn.setText(game.getTurn() == SosGame.PlayerTurn.BLUE ? "Blue" : "Red");
    lblCurrentTurn.setTextFill(game.getTurn() == SosGame.PlayerTurn.BLUE ? Color.BLUE : Color.RED);
  }

  private void refreshUI() {
    drawBoard();
    drawLines();
    updateScore();
    updateTurn();
  }
  
  private void displayGameStatus() {
    if (game.getGameState() == SosGame.GameState.PLAYING) {
      return;
    }
    if (game.getGameState() == SosGame.GameState.DRAW) {
      showGameResult("It's a draw!");
    } else if (game.getGameState() == SosGame.GameState.BLUE_WON) {
      showGameResult("Blue Player won!");
    } else if (game.getGameState() == SosGame.GameState.RED_WON) {
      showGameResult("Red Player won!");
    }
    try {
      gameRecorder.saveGameToFile();
    } catch (FileNotFoundException e) {
      showError(e.getMessage());
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Input");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showGameResult(String message) {
    Alert gameResult = new Alert(Alert.AlertType.INFORMATION);
    gameResult.setTitle("Game Over");
    gameResult.setHeaderText(null);
    gameResult.setContentText(message);
    gameResult.setGraphic(null);
    gameResult.showAndWait();
    if (chkRecordGame.isSelected()) {
      btnReplay.setDisable(false);
    }
  }
  
  private void disableSettings() {
    rbSimpleGame.setDisable(true);
    rbGeneralGame.setDisable(true);
    txtBoardSize.setDisable(true);
    btnStartGame.setDisable(true);
    chkRecordGame.setDisable(true);
  }
  
  private void setDisableBlueMoveSelection(boolean disable) {
    rbBlueS.setDisable(disable);
    rbBlueO.setDisable(disable);
  }
  
  private void setDisableRedMoveSelection(boolean disable) {
    rbRedS.setDisable(disable);
    rbRedO.setDisable(disable);
  }
  
  private void disablePlayerSelection() {
    rbBlueHuman.setDisable(true);
    rbBlueComputer.setDisable(true);
    rbRedHuman.setDisable(true);
    rbRedComputer.setDisable(true);
  }

  private void buildSettingsPane() {
    HBox settingsPane = new HBox(20);
    settingsPane.setStyle("-fx-border-color: black");
    settingsPane.setPadding(new Insets(20, 0, 20, 0));
    settingsPane.setAlignment(Pos.CENTER);

    ToggleGroup rbGroupGameMode = new ToggleGroup();
    rbSimpleGame = createRadio("Simple Game", rbGroupGameMode, true);
    rbGeneralGame = createRadio("General Game", rbGroupGameMode, false);

    Label lblBoardSize = new Label("Board size: ");
    txtBoardSize = new TextField();
    txtBoardSize.setMaxWidth(50);
    btnStartGame = new Button("Start Game");

    settingsPane.getChildren().addAll(rbSimpleGame, rbGeneralGame, lblBoardSize, txtBoardSize, btnStartGame);
    layout.setTop(settingsPane);
  }

  private void buildBluePlayerPane() {
    bluePlayerPane = new VBox(15);
    bluePlayerPane.setStyle("-fx-border-color: black");
    bluePlayerPane.setPadding(new Insets(0, 100, 0, 100));
    bluePlayerPane.setAlignment(Pos.CENTER);

    Label lblBlue = createLabel("Blue Player", Color.BLUE);

    ToggleGroup rbGroupBlueType = new ToggleGroup();
    rbBlueHuman = createRadio("Human", rbGroupBlueType, true);
    rbBlueComputer = createRadio("Computer", rbGroupBlueType, false);

    rbGroupBlueMoves = new ToggleGroup();
    rbBlueS = createRadio("S", rbGroupBlueMoves, true);
    rbBlueO = createRadio("O", rbGroupBlueMoves, false);
    setDisableBlueMoveSelection(true);
    rbBlueS.setUserData('S');
    rbBlueO.setUserData('O');

    bluePlayerPane.getChildren().addAll(lblBlue, rbBlueHuman, rbBlueS, rbBlueO, rbBlueComputer);
    layout.setLeft(bluePlayerPane);
  }

  private void buildRedPlayerPane() {
    redPlayerPane = new VBox(15);
    redPlayerPane.setStyle("-fx-border-color: black");
    redPlayerPane.setPadding(new Insets(0, 100, 0, 100));
    redPlayerPane.setAlignment(Pos.CENTER);

    Label lblRed = createLabel("Red Player", Color.RED);

    ToggleGroup rbGroupRedType = new ToggleGroup();
    rbRedHuman = createRadio("Human", rbGroupRedType, true);
    rbRedComputer = createRadio("Computer", rbGroupRedType, false);

    rbGroupRedMoves = new ToggleGroup();
    rbRedS = createRadio("S", rbGroupRedMoves, true);
    rbRedO = createRadio("O", rbGroupRedMoves, false);
    setDisableRedMoveSelection(true);
    rbRedS.setUserData('S');
    rbRedO.setUserData('O');

    redPlayerPane.getChildren().addAll(lblRed, rbRedHuman, rbRedS, rbRedO, rbRedComputer);
    layout.setRight(redPlayerPane);
  }

  private void buildInfoPane() {
    BorderPane infoPane = new BorderPane();
    infoPane.setStyle("-fx-border-color: black");
    infoPane.setPadding(new Insets(50, 0, 50, 0));
    
    VBox recordGamePane = new VBox();
    recordGamePane.setPadding(new Insets(0, 100, 0, 100));
    recordGamePane.setAlignment(Pos.CENTER);
    chkRecordGame = new CheckBox("Record Game");
    recordGamePane.getChildren().add(chkRecordGame);
    infoPane.setLeft(recordGamePane);

    HBox turnStatusPane = new HBox();
    turnStatusPane.setAlignment(Pos.CENTER);
    Label lblTurn = createLabel("Current Turn: ", Color.BLACK);
    lblCurrentTurn = createLabel("Blue", Color.BLUE);
    turnStatusPane.getChildren().addAll(lblTurn, lblCurrentTurn);
    infoPane.setCenter(turnStatusPane);

    VBox gameButtonsPane = new VBox(20);
    gameButtonsPane.setPadding(new Insets(0, 100, 0, 100));
    gameButtonsPane.setAlignment(Pos.CENTER);
    btnReplay = new Button("Replay");
    btnNewGame = new Button("New Game");
    btnReplay.setDisable(true);
    gameButtonsPane.getChildren().addAll(btnReplay, btnNewGame);
    infoPane.setRight(gameButtonsPane);

    layout.setBottom(infoPane);
  }

  private void buildPointDisplays() {
    if (getGameMode() == GameMode.GENERAL) {
      lblBluePoints = new Label("0");
      bluePlayerPane.getChildren().addAll(new Label("Points: "), lblBluePoints);

      lblRedPoints = new Label("0");
      redPlayerPane.getChildren().addAll(new Label("Points: "), lblRedPoints);
    }
  }

  private RadioButton createRadio(String text, ToggleGroup group, boolean selected) {
    RadioButton rb = new RadioButton(text);
    rb.setToggleGroup(group);
    rb.setSelected(selected);
    return rb;
  }
  
  private Label createLabel(String text, Color color) {
    Label lbl = new Label(text);
    lbl.setTextFill(color);
    lbl.setFont(Font.font(24));
    return lbl;
  }

  public class Square {
    private StackPane square;
    private Label lblValue;
    private int row;
    private int column;
    private int width;

    public Square(int width, int row, int col) {
      this.width = width;
      this.row = row;
      this.column = col;

      Rectangle border = new Rectangle(width, width);
      border.setFill(Color.TRANSPARENT);
      border.setStroke(Color.BLACK);

      lblValue = new Label("");
      lblValue.setFont(Font.font(24));

      square = new StackPane(border, lblValue);
      square.setOnMouseClicked(event -> {
        if (game != null && !(game.getCurrentPlayer() instanceof ComputerPlayer)) {
          handleMove(this);
        }
      });
    }

    public StackPane getSquare() {
      return square;
    }

    public void setValue(String value) {
      lblValue.setText(value);
    }

    public int getRow() {
      return row;
    }

    public int getColumn() {
      return column;
    }

    public int getWidth() {
      return width;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}