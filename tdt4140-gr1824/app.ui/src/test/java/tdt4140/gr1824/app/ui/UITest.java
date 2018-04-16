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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class UITest extends ApplicationTest{

	FxRobot userSimulator = new FxRobot();
	
	@BeforeClass
	public static void headless() {
		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
			GitlabCISupport.headless();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuUI.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		stage.setTitle("JavaFX and Maven");
		stage.setScene(scene);
		stage.show();
	}
	
	@Test
	public void testUserButtonExists() {
		//Did the main menu start properly?
		Node userButton = lookup("User").query();
		assertTrue(userButton instanceof Button);
	}
	
	@Test
	public void testSitButtonExists() {
		//Did the main menu start properly?
		Node sitButton = lookup("SiT").query();
		assertTrue(sitButton instanceof Button);
	}
	
	@Test
	public void testUserButtonAndReturn() {
		userSimulator.clickOn((Button) lookup("User").query()); //Move to UserUI
		assertTrue((Button) lookup("Return").query() instanceof Button);
		
		userSimulator.clickOn((Button) lookup("Return").query()); //Return to main menu
		//Confirm that the main menu is the active scene
		Node sitButton = lookup("SiT").query();
		assertTrue(sitButton instanceof Button);
		Node userButton = lookup("User").query();
		assertTrue(userButton instanceof Button);
	}
	
	@Test
	public void testUserLogin() {
		userSimulator.clickOn((Button) lookup("User").query());
		
		assertTrue((TextField) lookup("#userID").query() instanceof TextField);
		userSimulator.clickOn((TextField) lookup("#userID").query());
		userSimulator.write("1");
		assertTrue((PasswordField) lookup("#passwordField").query() instanceof PasswordField);
		userSimulator.clickOn((PasswordField) lookup("#passwordField").query());
		userSimulator.write("password");
		assertTrue((Button) lookup("Login").query() instanceof Button);
	}
	
	@Test
	public void testSitButtonAndReturn() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		assertTrue((Button) lookup("#returnButton").query() instanceof Button);
		
		userSimulator.clickOn((Button) lookup("#returnButton").query()); //Return to main menu
		
		Node userButton = lookup("User").query();
		assertTrue(userButton instanceof Button);
	}
	
	@Test
	public void testSitPieChartView() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		assertTrue((Button) lookup("#returnButton").query() instanceof Button);
		
		userSimulator.clickOn((HBox) lookup("#piechartStat").query());
		assertTrue(((ImageView) lookup("#pieIcon").query()).isVisible());
	}
	
	@Test
	public void testProgressionView() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		assertTrue((Button) lookup("#returnButton").query() instanceof Button);
		
		userSimulator.clickOn((HBox) lookup("#progressionStat").query());
		assertTrue(((ImageView) lookup("#progressionIcon").query()).isVisible());
	}
	
	@Test
	public void testGymStatView() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		assertTrue((Button) lookup("#returnButton").query() instanceof Button);
		
		userSimulator.clickOn((HBox) lookup("#gymStat").query());
		assertTrue(((ImageView) lookup("#gymStatIcon").query()).isVisible());
	}
	
	@Test
	public void testCreateCompView() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		assertTrue((Button) lookup("#returnButton").query() instanceof Button);
		
		userSimulator.clickOn((HBox) lookup("#createComp").query());
		assertTrue(((TextField) lookup("#requiredHours").query()).isVisible());
	}
	
	@Test
	public void testClickingAroundSiT() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		
		userSimulator.clickOn((HBox) lookup("#createComp").query());
		assertTrue(((TextField) lookup("#requiredHours").query()).isVisible());
		userSimulator.clickOn((HBox) lookup("#gymStat").query());
		assertTrue(((ImageView) lookup("#gymStatIcon").query()).isVisible());
		userSimulator.clickOn((HBox) lookup("#progressionStat").query());
		assertTrue(((ImageView) lookup("#progressionIcon").query()).isVisible());
		userSimulator.clickOn((HBox) lookup("#welcomeScreen").query());
		assertTrue(((ImageView) lookup("#studCapLogo").query()).isVisible());
	}
	
	@Test
	public void testEnableGroupStats() {
		userSimulator.clickOn((Button) lookup("SiT").query()); //Move to SiTUI
		
		userSimulator.clickOn((HBox) lookup("#progressionStat").query());
		assertTrue(((ImageView) lookup("#progressionIcon").query()).isVisible());
		assertTrue(((ToggleButton) lookup("#enableGroupStats").query()).isVisible());
		userSimulator.clickOn((ToggleButton) lookup("#enableGroupStats").query());
		assertTrue(((ToggleButton) lookup("#enableGroupStats").query()).isSelected());
	}
}
