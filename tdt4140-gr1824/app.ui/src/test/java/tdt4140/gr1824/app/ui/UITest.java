package tdt4140.gr1824.app.ui;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class UITest extends ApplicationTest{

//	FxRobot userSimulator = new FxRobot();
//	
//	@BeforeClass
//	public static void headless() {
//		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
//			GitlabCISupport.headless();
//		}
//	}
//
//	@Override
//	public void start(Stage stage) throws Exception {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
//		Parent root = loader.load();
//		Scene scene = new Scene(root);
//		
//		stage.setTitle("JavaFX and Maven");
//		stage.setScene(scene);
//		stage.show();
//	}
//	
//	@Test
//	public void testUserButtonExists() {
//		//Did the main menu start properly?
//		Node userButton = lookup("User").query();
//		assertTrue(userButton instanceof Button);
//	}
//	
//	@Test
//	public void testSitButtonExists() {
//		//Did the main menu start properly?
//		Node sitButton = lookup("SiT").query();
//		assertTrue(sitButton instanceof Button);
//	}
//	
//	@Test
//	public void testUserButtonAndReturn() {
//		userSimulator.clickOn((Button) lookup("User").query()); //Move to UserUI
//		assertTrue((Button) lookup("Return").query() instanceof Button);
//		
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//		//Confirm that the main menu is the active scene
//		Node sitButton = lookup("SiT").query();
//		assertTrue(sitButton instanceof Button);
//		Node userButton = lookup("User").query();
//		assertTrue(userButton instanceof Button);
//	}
//	
//	@Test
//	public void testSitButtonAndReturn() {
//		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
//		assertTrue((Button) lookup("Return").query() instanceof Button);
//		
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//		
//		Node userButton = lookup("User").query();
//		assertTrue(userButton instanceof Button);
//	}
//	
//	@Test
//	public void testUserGetStats() throws InterruptedException {
//		userSimulator.clickOn((Button) lookup("User").query()); //Move to userUI
//		
//		assertTrue((TextField) lookup("#userID").query() instanceof TextField); //Test user UI
//		userSimulator.clickOn((TextField) lookup("#userID").query());
//		userSimulator.write("1");
//		assertTrue((Button) lookup("Get Stats").query() instanceof Button);
//		userSimulator.clickOn((Button) lookup("Get Stats").query());
//		assertTrue((TextField) lookup("1").query() instanceof TextField);
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//		
//		Node userButton = lookup("User").query();
//		assertTrue(userButton instanceof Button);
//	}
//	
//	
//	@Test
//	public void testSitGetStats() {
//		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
//		
//		assertTrue((Button) lookup("Get Stats").query() instanceof Button);
//		userSimulator.clickOn((Button) lookup("Get Stats").query());
//		assertTrue((Button) lookup("Return").query() instanceof Button);
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//		
//		Node userButton = lookup("User").query();
//		assertTrue(userButton instanceof Button);
//	}
//	
//	@Test
//	public void testUserCompareToggle() {
//		userSimulator.clickOn((Button) lookup("User").query()); //Move to userUI
//		
//		assertTrue((ToggleButton) lookup("Compare").query() instanceof ToggleButton);
//		userSimulator.clickOn((ToggleButton) lookup("Compare").query());
//		userSimulator.clickOn((TextField) lookup("#userID").query());
//		userSimulator.write("1");
//		assertTrue((TextField) lookup("#groupID").query() instanceof TextField);
//		userSimulator.clickOn((TextField) lookup("#groupID").query());
//		userSimulator.write("male");
//		assertTrue((TextField) lookup("male").query() instanceof TextField);
//		userSimulator.clickOn((Button) lookup("Get Stats").query());
//		
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//	}
//	
//	@Test
//	public void testSitGetStatsWithShowGroupStatsToggled() {
//		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
//		
//		assertTrue((ToggleButton) lookup("Show Group stats").query() instanceof ToggleButton);
//		userSimulator.clickOn((ToggleButton) lookup("Show Group stats").query());
//		
//		assertTrue((TextField) lookup("#groupID").query() instanceof TextField);
//		userSimulator.clickOn((TextField) lookup("#groupID").query());
//		userSimulator.write("male");
//		assertTrue((TextField) lookup("male").query() instanceof TextField);
//		
//		assertTrue((Button) lookup("Get Stats").query() instanceof Button);
//		userSimulator.clickOn((Button) lookup("Get Stats").query());
//		
//		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
//		
//	}
	
}
