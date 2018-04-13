package tdt4140.gr1824.app.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCommunicator {
	
	private static Connection connection = null;
	private static DataSource dataSource = DatabaseConnection.getMySQLDataSource();
	private static ResultSet rs = null;
	
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				try {
					//System.out.println("Connecting database...");
					connection = dataSource.getConnection();
					//System.out.println("Successfully connected to the database");
				} catch (SQLException e) {
					System.out.println("Could not connect to database");
					throw e;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				//System.out.println("Connection closed");
			} catch (SQLException e) {
				System.out.println("Could not close connection");
				e.printStackTrace();
			}
		}
	}
			
	public static ResultSet getResultSet(String query) throws SQLException{
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			String sql = query;
			rs = stmt.executeQuery(sql);
			return rs;
		} 
		catch (SQLException e) {
			throw e;
		}
	};
	
	public static void updateTable(String query) {
		Connection connection = null;
		Statement stmt = null;
		
		try {
			connection = getConnection();
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			//System.out.println("Query successfully executed");
			
		}
		catch( SQLException se )
        {
			System.out.println("Couldn't ecexute query");
            se.printStackTrace();
        }
	}
		
	public static String[] getCurrentStay(int personID) throws SQLException {
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String areaName = "";
		String starttime = "";
		int areaID = 0;
		
		String Query1 = "SELECT starttime as time FROM currentstay WHERE personID = "+personID+";";
		String Query2 = "SELECT areaID as area FROM currentstay WHERE personID = "+personID+";";
		
		rs1 = getResultSet(Query1);
		if (rs1.next()) {
			starttime = rs1.getString("time");
		}
		
		rs2 = getResultSet(Query2);
		if (rs2.next()) {
			areaID = rs2.getInt("area");
		}
		closeConnection();
		starttime = starttime.substring(0, starttime.length() - 2);
		areaName = getAreaName(areaID);
		String[] returnSet = {areaName,starttime};
		System.out.println("Starttime: "+starttime+" Areaname: "+areaName);
		return returnSet;
	}
	
	public static void createUser(String fullname, String gender, int schoolYear, String major) throws SQLException {
		
		Integer nextID = getNextPersonID();
		
		System.out.println("Here is the information you provided:\r\n"+"Fullname: "+fullname+", Gender: "+gender+", Course: "+major+", Year: "+schoolYear);
		
		String sql1 = "INSERT INTO person VALUES("+nextID+", '"+fullname+"', '"+gender+"', "+schoolYear+", '"+major+"');";
		String sql2 = "INSERT INTO currentstay VALUES ("+nextID+", 4, current_timestamp);";
		
		updateTable(sql1);
		updateTable(sql2);
		
		closeConnection();
	}
  
	public static boolean userInDatabase(int userID) throws SQLException {
		boolean userInDatabase = false;
		
		rs = getResultSet("select * from person where personID = "+userID);
		if (rs.next()) {
			userInDatabase = true;
		}
		closeConnection();
		return userInDatabase;
	}

	public static void updateCurrentStay(int userID, String areaName, String dateTime) throws SQLException {
		
		int areaID = getAreaID(areaName);
		String query = "UPDATE currentstay SET starttime = '"+dateTime+"', areaID = "+areaID+" WHERE personID = "+userID+";";
		updateTable(query);
		
		closeConnection();
		
	}
	
	public static void deleteUser(Integer personID) {
		updateTable("DELETE FROM person WHERE personID = "+personID+";");
		closeConnection();
	}	

	public static int getNextAreaID() throws SQLException {
		
		int nextID = 0;
		rs = getResultSet("SELECT MAX(areaID) AS maximum FROM definedarea;");
		
		if (rs.next()) {
			int currID = rs.getInt("maximum");
			nextID = currID+1;
		}
		closeConnection();
		return nextID;
	}		
	
	public static void addArea(String areaName) throws SQLException {
		int areaID = getNextAreaID();
		updateTable("INSERT INTO definedarea VALUES("+areaID+", '"+areaName+"');");
		closeConnection();
	}	
	
	public static void deleteArea(Integer areaID) {
		
		updateTable("DELETE FROM definedarea WHERE areaID="+areaID+";");
		closeConnection();	
	}	

	public static int getAreaID(String areaName) throws SQLException {
		int areaID = 0;
		rs = getResultSet("SELECT areaID as ID FROM definedarea WHERE areaname = '"+areaName+"';");
		
		if (rs.next()) {
			areaID = rs.getInt("ID");
		}
		
		closeConnection();
		return areaID;
	}
	
	public static void addStay(String starttime, Long duration, String areaName, int userID) throws SQLException {
		
		rs = getResultSet("SELECT * FROM definedarea WHERE areaname = '"+areaName+"'");
		Integer areaID = 0;
		
		if (rs.next()) {
			areaID = rs.getInt("areaID");
		}
		
		updateTable("INSERT INTO stay (starttime, duration, personID, areaID) VALUES('"+starttime+"', "+duration+", "+userID+", "+areaID+");");
		
		closeConnection();
		
	
	}
	
	public static void deleteStay(Integer stayID) {
		
		updateTable("DELETE FROM stay WHERE stayID='"+stayID+"';");
		closeConnection();
	}	

	public static int getNextPersonID() throws SQLException {
		
		int nextID = 0;
		rs = getResultSet("SELECT MAX(personID) AS maximum FROM person;");
		
		if (rs.next()) {
			int currID = rs.getInt("maximum");
			nextID = currID+1;
		}
		closeConnection();
		return nextID;
	}	

	public static String getAreaName(int areaID) throws SQLException {
		
		String areaName = "";
		
		rs = getResultSet("SELECT areaname as name FROM definedarea WHERE areaID = '"+areaID+"';");
		if (rs.next()) {
			areaName = rs.getString("name");
		}
		closeConnection();
		return areaName;
	}
	
	public static int[] getUserStats(int userID) throws SQLException {
		int glosDur = 0;
		int sitTreningDur = 0;
		int samfundetDur = 0;
		int otherDur = 0;
		
		String glosQuery = "SELECT SUM(duration) AS dur FROM stay WHERE personID = "+userID+" AND areaID = 1;";
		String sitQuery = "SELECT SUM(duration) AS dur FROM stay WHERE personID = "+userID+" AND areaID = 2;";
		String samfQuery = "SELECT SUM(duration) AS dur FROM stay WHERE personID = "+userID+" AND areaID = 3;";
		String otherQuery = "SELECT SUM(duration) AS dur FROM stay WHERE personID = "+userID+" AND areaID = 4;";
				
		ResultSet rs1 = getResultSet(glosQuery);
		if (rs1.next()) {
			glosDur = rs1.getInt("dur");
		} rs1.close();
		
		ResultSet rs2 = getResultSet(sitQuery);
		if (rs2.next()) {
			sitTreningDur = rs2.getInt("dur");
		} rs2.close();
		
		ResultSet rs3 = getResultSet(samfQuery);
		if (rs3.next()) {
			samfundetDur = rs3.getInt("dur");
		} rs3.close();
		
		ResultSet rs4 = getResultSet(otherQuery);
		if (rs4.next()) {
			otherDur = rs4.getInt("dur");
		} rs4.close();
		
		closeConnection();
		
		System.out.println("glos: "+glosDur+" sit: "+sitTreningDur+" samf: "+samfundetDur+" other: "+otherDur);
		

		int[] userStatset = {glosDur,sitTreningDur,samfundetDur,otherDur};

		return userStatset;
	}
	
	public static int[] getAllStats() throws SQLException {
		int glosDur = 0;
		int sitTreningDur = 0;
		int samfundetDur = 0;
		int otherDur = 0;
		
		String glosQuery = "SELECT SUM(duration) AS dur FROM stay WHERE areaID = 1;";
		String sitQuery = "SELECT SUM(duration) AS dur FROM stay WHERE areaID = 2;";
		String samfQuery = "SELECT SUM(duration) AS dur FROM stay WHERE areaID = 3;";
		String otherQuery = "SELECT SUM(duration) AS dur FROM stay WHERE areaID = 4;";
				
		ResultSet rs1 = getResultSet(glosQuery);
		if (rs1.next()) {
			glosDur = rs1.getInt("dur");
		} rs1.close();
		
		ResultSet rs2 = getResultSet(sitQuery);
		if (rs2.next()) {
			sitTreningDur = rs2.getInt("dur");
		} rs2.close();
		
		ResultSet rs3 = getResultSet(samfQuery);
		if (rs3.next()) {
			samfundetDur = rs3.getInt("dur");
		} rs3.close();
		
		ResultSet rs4 = getResultSet(otherQuery);
		if (rs4.next()) {
			otherDur = rs4.getInt("dur");
		} rs4.close();
		
		closeConnection();
		
		System.out.println("glos: "+glosDur+" sit: "+sitTreningDur+" samf: "+samfundetDur+" other: "+otherDur);
		

		int[] userStatset = {glosDur,sitTreningDur,samfundetDur,otherDur};

		return userStatset;
	}
	
	public static int[] getGroupStats(String gender) throws SQLException {
		int glosDur = 0;
		int sitTreningDur = 0;
		int samfundetDur = 0;
		int otherDur = 0;
		
		if(!(gender.equals("male")||gender.equals("female")||gender.equals("other"))) {
			throw new IllegalArgumentException("Not a valid gender");
		}
		
		String glosQuery = "SELECT SUM(duration) AS dur FROM stay INNER JOIN person ON stay.personID = person.personID WHERE gender = '"+gender+"' AND areaID = 1;";
		String sitQuery = "SELECT SUM(duration) AS dur FROM stay INNER JOIN person ON stay.personID = person.personID WHERE gender = '"+gender+"' AND areaID = 2;";
		String samfQuery = "SELECT SUM(duration) AS dur FROM stay INNER JOIN person ON stay.personID = person.personID WHERE gender = '"+gender+"' AND areaID = 3;";
		String otherQuery = "SELECT SUM(duration) AS dur FROM stay INNER JOIN person ON stay.personID = person.personID WHERE gender = '"+gender+"' AND areaID = 4;";
		
		ResultSet rs1 = getResultSet(glosQuery);
		if (rs1.next()) {
			glosDur = rs1.getInt("dur");
		} rs1.close();
		
		ResultSet rs2 = getResultSet(sitQuery);
		if (rs2.next()) {
			sitTreningDur = rs2.getInt("dur");
		} rs2.close();
		
		ResultSet rs3 = getResultSet(samfQuery);
		if (rs3.next()) {
			samfundetDur = rs3.getInt("dur");
		} rs3.close();
		
		ResultSet rs4 = getResultSet(otherQuery);
		if (rs4.next()) {
			otherDur = rs4.getInt("dur");
		} rs4.close();
		
		closeConnection();
		
		System.out.println("glos: "+glosDur+" sit: "+sitTreningDur+" samf: "+samfundetDur+" other: "+otherDur);
		

		int[] userStatset = {glosDur,sitTreningDur,samfundetDur,otherDur};

		return userStatset;
	}
	
	//Method that takes in starttime, stoptime and area and returns a list on the format [userID(0), minutes(0), userID(1), minutes(1)...] of all users that has been in the area over the duration. 
	//Minutes is how many minutes the user has been there.
	public static List<Integer> getDurationOfStays(String starttime, String stoptime, int areaID) throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		String query = "select personID, sum(duration) as timeSpent from (select timestampdiff(minute, starttime, stoptime) as duration, personID from ((select starttime, stoptime, personID From stay as stay1 where starttime >= '"+starttime+"' AND stoptime <= '"+stoptime+"' AND areaID = "+areaID+" UNION select starttime, stoptime, personID From stay as stay2 where starttime < '"+starttime+"' AND stoptime <= '"+stoptime+"' AND stoptime >= '"+starttime+"' AND areaID = "+areaID+" UNION select starttime, stoptime, personID from stay as stay3 where starttime >= '"+starttime+"' AND starttime <= '"+stoptime+"' AND stoptime > '"+stoptime+"' AND areaID = "+areaID+" UNION SELECT starttime,  '"+stoptime+"' AS stoptime, personID from currentstay where areaID = "+areaID+" AND starttime >= '"+starttime+"' AND starttime <= '"+stoptime+"' UNION SELECT '"+starttime+"' as starttime,  '"+stoptime+"' AS stoptime, personID from currentstay where areaID = "+areaID+" AND starttime <= '"+starttime+"' )AS table1)) as durationspp group by personID;";
		ResultSet rs = getResultSet(query);
		while (rs.next()) {
			list.add(rs.getInt("personID"));
			list.add(rs.getInt("timeSpent"));
		} 
		
		rs.close();
		closeConnection();
		return list;
		
	}
	
	//Returns a list of the names of all the competitions the user has won.
	public static List<String> getCompetitionNamesByUser(int userID) throws SQLException{
		List<String> list = new ArrayList<String>();
		List<String> startTimes = new ArrayList<String>();
		List<String> stopTimes = new ArrayList<String>();
		List<Integer> areas = new ArrayList<Integer>();
		List<Integer> hours = new ArrayList<Integer>();
		List<String> names = new ArrayList<String>();
		String query = "select competitionName, description, startdate, stopdate, areaID, hours, prize from competition where stopdate < curdate();";
		ResultSet rs1 = getResultSet(query);
		while (rs1.next()) { 
			startTimes.add(rs1.getString("startdate"));
			stopTimes.add(rs1.getString("stopdate"));
			areas.add(rs1.getInt("areaID"));
			hours.add(rs1.getInt("hours"));
			names.add(rs1.getString("competitionName"));
		}rs1.close();
		int n = startTimes.size();
		for (int i = 0; i < n; i++) {
			List<Integer> list2 = getDurationOfStays(startTimes.get(i), stopTimes.get(i), areas.get(i));
			for (int j = 0; j < list2.size(); j+=2) {
				if(list2.get(j) == userID) {
					if((list2.get(j+1)) >= hours.get(i)*60) {
						list.add(names.get(i));
						}
					}
				}
			}
		
		closeConnection();
		return list;
	}
	
	
	// Gir List<String> i denne rekkefølgen: description, prize, starttime, stoptime, areaID, hours, actual hours.
	public static List<String> getCompInfo(int userID, String competitionName) throws SQLException {
		String query = "select * from competition where competitionName = '"+competitionName+"';";
		List<String> compInfo = new ArrayList<String>();
		String startDate;
		String endDate;
		int area;
		ResultSet rs = getResultSet(query);
		if(rs.next()) {
			startDate = rs.getString("startDate");
			endDate = rs.getString("stopDate");
			area = rs.getInt("areaID");
			compInfo.add(rs.getString("description"));
			compInfo.add(rs.getString("prize"));
			compInfo.add(startDate);
			compInfo.add(endDate);
			compInfo.add("" + area);
			compInfo.add("" + rs.getInt("hours"));
			rs.close();
			List<Integer> tempList = getDurationOfStays(startDate, endDate, area);
			for (int i = 0; i < tempList.size(); i+=2) {
				if(tempList.get(i) == userID) {
					compInfo.add("" + tempList.get(i+1)/60);
				}
			}
		}
		closeConnection();
		return compInfo;
	}
	
	
	public static void main(String[] args) throws SQLException {
		getDurationOfStays("2018-04-06 00:00:00", "2018-04-10 23:59:59", 2);
		System.out.println(getCompetitionNamesByUser(2));
		System.out.println(getCompInfo(2, "Skoleflink"));
	}
	
}