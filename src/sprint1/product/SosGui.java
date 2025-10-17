package sprint1.product;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SosGui extends Application {
	
	

	private BorderPane layout;
	private ToggleGroup rbGroupGameMode, rbGroupBlueType, rbGroupRedType, rbGroupBlueMoves, rbGroupRedMoves;
	private TextField txtBoardSize;
	private Label lblCurrentTurn;
	
	private static final int BOARD_SIZE = 500;
	
	private Square[][] board;

	@Override
	public void start(Stage primaryStage) {
		
		layout = new BorderPane();
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		buildPlayerPanes();
		buildBoardPane();
		buildInfoPane();
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void buildSettingsPane() {
		
		HBox settingsPane = new HBox(20);
		settingsPane.setStyle("-fx-border-color: black");
		settingsPane.setPadding(new Insets(50, 0, 50, 0));
		settingsPane.setAlignment(Pos.CENTER);
		
		RadioButton rbSimpleGame = new RadioButton("Simple Game");
		RadioButton rbGeneralGame = new RadioButton("General Game");
		rbGroupGameMode  = new ToggleGroup();
		rbSimpleGame.setToggleGroup(rbGroupGameMode);
		rbGeneralGame.setToggleGroup(rbGroupGameMode);
		
		Label lblBoardSize = new Label("Board size: ");
		
		txtBoardSize = new TextField();
		txtBoardSize.setMaxWidth(25);
		
		settingsPane.getChildren().addAll(rbSimpleGame, rbGeneralGame, lblBoardSize, txtBoardSize);
		
		layout.setTop(settingsPane);
		
	}
	
	public void buildPlayerPanes() {
		
		rbGroupBlueType = new ToggleGroup();
		RadioButton rbBlueHuman = new RadioButton("Human");
		RadioButton rbBlueComputer = new RadioButton("Computer");
		rbBlueHuman.setSelected(true);
		rbBlueHuman.setToggleGroup(rbGroupBlueType);
		rbBlueComputer.setToggleGroup(rbGroupBlueType);
		
		VBox bluePlayerPane = new VBox(15);
		bluePlayerPane.setStyle("-fx-border-color: black");
		bluePlayerPane.setPadding(new Insets(0, 100, 0, 100));
		bluePlayerPane.setAlignment(Pos.CENTER);
		rbGroupBlueMoves = new ToggleGroup();
		RadioButton rbBlueS = new RadioButton("S");
		RadioButton rbBlueO = new RadioButton("O");
		rbBlueS.setToggleGroup(rbGroupBlueMoves);
		rbBlueO.setToggleGroup(rbGroupBlueMoves);
		rbBlueS.setSelected(true);
		Label lblBlue = new Label("Blue Player");
		lblBlue.setTextFill(Color.BLUE);
		lblBlue.setFont(Font.font(24));
		bluePlayerPane.getChildren().addAll(lblBlue, rbBlueHuman, rbBlueS, rbBlueO, rbBlueComputer);
		layout.setLeft(bluePlayerPane);
		
		
		rbGroupRedType = new ToggleGroup();
		RadioButton rbRedHuman = new RadioButton("Human");
		RadioButton rbRedComputer = new RadioButton("Computer");
		rbRedHuman.setSelected(true);
		rbRedHuman.setToggleGroup(rbGroupRedType);
		rbRedComputer.setToggleGroup(rbGroupRedType);
		
		VBox redPlayerPane = new VBox(15);
		redPlayerPane.setStyle("-fx-border-color: black");
		redPlayerPane.setPadding(new Insets(0, 100, 0, 100));
		redPlayerPane.setAlignment(Pos.CENTER);
		rbGroupRedMoves = new ToggleGroup();
		RadioButton rbRedS = new RadioButton("S");
		RadioButton rbRedO = new RadioButton("O");
		rbRedS.setToggleGroup(rbGroupRedMoves);
		rbRedO.setToggleGroup(rbGroupRedMoves);
		rbRedS.setSelected(true);
		Label lblRed = new Label("Red Player");
		lblRed.setTextFill(Color.RED);
		lblRed.setFont(Font.font(24));
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
	
	public void buildBoardPane() {
		
		//temp variable
		int size = 10;
		
		int width = BOARD_SIZE/size;
		
		GridPane boardPane = new GridPane();
		boardPane.setStyle("-fx-border-color: black");
		boardPane.setPadding(new Insets(30));
		boardPane.setAlignment(Pos.CENTER);
		
		board = new Square[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = new Square(width);
				boardPane.add(board[row][col].getSquare(), col, row);
			}
		}
		
		layout.setCenter(boardPane);
		
	}
	
	
	private class Square {
		
		private StackPane square;
		private Rectangle border;
		private Label lblValue;
		
		public Square(int width) {
			square = new StackPane();
			
			border = new Rectangle();
			border.setWidth(width);
			border.setHeight(width);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(Color.BLACK);
			square.getChildren().add(border);
			
			lblValue = new Label("");
			lblValue.setAlignment(Pos.CENTER);
			lblValue.setFont(Font.font(24));
			square.getChildren().add(lblValue);
			
			square.setOnMouseClicked(event -> {
				System.out.println("Not implemented yet. Change to S or O.");
			});
			
		}
		
		public StackPane getSquare() {
			return square;
		}
		
		public String getValue() {
			return lblValue.getText();
		}
		
		public void setValue(String value) {
			lblValue.setText(value);
		}
		
		
	}
	

	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
