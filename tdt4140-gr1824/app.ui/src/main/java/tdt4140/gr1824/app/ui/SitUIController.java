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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tdt4140.gr1824.app.db.DatabaseCommunicator;
import tdt4140.gr1824.app.core.Statistics;

public class SitUIController{ 
	
	@FXML
	public LineChart<Number,Number> lineChart;
	
	@FXML
	public Text gymStatus;
	
	@FXML
	public DatePicker endDate;
	
	@FXML
	public DatePicker startDate;
	
	@FXML
	public ComboBox<String> comboBox;
	
	@FXML
	public TextField groupID; //Reads text from the group-field
	
	@FXML
	public PieChart groupChart; //PieChart used to display group-stats
	
	@FXML
	public PieChart averageChart; //PieChart used to display average stats for all users
	
	private Statistics statistics = new Statistics(new DatabaseCommunicator());
	private boolean groupStatsToggle = false; //State of the toggle button
	private boolean progressionToggle = false;
	private int gymThreshold = 5;
	
	private ObservableList<String> comboBoxElements = FXCollections.observableArrayList("Gloshaugen","SiT Trening","Samfundet","Other");
	
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in MainMenuUI.fxml as the scene in the primary Stage*/
		Parent sitViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userViewScene = new Scene(sitViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
	}
	
	@FXML
	public void handleCompetitionsButton(ActionEvent event) throws IOException {
		final Stage competitionStage = new Stage();
		competitionStage.initModality(Modality.APPLICATION_MODAL);
		competitionStage.initOwner((Stage) (((Node) event.getSource()).getScene().getWindow())); //Set primaryStage as owner
		
		Parent root = FXMLLoader.load(getClass().getResource("SiTCompetitionUI.fxml"));
		
		Scene competitionScene = new Scene(root);
		competitionStage.setTitle("SiT Competition Menu");
		competitionStage.setScene(competitionScene);
		competitionStage.show();
	}

	@FXML
	public void handleProgressionToggle() {
		
	}
	
	@FXML
	public void handleRefreshButton() {
		if (this.statistics.getNumberAtGym() <= this.gymThreshold) {
			this.gymStatus.setText("Good to go");
			this.gymStatus.setFill(Color.GREEN);
		} else {
			this.gymStatus.setText("Too crowded");
			this.gymStatus.setFill(Color.RED);
		}
	}
	
	@FXML
	public void handleGetStatsButton() {
		/*Retrieves and displays stats based on state of toggle button text-field*/
		if (this.groupStatsToggle && !this.groupID.getText().isEmpty()) { 
			this.setPieChart(this.statistics.getGroupStats(this.groupID.getText()), this.groupChart, groupID.getText());							
		}
		this.setPieChart(this.statistics.getAllStats(), this.averageChart, "Average stats all users");
	}
	
	@FXML
	public void handleToggleButton() {
		/*Toggles the toggle variable. Enables/Disables text field based on toggle-state*/
		this.groupStatsToggle = !this.groupStatsToggle;
		if (this.groupStatsToggle == false) {
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
