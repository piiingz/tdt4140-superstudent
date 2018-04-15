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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tdt4140.gr1824.app.core.UIBackendController;
import tdt4140.gr1824.app.db.DatabaseCommunicator;

public class UserUIController {

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
	public HBox rewardsView; //ID: 4
		
	@FXML
	public HBox viewComp; //ID: 5
	
	@FXML
	public HBox welcomeScreen; //ID: 6
		
	private int adminID = 6; //Number used to identify which button was last clicked. From top to bottom: 1-6
	
	//Pie chart statistics elements:
	@FXML
	public ImageView pieIcon;
	
	@FXML
	public PieChart userChart; //Display user stats
	
	@FXML
	public PieChart groupChart; //Display group stats
	
	@FXML
	public PieChart averageChart; //Display stats for all users
	
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
	
	//Gym status elements:
	@FXML
	public ImageView gymStatIcon;
	
	@FXML
	public ImageView goodToGo;
	
	@FXML
	public ImageView tooCrowded;
	
	@FXML
	public Text gymStatText;
	
	//Rewards from competitions elements:
	@FXML
	public Text currentProgress;
	
	@FXML
	public Text currentHours;
	
	@FXML
	public ImageView medalIconCorner;
	
	@FXML
	public ImageView medalIconCenter;
	
	//View Competitions elements:
	@FXML
	public ImageView competitionIconCenter;
	
	@FXML
	public ImageView competitionIconCorner;
	
	//View competitions and view Rewards common elements:
	@FXML
	public Text compScreenHeader;
	
	@FXML
	public ComboBox<String> comboBoxCompetitions;
	
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
	
	//Welcome screen elements:
	@FXML
	public ImageView studCapLogo;
	
	@FXML
	public Text welcomeMessage;
	
	@FXML
	public Text instructionMessage;
	
	@FXML
	public Button deleteButton;
	
	//Other/common variables
	private UIBackendController backendController = new UIBackendController();
	private ObservableList<String> comboBoxElements = FXCollections.observableArrayList("Gloshaugen","SiT Trening","Samfundet","Other");
	private boolean compareToggle = false;
	private int gymThreshold = 5;
	private static int currentUserID;
	
	//Elements used by both pie and progression views:
	@FXML
	public TextField newGoalField;
	
	@FXML
	public Text currentGoal;
	
	@FXML
	public Text currentGoalVal;
	
	@FXML
	public Text endMessage;
	
	@FXML
	public Button updateButton;
	
	@FXML
	public ToggleButton enableCompareStats; 
	
	@FXML
	public TextField groupID; 
	
	//Handle compare toggle:
	@FXML
	public void handleEnableCompareToggleButton() {
		this.compareToggle = !this.compareToggle;
		if (this.compareToggle) {
			this.groupID.setDisable(false);
		} else {
			this.groupID.setDisable(true);
		}
	}
	
	@FXML
	public void handleUpdateButton() {
		if (!this.newGoalField.getText().isEmpty()) {
			if (this.isNumber(this.newGoalField.getText())) {
				this.currentGoalVal.setText(this.newGoalField.getText());
				this.backendController.setNewGoal(Integer.valueOf(this.currentGoalVal.getText()), currentUserID);
			}
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
			this.handleRewardsViewButton();
		} else if (this.adminID == 5) {
			this.handleViewCompButton();
		}
	}
	
	//Handle user panel clicks:
	@FXML
	public void handlePieClick() throws SQLException {
		this.resetHboxColor();
		this.adminID = 1;
		this.piechartStat.setStyle("-fx-background-color: #FFFFFF;");
		this.initializePieView();
	}
	
	@FXML
	public void handleProgressionClick() throws SQLException {
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
	public void handleRewardsViewClick() throws SQLException {
		this.resetHboxColor();
		this.adminID = 4;
		this.rewardsView.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeRewardsView();
	}
	
	@FXML
	public void handleViewCompViewClick() throws SQLException {
		this.resetHboxColor();
		this.adminID = 5;
		this.viewComp.setStyle("-fx-background-color: #FFFFFF;");
		this.initializeViewCompView();
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
			this.rewardsView.setStyle(null);
			this.destructRewardsView();
		} else if (this.adminID == 5) {
			this.viewComp.setStyle(null);
			this.destructViewCompView();
		} else if (this.adminID == 6) {
			this.welcomeScreen.setStyle(null);
			this.destructWelcomeView();
		}
	}
	
	//Handle welcome screen events:
	private void initializeWelcomeView() {
		this.deleteButton.setVisible(true);
		this.welcomeMessage.setVisible(true);
		this.studCapLogo.setVisible(true);
		this.instructionMessage.setVisible(true);
	}
	
