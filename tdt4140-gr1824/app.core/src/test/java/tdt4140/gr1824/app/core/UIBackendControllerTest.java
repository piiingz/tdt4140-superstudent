package tdt4140.gr1824.app.core;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Test;


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

	
}
