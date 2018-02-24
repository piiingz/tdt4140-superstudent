package tdt4140.gr1824.app.core;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;

public class AreaTest {

	//This represents Gloshaugen
	private Location northWest = new Location(63.4305149,10.3950528);
	private Location northEast = new Location(63.4305149,10.408700);
	private Location southWest = new Location(63.415836,10.3950528);
	private Location southEast = new Location(63.415836,10.408700);
	
	private Area area = new Area(this.northWest, this.northEast, this.southWest, this.southEast);
	
	private Location outsideArea = new Location(65.4305149,12.3950528);
	private Location insideArea = new Location(63.418177,10.403385);
	
	@Test
	public void testInAreaTrue() {
		assertTrue(this.area.inArea(this.insideArea));
	}
	
	@Test
	public void testInAreaFalse() {
		assertFalse(this.area.inArea(this.outsideArea));
	}
	
	@Test
	public void testAreaConstruction() {
		assertTrue(this.area.getNorthWest() == this.northWest);
	}
}
