package tdt4140.gr1824.app.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1824.app.db.DatabaseCommunicator;

public class UserLoginController {

	@FXML
	public TextField userID;
	
	@FXML
	public PasswordField passwordField;
	
	
	@FXML
	public void handleLoginButton(ActionEvent event) throws IOException{
		if (this.loginChecker()) {
			UserUIController.setCurrentUserID(Integer.valueOf(this.userID.getText()));
			
			Parent userViewParent = FXMLLoader.load(getClass().getResource("UserUI.fxml"));
			Scene userViewScene = new Scene(userViewParent);
			Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get the current stage from the system
			window.setScene(userViewScene); //Set the new scene
			window.show();
		}
	}
	
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in MainMenuUI.fxml as the scene in the primary Stage*/
		Parent userLoginViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userLoginViewScene = new Scene(userLoginViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userLoginViewScene);
		window.show();
	}
	
	private boolean loginChecker() {
		//Passwords are "password"
		if (this.userID.getText().isEmpty() || this.passwordField.getText().isEmpty()) {
			return false;
		}
		if (this.passwordField.getText().equals("password") && this.isNumber(this.userID.getText())){
			if (DatabaseCommunicator.userExists(Integer.valueOf(this.userID.getText()))){
				return true;
			}			
		}
		return false;
	}
	
	private boolean isNumber(String numberString) {
		for (int i = 0; i < numberString.length(); i++) {
			if (!Character.isDigit(numberString.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
}
