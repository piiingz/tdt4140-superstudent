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
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tdt4140.gr1824.app.db.DatabaseCommunicator;
import tdt4140.gr1824.app.core.UIBackendController;

public class OldSitUIController{ 
	
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
	
	private UIBackendController backendController = new UIBackendController(new DatabaseCommunicator());
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
		
		Parent root = FXMLLoader.load(getClass().getResource("SiTUI.fxml"));
		
		Scene competitionScene = new Scene(root);
		competitionStage.setTitle("SiT Competition Menu");
		competitionStage.setScene(competitionScene);
		competitionStage.show();
	}
	
	@FXML
	public void handleRefreshButton() {
		if (this.backendController.getNumberAtGym() <= this.gymThreshold) {
			this.gymStatus.setText("Good to go");
			this.gymStatus.setFill(Color.GREEN);
		} else {
			this.gymStatus.setText("Too crowded");
			this.gymStatus.setFill(Color.RED);
		}
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
	
	@FXML
	public void handleProgressionToggle() {
		/*Toggles the progressionToggle variable. Enables/Disables linechart/piechart elements based on progressionToggle-state*/
		this.progressionToggle = !this.progressionToggle;
		if (this.progressionToggle == false) {
			
			//Disable all progression-related elements and remove all data-points from linechart
			this.lineChart.setVisible(false);
			this.comboBox.setDisable(true);
			this.startDate.setDisable(true);
			this.endDate.setDisable(true);
			
			//Enable piechart-related elements
			this.groupChart.setVisible(true);
			this.averageChart.setVisible(true);
			
		} else {
			
			//Enable all progression-related elements
			this.lineChart.setVisible(true);
			this.comboBox.setDisable(false);
			this.startDate.setDisable(false);
			this.endDate.setDisable(false);
			
			//Disable all piechart-related elements
			this.groupChart.setVisible(false);
			this.averageChart.setVisible(false);
			this.comboBox.setItems(this.comboBoxElements);
		}
	}
	
	@FXML
	public void handleGetStatsButton(ActionEvent event) {
		/*Retrieves and displays stats based on state of toggle button text-field*/
		if (this.progressionToggle) {
			//Linechart-mode
			this.lineChart.getData().clear();
			if (this.comboBox.getValue() != null && this.startDate.getValue() != null && this.endDate.getValue() != null) {
				if (!this.startDate.getValue().isBefore(this.endDate.getValue())) {
					this.popupDateError(event);
					return;
				}
				if (this.groupStatsToggle) {
					if (!this.groupID.getText().isEmpty()) {
						this.setLineChart("Group: " + this.groupID.getText(), this.backendController.getLinePointsGroup(this.groupID.getText(), this.startDate.getValue(), this.endDate.getValue(), this.comboBox.getValue())); //SetLinechart for the group
					}
				}
				this.setLineChart("Average all users", this.backendController.getLinePointsAll(this.startDate.getValue(), this.endDate.getValue(), this.comboBox.getValue())); //SetLinechart for the average of all users
			}
		} else {
			//Piechart-mode 
			if (this.groupStatsToggle && !this.groupID.getText().isEmpty()) { 
				this.setPieChart(this.backendController.getGroupStats(this.groupID.getText()), this.groupChart, groupID.getText());							
			}
			this.setPieChart(this.backendController.getAllStats(), this.averageChart, "Average stats all users");			
		}
	}
	
	private void setLineChart(String lineName, int[] dataPoints) {
		XYChart.Series series = new XYChart.Series();
        series.setName(lineName);
        int weekNr = 1;
        
        //populating the series with data
        for (int dataPoint : dataPoints) {
        	series.getData().add(new XYChart.Data(Integer.toString(weekNr), dataPoint));
        	weekNr += 1;
        }
        this.lineChart.getData().add(series);
        this.lineChart.getYAxis().setLabel("Timer");
        this.lineChart.getXAxis().setLabel("Uke nr:");
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
	
	private void popupDateError(ActionEvent event) {
		final Stage errMessage = new Stage();
		errMessage.initModality(Modality.APPLICATION_MODAL);
		errMessage.initOwner((Stage) (((Node) event.getSource()).getScene().getWindow())); //Set primaryStage as owner
		VBox errMessageVbox = new VBox(20);
		errMessageVbox.getChildren().add(new Text("Start date must be before end date."));
		Scene errMessageScene = new Scene(errMessageVbox, 300, 100);
		errMessage.setScene(errMessageScene);
		errMessage.show();
	}
}