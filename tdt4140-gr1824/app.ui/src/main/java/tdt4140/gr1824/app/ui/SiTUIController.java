package tdt4140.gr1824.app.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tdt4140.gr1824.app.uiBackend.UIBackendController;

public class SiTUIController {

	//Bottom right corner button
	@FXML
	public Button bottomRightButton; //bottomRightButton.setText(String) depending on active view
	
	//Admin panel buttons and ID:
	@FXML
	public HBox piechartStat; //ID: 1
	
	@FXML
	public HBox progressionStat; //ID: 2
	
	@FXML
	public HBox gymStat; //ID: 3
	
	@FXML
	public HBox viewComp; //ID: 4
	
	@FXML
	public HBox createComp; //ID: 5
	
	@FXML
	public HBox welcomeScreen; //ID: 6 
	
	private int adminID = 6; //Number used to identify which button was last clicked. From top to bottom: 1-5
	
	@FXML
	public Button returnButton;
	//Pie chart statistics elements:
	@FXML
	public ImageView pieIcon; 
	
	@FXML
	public PieChart groupChart; //PieChart used to display group-stats
	
	@FXML
	public PieChart averageChart; //PieChart used to display average stats for all users
	
	//Progression statistics elements:
	@FXML
	public ComboBox<String> comboBoxProgression;

	@FXML
	public DatePicker startDate;
	
	@FXML
	public DatePicker endDate;
	
	@FXML
	public LineChart<Number,Number> lineChart;
	
	@FXML
	public ImageView progressionIcon;
	
	//Gym-status elements:
	@FXML
	public ImageView gymStatIcon;
	
	@FXML
	public ImageView goodToGo;
	
	@FXML
	public ImageView tooCrowded;
	
	@FXML 
	public Text statusText;
	
	@FXML
	public Text gymStatText;
	//View competitions elements:
	@FXML
	public Text winnersText;
	
	@FXML
	public ListView<String> winnersList; 
	
	@FXML
	public Text viewCompText;
	
	@FXML
	public ComboBox<String> comboBoxViewComp;
	
	@FXML
	public ImageView compViewMedalIcon;
	
	@FXML
	public Text compViewDescription;
	
	@FXML
	public Text compViewPrize;
	
	@FXML
	public Text compViewStartDate;
	
	@FXML
	public Text compViewEndDate;
	
	@FXML
	public Text compViewCompetitionArea;
	
	@FXML
	public Text compViewRequiredHours;
	
	@FXML
	public Text compViewDescriptionVal;
	
	@FXML
	public Text compViewPrizeVal;
	
	@FXML
	public Text compViewStartDateVal;
	
	@FXML
	public Text compViewEndDateVal;
	
	@FXML
	public Text compViewCompetitionAreaVal;
	
	@FXML
	public Text compViewRequiredHoursVal;
	
	//Create new competition elements:
	@FXML
	public ComboBox<String> comboBoxCreateComp;
	
	@FXML
	public TextField requiredHours;
	
	@FXML
	public DatePicker startDateCreateComp;
	
	@FXML
	public DatePicker endDateCreateComp;
	
	@FXML
	public TextField competitionNameCreate;
	
	@FXML
	public TextField prizeDescription;
	
	@FXML
	public TextField competitionDescription;
	
	@FXML
	public Text createNewCompetition;
	
	@FXML
	public ImageView smallPlus;
	
	@FXML
	public ImageView competitionIcon;
	
	//Welcome screen elements:
	@FXML
	public ImageView studCapLogo;
	
	@FXML
	public Text welcomeMessage;
	
	@FXML
	public Text instructionMessage;
	
	//Other/common variables
	private UIBackendController backendController = new UIBackendController();
	private ObservableList<String> comboBoxElements = FXCollections.observableArrayList("Gloshaugen","SiT Trening","Samfundet","Other");
	private boolean groupStatsToggle = false;
	
	@FXML
	public ToggleButton enableGroupStats; //Used by pie and progression view
	
	@FXML
	public TextField groupID; //Used by pie and progression view
	
	
	@FXML
	public void handleEnableGroupToggleButton() {
		this.groupStatsToggle = !this.groupStatsToggle;
		if (this.groupStatsToggle) {
			this.groupID.setDisable(false);
		} else {
			this.groupID.setDisable(true);
		}
	}
	
