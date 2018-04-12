package tdt4140.gr1824.app.core;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tdt4140.gr1824.app.db.DatabaseCommunicator;
import tdt4140.gr1824.app.mock.DummyClassDatabaseCommunicatorForStatisticsTest;

public class UIBackendController {

	private DummyClassDatabaseCommunicatorForStatisticsTest dummydbCom;
	private DatabaseCommunicator dbCom;
	
	public UIBackendController(DatabaseCommunicator dbcom) {
		this.dbCom = dbcom;
	}
	
	public UIBackendController(DummyClassDatabaseCommunicatorForStatisticsTest dbcom) {
		this.dummydbCom = dbcom;
	}
	
	public double[] getAllUserStatPercentage() {
		/*Return total stay-times as percentages*/
		return this.calculateStayPercentage(this.getAllStats());
	}
	
	public double[] getGroupStatPercentage(String groupID) {
		/*Return group stays as percentages*/
		return this.calculateStayPercentage(this.getGroupStats(groupID));
	}
	
	public double[] getUserStatPercentage(int userID) {
		/*Return user stays as percentages */
		return this.calculateStayPercentage(this.getUserStats(userID));
	}
	
	/**
	 * The methods: getUserStats, getGroupStats and getAllUserStats
	 * uses the corresponding DatabaseCommunicator-methods to provide
	 * 4-element int[]-arrays on the form: [Gloshaugen, SitTrening, Samfundet, Other] 
	 */
	public int[] getUserStats(int userID) {
		try {
			return this.dbCom.getUserStats(userID);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int[] getGroupStats(String groupID) {
		try {
			return this.dbCom.getGroupStats(groupID);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int[] getAllStats() {
		try {
			return dbCom.getAllStats();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private double[] calculateStayPercentage(int[] stayData) {
		/* Calculate how many percent each element in the array is of the total value */
		int totalStayTime = 0;
		for (int stayTime : stayData) {
			totalStayTime += stayTime;
		}
		
		double[] stayPercentage = new double[stayData.length];
		for (int i = 0; i < stayData.length; i++) {
			stayPercentage[i] = (((double) stayData[i]) / totalStayTime);
		}
		
		return stayPercentage;
	}
	
	//Used by test:

	public double[] getAllUserStatPercentagetest() {
		/*Return total stay-times as percentages*/
		return this.calculateStayPercentage(this.getAllStatsTest());
	}
	
	public double[] getGroupStatPercentagetest(String groupID) {
		/*Return group stays as percentages*/
		return this.calculateStayPercentage(this.getGroupStatsTest(groupID));
	}
	
	public double[] getUserStatPercentagetest(int userID) {
		/*Return user stays as percentages */
		return this.calculateStayPercentage(this.getUserStatsTest(userID));
	}
	
	public int[] getUserStatsTest(int userID) {
		return this.dummydbCom.getUserStats(userID);
	}
	
	public int[] getGroupStatsTest(String groupID) {
		return this.dummydbCom.getGroupStats(groupID);
	}
	
	public int[] getAllStatsTest() {
		return this.dummydbCom.getAllStats();
		
	}

	public int[] getLinePointsGroup(String groupID, LocalDate startDate, LocalDate endDate, String areaName) {
		int[] retVal = {2,5,10,30,7,6,9};
		System.out.println(startDate);
		return retVal;
	}

	public int[] getLinePointsAll(LocalDate startDate, LocalDate endDate, String areaName) {
		int[] retVal = {7,8,13,17,2,20,50};
		return retVal;
	}

	public int[] getLinePointsUser(int currentUserID, LocalDate startDate, LocalDate endDate, String areaName) {
		int[] retVal = {20,10,14,15,30,24,19};
		return retVal;
	}

	public void setNewGoal(Integer goal, int userID) {
		System.out.println(goal);
	}

	public int getNumberAtGym() {
		// TODO Auto-generated method stub
		return 4;
	}
	
	public void createCompetition(String competitionName, String areaName, int requiredHours, LocalDate startDate, LocalDate endDate, String competitionDescription, String prizeDescription) {
		System.out.println("Areaname: " + areaName);
		System.out.println("Required hours: " + requiredHours);
		System.out.println("Start date: " + startDate);
		System.out.println("End date: " + endDate);
		System.out.println("Competition description: " + competitionDescription);
		System.out.println("Prize description: " + prizeDescription);
		System.out.println("Competition name: " + competitionName);
	}
	
	public ObservableList<String> getAllCompetitionNames() {
		ObservableList<String> competitionNames = FXCollections.observableArrayList("Get Fit or die trying","Supernerd!","Eternal party","Stranger in T-town");
		return competitionNames;
	}
	
	public String[] getCompetitionDetails(String competitionName) {
		if (competitionName.equals("Get Fit or die trying")) { //[areaname, required hours, startdate, enddate, competition description, prize description]
			String[] compInfo = {"SiT Trening", "879", "12.04.2018", "30.04.2018", "Bli så sykt bola slik at skjorta sprekker", "Gratis kaffe på stripa"};
			return compInfo;
		} else if (competitionName.equals("Supernerd!")) {
			String[] compInfo = {"Gløshaugen", "563", "10.04.2018", "20.04.2018", "Bli smartere enn den smarteste", "Hele innholdet til akademika"};
			return compInfo;
		} else if (competitionName.equals("Eternal party")) {
			String[] compInfo = {"Samfundet", "700", "01.04.2018", "05.05.2018", "Nyt livet! Ha det gøy!", "Lifetime supply av tequila shots for deg og 6 venner"};
			return compInfo;
		} else if (competitionName.equals("Stranger in T-town")) {
			String[] compInfo = {"Other", "1250", "01.04.2018", "30.04.2018", "Du henger ikke mye på de populære stedene i Trondheim...", "Kom deg på skolen!"};
			return compInfo;
		} 
		return null;
	}
	
	public boolean competitionExists(String competitionName) {
		return false;
	}
	
	public static boolean userInDatabase(Integer userID) throws SQLException {
		return DatabaseCommunicator.userInDatabase(userID);
	}
}
