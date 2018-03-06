package tdt4140.gr1824.app.core;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AreaTest {

	//This represents Gloshaugen, square shaped (simplified)
	private Location northWest = new Location(63.4305149,10.3950528);
	private Location northEast = new Location(63.4305149,10.408700);
	private Location southWest = new Location(63.415836,10.3950528);
	private Location southEast = new Location(63.415836,10.408700);
	private Area area = new Area("glos", this.northWest, this.northEast, this.southWest, this.southEast);
	private Location outsideArea = new Location(65.4305149,12.3950528);
	private Location insideArea = new Location(63.418177,10.403385);
	
	//Testing pentagonal shaped area, coordinates around Samfundet, clockwise order
	private Location pentPoint1 = new Location(63.422547, 10.394912);
	private Location pentPoint2 = new Location(63.422636, 10.395427);
	private Location pentPoint3 = new Location(63.422569, 10.396070);
	private Location pentPoint4 = new Location(63.422226, 10.395754);
	private Location pentPoint5 = new Location(63.422317, 10.395164);
	private Area pentArea = new Area("samfundet", this.pentPoint1, this.pentPoint2, this.pentPoint3, this.pentPoint4, this.pentPoint5);
	private Location outsidePentArea = new Location (63.422309, 10.395153);
	private Location insidePentArea = new Location (63.422476, 10.395416);
			
	//Testing hexagonal shaped area, coordinates around Sit Trening and anti-clockwise order
	private Location hexPoint1 = new Location(63.420813, 10.404738);
	private Location hexPoint2 = new Location(63.421076, 10.405420);
	private Location hexPoint3 = new Location(63.421266, 10.405052);
	private Location hexPoint4 = new Location(63.421217, 10.404931);
	private Location hexPoint5 = new Location(63.421302, 10.404762);
	private Location hexPoint6 = new Location(63.421100, 10.404210);
	private Area hexArea = new Area("sitTrening", this.hexPoint1, this.hexPoint2, this.hexPoint3, this.hexPoint4, this.hexPoint5, this.hexPoint6);
	private Location outsideHexArea = new Location (63.421262, 10.404921);
	private Location insideHexArea = new Location (63.421079, 10.405396);
	
	// Tests that the Area class returns correct restults for locations that are outside or inside the area.
	@Test
	public void testInSquareAreaTrue() {
		assertTrue(this.area.inArea(this.insideArea));
	}
	
	@Test
	public void testInSquareAreaFalse() {
		assertFalse(this.area.inArea(this.outsideArea));
	}
	
	@Test
	public void testInPentAreaTrue() {
		assertTrue(this.pentArea.inArea(this.insidePentArea));
	}
	
	@Test
	public void testInPentAreaFalse() {
		assertFalse(this.pentArea.inArea(this.outsidePentArea));
	}
	
	@Test
	public void testInHexAreaTrue() {
		assertTrue(this.hexArea.inArea(this.insideHexArea));
	}
	
	@Test
	public void testInHexAreaFalse() {
		assertFalse(this.hexArea.inArea(this.outsideHexArea));
	}

	@Test
	public void testNonArea() {
		// Test area that consists of only 2 points (not an area) 
		// inArea should always return false if the area consists of 2 or less locations
		Location NonAreaPoint1 = new Location(63.420813, 10.404738);
		Location NonAreaPoint2 = new Location(63.421076, 10.405420);
		Area NonArea = new Area ("NonArea", NonAreaPoint1, NonAreaPoint2);
		assertFalse(NonArea.inArea(new Location(63.00000, 10.000000)));
	}
}