	//handlebottomRightButton: Call appropriate method based on which view is active
	@FXML
	public void handleBottomRightButton(ActionEvent event) throws SQLException {
		if (this.adminID == 1) {
			this.handlePieButton();
		} else if (this.adminID == 2) {
			this.handleProgressionButton(event);
		} else if (this.adminID == 3) {
			this.handleGymStatButton();
		} else if (this.adminID == 4) {
			this.handleViewCompButton();
		} else if (this.adminID == 5) {
			this.handleCreateCompButton(event);
		}
	}
	
	//Handle admin panel clicks:
	@FXML
	public void handlePieClick() {
		this.resetHboxColor();
		this.adminID = 1;
		this.piechartStat.setStyle("-fx-background-color: #FFFFFF;");
		this.initializePieView();
	}
	
	@FXML
	public void handleProgressionClick() {
		this.resetHboxColor();
		this.adminID = 2;
		this.progressionStat.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeProgressionView();
	}
	
	@FXML
	public void handleGymstatClick() {
		this.resetHboxColor();
		this.adminID = 3;
		this.gymStat.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeGymStatView();
	}
	
	@FXML
	public void handleViewCompClick() throws SQLException {
		this.resetHboxColor();
		this.adminID = 4;
		this.viewComp.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeViewCompView();
	}
	
	@FXML
	public void handleCreateCompClick() {
		this.resetHboxColor();
		this.adminID = 5;
		this.createComp.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeCreateCompView();
	}
	
	@FXML
	public void handleWelcomeScreenClick() {
		this.resetHboxColor();
		this.adminID = 6;
		this.welcomeScreen.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeWelcomeView();
	}
	
	private void resetHboxColor() {	
		if (this.adminID == 1) {
			this.piechartStat.setStyle(null);
			this.destructPieView();
		} else if (this.adminID == 2) {
			this.progressionStat.setStyle(null);
			this.destructProgressionView();
		} else if (this.adminID == 3) {
			this.gymStat.setStyle(null);
			this.destructGymStatView();
		} else if (this.adminID == 4) {
			this.viewComp.setStyle(null);
			this.destructViewCompView();
		} else if (this.adminID == 5) {
			this.createComp.setStyle(null);
			this.destructCreateCompView();
		} else if (this.adminID == 6) {
			this.welcomeScreen.setStyle(null);
			this.destructWelcomeView();
		}
	}
	
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in MainMenuUI.fxml as the scene in the primary Stage*/
		Parent sitViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userViewScene = new Scene(sitViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
	}
	
	//Handle welcome screen events:
	private void initializeWelcomeView() {
		this.welcomeMessage.setVisible(true);
		this.studCapLogo.setVisible(true);
		this.instructionMessage.setVisible(true);
	}
	
	private void destructWelcomeView() {
		this.studCapLogo.setVisible(false);
		this.welcomeMessage.setVisible(false);
		this.instructionMessage.setVisible(false);
	}
	
	//Handle pie chart statistics events:
	private void initializePieView() {
		this.pieIcon.setVisible(true);
		this.groupChart.setVisible(true);
		this.averageChart.setVisible(true);
		this.enableGroupStats.setVisible(true);
		this.groupID.setVisible(true);
		this.bottomRightButton.setText("Get Stats");
		this.bottomRightButton.setVisible(true);
	}
	
	private void handlePieButton() {
		if (this.groupStatsToggle && !this.groupID.getText().isEmpty()) {
			this.setPieChart(this.backendController.getGroupStats(this.groupID.getText()), this.groupChart, this.groupID.getText());
		}
		this.setPieChart(this.backendController.getAllStats(), this.averageChart, "Average stats all users");
	}
	
	private void destructPieView() {
		this.pieIcon.setVisible(false);
		this.groupChart.setVisible(false);
		this.averageChart.setVisible(false);
		this.enableGroupStats.setVisible(false);
		this.groupID.setVisible(false);
		this.bottomRightButton.setVisible(false);
	}
	
	//Handle progression statistics events:
	private void initializeProgressionView() {
		this.comboBoxProgression.setVisible(true);
		this.startDate.setVisible(true);
		this.endDate.setVisible(true);
		this.lineChart.setVisible(true);
		this.progressionIcon.setVisible(true);
		this.bottomRightButton.setText("Get Stats");
		this.bottomRightButton.setVisible(true);
		this.enableGroupStats.setVisible(true);
		this.groupID.setVisible(true);
		this.comboBoxProgression.setItems(this.comboBoxElements);
	}
	
