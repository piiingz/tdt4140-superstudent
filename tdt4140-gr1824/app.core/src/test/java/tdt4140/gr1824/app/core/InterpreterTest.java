package tdt4140.gr1824.app.core;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class InterpreterTest {
	
	private Interpreter interpreter = new Interpreter();
	
	
//	public void setup() {
//		interpreter.receive("1,06325.0761,01024.2219"); //Dette er inni glos
//	}
	

	@Test
	public void testReceive() throws SQLException {
		interpreter.receive("1,06325.0761,01024.2219"); //Dette er inni glos
		Assert.assertTrue(DatabaseCommunicator.getCurrentStay(1)[0].equals(DefinedAreas.glos.getName()));
		
		interpreter.receive("1,06325.347,01023.712");
		Assert.assertFalse(DatabaseCommunicator.getCurrentStay(1)[0].equals(DefinedAreas.glos.getName()));
		Assert.assertTrue(DatabaseCommunicator.getCurrentStay(1)[1].equals(interpreter.dateToDatetimeString(interpreter.getCurrentTime())));
		System.out.println(DatabaseCommunicator.getCurrentStay(1)[1].equals(interpreter.dateToDatetimeString(interpreter.getCurrentTime())));
	}
	
	
	@Test
	public void testChange() throws SQLException {
		interpreter.receive("1,632411.8,0102536.9");
		Assert.assertTrue(DatabaseCommunicator.getCurrentStay(1)[0].equals(DefinedAreas.other.getName()));
	}
	
	
	@Test
	public void testInDefinedArea() {
		Assert.assertTrue((DefinedAreas.glos == interpreter.inDefinedArea(interpreter.buildLocation("06325.0761","01024.2219"))));
//		Assert.assertTrue(interpreter.inDefinedArea(interpreter.buildLocation("5.18355", "166.30025")) == DefinedAreas.nowhere);
		
	}
}