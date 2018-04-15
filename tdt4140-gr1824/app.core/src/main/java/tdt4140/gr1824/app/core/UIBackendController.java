package tdt4140.gr1824.app.core;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tdt4140.gr1824.app.db.DatabaseCommunicator;

public class UIBackendController {
	
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
			return DatabaseCommunicator.getUserStats(userID);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int[] getGroupStats(String groupID) {
		try {
			return DatabaseCommunicator.getGroupStats(groupID);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int[] getAllStats() {
		try {
			return DatabaseCommunicator.getAllStats();
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
	
	public static int[] getLinePointsAll(LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHoursAll = new ArrayList<Integer>();
		String dbAreaName = prettyNameTodbName(areaName);
		
		while (startDate.isBefore(stopDate) && startDate.isBefore(LocalDate.now())) {
			String startTime = localDateToStartString(startDate);
			String endTime;
			
			if (startDate.plusDays(6).isAfter(LocalDate.now())) {
				endTime = localDateToEndString(LocalDate.now());
			}
			else {
				endTime = localDateToEndString(startDate.plusDays(6));
			}
			weeklyHoursAll.add(DatabaseCommunicator.getWeeklyHoursAll(startTime, endTime, dbAreaName));
			startDate = startDate.plusWeeks(1);
		}
		// converting to hours
		int[] retVal = new int[weeklyHoursAll.size()];
		for (int i = 0; i < weeklyHoursAll.size(); i++) {
			retVal[i] = (int)weeklyHoursAll.get(i)/60;
		}
		
		return retVal;
	}
	
	public static int[] getLinePointsGroup(String groupID, LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHoursGroup = new ArrayList<Integer>();
		String dbAreaName = prettyNameTodbName(areaName);
		
		while (startDate.isBefore(stopDate) && startDate.isBefore(LocalDate.now())) {
			String startTime = localDateToStartString(startDate);
			String endTime;
			
			if (startDate.plusDays(6).isAfter(LocalDate.now())) {
				endTime = localDateToEndString(LocalDate.now());
			}
			else {
				endTime = localDateToEndString(startDate.plusDays(6));
			}
			weeklyHoursGroup.add(DatabaseCommunicator.getWeeklyHoursGroup(groupID, startTime, endTime, dbAreaName));
			startDate = startDate.plusWeeks(1);
		}
		int[] retVal = new int[weeklyHoursGroup.size()];
		for (int i = 0; i < weeklyHoursGroup.size(); i++) {
			retVal[i] = (int)weeklyHoursGroup.get(i)/60;
		}
		
		return retVal;
	}
	
	public static int[] getLinePointsUser(int userID, LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHours = new ArrayList<Integer>();
		String dbAreaName = prettyNameTodbName(areaName);
		
		while (startDate.isBefore(stopDate) && startDate.isBefore(LocalDate.now())) {
			String startTime = localDateToStartString(startDate);
			String endTime;
			
			if (startDate.plusDays(6).isAfter(LocalDate.now())) {
				endTime = localDateToEndString(LocalDate.now());
			}
			else {
				endTime = localDateToEndString(startDate.plusDays(6));
			}
			
			weeklyHours.add(DatabaseCommunicator.getWeeklyHoursUser(userID, startTime, endTime, dbAreaName));
			startDate = startDate.plusWeeks(1);
		}
		int[] retVal = new int[weeklyHours.size()];
		for (int i = 0; i < weeklyHours.size(); i++) {
			retVal[i] = (int)weeklyHours.get(i)/60;
		}
		
		return retVal;
	}
	
	
	private static String localDateToStartString (LocalDate localdate) {
		return ""+String.format("%1$tY-%1$tm-%1$td", localdate)+" 00:00:00";
	}
	
	private static String localDateToEndString (LocalDate localdate) {
		return ""+String.format("%1$tY-%1$tm-%1$td", localdate)+" 23:59:59";
	}

	public void setNewGoal(Integer goal, int userID) {
		DatabaseCommunicator.updateGoal(goal, userID);
	}

	public String getGoal(int userID) throws SQLException {
		return String.valueOf(DatabaseCommunicator.getGoal(userID));
	}
	
	public int getNumberAtGym() throws SQLException {
		return DatabaseCommunicator.getNumberAtGym();
	}
	
	public void createCompetition(String competitionName, String areaName, int requiredHours, LocalDate startDate, LocalDate endDate, String competitionDescription, String prizeDescription) throws SQLException {
		DatabaseCommunicator.addCompetition(competitionName, this.prettyNameToId(areaName), requiredHours, this.localDateToString(startDate, true), this.localDateToString(endDate, false), competitionDescription, prizeDescription);
	}
	
	public ObservableList<String> getAllCompetitionNames() throws SQLException {
		ObservableList<String> competitionNames = FXCollections.observableArrayList(DatabaseCommunicator.getAllCompetitionNames());
		return competitionNames;
	}
	
	public ObservableList<String> getRewardNames(int userID) throws SQLException { 
		ObservableList<String> rewardNames = FXCollections.observableArrayList(DatabaseCommunicator.getCompetitionNamesByUser(userID));
		return rewardNames;
	}
	
	public String[] getCompetitionDetails(String competitionName) throws SQLException {
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
	
	public String getStaydurationUserArea(String competitionName, int userID) throws SQLException { 
		return DatabaseCommunicator.getCompInfo(userID, competitionName).get(6) + " hours";
	}
	
	public boolean competitionExists(String competitionName) throws SQLException {
		return DatabaseCommunicator.competitionInDatabase(competitionName);
	}
	
	public ObservableList<String> getWinners(String competitionName) throws SQLException { 
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
	
	private String localDateToString(LocalDate date, boolean startDate) {
		if (startDate) {
			return date.toString() + " 00:00:00";
		} else {
			return date.toString() + " 23:59:59";
		}
	}
	
	private static String prettyNameTodbName(String areaName) {
		if (areaName.equals("Gloshaugen")) {
			return "glos";
		} else if (areaName.equals("SiT Trening")) {
			return "sitTrening";
		} else if (areaName.equals("Samfundet")) {
			return "samfundet";
		} else {
			return "other";
		}
	}

	
}
