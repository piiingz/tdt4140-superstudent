package tdt4140.gr1824.app.core;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1824.app.db.DatabaseCommunicator;

public class InterpreterTest {
	
	private Interpreter interpreter = new Interpreter();
	
	private NMEAdata data = new NMEAdata();
	
	@Before
	public void buildNMEAdata() {
		data.setId(1);
	}
	
	@Test
	public void testReceive() throws SQLException {
		data.setDegLatitude("06325.0761");
		data.setDegLongitude("01024.2219");
		interpreter.receive(data); //Dette er inni glos
		Assert.assertTrue(DatabaseCommunicator.getCurrentStay(1)[0].equals(DefinedAreas.glos.getName()));
	}
	
	
	@Test
	public void testChange() throws SQLException {
		data.setDegLatitude("632411.8");
		data.setDegLongitude("0102536.9");
		interpreter.receive(data);
		Assert.assertTrue(DatabaseCommunicator.getCurrentStay(1)[0].equals(DefinedAreas.other.getName()));
	}
	
	
	@Test
	public void testInDefinedArea() {
		Assert.assertTrue((DefinedAreas.glos == interpreter.inDefinedArea(interpreter.buildLocation("06325.0761","01024.2219"))));
	}
}