	private void handleProgressionButton(ActionEvent event) throws SQLException {
		this.lineChart.getData().clear();
		if (this.comboBoxProgression.getValue() != null && this.startDate.getValue() != null && this.endDate.getValue() != null) {
			if(!this.startDate.getValue().isBefore(this.endDate.getValue())) {
				this.popupErrorMessage(event, "Start date must be before end date!");
				return;
			} 
			if(this.startDate.getValue().getDayOfWeek() != DayOfWeek.MONDAY || this.endDate.getValue().getDayOfWeek() != DayOfWeek.SUNDAY) {
				this.popupErrorMessage(event, "Start date must be a monday!\n End date must be a Sunday");
				return;
			}
			
			if (this.groupStatsToggle) {
				if (!this.groupID.getText().isEmpty()) {
					this.setLineChart("Group: " + this.groupID.getText(), this.backendController.getLinePointsGroup(this.groupID.getText(),  this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));					
				}
			}
			this.setLineChart("Average all users", this.backendController.getLinePointsAll(this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));
		}
	}
	
	private void destructProgressionView() {
		this.comboBoxProgression.setVisible(false);
		this.startDate.setVisible(false);
		this.endDate.setVisible(false);
		this.lineChart.setVisible(false);
		this.progressionIcon.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.enableGroupStats.setVisible(false);
		this.groupID.setVisible(false);
	}
	
	//Handle Gym status events:
	private void initializeGymStatView() {
		this.gymStatIcon.setVisible(true);
		this.gymStatText.setVisible(true);
		this.bottomRightButton.setText("Refresh");
		this.bottomRightButton.setVisible(true);
		this.statusText.setVisible(true);
	}
	
	private void handleGymStatButton() throws SQLException {
		this.goodToGo.setVisible(false);
		this.tooCrowded.setVisible(false);
		if (this.backendController.getNumberAtGym() <= this.backendController.getGymThreshold()) {
			this.goodToGo.setVisible(true);
			this.statusText.setText("Good to go!");
		} else {
			this.tooCrowded.setVisible(true);
			this.statusText.setText("Too crowded!");
		}
	}
	
	private void destructGymStatView() {
		this.gymStatIcon.setVisible(false);
		this.goodToGo.setVisible(false);
		this.tooCrowded.setVisible(false);
		this.gymStatText.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.statusText.setVisible(false);
		this.statusText.setText("Click the button to see");
	}
	
	//Handle View Competitions events:
	private void initializeViewCompView() throws SQLException {
		this.competitionIcon.setVisible(true);
		this.viewCompText.setVisible(true);
		this.bottomRightButton.setText("View");
		this.bottomRightButton.setVisible(true);
		this.comboBoxViewComp.setVisible(true);
		this.comboBoxViewComp.setItems(this.backendController.getAllCompetitionNames());
		
		//Center of view, all static elements:
		this.compViewMedalIcon.setVisible(true);
		this.compViewDescription.setVisible(true);
		this.compViewPrize.setVisible(true);
		this.compViewStartDate.setVisible(true);
		this.compViewEndDate.setVisible(true);
		this.compViewCompetitionArea.setVisible(true);
		this.compViewRequiredHours.setVisible(true);
		
		//Listview:
		this.winnersText.setVisible(true);
		this.winnersList.setVisible(true);
		
	}
	
	private void handleViewCompButton() throws SQLException {
		if (this.comboBoxViewComp.getValue() != null) {
			String[] compInfo = this.backendController.getCompetitionDetails(this.comboBoxViewComp.getValue());
			
			this.compViewCompetitionAreaVal.setText(compInfo[0]);
			this.compViewRequiredHoursVal.setText(compInfo[1]);
			this.compViewStartDateVal.setText(compInfo[2]);
			this.compViewEndDateVal.setText(compInfo[3]);
			this.compViewDescriptionVal.setText(compInfo[4]);
			this.compViewPrizeVal.setText(compInfo[5]);
			
			this.compViewCompetitionAreaVal.setVisible(true);
			this.compViewRequiredHoursVal.setVisible(true);
			this.compViewStartDateVal.setVisible(true);
			this.compViewEndDateVal.setVisible(true);
			this.compViewDescriptionVal.setVisible(true);
			this.compViewPrizeVal.setVisible(true);
			
			//Listview:
			this.winnersList.setItems(this.backendController.getWinners(this.comboBoxViewComp.getValue()));
		}
	}
	
