package tdt4140.gr1824.app.ui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainMenuController {
	
	@FXML
	public void handleUserButton(ActionEvent event) throws IOException {
		/*Sets interface described in UserUI.fxml as the scene in the primary Stage*/
		Parent userViewParent = FXMLLoader.load(getClass().getResource("UserUI.fxml"));
		Scene userViewScene = new Scene(userViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get the current stage from the system
		window.setScene(userViewScene); //Set the new scene
		window.show();
	}
	
	@FXML
	public void handleSitButton(ActionEvent event) throws IOException{
		/*Sets interface described in SiTUI.fxml as the scene in the primary Stage*/
		Parent userViewParent = FXMLLoader.load(getClass().getResource("SiTUI.fxml"));
		Scene userViewScene = new Scene(userViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get the current stage from the system through ActionEvent
		window.setScene(userViewScene);
		window.show();
	}
}
