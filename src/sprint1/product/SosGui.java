package sprint1.product;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SosGui extends Application {
	

	@Override
	public void start(Stage primaryStage) {
		 
		BorderPane borderPane = new BorderPane();
		
		Scene scene = new Scene(borderPane);
		
		
		primaryStage.setTitle("SOS Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
