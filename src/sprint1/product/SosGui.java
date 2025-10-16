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
import javafx.stage.Stage;

public class SosGui extends Application {

	private BorderPane layout;
	private RadioButton rbSimpleGame, rbGeneralGame;
	private TextField txtBoardSize;
	

	@Override
	public void start(Stage primaryStage) {
		
		layout = new BorderPane();
		layout.setPadding(new Insets(10, 20, 10, 20));
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void buildSettingsPane() {
		
		GridPane settingsPane = new GridPane();
		settingsPane.setHgap(10);
		
		rbSimpleGame = new RadioButton("Simple Game");
		rbGeneralGame = new RadioButton("General Game");
		ToggleGroup rbGameModeGroup  = new ToggleGroup();
		rbSimpleGame.setToggleGroup(rbGameModeGroup);
		rbGeneralGame.setToggleGroup(rbGameModeGroup);
		
		settingsPane.add(rbSimpleGame, 0, 0);
		settingsPane.add(rbGeneralGame, 1, 0);
		
		Label lblBoardSize = new Label("Board size: ");
		settingsPane.add(lblBoardSize, 2, 0);
		
		txtBoardSize = new TextField();
		txtBoardSize.setMaxWidth(20);
		settingsPane.add(txtBoardSize, 3, 0);
		
		layout.setTop(settingsPane);
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
