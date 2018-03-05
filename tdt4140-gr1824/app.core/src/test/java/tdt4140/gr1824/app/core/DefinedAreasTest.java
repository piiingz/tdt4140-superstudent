package tdt4140.gr1824.app.core;

import org.junit.Test;
import org.junit.Assert;;

public class DefinedAreasTest {
	
	private Location insideGlos = new Location(63.418177,10.403385);
	
	@Test
	public void testArea() {
		for (Area area : DefinedAreas.areas) {
			if (area == DefinedAreas.glos) {
				Assert.assertTrue(area.inArea(insideGlos));
			}
			else {
				Assert.assertFalse(area.inArea(insideGlos));
			}
		}
	}

}
