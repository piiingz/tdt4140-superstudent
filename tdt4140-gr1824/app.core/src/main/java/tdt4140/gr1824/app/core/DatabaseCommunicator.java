package tdt4140.gr1824.app.core;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Scanner;

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
				System.out.println("Couldn't close connection");
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
	
    public static void main(String[] args) throws SQLException {
    	//DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
    	//createUser();
    	//deleteUser(1);
    	//addArea("Hjemme hos Espen");
    	//deleteArea(5);
    	//databaseCommunicator.printTables();
    //System.out.println(getNextPersonID());
    	//System.out.println(getAreaID("glos"));
    	//System.out.println(getAreaName(1));
    	//printPersonTable();
    	//updateCurrentStay(20, "glos", "2018-03-21 21:01:55");
    	//getCurrentStay(20);
    	//deleteUser(20);
    	//addStay("2018-03-21 21:01:55", 30, "other", 1);
    //	deleteStay(8);
    //getGroupStats("other");
    	//getAllStats();
    	//getUserStats(30);
    	//System.out.println(userInDatabase(30));
    //System.out.println(getAreaName(5));
    }

}