package tdt4140.gr1824.app.db;

import java.sql.Connection;
import java.sql.Statement;
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
		return returnSet;
	}
	
	public static void createUser(String fullname, String gender, int schoolYear, String major, int weeklygoal) throws SQLException {
		
		Integer nextID = getNextPersonID();
		
		System.out.println("Here is the information you provided:\r\n"+"Fullname: "+fullname+", Gender: "+gender+", Course: "+major+", Year: "+schoolYear);
		
		String sql1 = "INSERT INTO person VALUES("+nextID+", '"+fullname+"', '"+gender+"', "+schoolYear+", '"+major+"', "+weeklygoal+");";
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
	
	public static void addStay(String starttime, String stoptime, Long duration, String areaName, int userID) throws SQLException {
		
		rs = getResultSet("SELECT * FROM definedarea WHERE areaname = '"+areaName+"'");
		Integer areaID = 0;
		
		if (rs.next()) {
			areaID = rs.getInt("areaID");
		}
		
		updateTable("INSERT INTO stay (starttime, stoptime, duration, personID, areaID) VALUES('"+starttime+"', '"+stoptime+"', "+duration+", "+userID+", "+areaID+");");
		
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

	public static void updateGoal(int hours, int personID) {
		
		String query = "UPDATE person SET weeklygoal = "+hours+" WHERE personID = "+personID+";";
		
		updateTable(query);
		
		closeConnection();
		
	}
	
	public static int getNumberAtGym() throws SQLException {
		
		int numberAtGym = 0;
		
		rs = getResultSet("select COUNT(personID) as num FROM currentstay WHERE areaID = 2;");
		
		if (rs.next()) {
			numberAtGym = rs.getInt("num");
		}
		closeConnection();
		
		return numberAtGym;
	}

	public static Integer getWeeklyHoursUser(int personID, String startTime, String stopTime, String areaName) throws SQLException {
		int areaID = getAreaID(areaName);
		int weeklyHours = 0;
		String query = "select timeSpent as personHours from (select personID, sum(duration) as timeSpent from (select timestampdiff(minute, starttid, stoptid) as duration, personID from"
				+"((select starttime as starttid, stoptime as stoptid, personID From stay as stay1 where starttime >= '"+startTime+"' AND stoptime <= '"+stopTime+"' AND areaID = "+areaID+" UNION "
				+"select '"+startTime+"' as starttid, stoptime as stoptid, personID From stay as stay2 where starttime < '"+startTime+"' AND stoptime <= '"+stopTime+"' AND stoptime >= '"+startTime+"' AND areaID = "+areaID+" UNION " 
				+"select starttime as starttid, '"+stopTime+"' as stoptid, personID from stay as stay3 where starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' AND stoptime > '"+stopTime+"' AND areaID = "+areaID+" UNION " 
				+"SELECT starttime as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from stay where areaID = "+areaID+" and starttime < '"+startTime+"' and stoptime > '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime <= '"+startTime+"' )AS table1)) as durationspp group by personID) as personStats "
				+"where personID = "+personID+";";
	
		getConnection();
		ResultSet rs = getResultSet(query);
		if (rs.next()) {
			weeklyHours = rs.getInt("personHours");
		}
		closeConnection();
		
		return weeklyHours;
	}

	public static Integer getWeeklyHoursGroup(String groupID, String startTime, String stopTime, String areaName) throws SQLException {
		int areaID = getAreaID(areaName);
		
		int weeklyHours = 0;
		String query = "select avg(timeSpent) as genderAverage from (select timeSpent, gender from "
				+"(select personID, sum(duration) as timeSpent from (select timestampdiff(minute, starttid, stoptid) as duration, personID from"
				+"((select starttime as starttid, stoptime as stoptid, personID From stay as stay1 where starttime >= '"+startTime+"' AND stoptime <= '"+stopTime+"' AND areaID = "+areaID+" UNION "
				+"select '"+startTime+"' as starttid, stoptime as stoptid, personID From stay as stay2 where starttime < '"+startTime+"' AND stoptime <= '"+stopTime+"' AND stoptime >= '"+startTime+"' AND areaID = "+areaID+" UNION " 
				+"select starttime as starttid, '"+stopTime+"' as stoptid, personID from stay as stay3 where starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' AND stoptime > '"+stopTime+"' AND areaID = "+areaID+" UNION " 
				+"SELECT starttime as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from stay where areaID = "+areaID+" and starttime < '"+startTime+"' and stoptime > '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime <= '"+startTime+"' )AS table1)) as durationspp group by personID) as tempGender inner join person on person.personID = tempGender.personID) as GenderStats where gender = '"+groupID+"';";
		getConnection();
		ResultSet rs = getResultSet(query);
		if (rs.next()) {
			weeklyHours = rs.getInt("genderAverage");
		}
		closeConnection();
		
		return weeklyHours;
	}
	
	// Get weekly AVERAGE hours for all
	public static Integer getWeeklyHoursAll(String startTime, String stopTime, String areaName) throws SQLException {
		int areaID = getAreaID(areaName);
		int weeklyHours = 0;
		String query = "select avg(timeSpent) as allAverage from (select personID, sum(duration) as timeSpent from (select timestampdiff(minute, starttid, stoptid) as duration, personID from"
				+"((select starttime as starttid, stoptime as stoptid, personID From stay as stay1 where starttime >= '"+startTime+"' AND stoptime <= '"+stopTime+"' AND areaID = "+areaID+" UNION "
				+"select '"+startTime+"' as starttid, stoptime as stoptid, personID From stay as stay2 where starttime < '"+startTime+"' AND stoptime <= '"+stopTime+"' AND stoptime >= '"+startTime+"' AND areaID = "+areaID+" UNION " 
				+"select starttime as starttid, '"+stopTime+"' as stoptid, personID from stay as stay3 where starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' AND stoptime > '"+stopTime+"' AND areaID = "+areaID+" UNION " 
				+"SELECT starttime as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime >= '"+startTime+"' AND starttime <= '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from stay where areaID = "+areaID+" and starttime < '"+startTime+"' and stoptime > '"+stopTime+"' UNION " 
				+"SELECT '"+startTime+"' as starttid,  '"+stopTime+"' AS stoptid, personID from currentstay where areaID = "+areaID+" AND starttime <= '"+startTime+"' )AS table1)) as durationspp group by personID) as allStats;";
		getConnection();
		ResultSet rs = getResultSet(query);
		if (rs.next()) {
			weeklyHours = rs.getInt("allAverage");
		}
		closeConnection();
		
		return weeklyHours;
		
	}
	
	public static int getGoal(int personID) throws SQLException {
		int goal = 0;
		
		rs = getResultSet("select weeklygoal as num FROM person WHERE personID = "+personID+";");
		
		if (rs.next()) {
			goal = rs.getInt("num");
		}
		closeConnection();
		
		return goal;
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(getWeeklyHoursGroup("male","2018-04-06 00:00:00", "2018-04-10 23:59:59", "sitTrening"));
	}

}













