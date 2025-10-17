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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SosGui extends Application {
	
	

	private BorderPane layout;
	//private RadioButton rbSimpleGame, rbGeneralGame, rbBlueS, rbBlueO, rbRedS, rbRedO;
	private ToggleGroup rbGroupGameMode, rbGroupBlue, rbGroupRed;
	private TextField txtBoardSize;
	
	private Square[][] board;

	@Override
	public void start(Stage primaryStage) {
		
		layout = new BorderPane();
		layout.setPadding(new Insets(10, 20, 10, 20));
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		buildPlayerPanes();
		buildBoardPane();
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void buildSettingsPane() {
		
		GridPane settingsPane = new GridPane();
		settingsPane.setHgap(10);
		
		RadioButton rbSimpleGame = new RadioButton("Simple Game");
		RadioButton rbGeneralGame = new RadioButton("General Game");
		rbGroupGameMode  = new ToggleGroup();
		rbSimpleGame.setToggleGroup(rbGroupGameMode);
		rbGeneralGame.setToggleGroup(rbGroupGameMode);
		
		settingsPane.add(rbSimpleGame, 0, 0);
		settingsPane.add(rbGeneralGame, 1, 0);
		
		Label lblBoardSize = new Label("Board size: ");
		settingsPane.add(lblBoardSize, 2, 0);
		
		txtBoardSize = new TextField();
		txtBoardSize.setMaxWidth(20);
		settingsPane.add(txtBoardSize, 3, 0);
		
		layout.setTop(settingsPane);
		
	}
	
	public void buildPlayerPanes() {
		
		VBox bluePlayerPane = new VBox(15);
		rbGroupBlue = new ToggleGroup();
		RadioButton rbBlueS = new RadioButton("S");
		RadioButton rbBlueO = new RadioButton("O");
		rbBlueS.setToggleGroup(rbGroupBlue);
		rbBlueO.setToggleGroup(rbGroupBlue);
		bluePlayerPane.getChildren().addAll(new Label("Blue Player"), rbBlueS, rbBlueO);
		layout.setLeft(bluePlayerPane);
		
		VBox redPlayerPane = new VBox(15);
		rbGroupRed = new ToggleGroup();
		RadioButton rbRedS = new RadioButton("S");
		RadioButton rbRedO = new RadioButton("O");
		rbRedS.setToggleGroup(rbGroupRed);
		rbRedO.setToggleGroup(rbGroupRed);
		redPlayerPane.getChildren().addAll(new Label("Red Player"), rbRedS, rbRedO);
		layout.setRight(redPlayerPane);
		
	}
	
	public void buildBoardPane() {
		
		//temp variable
		int size = 3;
		
		StackPane boardPane = new StackPane();
		
		
		board = new Square[size][size];
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Square square = new Square();
				square.getSquare().setTranslateX((col * 100) - 100);
				square.getSquare().setTranslateY((row * 100) - 100);
				boardPane.getChildren().add(square.getSquare());
				board[row][col] = square;
			}
		}
		
		layout.setCenter(boardPane);
		
	}
	
	
	private class Square {
		
		private StackPane square;
		private Label lblValue;
		
		public Square() {
			square = new StackPane();
			
			Rectangle border = new Rectangle();
			border.setWidth(100);
			border.setHeight(100);
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
