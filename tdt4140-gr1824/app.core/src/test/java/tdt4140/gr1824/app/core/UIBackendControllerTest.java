package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;

import javafx.collections.ObservableList;


public class UIBackendControllerTest {
	UIBackendController backendController = new UIBackendController();
	
	@Test 
	public void getLinePointsAllTest() throws SQLException {
		assertTrue(this.backendController.getLinePointsAll(LocalDate.parse("2018-04-02"), LocalDate.parse("2018-04-02"), "glos").length == 0);
		assertTrue(this.backendController.getLinePointsAll(LocalDate.parse("2018-04-02"), LocalDate.parse("2018-06-02"), "glos").length <= 8);
	}
	
	@Test 
	public void getLinePointsGroupTest() throws SQLException {
		assertTrue(this.backendController.getLinePointsGroup("male",LocalDate.parse("2018-04-02"), LocalDate.parse("2018-04-08"), "glos").length == 1);
	}
	
	@Test 
	public void getLinePointsUserTest() throws SQLException {
		assertTrue(this.backendController.getLinePointsUser(1,LocalDate.parse("2018-04-02"), LocalDate.parse("2018-04-02"), "glos").length == 0);
		assertTrue(this.backendController.getLinePointsUser(1,LocalDate.parse("2018-04-02"), LocalDate.parse("2018-06-02"), "glos").length <= 8);
	}

	@Test
	public void testCompetitions() throws SQLException {
		ObservableList<String> allCompetitionNames = backendController.getAllCompetitionNames();
		assertTrue(!allCompetitionNames.isEmpty());
		String[] compDetails = backendController.getCompetitionDetails(allCompetitionNames.get(0));
		assertTrue(compDetails.length != 0);
		assertTrue(backendController.getWinners(allCompetitionNames.get(0)) != null);
	}
	
	@Test
	public void testNumberAtGym() throws SQLException {
		assertTrue(this.backendController.getNumberAtGym() >= 0);
	}
	
	@Test
	public void testGetStats() {
		assertTrue(backendController.getUserStats(1).length == 4);
		assertTrue(backendController.getGroupStats("male").length == 4);
		assertTrue(backendController.getAllStats().length == 4);
	}
	
	@Test
	public void testGetGoal() throws NumberFormatException, SQLException {
		assertTrue(Integer.valueOf(backendController.getGoal(1)) instanceof Integer);
	}
	
	@Test
	public void testPrettyNameToID() {
		assertTrue(this.backendController.prettyNameToId("Gloshaugen") == 1);
		assertTrue(this.backendController.prettyNameToId("SiT Trening") == 2);
		assertTrue(this.backendController.prettyNameToId("Samfundet") == 3);
		assertTrue(this.backendController.prettyNameToId("Other") == 4);
	}
	
	@Test
	public void testIDtoPrettyName() {
		assertTrue(this.backendController.idToPrettyName(1).equals("Gloshaugen"));
		assertTrue(this.backendController.idToPrettyName(2).equals("SiT Trening"));
		assertTrue(this.backendController.idToPrettyName(3).equals("Samfundet"));
		assertTrue(this.backendController.idToPrettyName(4).equals("Other"));
	}
}