	@FXML
	public void handleDeleteButton(ActionEvent event) throws IOException{
		this.backendController.deleteUser(currentUserID);
		//Go back to main menu
		Parent userViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userViewScene = new Scene(userViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
	}
	
	private void destructWelcomeView() {
		this.studCapLogo.setVisible(false);
		this.welcomeMessage.setVisible(false);
		this.instructionMessage.setVisible(false);
		this.deleteButton.setVisible(false);
		
	}
	
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in UserLoginUI.fxml as the scene in the primary Stage*/
		Parent userViewParent = FXMLLoader.load(getClass().getResource("UserLoginUI.fxml"));
		Scene userViewScene = new Scene(userViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
	}
	
	//Handle pie chart statistics events:
	private void initializePieView() throws SQLException {
		this.pieIcon.setVisible(true);
		this.userChart.setVisible(true);
		this.groupChart.setVisible(true);
		this.averageChart.setVisible(true);
		this.enableCompareStats.setVisible(true);
		this.groupID.setVisible(true);
		this.bottomRightButton.setText("Get Stats");
		this.bottomRightButton.setVisible(true);
		this.newGoalField.setVisible(true);
		this.updateButton.setVisible(true);
		this.currentGoal.setVisible(true);
		this.currentGoalVal.setVisible(true);
		this.endMessage.setVisible(true);
		this.currentGoalVal.setText(this.backendController.getGoal(currentUserID));
	}
	
	private void handlePieButton() {
		if (this.compareToggle) {
			if (!this.groupID.getText().isEmpty()) { //Don't get stats if textfield is empty
				this.setPieChart(this.backendController.getGroupStats(this.groupID.getText()), this.groupChart, this.groupID.getText());
			}
			this.setPieChart(this.backendController.getAllStats(), this.averageChart, "Average stats all users");
		} 
		this.setPieChart(this.backendController.getUserStats(currentUserID), this.userChart, "User ID: " + currentUserID);
	}
	
	private void destructPieView() {
		this.pieIcon.setVisible(false);
		this.userChart.setVisible(false);
		this.groupChart.setVisible(false);
		this.averageChart.setVisible(false);
		this.enableCompareStats.setVisible(false);
		this.groupID.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.newGoalField.setVisible(false);
		this.updateButton.setVisible(false);
		this.currentGoal.setVisible(false);
		this.currentGoalVal.setVisible(false);
		this.endMessage.setVisible(false);
	}
	
	//Handle progression statistics events:
	private void initializeProgressionView() throws SQLException {
		this.comboBoxProgression.setVisible(true);
		this.startDate.setVisible(true);
		this.endDate.setVisible(true);
		this.lineChart.setVisible(true);
		this.progressionIcon.setVisible(true);
		this.bottomRightButton.setText("Get Stats");
		this.bottomRightButton.setVisible(true);
		this.enableCompareStats.setVisible(true);
		this.groupID.setVisible(true);
		this.comboBoxProgression.setItems(this.comboBoxElements);
		this.newGoalField.setVisible(true);
		this.updateButton.setVisible(true);
		this.currentGoal.setVisible(true);
		this.currentGoalVal.setVisible(true);
		this.endMessage.setVisible(true);
		this.currentGoalVal.setText(this.backendController.getGoal(currentUserID));
		
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
			if (this.compareToggle) {
				if (!this.groupID.getText().isEmpty()) {
					this.setLineChart("Group: " + this.groupID.getText(), this.backendController.getLinePointsGroup(this.groupID.getText(),  this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));					
				}
				this.setLineChart("Average all users", this.backendController.getLinePointsAll(this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));
			}
			this.setLineChart("User ID: " + currentUserID, this.backendController.getLinePointsUser(currentUserID, this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue())); //SetLinechart for the user.
		}
	}
	
	private void destructProgressionView() {
		this.comboBoxProgression.setVisible(false);
		this.startDate.setVisible(false);
		this.endDate.setVisible(false);
		this.lineChart.setVisible(false);
		this.progressionIcon.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.enableCompareStats.setVisible(false);
		this.groupID.setVisible(false);
		this.newGoalField.setVisible(false);
		this.updateButton.setVisible(false);
		this.currentGoal.setVisible(false);
		this.currentGoalVal.setVisible(false);
		this.endMessage.setVisible(false);
	}
	
	//Handle Gym status events:
	private void initializeGymStatView() {
		this.gymStatIcon.setVisible(true);
		this.gymStatText.setVisible(true);
		this.bottomRightButton.setText("Refresh");
		this.bottomRightButton.setVisible(true);
	}
	
	private void handleGymStatButton() throws SQLException {
		this.goodToGo.setVisible(false);
		this.tooCrowded.setVisible(false);
		if (this.backendController.getNumberAtGym() <= this.gymThreshold) {
			this.goodToGo.setVisible(true);
		} else {
			this.tooCrowded.setVisible(true);
		}
	}
	
	private void destructGymStatView() {
		this.gymStatIcon.setVisible(false);
		this.goodToGo.setVisible(false);
		this.tooCrowded.setVisible(false);
		this.gymStatText.setVisible(false);
		this.bottomRightButton.setVisible(false);
	}
	
