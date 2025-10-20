package sprint1.product;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SosGui extends Application {

	private static final int BOARD_PIXEL_SIZE = 450;
	static private SosGame game;
	
	private Square[][] squares;
	private BorderPane layout;
	private ToggleGroup rbGroupGameMode;
	private ToggleGroup rbGroupBlueType;
	private ToggleGroup rbGroupRedType;
	private ToggleGroup rbGroupBlueMoves;
	private ToggleGroup rbGroupRedMoves;
	private RadioButton rbSimpleGame;
	private RadioButton rbGeneralGame;
	private RadioButton rbBlueS;
	private RadioButton rbBlueO;
	private RadioButton rbRedS;
	private RadioButton rbRedO;
	private TextField txtBoardSize;
	private Label lblCurrentTurn;
	private Button btnStartGame;

	@Override
	public void start(Stage primaryStage) {
		layout = new BorderPane();
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		buildPlayerPanes();
		buildInfoPane();
		layout.setCenter(new GridPane());
		setUpActions();
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setMaximized(true);
		primaryStage.setMinHeight(720);
		primaryStage.setMinWidth(1280);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void buildSettingsPane() {
		HBox settingsPane = new HBox(20);
		settingsPane.setStyle("-fx-border-color: black");
		settingsPane.setPadding(new Insets(20, 0, 20, 0));
		settingsPane.setAlignment(Pos.CENTER);
		
		rbSimpleGame = new RadioButton("Simple Game");
		rbGeneralGame = new RadioButton("General Game");
		rbGroupGameMode  = new ToggleGroup();
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
	
	public void buildPlayerPanes() {
		// Blue player pane
		VBox bluePlayerPane = new VBox(15);
		bluePlayerPane.setStyle("-fx-border-color: black");
		bluePlayerPane.setPadding(new Insets(0, 100, 0, 100));
		bluePlayerPane.setAlignment(Pos.CENTER);
		
		Label lblBlue = new Label("Blue Player");
		lblBlue.setTextFill(Color.BLUE);
		lblBlue.setFont(Font.font(24));
		
		rbGroupBlueType = new ToggleGroup();
		RadioButton rbBlueHuman = new RadioButton("Human");
		RadioButton rbBlueComputer = new RadioButton("Computer");
		rbBlueHuman.setToggleGroup(rbGroupBlueType);
		rbBlueComputer.setToggleGroup(rbGroupBlueType);
		rbBlueHuman.setSelected(true);
		
		rbGroupBlueMoves = new ToggleGroup();
		rbBlueS = new RadioButton("S");
		rbBlueO = new RadioButton("O");
		rbBlueS.setUserData('S');
		rbBlueO.setUserData('O');
		rbBlueS.setToggleGroup(rbGroupBlueMoves);
		rbBlueO.setToggleGroup(rbGroupBlueMoves);
		rbBlueS.setSelected(true);
		
		bluePlayerPane.getChildren().addAll(lblBlue, rbBlueHuman, rbBlueS, rbBlueO, rbBlueComputer);
		layout.setLeft(bluePlayerPane);
		
		// Red player pane
		VBox redPlayerPane = new VBox(15);
		redPlayerPane.setStyle("-fx-border-color: black");
		redPlayerPane.setPadding(new Insets(0, 100, 0, 100));
		redPlayerPane.setAlignment(Pos.CENTER);
		
		Label lblRed = new Label("Red Player");
		lblRed.setTextFill(Color.RED);
		lblRed.setFont(Font.font(24));
		
		rbGroupRedType = new ToggleGroup();
		RadioButton rbRedHuman = new RadioButton("Human");
		RadioButton rbRedComputer = new RadioButton("Computer");
		rbRedHuman.setToggleGroup(rbGroupRedType);
		rbRedComputer.setToggleGroup(rbGroupRedType);
		rbRedHuman.setSelected(true);
		
		rbGroupRedMoves = new ToggleGroup();
		rbRedS = new RadioButton("S");
		rbRedO = new RadioButton("O");
		rbRedS.setUserData('S');
		rbRedO.setUserData('O');
		rbRedS.setToggleGroup(rbGroupRedMoves);
		rbRedO.setToggleGroup(rbGroupRedMoves);
		rbRedS.setSelected(true);
		
		redPlayerPane.getChildren().addAll(lblRed, rbRedHuman, rbRedS, rbRedO, rbRedComputer);
		layout.setRight(redPlayerPane);
	}
	
	public void buildInfoPane() {
		HBox infoPane = new HBox();
		infoPane.setStyle("-fx-border-color: black");
		infoPane.setPadding(new Insets(50, 0, 50, 0));
		infoPane.setAlignment(Pos.CENTER);
		
		Label lblTurn = new Label("Current Turn: ");
		lblTurn.setFont(Font.font(24));
		lblCurrentTurn = new Label("Blue");
		lblCurrentTurn.setFont(Font.font(24));
		lblCurrentTurn.setTextFill(Color.BLUE);
		
		infoPane.getChildren().addAll(lblTurn, lblCurrentTurn);
		layout.setBottom(infoPane);
	}
	
	public void setUpBoard(int size) {
		int width = BOARD_PIXEL_SIZE/size;
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

		layout.setCenter(boardPane);
	}
	
	private void drawBoard() {
		for (int row = 0; row < game.getTotalRows(); row++) {
			for (int col = 0; col < game.getTotalColumns(); col++) {
				if (game.getCell(row, col) == SosGame.Cell.S) {
					squares[row][col].setValue("S");
				} else if (game.getCell(row, col) == SosGame.Cell.O) {
					squares[row][col].setValue("O");
				}
			}
		}
	}
	
	private void displayTurn() {
		if (game.getGameState() == SosGame.GameState.PLAYING) {
			if (game.getTurn().equals("BLUE")) {
				lblCurrentTurn.setText("Blue");
				lblCurrentTurn.setTextFill(Color.BLUE);
			} else {
				lblCurrentTurn.setText("Red");
				lblCurrentTurn.setTextFill(Color.RED);
			}
		}
	}
	
	private void setUpActions() {
		btnStartGame.setOnAction(event -> handleStartGame());
		
		rbGroupBlueMoves.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
	            if (newValue != null && game != null) { 
	            	game.setBlueMove((char) newValue.getUserData());
	            }
	        }
		});
		
		rbGroupRedMoves.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
	            if (newValue != null && game != null) { 
	            	game.setRedMove((char)newValue.getUserData());
	            }
	        }
		});
	}
	
	private void handleStartGame() {
		try {
			int size = getBoardSize();
			
			if (rbSimpleGame.isSelected()) {
				game = new SosSimpleGame();
			} else {
				game = new SosGeneralGame();
			}
			
			game.setupNewGame(size);
			setUpBoard(size);
			
			// Disable controls in settings pane
			rbSimpleGame.setDisable(true);
			rbGeneralGame.setDisable(true);
			txtBoardSize.setDisable(true);
			btnStartGame.setDisable(true);
			
		} catch (NumberFormatException e) {
			showError("Please enter a valid integer for the board size.");
		} catch (IllegalArgumentException e) {
			showError(e.getMessage());
		} catch (Exception e) {
			showError("Unexpected error: " + e.getMessage());
		}
	}
	
	public void handleMove(Square square) {
		if (game == null) {
			return;
		}
		try {
			game.makeMove(square.getRow(), square.getColumn());
			drawBoard();
			displayTurn();
		} catch (IndexOutOfBoundsException e) {
			showError("That move is outside the board.");
		} catch (IllegalStateException e) {
			showError(e.getMessage());
		} catch (Exception e) {
			showError("Unexpected error: " + e.getMessage());
		}
		
	}
	
	private int getBoardSize() throws Exception {
		String input = txtBoardSize.getText().trim();
			int size = Integer.parseInt(input);
			
			if (size < 3 || size > 10) {
				throw new IllegalArgumentException("Board size must be between 3 and 10");
			}
			return size;
	}
	
	private void showError(String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle("Invalid Input");
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	
	public class Square {
		
		private StackPane square;
		private Label lblValue;
		private int row;
		private int column;
		
		public Square(int width, int row, int col) {
			this.row = row;
			this.column = col;
			
			Rectangle border = new Rectangle(width, width);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(Color.BLACK);
			
			lblValue = new Label("");
			lblValue.setFont(Font.font(24));
			
			square = new StackPane(border, lblValue);
			square.setOnMouseClicked(event -> handleMove(this));
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