	private void destructViewCompView() {
		this.competitionIcon.setVisible(false);
		this.viewCompText.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.comboBoxViewComp.setVisible(false);
		
		//Center of view, all static elements:
		this.compViewMedalIcon.setVisible(false);
		this.compViewDescription.setVisible(false);
		this.compViewPrize.setVisible(false);
		this.compViewStartDate.setVisible(false);
		this.compViewEndDate.setVisible(false);
		this.compViewCompetitionArea.setVisible(false);
		this.compViewRequiredHours.setVisible(false);
		
		//Center of view, dynamic elements:
		this.compViewCompetitionAreaVal.setVisible(false);
		this.compViewRequiredHoursVal.setVisible(false);
		this.compViewStartDateVal.setVisible(false);
		this.compViewEndDateVal.setVisible(false);
		this.compViewDescriptionVal.setVisible(false);
		this.compViewPrizeVal.setVisible(false);
		
		//Listview:
		this.winnersText.setVisible(false);
		this.winnersList.setVisible(false);
		this.winnersList.setItems(null);
	}
	
	//Handle create new competition events:
	private void initializeCreateCompView() {
		this.competitionNameCreate.setVisible(true);
		this.comboBoxCreateComp.setVisible(true);
		this.comboBoxCreateComp.setItems(this.comboBoxElements);
		this.requiredHours.setVisible(true);
		this.startDateCreateComp.setVisible(true);
		this.endDateCreateComp.setVisible(true);
		this.prizeDescription.setVisible(true);
		this.competitionDescription.setVisible(true);
		this.createNewCompetition.setVisible(true);
		this.competitionIcon.setVisible(true);
		this.smallPlus.setVisible(true);
		this.bottomRightButton.setText("Create");
		this.bottomRightButton.setVisible(true);
	}
	
	private void handleCreateCompButton(ActionEvent event) throws SQLException {
		if (!this.competitionNameCreate.getText().isEmpty() && this.comboBoxCreateComp.getValue() != null && this.startDateCreateComp.getValue() != null && this.endDateCreateComp.getValue() != null && !this.requiredHours.getText().isEmpty() && !this.competitionDescription.getText().isEmpty() && !this.prizeDescription.getText().isEmpty()) {
			if (!this.startDateCreateComp.getValue().isBefore(this.endDateCreateComp.getValue())) {
				this.popupErrorMessage(event, "Start date must be before end date!");
				return;
			}
			if (this.backendController.competitionExists(this.competitionNameCreate.getText())) {
				return;
			}
			this.backendController.createCompetition(this.competitionNameCreate.getText(), this.comboBoxCreateComp.getValue(), Integer.valueOf(this.requiredHours.getText()), this.startDateCreateComp.getValue(), this.endDateCreateComp.getValue(), this.competitionDescription.getText(), this.prizeDescription.getText());			
		}
	}
	
	private void destructCreateCompView() {
		this.competitionNameCreate.setVisible(false);
		this.comboBoxCreateComp.setVisible(false);
		this.requiredHours.setVisible(false);
		this.startDateCreateComp.setVisible(false);
		this.endDateCreateComp.setVisible(false);
		this.prizeDescription.setVisible(false);
		this.competitionDescription.setVisible(false);
		this.createNewCompetition.setVisible(false);
		this.competitionIcon.setVisible(false);
		this.smallPlus.setVisible(false);
		this.bottomRightButton.setVisible(false);
	}
	
	//Helper methods:
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
	
	private void popupErrorMessage(ActionEvent event, String errorMessage) {
		final Stage errMessage = new Stage();
		errMessage.initModality(Modality.APPLICATION_MODAL);
		errMessage.initOwner((Stage) (((Node) event.getSource()).getScene().getWindow())); //Set primaryStage as owner
		VBox errMessageVbox = new VBox(20);
		errMessageVbox.getChildren().add(new Text(errorMessage));
		Scene errMessageScene = new Scene(errMessageVbox, 300, 100);
		errMessage.setScene(errMessageScene);
		errMessage.show();
	}
}
