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
	
	public static void main(String[] args) {
		DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
		//databaseCommunicator.createUser();
		//databaseCommunicator.deleteUser(1);
		//databaseCommunicator.addArea("Hjemme hos Espen");
		//databaseCommunicator.deleteArea("glos");
		//databaseCommunicator.printTables();
		//databaseCommunicator.addStay();
		//System.out.println(getResultSet("SELECT * FROM person;"));
		createUser2();
	}
	
	
	public static ResultSet getResultSet(String query) {
		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
		ResultSet rs;
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = query;
			
			rs = stmt.executeQuery(sql);
			rs.close();
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

	public static void createUser2() {

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
		
		System.out.println("Here is the information you provided:\r\n"+"Fullname: "+fullname+", Gender: "+gender+", Course: "+major+", Year: "+schoolYear
				);
		
		updateTable("INSERT INTO person VALUES(18,'"+fullname+"', '"+gender+"', "+schoolYear+", '"+major+"');");
		
	}
	
	
	public static void createUser() {

		Connection connection = null;
		Statement stmt = null;
		
		DataSource dataSource = DatabaseConnection.getMySQLDataSource();
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
		
		System.out.println("Here is the information you provided:\r\n"+"Fullname: "+fullname+", Gender: "+gender+", Course: "+major+", Year: "+schoolYear
				);
		
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			String sql = "INSERT INTO person (fullname, gender, schoolyear, major) VALUES('"+fullname+"', '"+gender+"', "+schoolYear+", '"+major+"');";
			
			stmt.executeUpdate(sql);
			System.out.println("Person successfully added");
			
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
			String sql = "SELECT * FROM person;";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
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
			}
			rs.close();
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
	
}
