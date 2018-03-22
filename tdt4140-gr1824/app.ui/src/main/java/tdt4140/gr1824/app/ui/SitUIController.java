package tdt4140.gr1824.app.ui;


import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tdt4140.gr1824.app.db.DatabaseCommunicator;
import tdt4140.gr1824.app.core.Statistics;

public class SitUIController{ 
	
	@FXML
	public TextField groupID; //Reads text from the group-field
	
	@FXML
	public PieChart groupChart; //PieChart used to display group-stats
	
	@FXML
	public PieChart averageChart; //PieChart used to display average stats for all users
	
	private boolean toggle = false; //State of the toggle button
	private Statistics statistics = new Statistics(new DatabaseCommunicator());
	
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in MainMenuUI.fxml as the scene in the primary Stage*/
		Parent userViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userViewScene = new Scene(userViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
	}

	@FXML
	public void handleGetStatsButton() {
		/*Retrieves and displays stats based on state of toggle button text-field*/
		if (this.toggle && !this.groupID.getText().isEmpty()) { 
			this.setPieChart(this.statistics.getGroupStats(this.groupID.getText()), this.groupChart, groupID.getText());							
		}
		this.setPieChart(this.statistics.getAllStats(), this.averageChart, "Average stats all users");
	}
	
	@FXML
	public void handleToggleButton() {
		/*Toggles the toggle variable. Enables/Disables text field based on toggle-state*/
		this.toggle = !this.toggle;
		if (this.toggle == false) {
			this.groupID.setDisable(true);
		} else {
			this.groupID.setDisable(false);
		}
	}
	
	private void setPieChart(int[] stats, PieChart chart, String groupName) {
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Gloshaugen", stats[0]),
                new PieChart.Data("SiT Trening", stats[1]),
                new PieChart.Data("Samfundet", stats[2]),
                new PieChart.Data("Andre steder", stats[3]));
		chart.setData(pieChartData);
		chart.setTitle(groupName);
	}
}