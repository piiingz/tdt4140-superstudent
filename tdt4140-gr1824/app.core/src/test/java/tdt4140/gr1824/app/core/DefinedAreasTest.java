package tdt4140.gr1824.app.core;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;

public class DefinedAreasTest {
	
	private Location insideGl�s = new Location(63.418177,10.403385);
	
	@Test
	public void testArea() {
		assertTrue(DefinedAreas.gl�s.inArea(insideGl�s));
	}

}
