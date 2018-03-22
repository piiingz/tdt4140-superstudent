package tdt4140.gr1824.app.core;

import java.sql.SQLException;

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
}
