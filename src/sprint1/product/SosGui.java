package sprint1.product;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SosGui extends Application {

	private BorderPane layout;
	private RadioButton rbSimpleGame, rbGeneralGame;
	

	@Override
	public void start(Stage primaryStage) {
		
		layout = new BorderPane();
		Scene scene = new Scene(layout);
		
		buildSettingsPane();
		
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void buildSettingsPane() {
		
		ToggleGroup rbGameModeGroup  = new ToggleGroup();
		rbSimpleGame = new RadioButton("Simple Game");
		rbGeneralGame = new RadioButton("General Game");
		
		GridPane settingsPane = new GridPane();
		settingsPane.add(rbSimpleGame, 0, 0);
		settingsPane.add(rbGeneralGame, 1, 0);
		
		rbSimpleGame.setToggleGroup(rbGameModeGroup);
		rbGeneralGame.setToggleGroup(rbGameModeGroup);
		layout.setTop(settingsPane);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
