package tdt4140.gr1824.app.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStart extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		/**
		 * User-interface entry point. Reads the main menu FXML-file and loads the scene onto the primary stage. 
		 */
		Parent root = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }
}

