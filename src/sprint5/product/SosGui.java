package sprint5.product;

import java.io.FileNotFoundException;

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

public class SosGui extends Application {

  private static final int BOARD_PIXEL_SIZE = 450;
  private GameRecorder gameRecorder;
  static private SosGame game;

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

  private void resetGame() {
    game = null;
    buildSettingsPane();
    buildBluePlayerPane();
    buildRedPlayerPane();
    buildInfoPane();
    layout.setCenter(new GridPane());
    setUpActions();
  }

  private void buildSettingsPane() {
    HBox settingsPane = new HBox(20);
    settingsPane.setStyle("-fx-border-color: black");
    settingsPane.setPadding(new Insets(20, 0, 20, 0));
    settingsPane.setAlignment(Pos.CENTER);

    rbSimpleGame = new RadioButton("Simple Game");
    rbGeneralGame = new RadioButton("General Game");
    ToggleGroup rbGroupGameMode = new ToggleGroup();
    rbSimpleGame.setToggleGroup(rbGroupGameMode);
    rbGeneralGame.setToggleGroup(rbGroupGameMode);
    rbSimpleGame.setSelected(true);

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

    Label lblBlue = new Label("Blue Player");
    lblBlue.setTextFill(Color.BLUE);
    lblBlue.setFont(Font.font(24));

    ToggleGroup rbGroupBlueType = new ToggleGroup();
    rbBlueHuman = new RadioButton("Human");
    rbBlueComputer = new RadioButton("Computer");
    rbBlueHuman.setToggleGroup(rbGroupBlueType);
    rbBlueComputer.setToggleGroup(rbGroupBlueType);
    rbBlueHuman.setSelected(true);

    rbGroupBlueMoves = new ToggleGroup();
    rbBlueS = new RadioButton("S");
    rbBlueO = new RadioButton("O");
    rbBlueS.setDisable(true);
    rbBlueO.setDisable(true);
    rbBlueS.setUserData('S');
    rbBlueO.setUserData('O');
    rbBlueS.setToggleGroup(rbGroupBlueMoves);
    rbBlueO.setToggleGroup(rbGroupBlueMoves);
    rbBlueS.setSelected(true);

    bluePlayerPane.getChildren().addAll(lblBlue, rbBlueHuman, rbBlueS, rbBlueO, rbBlueComputer);
    layout.setLeft(bluePlayerPane);
  }

  private void buildRedPlayerPane() {
    redPlayerPane = new VBox(15);
    redPlayerPane.setStyle("-fx-border-color: black");
    redPlayerPane.setPadding(new Insets(0, 100, 0, 100));
    redPlayerPane.setAlignment(Pos.CENTER);

    Label lblRed = new Label("Red Player");
    lblRed.setTextFill(Color.RED);
    lblRed.setFont(Font.font(24));

    ToggleGroup rbGroupRedType = new ToggleGroup();
    rbRedHuman = new RadioButton("Human");
    rbRedComputer = new RadioButton("Computer");
    rbRedHuman.setToggleGroup(rbGroupRedType);
    rbRedComputer.setToggleGroup(rbGroupRedType);
    rbRedHuman.setSelected(true);

    rbGroupRedMoves = new ToggleGroup();
    rbRedS = new RadioButton("S");
    rbRedO = new RadioButton("O");
    rbRedS.setDisable(true);
    rbRedO.setDisable(true);
    rbRedS.setUserData('S');
    rbRedO.setUserData('O');
    rbRedS.setToggleGroup(rbGroupRedMoves);
    rbRedO.setToggleGroup(rbGroupRedMoves);
    rbRedS.setSelected(true);

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
    Label lblTurn = new Label("Current Turn: ");
    lblTurn.setFont(Font.font(24));
    lblCurrentTurn = new Label("Blue");
    lblCurrentTurn.setFont(Font.font(24));
    lblCurrentTurn.setTextFill(Color.BLUE);
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
    lblBluePoints = new Label("0");
    bluePlayerPane.getChildren().addAll(new Label("Points: "), lblBluePoints);

    lblRedPoints = new Label("0");
    redPlayerPane.getChildren().addAll(new Label("Points: "), lblRedPoints);
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

  private void setUpGameMode() {
    if (rbSimpleGame.isSelected()) {
      game = new SosSimpleGame();
    } else {
      game = new SosGeneralGame();
    }
  }

  private void setPlayers() {
    char bluePlayer;
    char redPlayer;

    rbBlueHuman.setDisable(true);
    rbBlueComputer.setDisable(true);
    rbRedHuman.setDisable(true);
    rbRedComputer.setDisable(true);

    if (rbBlueHuman.isSelected()) {
      rbBlueS.setDisable(false);
      rbBlueO.setDisable(false);
      bluePlayer = 'H';
    } else {
      bluePlayer = 'C';
    }

    if (rbRedHuman.isSelected()) {
      rbRedS.setDisable(false);
      rbRedO.setDisable(false);
      redPlayer = 'H';
    } else {
      redPlayer = 'C';
    }

    game.setUpPlayers(bluePlayer, redPlayer);
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

  private int getBoardSize() {
    String input = txtBoardSize.getText().trim();
    int size = Integer.parseInt(input);

    if (size < 3 || size > 10) {
      throw new IllegalArgumentException("Board size must be between 3 and 10");
    }
    return size;
  }

  private void handleStartGame() {
    try {
      int size = getBoardSize();
      setUpGameMode();
      setPlayers();
      game.setUpNewBoard(size);
      setUpBoard(size);

      // Disable controls in settings pane
      rbSimpleGame.setDisable(true);
      rbGeneralGame.setDisable(true);
      txtBoardSize.setDisable(true);
      btnStartGame.setDisable(true);
      chkRecordGame.setDisable(true);

      if (rbGeneralGame.isSelected()) {
        buildPointDisplays();
      }
      
      gameRecorder = new GameRecorder();
      gameRecorder.startRecording(chkRecordGame.isSelected(), size, rbSimpleGame.isSelected() ? "SIMPLE" : "GENERAL");

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
    //TODO: refresh UI with blank board and simulate game with recorded moves
    // start new game with recorded size and mode
    // set up regular Players()
    // loop through moves
      // set Player move and call makeMove()
      // refreshUI
    //displayGameStatus()
  }

  private void drawBoard() {
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        if (game.getCell(row, col) == SosGame.Cell.S) {
          squares[row][col].setValue("S");
        } else if (game.getCell(row, col) == SosGame.Cell.O) {
          squares[row][col].setValue("O");
        } else {
          squares[row][col].setValue("");
        }
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
    btnReplay.setDisable(false);
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