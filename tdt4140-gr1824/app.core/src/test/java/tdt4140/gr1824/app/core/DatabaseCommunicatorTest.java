package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import tdt4140.gr1824.app.db.DatabaseCommunicator;

import java.sql.Connection;

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
	
}
