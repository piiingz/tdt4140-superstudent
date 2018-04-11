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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tdt4140.gr1824.app.core.Statistics;
import tdt4140.gr1824.app.db.DatabaseCommunicator;

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
	
	private int adminID = 0; //Number used to identify which button was last clicked. From top to bottom: 1-5
	
	
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
	public Text gymStatText;
	//View competitions elements:
	
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
	public TextField prizeDescription;
	
	@FXML
	public TextField competitionDescription;
	
	@FXML
	public Text createNewCompetition;
	
	@FXML
	public ImageView competitionIcon;
	
	@FXML
	public ImageView smallPlus;
	//Welcome screen elements:
	@FXML
	public ImageView studCapLogo;
	
	@FXML
	public Text welcomeMessage;
	
	@FXML
	public Text instructionMessage;
	
	//Other/common variables
	private Statistics statistics = new Statistics(new DatabaseCommunicator());
	private ObservableList<String> comboBoxElements = FXCollections.observableArrayList("Gloshaugen","SiT Trening","Samfundet","Other");
	private boolean groupStatsToggle = false;
	private int gymThreshold = 5;
	
	@FXML
	public ToggleButton enableGroupStats; //Used by pie and progression view
	
	@FXML
	public TextField groupID; //Used by pie and progression view
	
	
	@FXML
	public void handleEnableGroupToggleButton() {
		this.groupStatsToggle = !this.groupStatsToggle;
		if (this.groupStatsToggle == false) {
			this.groupID.setDisable(true);
		} else {
			this.groupID.setDisable(false);
		}
	}
	
	//handlebottomRightButton: Call appropriate method based on which view is active
	@FXML
	public void handleBottomRightButton(ActionEvent event) {
		if (this.adminID == 1) {
			this.handlePieButton();
		} else if (this.adminID == 2) {
			this.handleProgressionButton(event);
		} else if (this.adminID == 3) {
			this.handleGymStatButton();
		} else if (this.adminID == 4) {
			this.handleViewCompButton();
		} else if (this.adminID == 5) {
			this.handleCreateCompButton();
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
	public void handleViewCompClick() {
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
	
	private void resetHboxColor() {
		if (this.adminID == 0) { //First time admin-panel is used this session
			this.destructWelcomeView();
		}
		
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
		}
		
		
	}
	//Handle welcome screen:
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
			this.setPieChart(this.statistics.getGroupStats(this.groupID.getText()), this.groupChart, this.groupID.getText());
		}
		this.setPieChart(this.statistics.getAllStats(), this.averageChart, "Average stats all users");
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
	
	private void handleProgressionButton(ActionEvent event) {
		this.lineChart.getData().clear();
		if (this.comboBoxProgression.getValue() != null && this.startDate.getValue() != null && this.endDate.getValue() != null) {
			if(!this.startDate.getValue().isBefore(this.endDate.getValue())) {
				this.popupDateError(event);
				return;
			}
			if (this.groupStatsToggle) {
				if (!this.groupID.getText().isEmpty()) {
					this.setLineChart("Group: " + this.groupID.getText(), this.statistics.getLinePointsGroup(this.groupID.getText(),  this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));					
				}
			}
			this.setLineChart("Average all users", this.statistics.getLinePointsAll(this.startDate.getValue(), this.endDate.getValue(), this.comboBoxProgression.getValue()));
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
	
	//Handle Gym-status events:
	private void initializeGymStatView() {
		this.gymStatIcon.setVisible(true);
		this.gymStatText.setVisible(true);
		this.bottomRightButton.setText("Refresh");
		this.bottomRightButton.setVisible(true);
	}
	
	private void handleGymStatButton() {
		if (this.statistics.getNumberAtGym() <= this.gymThreshold) {
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
	
	//Handle View Competitions events:
	private void initializeViewCompView() {
		
	}
	
	private void handleViewCompButton() {
		
	}
	
	private void destructViewCompView() {
		
	}
	
	//Handle create new competition events:
	private void initializeCreateCompView() {
		this.comboBoxCreateComp.setVisible(true);
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
	
	private void handleCreateCompButton() {
		
	}
	
	private void destructCreateCompView() {
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
	
	
	
	
	
	
	//Return
	@FXML
	public void handleReturnButton(ActionEvent event) throws IOException {
		/*Sets interface described in MainMenuUI.fxml as the scene in the primary Stage*/
		Parent sitViewParent = FXMLLoader.load(getClass().getResource("MainMenuUI.fxml"));
		Scene userViewScene = new Scene(sitViewParent);
		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow()); //Get stage from the action event
		window.setScene(userViewScene);
		window.show();
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