	//Handle Rewards from competitions-view events:
	private void initializeRewardsView() throws SQLException {
		this.medalIconCorner.setVisible(true);
		this.compScreenHeader.setText("Rewards");
		this.compScreenHeader.setVisible(true);
		this.bottomRightButton.setText("View");
		this.bottomRightButton.setVisible(true);
		this.comboBoxCompetitions.setVisible(true);
		this.comboBoxCompetitions.setItems(this.backendController.getRewardNames(currentUserID));
		
		//Center of view, all static elements:
		this.medalIconCenter.setVisible(true);
		this.compViewDescription.setVisible(true);
		this.compViewPrize.setVisible(true);
		this.compViewStartDate.setVisible(true);
		this.compViewEndDate.setVisible(true);
		this.compViewCompetitionArea.setVisible(true);
		this.compViewRequiredHours.setVisible(true);
		this.currentProgress.setVisible(true);
	}
	
	private void handleRewardsViewButton() throws SQLException {
		if (this.comboBoxCompetitions.getValue() != null) {
			String[] compInfo = this.backendController.getCompetitionDetails(this.comboBoxCompetitions.getValue());
			String stayDetails = this.backendController.getStaydurationUserArea(this.comboBoxCompetitions.getValue(), currentUserID);
			//Set values:
			this.compViewCompetitionAreaVal.setText(compInfo[0]);
			this.compViewRequiredHoursVal.setText(compInfo[1]);
			this.compViewStartDateVal.setText(compInfo[2]);
			this.compViewEndDateVal.setText(compInfo[3]);
			this.compViewDescriptionVal.setText(compInfo[4]);
			this.compViewPrizeVal.setText(compInfo[5]);
			this.currentHours.setText(stayDetails);
			//Set visibility:
			this.compViewCompetitionAreaVal.setVisible(true);
			this.compViewRequiredHoursVal.setVisible(true);
			this.compViewStartDateVal.setVisible(true);
			this.compViewEndDateVal.setVisible(true);
			this.compViewDescriptionVal.setVisible(true);
			this.compViewPrizeVal.setVisible(true);
			this.currentHours.setVisible(true);
			
		}
	}
	
	private void destructRewardsView() {
		this.compScreenHeader.setVisible(false);
		this.medalIconCorner.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.comboBoxCompetitions.setVisible(false);
		
		//Center of view, all static elements:
		this.medalIconCenter.setVisible(false);
		this.compViewDescription.setVisible(false);
		this.compViewPrize.setVisible(false);
		this.compViewStartDate.setVisible(false);
		this.compViewEndDate.setVisible(false);
		this.compViewCompetitionArea.setVisible(false);
		this.compViewRequiredHours.setVisible(false);
		this.currentProgress.setVisible(false);
		//Center of view, dynamic elements:
		this.compViewCompetitionAreaVal.setVisible(false);
		this.compViewRequiredHoursVal.setVisible(false);
		this.compViewStartDateVal.setVisible(false);
		this.compViewEndDateVal.setVisible(false);
		this.compViewDescriptionVal.setVisible(false);
		this.compViewPrizeVal.setVisible(false);
		this.currentHours.setVisible(false);
	}
	
	//Handle View competitions events:
	private void initializeViewCompView() throws SQLException {
		this.competitionIconCorner.setVisible(true);
		this.compScreenHeader.setText("All competitions");
		this.compScreenHeader.setVisible(true);
		this.bottomRightButton.setText("View");
		this.bottomRightButton.setVisible(true);
		this.comboBoxCompetitions.setVisible(true);
		this.comboBoxCompetitions.setItems(this.backendController.getAllCompetitionNames());
		
		//Center of view, all static elements:
		this.competitionIconCenter.setVisible(true);
		this.compViewDescription.setVisible(true);
		this.compViewPrize.setVisible(true);
		this.compViewStartDate.setVisible(true);
		this.compViewEndDate.setVisible(true);
		this.compViewCompetitionArea.setVisible(true);
		this.compViewRequiredHours.setVisible(true);
	}
	
	private void handleViewCompButton() throws SQLException {
		if (this.comboBoxCompetitions.getValue() != null) {
			String[] compInfo = this.backendController.getCompetitionDetails(this.comboBoxCompetitions.getValue());
			
			//Set values:
			this.compViewCompetitionAreaVal.setText(compInfo[0]);
			this.compViewRequiredHoursVal.setText(compInfo[1]);
			this.compViewStartDateVal.setText(compInfo[2]);
			this.compViewEndDateVal.setText(compInfo[3]);
			this.compViewDescriptionVal.setText(compInfo[4]);
			this.compViewPrizeVal.setText(compInfo[5]);
			//Visibility:
			this.compViewCompetitionAreaVal.setVisible(true);
			this.compViewRequiredHoursVal.setVisible(true);
			this.compViewStartDateVal.setVisible(true);
			this.compViewEndDateVal.setVisible(true);
			this.compViewDescriptionVal.setVisible(true);
			this.compViewPrizeVal.setVisible(true);
		}
	}
	
	private void destructViewCompView() {
		this.compScreenHeader.setVisible(false);
		this.competitionIconCorner.setVisible(false);
		this.bottomRightButton.setVisible(false);
		this.comboBoxCompetitions.setVisible(false);
		
		//Center of view, all static elements:
		this.competitionIconCenter.setVisible(false);
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
	
	public static void setCurrentUserID(int userID) {
		//Used by UserLoginController
		currentUserID = userID;
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
