package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import tdt4140.gr1824.app.db.DatabaseCommunicator;

import java.sql.Connection;
import java.sql.ResultSet;

public class DatabaseCommunicatorTest {


	@Test
	public void testGetConnection(){
	  Connection connection  = DatabaseCommunicator.getConnection();
	  assertTrue(connection != null);
	}

	@Test
	public void testCreateUser() {
		try {
			DatabaseCommunicator.createUser("testPerson", "female", 2013, "MTKOM");
			int personID = DatabaseCommunicator.getNextPersonID()-1;
			assertTrue(DatabaseCommunicator.userInDatabase(personID) == true);
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteUser() {
		try {
			int currpersonID = DatabaseCommunicator.getNextPersonID()-1;
			DatabaseCommunicator.deleteUser(currpersonID);
			assertTrue(DatabaseCommunicator.userInDatabase(currpersonID) == false);
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	@Test
	public void testAddArea(){
		try {
			int areaID = DatabaseCommunicator.getNextAreaID();
			DatabaseCommunicator.addArea("testArea");
			assertTrue(areaID == DatabaseCommunicator.getAreaID("testArea"));
		} catch (SQLException e) {
			e.printStackTrace();
	}
	}
	
	@Test
	public void testDeleteArea(){
	  try {
		  int currAreaID = DatabaseCommunicator.getNextAreaID()-1;
		  DatabaseCommunicator.deleteArea(currAreaID);
		  assertTrue(DatabaseCommunicator.getAreaName(currAreaID).isEmpty());
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	
	@Test
	public void testGetAreaName() {
		String testName = "";
		try {
			testName = DatabaseCommunicator.getAreaName(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(testName.equals("glos")); 
	}
	
	@Test
	public void testAreaID() {
		int testInt = 0;
		try {
			testInt = DatabaseCommunicator.getAreaID("glos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(testInt == 1);
	}
	
	@Test
	public void testGetUserStats() throws SQLException {
		assertTrue(DatabaseCommunicator.getUserStats(1).getClass().getComponentType() == int.class);
	}
	
	@Test
	public void testGetAllStats() throws SQLException {
		assertTrue(DatabaseCommunicator.getAllStats().getClass().getComponentType() == int.class);
	}
	
	@Test 
	public void testGroupStats() throws SQLException {
		assertTrue(DatabaseCommunicator.getGroupStats("male").getClass().getComponentType() == int.class);
		assertTrue(DatabaseCommunicator.getGroupStats("female").getClass().getComponentType() == int.class);
		assertTrue(DatabaseCommunicator.getGroupStats("other").getClass().getComponentType() == int.class);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFalseInputGroupStats() throws SQLException {
		DatabaseCommunicator.getGroupStats("None");
	}

	@Test
	public void testAddCompetition(){
		try {
			// Creating a competition in the database
			DatabaseCommunicator.addCompetition("Test name", 1, 30, "2018-04-02 00:00:00", "2018-04-06 23:59:59", "Test description", "Test prize");
			
			// Extracting the info in the newly created row in the table
			int areaID = DatabaseCommunicator.getCompetitionAreaID("Test name");
			int duration = DatabaseCommunicator.getCompetitionDuration("Test name");
			String[] dates = DatabaseCommunicator.getCompetitionDates("Test name");
			String description = DatabaseCommunicator.getCompetitionDescription("Test name");
			String prize = DatabaseCommunicator.getCompetitionPrize("Test name");
			
			DatabaseCommunicator.updateTable("DELETE from competition where competitionName = 'Test name';");
			DatabaseCommunicator.closeConnection();
			
			// Checking if the extracted info is as expected
			assertEquals(areaID, 1);
			assertEquals(duration,30);
			assertEquals(dates[0], "2018-04-02 00:00:00.0");
			assertEquals(dates[1], "2018-04-06 23:59:59.0");
			assertEquals(description, "Test description");
			assertEquals(prize, "Test prize");
						
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}

	@Test
	public void testCompetitionInDB(){
		try {
			// Creating a competition in the database
			DatabaseCommunicator.addCompetition("Test name", 1, 30, "2018-04-02 00:00:00", "2018-04-06 23:59:59", "Test description", "Test prize");
			boolean got = DatabaseCommunicator.competitionInDatabase("Test name");
			
			// Deleting the same competition
			DatabaseCommunicator.updateTable("DELETE from competition where competitionName = 'Test name';");
			DatabaseCommunicator.closeConnection();
	
			assertTrue(got);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAllCompetitionNames() {
		try {
			List<String> foundNames = DatabaseCommunicator.getAllCompetitionNames();
			List<String> actualNames = new ArrayList<String>();
			ResultSet rs = DatabaseCommunicator.getResultSet("SELECT competitionName from competition;");
			
			while (rs.next()) {
				actualNames.add(rs.getString("competitionName"));
			}	
			DatabaseCommunicator.closeConnection();
			
			// Sorting the lists to be able to compare
			Collections.sort(foundNames);
			Collections.sort(actualNames);
			
			assertEquals(foundNames, actualNames);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}



}
		

