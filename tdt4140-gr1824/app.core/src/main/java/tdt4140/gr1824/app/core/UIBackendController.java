package tdt4140.gr1824.app.core;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

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

	public int[] getLinePointsGroup(String groupID, LocalDate startDate, LocalDate endDate, String areaName) {//TODO
		int[] retVal = {2,5,10,30,7,6,9};
		System.out.println(startDate);
		return retVal;
	}

	public int[] getLinePointsAll(LocalDate startDate, LocalDate endDate, String areaName) {//TODO
		int[] retVal = {7,8,13,17,2,20,50};
		return retVal;
	}

	public int[] getLinePointsUser(int currentUserID, LocalDate startDate, LocalDate endDate, String areaName) {//TODO
		int[] retVal = {20,10,14,15,30,24,19};
		return retVal;
	}

	public void setNewGoal(Integer goal, int userID) {
		DatabaseCommunicator.updateGoal(goal, userID);
	}

	public String getGoal(int userID) {
		return DatabaseCommunicator.getGoal(int userID);
	}
	
	public int getNumberAtGym() {
		return DatabaseCommunicator.getNumberAtGym();
	}
	
	public void createCompetition(String competitionName, String areaName, int requiredHours, LocalDate startDate, LocalDate endDate, String competitionDescription, String prizeDescription) {
		DatabaseCommunicator.addCompetition(competitionName, this.prettyNameToId(areaName), requiredHours, this.localDateToString(startDate), this.localDateToString(endDate), competitionDescription, prizeDescription);
	}
	
	public ObservableList<String> getAllCompetitionNames() {
		ObservableList<String> competitionNames = FXCollections.observableArrayList(DatabaseCommunicator.getAllCompetitionNames());
		return competitionNames;
	}
	
	public ObservableList<String> getRewardNames(int userID) { 
		ObservableList<String> rewardNames = FXCollections.observableArrayList(DatabaseCommunicator.getCompetitionNamesByUser(userID));
		return rewardNames;
	}
	
	public String[] getCompetitionDetails(String competitionName) {
		String[] compInfo = new String[6];
		compInfo[0] = this.idToPrettyName(DatabaseCommunicator.getCompetitionAreaID(competitionName));
		compInfo[1] = DatabaseCommunicator.getCompetitionDuration(competitionName) + " hours";
		
		String[] startEndDate = DatabaseCommunicator.getCompetitionDates(competitionName);
		
		compInfo[2] = startEndDate[0];
		compInfo[3] = startEndDate[1];
		compInfo[4] = DatabaseCommunicator.getCompetitionDescription(competitionName);
		compInfo[5] = DatabaseCommunicator.getCompetitionPrize(competitionName);
		return compInfo;
	}
	
	public String getStaydurationUserArea(String competitionName, int userID) { 
		return DatabaseCommunicator.getCompInfo(userID, competitionName).get(6);
	}
	
	public boolean competitionExists(String competitionName) {
		return DatabaseCommunicator.competitionInDatabase(competitionName);
	}
	
	public ObservableList<String> getWinners(String competitionName) { 
		ObservableList<String> winners = FXCollections.observableArrayList(DatabaseCommunicator.getWinners(competitionName)); //Denne returnerer personID-er
		return winners;
	}
	
	public static boolean userInDatabase(Integer userID) throws SQLException {
		return DatabaseCommunicator.userInDatabase(userID);
	}
	
	public void deleteUser(int userID) {
		DatabaseCommunicator.deleteUser(userID);
	}
	
	private int prettyNameToId(String prettyName) {
		if (prettyName.equals("Gloshaugen")) {
			return 1;
		} else if (prettyName.equals("SiT Trening")) {
			return 2;
		} else if (prettyName.equals("Samfundet")) {
			return 3;
		} else if (prettyName.equals("Other")) {
			return 4;
		}
		
		return 0;
	}
	
	private String idToPrettyName(int areaID) {
		if (areaID == 1) {
			return "Gloshaugen";
		} else if (areaID == 2) {
			return "SiT Trening";
		} else if (areaID == 3) {
			return "Samfundet"; 
		} else if (areaID == 4) {
			return "Other";
		}
		
		return null;
	}
	
	private String localDateToString(LocalDate date) {
		if (date.getDayOfWeek() == DayOfWeek.MONDAY) {
			return date.toString() + " 00:00:00";
		} else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.toString() + " 23:59:59";
		}
		return date.toString() + " 00:00:00";
	}
	
	
}
