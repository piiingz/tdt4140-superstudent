package tdt4140.gr1824.app.core;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.Statement;
import javax.print.attribute.standard.PrinterLocation;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabaseCommunicator {
	
	public static void main(String[] args) throws SQLException {
		DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
		//databaseCommunicator.createUser();
		//databaseCommunicator.deleteUser(1);
		//databaseCommunicator.addArea("Hjemme hos Espen");
		//databaseCommunicator.deleteArea("glos");
		//databaseCommunicator.printTables();
		//databaseCommunicator.addStay();
		//databaseCommunicator.getNextID();
		databaseCommunicator.getAreaID("glos");
		databaseCommunicator.getAreaName(1);
		databaseCommunicator.getCurrentStay(1);
		//databaseCommunicator.updateCurrentStay(1,"glos","2018-03-21 12:14:55");
	}
	
	public static ResultSet getResultSet(String query) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = query;
			
			rs = stmt.executeQuery(sql);
			return rs;
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try { rs.close();  
            }
            catch (Exception e) {
            	e.printStackTrace();
            }
            
        	try
            {
            	if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		return null;
	}
	
	public static void updateTable(String query) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			System.out.println("Query successfully executed");
			
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		
	}
	
	public static void createUser() throws SQLException {

		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter your fullname: ");
		String fullname = reader.nextLine();
		System.out.println("Enter your gender: ");
		String gender = reader.nextLine();
		System.out.println("Enter your study major: ");
		String major = reader.nextLine();
		System.out.println("Enter your year of study: ");
		Integer schoolYear = reader.nextInt();
		
		Integer nextID = getNextID();
		
		System.out.println("Here is the information you provided:\r\n"+"Fullname: "+fullname+", Gender: "+gender+", Course: "+major+", Year: "+schoolYear);
		
		String sql1 = "INSERT INTO person VALUES("+nextID+", '"+fullname+"', '"+gender+"', "+schoolYear+", '"+major+"');";
		String sql2 = "INSERT INTO currentstay VALUES ("+nextID+", 4, current_timestamp);";
		
		updateTable(sql1);
		updateTable(sql2);
	}

	public static void updateCurrentStay(int userID, String areaName, String dateTime) {
		
		int areaID = getAreaID(areaName);
		
		String query = "UPDATE currentstay SET starttime = '"+dateTime+"', areaID = "+areaID+" WHERE personID = "+userID+";";
		
		updateTable(query);
		
	}
	
	public static void deleteUser(Integer personID) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = "DELETE FROM person WHERE personID = "+personID+";";
			
			stmt.executeUpdate(sql);
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	
	}	

	public static void addArea(String areaName) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = "INSERT INTO definedarea (areaname) VALUES('"+areaName+"');";
			
			stmt.executeUpdate(sql);
			System.out.println("Area succesfully added!");
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	
	}	
	
	public static void deleteArea(Integer areaID) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = "DELETE FROM definedarea WHERE areaname="+areaID+";";
			
			stmt.executeUpdate(sql);
			System.out.println("Area succesfully deleted!");
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	
	}	

	public static void addStay(String starttime, Long duration, String areaName, int userID) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String getAreaIDsql = "SELECT * FROM definedarea WHERE areaname = '"+areaName+"'";
			ResultSet rs = stmt.executeQuery(getAreaIDsql);
			Integer areaID = 0;
			
			if (rs.next()) {
				areaID = rs.getInt("areaID");
			}
			
			String sql = "INSERT INTO stay (starttime, duration, personID, areaID) VALUES('"+starttime+"', "+duration+", "+userID+", "+areaID+");";
			stmt.executeUpdate(sql);
			System.out.println("Stay successfully added!");
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	
	}
	
	public static void deleteStay(Integer stayID) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = "DELETE FROM stay WHERE stayID='"+stayID+"';";
			
			stmt.executeUpdate(sql);
			System.out.println("Area succesfully deleted!");
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	
	}	

	public static void printTables() {

		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			//String sql = "SELECT * FROM person;";
			String getIDQuery = "SELECT MAX(personID) AS maximum FROM person;";
			
			ResultSet rs1 = stmt.executeQuery(getIDQuery);
			//ResultSet rs = stmt.executeQuery(sql);
			
			while (rs1.next()) {
				int currID = rs1.getInt("maximum");
				int nextID = currID+1;
				System.out.println("Next ID: "+nextID);
			}
			
			/*while (rs.next()) {
				int id = rs.getInt("personID");
				String name = rs.getString("fullname");
				int schoolYear = rs.getInt("schoolyear");
				String major = rs.getString("major");
				String gender = rs.getString("gender");
				
				System.out.print("ID: " + id);
				System.out.print(", Name: " + name);
				System.out.print(", Gender: " + gender);
				System.out.print(", Schoolyear: " + schoolYear);
				System.out.print(", Major: " + major + "\r\n");
			}*/
			//rs.close();
			rs1.close();
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            try
            {
                if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}	
	}
	
	public static int getNextID() throws SQLException {
		
		Connection connection = null;
		Statement stmt = null;
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs = null;
		
		int nextID = 0;
		String Query = "SELECT MAX(personID) AS maximum FROM person;";
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(Query);
			
			if (rs.next()) {
				int currID = rs.getInt("maximum");
				nextID = currID+1;
			}
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            
        	try
            {
            	if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		return nextID;
	}	

	public static int getAreaID(String areaName) {
		Connection connection = null;
		Statement stmt = null;
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs = null;
		
		int areaID = 0;
		String Query = "SELECT areaID as ID FROM definedarea WHERE areaname = '"+areaName+"';";
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(Query);
			
			if (rs.next()) {
				areaID = rs.getInt("ID");
			}
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            
        	try
            {
            	if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		System.out.println(areaID);
		return areaID;
	}

	public static String getAreaName(int areaID) {
		Connection connection = null;
		Statement stmt = null;
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs = null;
		
		String areaName = "";
		
		String Query = "SELECT areaname as name FROM definedarea WHERE areaID = '"+areaID+"';";
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(Query);
			
			if (rs.next()) {
				areaName = rs.getString("name");
			}
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            
        	try
            {
            	if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		System.out.println(areaName);
		return areaName;
	}

	public static String[] getCurrentStay(int personID) {
		Connection connection = null;
		Statement stmt = null;
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		String areaName = "";
		String starttime = "";
		int areaID = 0;
		
		String Query1 = "SELECT starttime as time FROM currentstay WHERE personID = "+personID+";";
		String Query2 = "SELECT areaID as area FROM currentstay WHERE personID = "+personID+";";
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs1 = stmt.executeQuery(Query1);
			
			if (rs1.next()) {
				starttime = rs1.getString("time");
			}
			
			stmt.close();
			stmt = connection.createStatement();
			rs2 = stmt.executeQuery(Query2);
			
			if (rs2.next()) {
				areaID = rs2.getInt("area");
			}
			
			stmt.close();
			
			
			areaName = getAreaName(areaID);
		}
		
		catch( SQLException se )
        {
            /*
             * Handle errors for JDBC
             */
            se.printStackTrace();
        }
        finally
        {
            /*
             * finally block used to close resources
             */
            
        	try
            {
            	if( stmt != null )
                {
                    stmt.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }
            try
            {
                if( connection != null )
                {
                    connection.close();
                }
            }
            catch( SQLException sqlException )
            {
                sqlException.printStackTrace();
            }			
		}
		String[] returnSet = {starttime,areaName};
		System.out.println("Starttime: "+starttime+" Areaname: "+areaName);
		return returnSet;
	}

	public static int[] getUserStats(int userID) {
		int glos = 0;
		int sitTrening = 0;
		int samfundet = 0;
		int other = 0;
		int[] userStatset = {glos,sitTrening,samfundet,other};
		return userStatset;
	}
}