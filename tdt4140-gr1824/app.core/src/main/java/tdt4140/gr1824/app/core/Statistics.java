
package tdt4140.gr1824.app.core;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

import tdt4140.gr1824.app.db.DatabaseCommunicator;
import tdt4140.gr1824.app.mock.DummyClassDatabaseCommunicatorForStatisticsTest;

public class Statistics {

	private DummyClassDatabaseCommunicatorForStatisticsTest dummydbCom;
	private DatabaseCommunicator dbCom;
	
	public Statistics(DatabaseCommunicator dbcom) {
		this.dbCom = dbcom;
	}
	
	public Statistics(DummyClassDatabaseCommunicatorForStatisticsTest dbcom) {
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
	 * 4-element int[]-arrays on the form: [Gløshaugen, SitTrening, Samfundet, Other] 
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
	
	public static List<Integer> getLinePointsAll(LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHoursAll = new ArrayList<Integer>();
		
		while (startDate.isBefore(stopDate)) {
			String startTime = localDateToStartString(startDate);
			String endTime = localDateToEndString(startDate.plusWeeks(1));
			weeklyHoursAll.add(DatabaseCommunicator.getWeeklyHoursAll(startTime, endTime, areaName));
			startDate = startDate.plusWeeks(1);
		}
		return weeklyHoursAll;
	}
	
	public static List<Integer> getLinePointsGroup(String groupID, LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHoursGroup = new ArrayList<Integer>();
		
		while (startDate.isBefore(stopDate)) {
			String startTime = localDateToStartString(startDate);
			String endTime = localDateToEndString(startDate.plusWeeks(1));
			weeklyHoursGroup.add(DatabaseCommunicator.getWeeklyHoursGroup(groupID, startTime, endTime, areaName));
			startDate = startDate.plusWeeks(1);
		}
		return weeklyHoursGroup;
	}
	
	public static List<Integer> getLinePointsUser(int userID, LocalDate startDate, LocalDate stopDate, String areaName) throws SQLException {
		List<Integer> weeklyHours = new ArrayList<Integer>();
		
		while (startDate.isBefore(stopDate)) {
			String startTime = localDateToStartString(startDate);
			String endTime = localDateToEndString(startDate.plusWeeks(1));
			weeklyHours.add(DatabaseCommunicator.getWeeklyHoursUser(userID, startTime, endTime, areaName));
			startDate = startDate.plusWeeks(1);
		}
		return weeklyHours;
	}
	
	private static String localDateToStartString (LocalDate localdate) {
		return ""+String.format("%1$tY-%1$tm-%1$td", localdate)+" 00:00:00";
	}
	
	private static String localDateToEndString (LocalDate localdate) {
		return ""+String.format("%1$tY-%1$tm-%1$td", localdate)+" 23:59:59";
	}
	
	public static void main(String[] args) throws SQLException {
		LocalDate startDate = LocalDate.parse("2018-04-02");
		LocalDate stopDate = LocalDate.parse("2018-05-03");
		System.out.println(getLinePointsUser(5,startDate, stopDate, "glos"));
	}
	
}
