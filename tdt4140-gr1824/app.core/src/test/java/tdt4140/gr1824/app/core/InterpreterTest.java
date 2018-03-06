package tdt4140.gr1824.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InterpreterTest {
	
	private Interpreter interpreter = new Interpreter();
	
	@Before
	public void setup() {
		interpreter.receive("1,06325.0761,01024.2219"); //Dette er inni glos
	}
	
	@Test
	public void testReceive() {
		Assert.assertEquals(1,interpreter.getUser(1).getId());
		Assert.assertTrue(interpreter.getUser(1).getArea() == DefinedAreas.glos);
	}
	
	@Test
	public void testChange() {
		interpreter.receive("1,632411.8,0102536.9");
		Assert.assertTrue(interpreter.getUser(1).getArea() == DefinedAreas.nowhere);
	}
	
	@Test
	public void testNoChange() {
		interpreter.receive("1,06325.079,01024.092");
		Assert.assertTrue(interpreter.getUser(1).getArea() == DefinedAreas.glos);
	}

}
