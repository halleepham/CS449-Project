package sprint1.product;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SosGui extends Application {

	private BorderPane layout;
	//private RadioButton rbSimpleGame, rbGeneralGame, rbBlueS, rbBlueO, rbRedS, rbRedO;
	private ToggleGroup rbGroupGameMode, rbGroupBlue, rbGroupRed;
	private TextField txtBoardSize;
	

	@Override
	public void start(Stage primaryStage) {
		
		layout = new BorderPane();
		layout.setPadding(new Insets(10, 20, 10, 20));
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		buildPlayerPanes();
		
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
	
	public void buildBoardPane() {
		
		
		
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
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
